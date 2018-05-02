package edu.ccd.model.database;

import edu.ccd.contracts.Auditable;
import edu.ccd.model.ComponentHasNoNameException;

public abstract class InventoryItem implements Auditable {
    private static int current_inventory_number = 0;

    public String _name;
    public float _value;
    public int inventory_number;

    protected static void setBaseUID(int newBase) {
        current_inventory_number = newBase;
    }
    public InventoryItem() {
        inventory_number = ++current_inventory_number;
    }

    public static boolean canCreate(String name) throws ComponentHasNoNameException {
        if( name.isEmpty() ) {
            throw new ComponentHasNoNameException("Cannot create without a name.");
        }
        return true;
    }

    public InventoryItem clone(InventoryItem copyfrom) {
        this._name = copyfrom._name;
        this._value = copyfrom._value;
        this.inventory_number = copyfrom.inventory_number;
        return this;
    }

    public InventoryItem( String name ) {
        _name = name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    protected void setInventoryNumber(int uid) { this.inventory_number = uid; }

    public void setValue(float value) { this._value = value; }

    public String getName() {
        return _name;
    }

    public float getValue() {
        return _value;
    }

    public int getInventoryNumber() {
        return inventory_number;
    }

    public static void displayReportHeader() {
        System.out.println("Identity \t\t\tName \t\t\t\t\t\tValue");
    }

    public void displayDetail() {
        System.out.printf("%d\t\t\t\t\t%15s\t\t\t\t%.2f\n", inventory_number, _name, _value );
    }

    public static void displayTotalItemsInInventory() {
        System.out.printf("The total number of items is %d\n" , current_inventory_number);
    }
}

/*Demo inventory*/
/*
insert into Inventory (inventory_number, kind, name, value, serial_number) values
(1, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen Threadripper 1950X', 75, 'GZKLDCZ'),
(2, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen Threadripper 1920X', 76, 'WMAOJAX'),
(3, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen Threadripper 1900X', 77, 'UBQDQST'),
(4, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i9-7900X', 78, 'INHCPYF'),
(5, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i9-7920X', 79, 'UNJRHYV'),
(6, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i9-7940X', 80, 'QTUURRV'),
(7, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i9-7960X', 81, 'XZHCSZJ'),
(8, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i9-7980XE', 82, 'CAPONSY'),
(9, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen 7 1800X', 83, 'YRGUKEL'),
(10, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen 7 1700X', 84, 'FXZZFCL'),
(11, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen 7 Pro 1700X', 85, 'UASTLRV'),
(12, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen 7 1700', 86, 'FTEIKVD'),
(13, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen 7 Pro 1700', 87, 'IUAXKIS'),
(14, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i7-8700K', 88, 'QDAKZCS'),
(15, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i7-8700', 89, 'JZZZHRM'),
(16, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i7-7740X', 90, 'ALHHKDB'),
(17, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i7-7700K', 91, 'LCZZCDR'),
(18, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i7-7700', 92, 'PBEZRLN'),
(19, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i7-7700T', 93, 'BDPFCWV'),
(20, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen 5 1600X', 94, 'OVESGGC'),
(21, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen 5 1600', 95, 'CQJMWCJ'),
(22, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen 5 Pro 1600', 96, 'OJHHSEP'),
(23, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen 5 1500X', 97, 'JYDIOII'),
(24, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen 5 Pro 1500', 98, 'HLGIMSI'),
(25, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen 5 1400', 99, 'MQIZPJZ'),
(26, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i5-8600K', 50, 'NWAKZKJ'),
(27, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i5-8400', 51, 'EMEBVXU'),
(28, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i5-7640X', 52, 'RDNBKGD'),
(29, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i5-7600K', 53, 'EASGDSN'),
(30, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i5-7600', 54, 'YGFDZMH'),
(31, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i5-7600T', 55, 'FADLQOD'),
(32, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i5-7500', 56, 'VLZOAGU'),
(33, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i5-7500T', 57, 'EEOZSZB'),
(34, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i5-7400', 58, 'QMULPHI'),
(35, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i5-7400T', 59, 'EAVRSQQ'),
(36, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen 3 1300X', 60, 'ULHQFWB'),
(37, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen 3 Pro 1300', 61, 'RLCUINE'),
(38, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen 3 1200', 62, 'WRZXSCE'),
(39, 'edu.ccd.model.inventoryitems.CPU', 'Ryzen 3 Pro 1200', 63, 'RLEEIDI'),
(40, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i3-8350K', 64, 'DZASFVF'),
(41, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i3-8100', 65, 'FYUSKCM'),
(42, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i3-7350K', 66, 'YKYGOZV'),
(43, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i3-7320', 67, 'SAQLGDG'),
(44, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i3-7300', 68, 'GPSQWNL'),
(45, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i3-7300T', 69, 'WNINCMR'),
(46, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i3-7100', 70, 'MFEFPUL'),
(47, 'edu.ccd.model.inventoryitems.CPU', 'Intel Core i3-7100T', 71, 'JSAQGWW'),
(48, 'edu.ccd.model.inventoryitems.CPU', 'AMD Athlon X4 970', 25, 'TBNCYRH'),
(49, 'edu.ccd.model.inventoryitems.CPU', 'AMD Athlon X4 950', 26, 'LATYZTE'),
(50, 'edu.ccd.model.inventoryitems.CPU', 'AMD Athlon X4 940', 27, 'DZKMZRN'),
(51, 'edu.ccd.model.inventoryitems.CPU', 'AMD A12-9800', 28, 'UPAUFDL'),
(52, 'edu.ccd.model.inventoryitems.CPU', 'AMD A12-9800E', 29, 'WFBRURQ'),
(53, 'edu.ccd.model.inventoryitems.CPU', 'AMD A10-9700', 30, 'IJYRIBD'),
(54, 'edu.ccd.model.inventoryitems.CPU', 'AMD A10-9700E', 31, 'QKOAFUU'),
(55, 'edu.ccd.model.inventoryitems.CPU', 'AMD A8-9600', 32, 'OJZNFML'),
(56, 'edu.ccd.model.inventoryitems.CPU', 'AMD A6-9550', 33, 'JTZASNJ'),
(57, 'edu.ccd.model.inventoryitems.CPU', 'AMD A6-9500', 34, 'CRTAYQF'),
(58, 'edu.ccd.model.inventoryitems.CPU', 'AMD A6-9500E', 35, 'IABZQPV'),
(59, 'edu.ccd.model.inventoryitems.CPU', 'Intel Pentium G4620', 36, 'VZLKHBD'),
(60, 'edu.ccd.model.inventoryitems.CPU', 'Intel Pentium G4600', 37, 'PPZBEBM'),
(61, 'edu.ccd.model.inventoryitems.CPU', 'Intel Pentium G4600T', 38, 'BCFBSLO'),
(62, 'edu.ccd.model.inventoryitems.CPU', 'Intel Pentium G4560', 39, 'QRGGELD'),
(63, 'edu.ccd.model.inventoryitems.CPU', 'Intel Pentium G4560T', 40, 'ZWDITTA'),
(64, 'edu.ccd.model.inventoryitems.CPU', 'Intel Celeron G3950', 41, 'KQLKOVS'),
(65, 'edu.ccd.model.inventoryitems.CPU', 'Intel Celeron G3930', 42, 'CXJWSOH'),
(66, 'edu.ccd.model.inventoryitems.CPU', 'Intel Celeron G3930T', 43, 'DRNITZL'),
(67, 'edu.ccd.model.inventoryitems.Keyboard', 'Atreus', 100, '0'),
(68, 'edu.ccd.model.inventoryitems.Keyboard', 'Code Keyboard', 123, '0'),
(69, 'edu.ccd.model.inventoryitems.Keyboard', 'Cooler Master Storm', 142, '0'),
(70, 'edu.ccd.model.inventoryitems.Keyboard', 'Das Keyboard', 141, '0'),
(71, 'edu.ccd.model.inventoryitems.Keyboard', 'Ducky Mini', 152, '0'),
(72, 'edu.ccd.model.inventoryitems.Keyboard', 'Ducky Shine', 145, '0'),
(73, 'edu.ccd.model.inventoryitems.Keyboard', 'Happy Hacking Keyboard', 165, '0'),
(74, 'edu.ccd.model.inventoryitems.Keyboard', 'IBM Model M', 187, '0'),
(75, 'edu.ccd.model.inventoryitems.Keyboard', 'Logitech G10 Orion', 145, '0'),
(76, 'edu.ccd.model.inventoryitems.Keyboard', 'Matias Tactile Pro', 133, '0'),
(77, 'edu.ccd.model.inventoryitems.Keyboard', 'Razer Black Widow Chroma', 176, '0'),
(78, 'edu.ccd.model.inventoryitems.Keyboard', 'Realforce', 134, '0'),
(79, 'edu.ccd.model.inventoryitems.Keyboard', 'Unicomp', 154, '0'),
(80, 'edu.ccd.model.inventoryitems.Keyboard', 'Vortex POK3R', 135, '0'),
(81, 'edu.ccd.model.inventoryitems.Keyboard', 'WASD V2', 144, '0'),
(82, 'edu.ccd.model.inventoryitems.Monitor', 'Acer Predator XB252Q', 353, 'IKVHGOC'),
(83, 'edu.ccd.model.inventoryitems.Monitor', 'Acer Predator XB272', 234, 'IELXKOK'),
(84, 'edu.ccd.model.inventoryitems.Monitor', 'ASUS ROG PG258Q', 345, 'CZZZRDQ'),
(85, 'edu.ccd.model.inventoryitems.Monitor', 'AOC AGON AG251FG', 234, 'CMWOFVW'),
(86, 'edu.ccd.model.inventoryitems.Monitor', 'Dell Alienware AW2518H', 123, 'NYYLTSX'),
(87, 'edu.ccd.model.inventoryitems.Monitor', 'Acer Predator Z301C', 124, 'VGZTQPE'),
(88, 'edu.ccd.model.inventoryitems.Monitor', 'Acer Predator Z35', 345, 'WQRUPFH'),
(89, 'edu.ccd.model.inventoryitems.Monitor', 'Acer Predator XB241H', 343, 'PNGJMAG'),
(90, 'edu.ccd.model.inventoryitems.Monitor', 'ASUS ROG PG248Q', 345, 'XETMUFN'),
(91, 'edu.ccd.model.inventoryitems.Monitor', 'Acer Predator XB241YU', 65, 'PEYVSHW'),
(92, 'edu.ccd.model.inventoryitems.Monitor', 'LG 32GK850G', 42, 'TVXCACN'),
(93, 'edu.ccd.model.inventoryitems.Monitor', 'Acer Predator XB271HU', 123, 'ZIPNHFJ'),
(94, 'edu.ccd.model.inventoryitems.Monitor', 'AOC AG271QG', 435, 'WEIKEOS'),
(95, 'edu.ccd.model.inventoryitems.Monitor', 'AOC AGON AG241QG', 124, 'IIWZUWV'),
(96, 'edu.ccd.model.inventoryitems.Monitor', 'ASUS ROG PG278QR', 564, 'DYYZTJT'),
(97, 'edu.ccd.model.inventoryitems.Monitor', 'ASUS ROG PG279Q', 234, 'QTEYCAN'),
(98, 'edu.ccd.model.inventoryitems.Monitor', 'Dell S2417DG', 765, 'TLBVCLP'),
(99, 'edu.ccd.model.inventoryitems.Monitor', 'Viewsonic XG2703-GS', 23, 'DNGTAEO'),
(100, 'edu.ccd.model.inventoryitems.Monitor', 'Acer Predator XB270HU', 123, 'BGDMRKV'),
(101, 'edu.ccd.model.inventoryitems.Monitor', 'ASUS ROG PG278Q', 645, 'MAQAJUQ'),
(102, 'edu.ccd.model.inventoryitems.Monitor', 'Dell S2716DG', 767, 'ZYNWACO'),
(103, 'edu.ccd.model.inventoryitems.Monitor', 'Acer Predator XB270H', 45, 'NLAGFKT'),
(104, 'edu.ccd.model.inventoryitems.Monitor', 'Acer XB240H', 856, 'WUZAZUK'),
(105, 'edu.ccd.model.inventoryitems.Monitor', 'AOC G2460PG', 34, 'XZJSILR'),
(106, 'edu.ccd.model.inventoryitems.Monitor', 'Philips 272G5DYEB', 344, 'IFAYNOH'),
(107, 'edu.ccd.model.inventoryitems.Mouse', 'Logitech MX1100 Cordless', 123, '0'),
(108, 'edu.ccd.model.inventoryitems.Mouse', 'Microsoft Explorer Mouse', 99, '0'),
(109, 'edu.ccd.model.inventoryitems.Mouse', 'Logitech MX Revolution', 123, '0'),
(110, 'edu.ccd.model.inventoryitems.Mouse', 'Wireless Laser Mouse 6000', 50, '0'),
(111, 'edu.ccd.model.inventoryitems.Mouse', 'Wireless Laser Mouse 7000', 100, '0'),
(112, 'edu.ccd.model.inventoryitems.Mouse', 'Wireless Laser Mouse 8000', 150, '0'),
(113, 'edu.ccd.model.inventoryitems.Mouse', 'Logitech G9 Laser Gaming', 120, '0'),
(114, 'edu.ccd.model.inventoryitems.Mouse', 'Microsoft SideWinder', 32, '0'),
(115, 'edu.ccd.model.inventoryitems.Mouse', 'Logitech G5', 231, '0'),
(116, 'edu.ccd.model.inventoryitems.Mouse', 'Razer DeathAdder', 67, '0'),
(117, 'edu.ccd.model.inventoryitems.Mouse', 'Ideazon Reaper Edge', 45, '0');
 */
