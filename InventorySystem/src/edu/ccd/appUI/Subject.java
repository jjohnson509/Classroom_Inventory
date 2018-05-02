package edu.ccd.appUI;

import java.util.ArrayList;

public class Subject {
    ArrayList<Observer> observerArrayList = new ArrayList<>();

    boolean hint = false;

    public void register(Observer newObserver) {
        observerArrayList.add(newObserver);
    }

    public void deregister(Observer oldObserver) {
        observerArrayList.remove(oldObserver);
    }

    public void update() {
        hint = !hint;
        for(Observer observer : observerArrayList)
            observer.update(this);
    }
}
