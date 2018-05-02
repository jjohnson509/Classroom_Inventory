package edu.ccd;
import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;

import edu.ccd.appUI.Login;
import edu.ccd.appUI.Subject;
import edu.ccd.appUI.ConcreteObserver;
import edu.ccd.model.SerializedItem;
import edu.ccd.model.database.InvalidUserOrNoPermissionException;
import edu.ccd.model.database.InventoryDatabaseMySQL;
import edu.ccd.model.database.InventoryItem;
import edu.ccd.model.security.SecurityContext;

import javax.swing.JTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.rmi.server.UID;
import java.util.ArrayList;


public class MainWindow extends JFrame implements ActionListener {

    class kindsList {
        String kind;
        String description;
    }


    private JPanel mainpanel;
    private JLabel userLabel;
    private JTextField username;
    private JLabel dropdownLabel;
    private JComboBox kinds;
    private InventoryDatabaseMySQL idb = new InventoryDatabaseMySQL();

    //new components
    private JTable table;
    private JLabel uidLabel;
    private JTextField uid;
    private JLabel nameLabel;
    private JTextField name;
    private JLabel valueLabel;
    private JTextField value;
    private JButton editButton;
    private JButton deleteButton;
    private JButton addButton;
    private JButton changeUser;
    private JLabel whichLabel;
    private JList<ArrayList> whichhole;
    private JComboBox<String> which;
    private JLabel serialLabel;
    private JTextField serialNumber;
    private DefaultTableModel model;

    private ArrayList<InventoryItem> whichContext = new ArrayList<InventoryItem>();
    private ArrayList<kindsList> kindListOptions = new ArrayList<kindsList>();

    private boolean DEBUG = false;

    private SecurityContext applicationSecurityContext = new SecurityContext();

    public static MainWindow the = null;



    public void setApplicationSecurityContext(String name, String token) {
        applicationSecurityContext.setSecurityContext(name, token);
        username.setText(name);
    }

    private MainWindow() {
        initComponents();
    }

    public static MainWindow the() {
        if (the == null) {
            the = new MainWindow();
        }
        return the;
    }

    public InventoryDatabaseMySQL getIdb() {
        return idb;
    }

    private void initComponents() {
        int _leftside = 10;
        int _rightside = 100;
        int _top = 10;
        int _labelwidth = 110;
        int _height = 25;

        setTitle("Inventory System");
        setSize(_rightside * 8, 750);
        mainpanel = new JPanel();
        mainpanel.setSize(_rightside * 7, 750);
        mainpanel.setLayout(null);
        add(mainpanel);

        userLabel = new JLabel("User");
        userLabel.setBounds(_leftside, _top, _labelwidth, _height);
        mainpanel.add(userLabel);

        username = new JTextField(20);
        username.setEnabled(false);
        username.setBounds(_rightside, _top, _labelwidth * 2, _height);
        mainpanel.add(username);

        dropdownLabel = new JLabel("Kinds");
        dropdownLabel.setBounds(_leftside, _top += 30, _labelwidth, _height);
        mainpanel.add(dropdownLabel);

        kinds = new JComboBox<String>();
        kinds.setBounds(_rightside, _top, _labelwidth * 2, _height);
        kinds.addActionListener(this);
        mainpanel.add(kinds);

        whichLabel = new JLabel("Which");
        whichLabel.setBounds(_leftside, _top += 30, _labelwidth, _height);
        mainpanel.add(whichLabel);

        which = new JComboBox<String>();
        //which.setBounds(_rightside, _top, _labelwidth * 2, _height * 2);
        which.addActionListener(this);
        mainpanel.add(which);

        uidLabel = new JLabel("UID");
        uidLabel.setBounds(_leftside, _top += 30, _labelwidth, _height);
        mainpanel.add(uidLabel);

        uid = new JTextField();
        uid.setEnabled(false);
        uid.setBounds(_rightside, _top, _labelwidth * 2, _height);
        mainpanel.add(uid);

        nameLabel = new JLabel("Name");
        nameLabel.setBounds(_leftside, _top += 30, _labelwidth, _height);
        mainpanel.add(nameLabel);

        name = new JTextField(20);
        name.setBounds(_rightside, _top, _labelwidth * 2, _height);
        mainpanel.add(name);

        valueLabel = new JLabel("Value");
        valueLabel.setBounds(_leftside, _top += 30, _labelwidth, _height);
        mainpanel.add(valueLabel);

        value = new JTextField(20);
        value.setBounds(_rightside, _top, _labelwidth * 2, _height);
        mainpanel.add(value);

        serialLabel = new JLabel("Serial");
        serialLabel.setBounds(_leftside, _top += 30, _labelwidth, _height);
        mainpanel.add(serialLabel);

        serialNumber = new JTextField(20);
        serialNumber.setBounds(_rightside, _top, _labelwidth * 2, _height);
        mainpanel.add(serialNumber);

        addButton = new JButton("Add");
        addButton.setBounds(_leftside, _top += 60, _labelwidth, _height);
        addButton.addActionListener(this);
        mainpanel.add(addButton);

        editButton = new JButton("Edit");
        editButton.setBounds(_leftside + _labelwidth + 10, _top, _labelwidth, _height);
        editButton.addActionListener(this);
        mainpanel.add(editButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(_leftside + (_labelwidth + 10) * 2, _top, _labelwidth + 10, _height);
        deleteButton.addActionListener(this);
        mainpanel.add(deleteButton);

        changeUser = new JButton("Change User");
        changeUser.setBounds(350 , 13, _labelwidth, _height-5);
        changeUser.addActionListener(this);
        mainpanel.add(changeUser);




        String[] columnNames = {"Kind","Name","Value", "Serial Number"};
        table = new JTable();
        model = new DefaultTableModel()
        {
            public boolean isCellEditable(int row,int column){
                if(column == 0) return false;
                return true;
            }
        };
        model.setColumnIdentifiers(columnNames);
        table.setModel(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(_leftside/2,_top+50,_labelwidth*7, _height*15);
        System.out.println(table.isCellEditable(1,0));
        table.setAutoCreateRowSorter(true);
        table.setSelectionBackground(Color.ORANGE);

        mainpanel.add(scrollPane);


        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        ;
    }





    public void loadKinds() {
        try {
            for (String akind : idb.getInventoryKinds()) {
                kindsList item = new kindsList();
                item.description = akind.substring(akind.lastIndexOf(".") + 1).trim();
                item.kind = akind;
                kindListOptions.add(item);

                kinds.addItem(item.description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void applyOperationalPermissions() {
        uidLabel.setVisible(false);
        uid.setVisible(false);
        addButton.setEnabled(idb.getUserRole() != null && idb.getUserRole().canAdd());
        editButton.setEnabled(idb.getUserRole() != null && idb.getUserRole().canEdit());
        deleteButton.setEnabled(idb.getUserRole() != null && idb.getUserRole().canDelete());
    }

    public static void main(String[] args) {
        new Login(MainWindow.the().getIdb());
        MainWindow.the().applyOperationalPermissions();
        MainWindow.the().loadKinds();

        Subject enableCheckmark = new Subject();

        ConcreteObserver uiElement1 = new ConcreteObserver();
        enableCheckmark.register(uiElement1);
        ConcreteObserver uiElement2 = new ConcreteObserver();
        enableCheckmark.register(uiElement2);
        ConcreteObserver uiElement3 = new ConcreteObserver();
        enableCheckmark.register(uiElement3);


        enableCheckmark.update();



        /*try {
            new Configuration().writeConfig("This is what should show up in the dialog.");
            NotificationDialog.the().displayNotification(new Configuration().readConfig());
        } catch (IOException e) {
            System.out.println("Could not find a config file.");
        }*/

        System.out.println("End program.");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton && e.getSource().equals(addButton)) {
            try {
                Object item = Class.forName(kindListOptions.get(kinds.getSelectedIndex()).kind).getDeclaredConstructor().newInstance();
                ((InventoryItem) item).setName(name.getText());
                ((InventoryItem) item).setValue(Float.parseFloat(value.getText()));


                if (item instanceof SerializedItem) {
                    ((SerializedItem) item).setSerialnumber(serialNumber.getText());
                }
                try {
                    MainWindow.the().getIdb().AddInventoryItem(((InventoryItem) item));
                } catch (InvalidUserOrNoPermissionException e1) {
                    e1.printStackTrace();
                }
//todo: Create add success window
                System.out.print("UID:" + ((InventoryItem) item).getInventoryNumber() + " Name:" + ((InventoryItem) item).getName() + " Value:" + ((InventoryItem) item).getValue());
                if (item instanceof SerializedItem) {
                    System.out.println("Serial number:" + ((SerializedItem) item).getSerialnumber());
                } else {
                    System.out.println();
                }
                try {
                    idb.AddInventoryItem(((InventoryItem) item));
                } catch (InvalidUserOrNoPermissionException e1) {
                    e1.printStackTrace();

                }
            }catch(Exception me){
                me.printStackTrace();
            }
        }
        if (e.getSource() instanceof JButton && e.getSource().equals(changeUser)) {
            new Login(MainWindow.the().getIdb());
            MainWindow.the().applyOperationalPermissions();
            MainWindow.the().loadKinds();
        }
        if (e.getSource() instanceof JButton && e.getSource().equals(editButton)) {
            //todo: This is not sensitive to SerializedItems like the which handler below.
            try {
                Object returnme = Class.forName(kindListOptions.get(kinds.getSelectedIndex()).kind).getDeclaredConstructor().newInstance();
                ((InventoryItem) returnme).setName(name.getText());
                ((InventoryItem) returnme).setValue(Float.parseFloat(value.getText()));
                MainWindow.the().getIdb().EditInventoryItem(Integer.parseInt(uid.getText()), ((InventoryItem) returnme));
            } catch (Exception me) {
                me.printStackTrace();
            }
        }

        if (e.getSource() instanceof JButton && e.getSource().equals(deleteButton)) {
            int i = table.getSelectedRow();
            model.removeRow(i);
            try {
                idb.DeleteInventoryItem(whichContext.get(i).inventory_number);
            } catch (InvalidUserOrNoPermissionException e1) {
                e1.printStackTrace();
            }



        }
        if (e.getSource() instanceof JComboBox && e.getSource().equals(kinds)) {
            try {
                //Clear any items from the last selection of "kind"
                which.removeAllItems();
                whichContext.removeAll(whichContext);

                for (InventoryItem row : MainWindow.the().getIdb().getAllInventoryOfKind(kindListOptions.get(kinds.getSelectedIndex()).kind)) {

                    whichContext.add(row);
                    which.addItem(row.getName());

                }
            } catch (Exception catchall) {
                catchall.printStackTrace();
            }
        }
        if (e.getSource() instanceof JComboBox && e.getSource().equals(which)) {
            try {
                if (((JComboBox) e.getSource()).getSelectedIndex() >= 0) {
                    InventoryItem row = whichContext.get(which.getSelectedIndex());
                    //uid.setText(String.valueOf(row.getInventoryNumber()));
                    // name.setText(row.getName());
                    // value.setText(String.valueOf(row.getValue()));
                    if (row instanceof SerializedItem) {
                        serialLabel.setVisible(true);
                        serialNumber.setVisible(true);
                        // serialNumber.setText(((SerializedItem) row).getSerialnumber());
                    } else {
                        serialLabel.setVisible(false);
                        serialNumber.setVisible(false);
                        serialNumber.setText("");
                    }
                    addRowtoTable(row);
                }


            } catch (Exception catchAll2) {
                catchAll2.printStackTrace();
            }
        }

    }

    public void addRowtoTable(InventoryItem row) throws InvalidUserOrNoPermissionException {
        model.setRowCount(0);
        for (InventoryItem rows : MainWindow.the().getIdb().getAllInventoryOfKind(kindListOptions.get(kinds.getSelectedIndex()).kind)) {
            whichContext.add(rows);
            String serial;
            String kind = kindListOptions.get(kinds.getSelectedIndex()).kind;
            kind = kind.substring(kind.lastIndexOf(".") + 1).trim();
            if (row instanceof SerializedItem) {
                serial = ((SerializedItem) rows).getSerialnumber();
            }
            else {
                serial = null;
            }
            Object[] thisRow = {kind , rows.getName(), rows.getValue(),serial};
            model.addRow(thisRow);

        }
    }











}




