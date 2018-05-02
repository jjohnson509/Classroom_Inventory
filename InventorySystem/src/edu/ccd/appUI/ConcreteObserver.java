package edu.ccd.appUI;

public class ConcreteObserver implements Observer {
    @Override
    public void update(Subject subject) {
        System.out.println("I was told the state of my subject is " + subject.hint );
    }
}
