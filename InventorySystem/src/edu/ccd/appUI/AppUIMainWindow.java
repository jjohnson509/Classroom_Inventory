package edu.ccd.appUI;

import edu.ccd.model.database.InvalidUserOrNoPermissionException;
import edu.ccd.model.database.InventoryItem;

import java.util.ArrayList;

public interface AppUIMainWindow {
    public ArrayList<String> getInventoryKinds() throws InvalidUserOrNoPermissionException;
    //public ArrayList<InventoryItem> getInventoryItemByName(String look4name) throws InvalidUserOrNoPermissionException;
}
