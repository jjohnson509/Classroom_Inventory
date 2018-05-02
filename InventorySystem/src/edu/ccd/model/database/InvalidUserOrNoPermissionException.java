package edu.ccd.model.database;

public class InvalidUserOrNoPermissionException extends Exception{
    public InvalidUserOrNoPermissionException(String context) { super(context); }
}
