package edu.ccd.model.database;

import edu.ccd.appUI.AppUIMainWindow;
import edu.ccd.contracts.InventoryDatabaseInterface;
import edu.ccd.model.security.Role;
import edu.ccd.model.SerializedItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDatabaseMySQL implements InventoryDatabaseInterface, AppUIMainWindow {
    //todo: The Inventory number static needs to be updated if the items are drawn from the database.

    private Connection conn = null;
    private Role userRole = null;

    public Role getUserRole() {
        return userRole;
    }

    private Connection getConnection () {
        if(conn == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                return DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/Inventory?useSSL=false", "root", "password"
                );
            } catch (Exception any) {
                any.printStackTrace();
            }
            primeInventory();
        }
        return conn;
    }

    public int getRoleSize() {
        try {
            ResultSet results = getConnection().createStatement().executeQuery(
                    "SELECT COUNT(*) FROM Roles;"
            );
            while (results.next())
                return /*results.getInt(1);*/ results.getInt("uid");
        } catch (Exception any) {
            any.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean AddRole(Role addrole) {
        try {
            getConnection().createStatement().executeUpdate(
                    "INSERT INTO Roles (uid, rolename, targetname, `permissions-view`, `permissions-add`, `permissions-delete`, `permissions-edit`, `permissions-reload`)" +
                    " VALUES (" + addrole.getUid() +
                    ", '" + addrole.getRolename() +
                    "', '" + addrole.getTargetname() +
                    "', " + (addrole.canView()?"1":"0") +
                    ", " + (addrole.canAdd()?"1":"0") +
                    ", " + (addrole.canDelete()?"1":"0") +
                    ", " + (addrole.canEdit()?"1":"0") +
                    ", " + (addrole.canReload()?"1":"0") + ");"
            );
        } catch (SQLException any) {
            any.printStackTrace();
        }

        return true;
    }

    @Override
    public Role ViewRole(int uid) {
        Role returnme = null;
        try {
            ResultSet results = getConnection().createStatement().executeQuery(
                    "SELECT * FROM Roles WHERE uid=" + uid + ";"
            );
            while (results.next()) {
                returnme = Role.cloneRole(
                        /*results.getInt(1),*/      results.getInt("uid"),
                        /*results.getString(2),*/   results.getString("rolename"),
                        /*results.getString(3),*/   results.getString("targetname"),
                        /*results.getBoolean(4),*/  results.getBoolean("permissions-view"),
                        /*results.getBoolean(5),*/  results.getBoolean("permissions-add"),
                        /*results.getBoolean(6),*/  results.getBoolean("permissions-delete"),
                        /*results.getBoolean(7),*/  results.getBoolean("permissions-edit"),
                        /*results.getBoolean(8)*/   results.getBoolean("permissions-reload")
                );
            }
        } catch (Exception any) {
            any.printStackTrace();
        }
        return returnme;
    }

    public boolean Login(String username) {
        userRole = null;
        try {
            ResultSet results = getConnection().createStatement().executeQuery(
                    "SELECT * FROM Roles WHERE rolename='" + username + "';"
            );
            while (results.next()) {
                userRole = Role.cloneRole(
                        /*results.getInt(1),*/      results.getInt("uid"),
                        /*results.getString(2),*/   results.getString("rolename"),
                        /*results.getString(3),*/   results.getString("targetname"),
                        /*results.getBoolean(4),*/  results.getBoolean("permissions-view"),
                        /*results.getBoolean(5),*/  results.getBoolean("permissions-add"),
                        /*results.getBoolean(6),*/  results.getBoolean("permissions-delete"),
                        /*results.getBoolean(7),*/  results.getBoolean("permissions-edit"),
                        /*results.getBoolean(8)*/   results.getBoolean("permissions-reload")
                );
            }
        } catch (Exception any) {
            any.printStackTrace();
        }
        //todo: what if Audit gets called with a null user or op?
        Audit(userRole, "Login()");
        return (userRole != null);

    }

    @Override
    public boolean DeleteRole(int uid) {
        try {
            getConnection().createStatement().executeUpdate(
                    "DELETE FROM Roles WHERE uid=" + uid + ";"
            );
        } catch (SQLException any) {
            any.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean EditRole(int replaceme, Role replacewith) {
        return DeleteRole(replaceme) && AddRole(replacewith);
    }

    @Override
    public Role ReloadRole(int uid) {
        return ViewRole(uid);
    }

    @Override
    public boolean AddInventoryItem(InventoryItem inv_item) throws InvalidUserOrNoPermissionException {
        if(userRole == null || !userRole.canAdd() ) throw new InvalidUserOrNoPermissionException(this.getClass().getName());
        if (ViewInventoryItem(inv_item.getInventoryNumber())!=null) {
            return false;
        }
        try {
            getConnection().createStatement().executeUpdate(
                    "INSERT INTO Inventory (inventory_number, kind, name, value, serial_number)" +
                            " VALUES (" + inv_item.getInventoryNumber() +
                            ", '" + inv_item.getClass().getName() +
                            "', '" + inv_item.getName() +
                            "', " + inv_item.getValue() +
                            (inv_item instanceof SerializedItem ?", '" +((SerializedItem)inv_item).getSerialnumber()+"');":", '0');"));
        } catch (SQLException any) {
            any.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean DeleteInventoryItem(int uid) throws InvalidUserOrNoPermissionException{
        if(userRole == null || !userRole.canDelete() ) throw new InvalidUserOrNoPermissionException(this.getClass().getName());
        try {
            getConnection().createStatement().executeUpdate(
                    "DELETE FROM Inventory WHERE inventory_number=" + uid + ";"
            );
        } catch (SQLException any) {
            any.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean EditInventoryItem(int replaceme, InventoryItem replacewith) throws InvalidUserOrNoPermissionException {
        if(userRole == null || !userRole.canEdit() ) throw new InvalidUserOrNoPermissionException(this.getClass().getName());
        InventoryItem replacement = ViewInventoryItem(replaceme).clone(replacewith);
        return DeleteInventoryItem(replaceme) && AddInventoryItem(replacement);
    }

    @Override
    public InventoryItem ViewInventoryItem(int uid) throws InvalidUserOrNoPermissionException {
        if(userRole == null || !userRole.canView() ) throw new InvalidUserOrNoPermissionException(this.getClass().getName());
        Object returnme = null;
        try {
            ResultSet results = getConnection().createStatement().executeQuery(
                    "SELECT * FROM Inventory WHERE inventory_number=" + uid + ";"
            );
            while (results.next()) {
                returnme = Class.forName(results.getString("kind")).getDeclaredConstructor().newInstance();
                ((InventoryItem)returnme).setInventoryNumber(results.getInt("inventory_number"));
                ((InventoryItem)returnme).setName(results.getString("name"));
                ((InventoryItem)returnme).setValue(results.getFloat("value"));
                if (returnme instanceof SerializedItem)
                    ((SerializedItem)returnme).setSerialnumber(results.getString("serial_number"));
            }
        } catch (Exception any) {
            any.printStackTrace();
        }
        return ((InventoryItem)returnme);
    }

    @Override
    public InventoryItem ReloadInventoryItem(int uid) throws InvalidUserOrNoPermissionException {
        if(userRole == null || !userRole.canReload() ) throw new InvalidUserOrNoPermissionException(this.getClass().getName());
        return ReloadInventoryItem(uid);
    }

    public void showAllInInventory() throws InvalidUserOrNoPermissionException{
        if(userRole == null || !userRole.canView() ) throw new InvalidUserOrNoPermissionException(this.getClass().getName());
        try {
            ResultSet results = getConnection().createStatement().executeQuery(
                    "SELECT * FROM Inventory;"
            );
            System.out.println("ID   Kind                      Name                 Value      Serial");
            System.out.println("---  ------------------------- -------------------- ---------- ----------------");
            while (results.next()) {
                System.out.printf("%2d   %-25s %-20s $%,9.2f %s%n",
                results.getInt("inventory_number"), results.getString("kind"), results.getString("name"),
                results.getFloat("value"),results.getString("serial_number"));
            }
        } catch (Exception any) {
            any.printStackTrace();
        }
    }

    public ArrayList<InventoryItem> getAllInventory() throws InvalidUserOrNoPermissionException{
        if(userRole == null || !userRole.canView() ) throw new InvalidUserOrNoPermissionException(this.getClass().getName());

        ArrayList<InventoryItem> returnArray = new ArrayList<InventoryItem>();
        Object returnme = null;
        try {
            ResultSet results = getConnection().createStatement().executeQuery(
                    "SELECT * FROM Inventory;"
            );
            while (results.next()) {
                returnme = Class.forName(results.getString("kind")).getDeclaredConstructor().newInstance();
                ((InventoryItem)returnme).setInventoryNumber(results.getInt("inventory_number"));
                ((InventoryItem)returnme).setName(results.getString("name"));
                ((InventoryItem)returnme).setValue(results.getFloat("value"));
                if (returnme instanceof SerializedItem)
                    ((SerializedItem)returnme).setSerialnumber(results.getString("serial_number"));
                returnArray.add((InventoryItem)returnme);
            }
        } catch (Exception any) {
            any.printStackTrace();
        }
        return returnArray;
    }

    public ArrayList<InventoryItem> getAllInventoryOfKind(String look4kind) throws InvalidUserOrNoPermissionException {
        if(userRole == null || !userRole.canView() ) throw new InvalidUserOrNoPermissionException(this.getClass().getName());

        ArrayList<InventoryItem> returnArray = new ArrayList<InventoryItem>();
        Object returnme = null;
        try {
            ResultSet results = getConnection().createStatement().executeQuery(
                    "SELECT * FROM Inventory WHERE kind = '"+look4kind+"';"
            );
            while (results.next()) {
                returnme = Class.forName(results.getString("kind")).getDeclaredConstructor().newInstance();
                ((InventoryItem)returnme).setInventoryNumber(results.getInt("inventory_number"));
                ((InventoryItem)returnme).setName(results.getString("name"));
                ((InventoryItem)returnme).setValue(results.getFloat("value"));
                if (returnme instanceof SerializedItem)
                    ((SerializedItem)returnme).setSerialnumber(results.getString("serial_number"));
                returnArray.add((InventoryItem)returnme);
            }
        } catch (Exception any) {
            any.printStackTrace();
        }
        return returnArray;
    }

    @Override
    public ArrayList<String> getInventoryKinds() throws InvalidUserOrNoPermissionException {
        if(userRole == null || !userRole.canView() ) throw new InvalidUserOrNoPermissionException(this.getClass().getName());

        ArrayList<String> returnArray = new ArrayList<String>();
        Object returnme = null;
        try {
            ResultSet results = getConnection().createStatement().executeQuery(
                    /*"SELECT DISTINCT SUBSTRING_INDEX(kind,\".\",-1) as kinds from Inventory;"*/
                    "SELECT DISTINCT kind as kinds from Inventory;"
            );
            while (results.next()) {
                returnArray.add(results.getString("kinds"));
            }
        } catch (Exception any) {
            any.printStackTrace();
        }
        return returnArray;
    }

    /*
    public ArrayList<InventoryItem> getInventoryItemByName(String look4name) throws InvalidUserOrNoPermissionException {
        if(userRole == null || !userRole.canView() ) throw new InvalidUserOrNoPermissionException(this.getClass().getName());

        //todo: it is possible that two names might match, and we need logic to take care of that.

        ArrayList<InventoryItem> returnArray = new ArrayList<InventoryItem>();
        Object returnme = null;
        try {
            ResultSet results = getConnection().createStatement().executeQuery(
                    "SELECT * FROM Inventory WHERE name = '"+look4name+"';"
            );
            while (results.next()) {
                returnme = Class.forName(results.getString("kind")).getDeclaredConstructor().newInstance();
                ((InventoryItem)returnme).setInventoryNumber(results.getInt("inventory_number"));
                ((InventoryItem)returnme).setName(results.getString("name"));
                ((InventoryItem)returnme).setValue(results.getFloat("value"));
                if (returnme instanceof SerializedItem)
                    ((SerializedItem)returnme).setSerialnumber(results.getString("serial_number"));
                returnArray.add((InventoryItem)returnme);
            }
        } catch (Exception any) {
            any.printStackTrace();
        }
        return returnArray;
    }
    */

    public boolean Audit(Role auditRole, String operation) {
        try {
            getConnection().createStatement().executeUpdate(
                    "INSERT INTO Audit ( mod_date, userid, operation)" +
                            " VALUES (NOW(), '" + auditRole.getRolename() + "' , '" + operation +  "');"
            );
        } catch (SQLException any) {
            any.printStackTrace();
        }

        return true;
    }

    public void primeInventory() {
        int maxInventoryNumber=1;
        try {
            ResultSet results = getConnection().createStatement().executeQuery(
                    "SELECT max(uid) as UID FROM Inventory;"
            );
            while (results.next()) {
                InventoryItem.setBaseUID(results.getInt(1));
            }
        } catch (Exception any) {
            any.printStackTrace();
        }
    }

}
