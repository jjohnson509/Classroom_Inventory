package edu.ccd.textUI;

import edu.ccd.contracts.Notifier;

public class Trace implements Notifier {

    public static Trace the = null;

    private Trace() {}

    public static Trace the() {
        if (the == null) {
            the = new Trace();
        }
        return the;
    }

    @Override
    public void displayNotification(String note) {
        System.out.println("Trace: " + note);
    }
}
