package edu.ccd.model.inventoryitems;

import edu.ccd.model.SerializedItem;

public class Monitor extends SerializedItem {

    public Monitor() {};

    public Monitor(String name, float value) {
        _name = name;
        _value = value;
    }

    public Monitor clone(Monitor copyfrom) {
        this._name = copyfrom._name;
        this._value = copyfrom._value;
        this.inventory_number = copyfrom.inventory_number;
        this.setSerialnumber(copyfrom.getSerialnumber());
        return this;
    }
}
