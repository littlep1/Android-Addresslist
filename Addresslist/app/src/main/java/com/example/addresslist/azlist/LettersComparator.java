package com.example.addresslist.azlist;

import com.example.addresslist.Person;

import java.util.Comparator;

public class LettersComparator implements Comparator<Person> {
    public int compare(Person o1, Person o2) {
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return 1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return -1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
