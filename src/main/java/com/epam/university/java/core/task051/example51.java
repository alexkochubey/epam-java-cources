package com.epam.university.java.core.task051;




import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

    /**
     * Main class for "date-time task 1":
     */
    public class example51 {

        /**
         * Constants.
         */
        private static final int HOURS_OF_OPENING = 8;

        private static final int NUM_2017 = 2017;
        private static final int NUM_4 = 4;
        private static final int NUM_14 = 14;
        private static final int NUM_22 = 22;

        private static final String WORK_SCHEDULE_FULL = "Today bank works from 8 a.m. till 7 p.m.";
        private static final String WORK_SCHEDULE_HALF = "Today bank works from 8 a.m. till 3 p.m.";
        private static final String WORK_SCHEDULE_NOT_WORKING = "Today bank is closed.";
        private static final String TIME_LEFT = "Time left: ";

        private static final Locale enUs = new Locale("en-US");
        private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

        /**
         * Private constructor.
         */
        private Main() {
            //empty
        }

        /**
         * Main function that tests written functionality.
         *
         * @param args Command line arguments.
         */
        public static void main(String[] args) {
            //First variant of creating LocalDateTime object (using PM/AM and formatter).
            final LocalDateTime test1 = parseDate("Feb 3 2017  12:05 PM");
            final LocalDateTime test2 = parseDate("Feb 2 2017  08:00 PM");

            //We can also create object using familiar time format (like 14:22).
            //Before output, it will be converted to 12 hour format.
            final LocalDateTime test3 = LocalDateTime.of(NUM_2017, Month.FEBRUARY, NUM_4, NUM_14, NUM_22);

            final LocalDateTime test4 = parseDate("Feb 5 2017  02:25 PM");
            final LocalDateTime test5 = parseDate("Feb 28 2017  02:45 PM");
            final LocalDateTime test6 = parseDate("Feb 3 2017  07:59 AM");
            final LocalDateTime test7 = parseDate("Feb 3 2017  08:00 AM");
            final LocalDateTime test8 = parseDate("Feb 3 2017  08:01 AM");

            bankScheduleAtDay(test1);
            bankScheduleAtDay(test2);
            bankScheduleAtDay(test3);
            bankScheduleAtDay(test4);
            bankScheduleAtDay(test5);
            bankScheduleAtDay(test6);
            bankScheduleAtDay(test7);
            bankScheduleAtDay(test8);

        }

        /**
         * Parses LocalDateTime object using String and formatter with 12 hour format.
         *
         * @param input String date according to formatter.
         * @return LocalDateTime parsed object.
         */
        private static LocalDateTime parseDate(String input) {
            try {
                final DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern("MMM d yyyy  hh:mm a", enUs);
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException exc) {
                Main.LOGGER.info("%s is not parsable!%n", input);
                // Rethrow the exception.
                throw exc;
            }
        }

        /**
         * Function that gives information about banks' schedule at given day.
         * It also writes how much time left till the bank will be closed (if it still working).
         *
         * @param date LocalDateTime object.
         */
        public static void bankScheduleAtDay(LocalDateTime date) {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy  hh:mm a", enUs);
            final String formatDateTime = date.format(formatter);
            Main.LOGGER.info("Given time: " + formatDateTime + " (day - " + date.getDayOfWeek() + ")\n");

            LocalDateTime lastWorkingDayOfTheMonth = date.with(TemporalAdjusters.lastDayOfMonth());

            if (lastWorkingDayOfTheMonth.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    lastWorkingDayOfTheMonth.getDayOfWeek() == DayOfWeek.SUNDAY) {

                lastWorkingDayOfTheMonth = date.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
            }

            String scheduleThatDay;

            if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                scheduleThatDay = WORK_SCHEDULE_NOT_WORKING;

            } else if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                    || date.getDayOfMonth() == lastWorkingDayOfTheMonth.getDayOfMonth()) {
                scheduleThatDay = WORK_SCHEDULE_HALF;
                final LocalDateTime dateSchedule = date.withHour(15).withMinute(0).withSecond(0);
                Main.LOGGER.info(TIME_LEFT + getTimeLeft(date, dateSchedule) + "\n");

            } else {
                scheduleThatDay = WORK_SCHEDULE_FULL;
                final LocalDateTime dateSchedule = date.withHour(19).withMinute(0).withSecond(0);
                Main.LOGGER.info(TIME_LEFT + getTimeLeft(date, dateSchedule) + "\n");
            }

            Main.LOGGER.info(scheduleThatDay + "\n\n");
        }

        /**
         * Calculates how much time left till the bank will be closed.
         * And finds out if it still working.
         *
         * @param givenDate LocalDateTime object.
         * @param scheduleDate LocalDateTime object.
         * @return String with information about how much time left.
         */
        private static String getTimeLeft(LocalDateTime givenDate, LocalDateTime scheduleDate) {
            if (ChronoUnit.MINUTES.between(givenDate, scheduleDate) <= 0) {
                return "The bank is closed already.";
            }
            final long numberOfHours = ChronoUnit.HOURS.between(givenDate, scheduleDate);
            final long numberOfMinutes = ChronoUnit.MINUTES.between(givenDate, scheduleDate)
                    - TimeUnit.HOURS.toMinutes(numberOfHours);

            if (numberOfHours > (scheduleDate.getHour() - HOURS_OF_OPENING) ||
                    ((numberOfHours == (scheduleDate.getHour() - HOURS_OF_OPENING)) && numberOfMinutes != 0)) {
                return "[!] The bank has not yet opened.";
            }
            return String.format("%d hour(s) %d min(s)", numberOfHours, numberOfMinutes);
        }

    }



}
