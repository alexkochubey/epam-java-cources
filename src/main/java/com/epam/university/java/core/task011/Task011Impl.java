package com.epam.university.java.core.task011;

import java.util.ArrayList;
import java.util.LinkedList;


public class Task011Impl implements Task011 {

    @Override
    public String getLastName(String[] collection) {
        if (collection == null
                || collection.length == 0) {
            throw new IllegalArgumentException();
        }
        int goneCounter = 0;
        int pointer = 0;
        if (collection.length == 1) {
            return collection[0];
        }
        do {
            collection[pointer] = "gone";
            goneCounter++;
            while (collection[pointer].equals("gone")) {
                pointer = (pointer + 1) % collection.length;
            }

            pointer = (pointer + 1) % collection.length;

            while (collection[pointer].equals("gone")) {
                pointer = (pointer + 1) % collection.length;
            }
        } while (goneCounter < collection.length - 1);
        return collection[pointer];
    }


    @Override
    public String getLastName(ArrayList<String> collection) {
        if (collection == null
                || collection.size() == 0) {
            throw new IllegalArgumentException();
        }

        int goneCounter = 0;
        int pointer = 0;

        if (collection.size() == 1) {
            return collection.get(0);
        }

        do {
            collection.set(pointer, "gone");
            goneCounter++;
            while (collection.get(pointer).equals("gone")) {
                pointer = (pointer + 1) % collection.size();
            }

            pointer = (pointer + 1) % collection.size();

            while (collection.get(pointer).equals("gone")) {
                pointer = (pointer + 1) % collection.size();
            }
        } while (goneCounter < collection.size() - 1);


        return collection.get(pointer);
    }


    @Override
    public String getLastName(LinkedList<String> collection) {
        if (collection == null
                || collection.size() == 0) {
            throw new IllegalArgumentException();
        }
        if (collection.size() == 1) {
            return collection.getFirst();
        }
        boolean previousStayed = true;
        do {
            if (previousStayed) {
                collection.removeFirst();
                previousStayed = false;
            } else {
                collection.addLast(collection.getFirst());
                collection.removeFirst();
                previousStayed = true;
            }
        } while (collection.size() > 1);

        return collection.getFirst();
    }
}