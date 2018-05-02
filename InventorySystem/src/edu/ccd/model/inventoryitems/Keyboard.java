package edu.ccd.model.inventoryitems;

import edu.ccd.model.database.InventoryItem;

public class Keyboard extends InventoryItem {
    public Keyboard() {

    }

    public Keyboard clone(Keyboard copyfrom) {
        this._name = copyfrom._name;
        this._value = copyfrom._value;
        this.inventory_number = copyfrom.inventory_number;
        return this;
    }

    public Keyboard(String name, float value) {
        _name = name;
        _value = value;
    }

    public void displayDetail() {
        System.out.println(0 + "\t\t\t\t\t" + _name + "\t\t\t\t" + _value);
    }
}
