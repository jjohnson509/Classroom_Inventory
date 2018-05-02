package edu.ccd.contracts;

import edu.ccd.model.database.InvalidUserOrNoPermissionException;
import edu.ccd.model.database.InventoryItem;
import edu.ccd.model.security.Role;

public interface InventoryDatabaseInterface {
    Role ViewRole(int uid);
    boolean AddRole(Role addrole);
    boolean DeleteRole(int uid);
    boolean EditRole(int replaceme, Role replacewith);
    Role ReloadRole(int uid);

    InventoryItem ViewInventoryItem(int uid) throws InvalidUserOrNoPermissionException;
    boolean AddInventoryItem(InventoryItem inv_item) throws InvalidUserOrNoPermissionException;
    boolean DeleteInventoryItem(int uid) throws InvalidUserOrNoPermissionException;
    boolean EditInventoryItem(int replaceme, InventoryItem replacewith) throws InvalidUserOrNoPermissionException;
    InventoryItem ReloadInventoryItem(int uid) throws InvalidUserOrNoPermissionException;
}
