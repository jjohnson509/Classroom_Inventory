package edu.ccd;

import edu.ccd.appUI.Login;
import edu.ccd.appUI.Subject;
import edu.ccd.appUI.ConcreteObserver;
import edu.ccd.model.SerializedItem;
import edu.ccd.model.database.InventoryDatabaseMySQL;
import edu.ccd.model.database.InventoryItem;
import edu.ccd.model.security.SecurityContext;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.EventListener;

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
    private JComboBox<String> which;
    private JLabel serialLabel;
    private JTextField serialNumber;

    private ArrayList<InventoryItem> whichContext = new ArrayList<InventoryItem>();
    private ArrayList<kindsList> kindListOptions = new ArrayList<kindsList>();

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
        setSize(_rightside * 4, 450);
        mainpanel = new JPanel();
        mainpanel.setSize(_rightside * 4, 450);
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
        which.setBounds(_rightside, _top, _labelwidth * 2, _height);
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
        changeUser.setBounds(_rightside - (_labelwidth / 4), _top += 30, _labelwidth * 2, _height);
        changeUser.addActionListener(this);
        mainpanel.add(changeUser);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
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
            //todo: How do we add an item?
            InventoryItem item = whichContext.get(which.getSelectedIndex());
            System.out.print("UID:" + item.getInventoryNumber() + " Name:" + item.getName() + " Value:" + item.getValue());
            if (item instanceof SerializedItem) {
                System.out.println("Serial number:" + ((SerializedItem) item).getSerialnumber());
            } else {
                System.out.println();
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
                    uid.setText(String.valueOf(row.getInventoryNumber()));
                    name.setText(row.getName());
                    value.setText(String.valueOf(row.getValue()));
                    if (row instanceof SerializedItem) {
                        serialLabel.setVisible(true);
                        serialNumber.setVisible(true);
                        serialNumber.setText(((SerializedItem) row).getSerialnumber());
                    } else {
                        serialLabel.setVisible(false);
                        serialNumber.setVisible(false);
                        serialNumber.setText("");
                    }
                }
            } catch (Exception catchAll2) {
                catchAll2.printStackTrace();
            }
        }

    }
}

