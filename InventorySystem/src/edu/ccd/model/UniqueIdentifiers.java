package edu.ccd.model;

public class UniqueIdentifiers {
    private static long uid = 0;
    private static UniqueIdentifiers the = null;

    private UniqueIdentifiers() { }

    public static UniqueIdentifiers the() {
        if(the == null) {
            the = new UniqueIdentifiers();
        }
        return the;
    }

    public long generateUID () {
        return ++uid;
    }
}
