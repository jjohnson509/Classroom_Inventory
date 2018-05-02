package edu.ccd.model.inventoryitems;

import edu.ccd.model.DuplicateItemException;
import edu.ccd.model.database.InventoryItem;
import edu.ccd.model.inventoryitems.CPU;
import edu.ccd.model.inventoryitems.Keyboard;
import edu.ccd.model.inventoryitems.Monitor;
import edu.ccd.model.inventoryitems.Mouse;

import java.util.ArrayList;
import java.util.Iterator;

public class Workstation {
    private ArrayList<InventoryItem> workstationComponents = new ArrayList<InventoryItem>();

    public Workstation() {}

    public Workstation(CPU aCPU, Monitor aMonitor, Keyboard aKeyboard, Mouse aMouse) {
        workstationComponents.add(aCPU);
        workstationComponents.add(aMonitor);
        workstationComponents.add(aKeyboard);
        workstationComponents.add(aMouse);
    }

    public void addInventoryItem(InventoryItem _addme) throws DuplicateItemException {
        int monitorcount = 0;
        Iterator<InventoryItem> ptr = workstationComponents.iterator();
        while(ptr.hasNext()) {
            InventoryItem focus = ptr.next();
            if( focus instanceof CPU)
                throw new DuplicateItemException("Too many CPUs.");
            if( focus instanceof  Monitor) {
                if(++monitorcount > 1)
                    throw new DuplicateItemException("Too many monitors.");
            }
            if( focus instanceof  Keyboard)
                throw new DuplicateItemException("Too many keyboards.");
            if( focus instanceof  Mouse)
                throw new DuplicateItemException("Too many mice.");
        }
        workstationComponents.add(_addme);
    }
}
