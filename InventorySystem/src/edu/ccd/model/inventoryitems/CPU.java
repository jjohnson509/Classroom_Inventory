package edu.ccd.model.inventoryitems;

import edu.ccd.model.SerializedItem;
import edu.ccd.model.database.InventoryItem;

public class CPU extends SerializedItem {

    public CPU() {};

    public CPU(String name, float value) {
        if(value < 1) {
            _value = 1;
        } else {
            _value = value;
        }
        _name = name;
    }

    public CPU clone(CPU copyfrom) {
        this._name = copyfrom._name;
        this._value = copyfrom._value;
        this.inventory_number = copyfrom.inventory_number;
        this.setSerialnumber(copyfrom.getSerialnumber());
        return this;
    }
}
