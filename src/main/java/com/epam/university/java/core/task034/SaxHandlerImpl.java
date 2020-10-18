package com.epam.university.java.core.task034;

import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.Collection;

public class SaxHandlerImpl extends SaxHandler {
    private static final String FIRST = "first-name";
    private static final String LAST = "last-name";
    private static final String PHONE = "person-phone";
    private static final String PERSON = "person";

    private PersonImpl person;
    private Collection<PhoneNumber> phoneNumbers;
    private String elementValue;

    @Override
    public void characters(char[] ch, int start, int length) {
        elementValue = new String(ch, start, length);
    }

    @Override
    public void startDocument() {
        person = new PersonImpl();
        phoneNumbers = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) {
        if (PERSON.equals(qName)) {
            person.setId(Integer.parseInt(attr.getValue("id")));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case FIRST:
                person.setFirstName(elementValue);
                break;
            case LAST:
                person.setLastName(elementValue);
                break;
            case PHONE:
                PhoneNumberImpl phoneNumber = new PhoneNumberImpl();
                phoneNumber.setPhoneNumber(elementValue);
                phoneNumbers.add(phoneNumber);
                break;
            default:
                break;
        }
    }

    @Override
    public void endDocument() {
        person.setPhoneNumbers(phoneNumbers);
    }

    public PersonImpl getPerson() {
        return person;
    }
}
