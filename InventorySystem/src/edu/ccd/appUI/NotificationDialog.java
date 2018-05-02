package edu.ccd.appUI;

import edu.ccd.contracts.Notifier;

import javax.swing.JOptionPane;

public class NotificationDialog implements Notifier {

    public static NotificationDialog the = null;
    private boolean quietmode = false;

    private NotificationDialog() {}

    public static NotificationDialog the() {
        if (the == null) {
            the = new NotificationDialog();
        }
        return the;
    }

    public boolean toggleQuietMode() {
        quietmode = !quietmode;
        return quietmode;
    }

    @Override
    public void displayNotification(String note) {
        if(!quietmode)
            JOptionPane.showMessageDialog(null,note);
    }
}
