package edu.ccd.model.inventoryitems;

import edu.ccd.model.database.InventoryItem;

public class Mouse extends InventoryItem {

    public Mouse() {};

    public Mouse clone(Mouse copyfrom) {
        this._name = copyfrom._name;
        this._value = copyfrom._value;
        this.inventory_number = copyfrom.inventory_number;
        return this;
    }
}
