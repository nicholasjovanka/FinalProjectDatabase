package DB;


import Controller.*;

import java.sql.*;

public class DB {
    public static String user = "root";
    public String pass = "";
    public String link = "localhost:3306";
    public String url = "jdbc:mysql://" + link + "/bookstore";
    public Connection conn;
    public ResultSet rs;
    public Statement stm;
    public String query;

    public void connectDB() throws SQLException {
        conn = DriverManager.getConnection(url, user, pass);
        stm = conn.createStatement();
    }


    public String next() {
        try {
            rs.next();
            // set column index
            return rs.getString(1);
        } catch (Exception e) {
            return "NULL";
        }
    }

    //Supplier DB COMMANDS
    public void getSupplierTable() throws SQLException { //Gets The Supplier Table
        this.query = "Select * from Supplier";
        rs = stm.executeQuery(query);
    }

    public void InsertSupplier(String SupplierIdst, String Suppliernamest) throws SQLException { //Use to Insert a new Supplier
        if (SupplierIdst.isEmpty()) {
            String query = "Insert into Supplier(Supplier_name)Values(?)";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setString(1, Suppliernamest);
                pst.executeUpdate();
            } catch (SQLException e) {
                SupplierController.alertMessage((e.toString()));
                e.printStackTrace();
            }
        } else {
            try {
                int SupplierId = Integer.parseInt(SupplierIdst);
                String query = "Insert into Supplier(Supplier_Id,Supplier_name)Values(?,?)";
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setInt(1, SupplierId);
                    pst.setString(2, Suppliernamest);
                    pst.executeUpdate();
                } catch (SQLException e) {
                    SupplierController.alertMessage((e.toString()));
                    e.printStackTrace();
                }
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                SupplierController.alertMessage(nfe.toString());
            }
        }

    }

    public void DeleteSupplier(int id) { //Used to Delete a supplier
        String query = "Delete from Supplier where Supplier_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            SupplierController.alertMessage((e.toString()));
            e.printStackTrace();
        }
    }

    public void UpdateSupplier(Supplier suppliervar, String SupplierId, String SupplierName) { //used to update a supplier
        String Name = suppliervar.getSupplierName(); //Get the original supplier name
        int Id = suppliervar.getSupplierId(); //Get the original Supplier Id
        if (!SupplierId.isEmpty()) { //Check if the Supplier Id is filled out by the user
            try {
                Id = Integer.parseInt(SupplierId);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                SupplierController.alertMessage(nfe.toString());
            }
        }
        if (!SupplierName.isEmpty()) {//Check if the Supplier Name is filled out by the user
            Name = SupplierName;
        }
        String updatequery = "Update Supplier SET Supplier_Id=?, Supplier_name=? Where Supplier_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(updatequery);) {
            pst.setInt(1, Id);
            pst.setString(2, Name);
            pst.setInt(3, suppliervar.getSupplierId());
            pst.executeUpdate();
        } catch (SQLException e) {
            SupplierController.alertMessage((e.toString()));
            e.printStackTrace();
        }

    }

    //Transaction_Type DB COMMANDS
    public void getTransactionTypeTable() throws SQLException { //Get The transaction Type Table;
        this.query = "Select * from Transaction_Type";
        rs = stm.executeQuery(query);
    }

    public void InsertTransactionType(String Id, String Type) { //Insert to the transaction type table
        if (Id.isEmpty()) {
            String query = "Insert into Transaction_Type(Transaction_Type)Values(?)";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setString(1, Type);
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                Transaction_Type_Controller.alertMessage(e.toString());

            }
        } else {
            try {
                int TypeId = Integer.parseInt(Id);
                String query = "Insert into Transaction_Type(Id,Transaction_Type)Values(?,?)";
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setInt(1, TypeId);
                    pst.setString(2, Type);
                    pst.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Transaction_Type_Controller.alertMessage(e.toString());
                }
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                Transaction_Type_Controller.alertMessage(nfe.toString());
            }
        }

    }

    public void DeleteTransactionType(int id) { //delete query for transaction type table
        String query = "Delete from Transaction_Type where Transaction_Type_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            Transaction_Type_Controller.alertMessage(e.toString());
        }
    }


    //ITEM DB COMMANDS
    public void getStoreId() throws  SQLException{
        this.query="Select Store_Id from Stores";
        rs=stm.executeQuery(query);
    }
    public void getItemDB(String type) throws SQLException { //Gets the item table where it will be ordered based on the selected type;
        this.query = "Select * from Item ORDER BY " + type;
        rs = stm.executeQuery(query);
    }

    public void ItemBasedOn(String type) throws SQLException {     //Gets all the distinct attribute from the  item table row;
        if (type != "Item_Id") {
            this.query = "Select DISTINCT " + type + " from Item";
            rs = stm.executeQuery(query);
        }
    }

    public void AutoIncrementItem(String update){ //Used to update the auto increment
    try{
        int updatevalue=Integer.parseInt(update);
        String query="Alter Table Item Auto_Increment=?";
        try(PreparedStatement pst = conn.prepareStatement(query)){
        pst.setInt(1,updatevalue);
        pst.executeUpdate();
        }catch(SQLException e){
            ItemController.alertMessage(e.toString());
        }
    } catch (NumberFormatException nfe) {
        System.out.println("NumberFormatException: " + nfe.getMessage());
        ItemController.alertMessage(nfe.toString());
    }
    }

    public void ItemView(String type, String item) throws SQLException { //Gets the table based on the distinct item selected
        if (item == "None") {
            getItemDB(type);
        } else if (type == "Category") {
            String query = "Select * from Item where Category='" + item + "'";
            rs = stm.executeQuery(query);
        } else if (type == "Item_Name") {
            String query = "Select * from Item where Item_Name='" + item + "'";
            rs = stm.executeQuery(query);

        } else if (type == "Price_Per_Item") {
            String query = "Select * from Item where Price_Per_Item=" + item;
            rs = stm.executeQuery(query);

        } else if (type == "Quantity") {
            String query = "Select * from Item where Quantity=" + item;
            rs = stm.executeQuery(query);
        }
        else if (type == "Store_Id") {
            String query = "Select * from Item where Store_Id=" + item;
            rs = stm.executeQuery(query);
        }
    }

    public void InsertItem(String Id, String Category, String Name, String Price, String Quantity,String storeid) { //Used for Inserting an item
        if (Id.isEmpty()) {
            String query = "Insert into Item(Category,Item_Name,Price_Per_Item,Quantity,Store_Id)Values(?,?,?,?,?)";
            try {
                int price = Integer.parseInt(Price);
                int quantity = Integer.parseInt(Quantity);
                int stid=Integer.parseInt(storeid);
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setString(1, Category);
                    pst.setString(2, Name);
                    pst.setInt(3, price);
                    pst.setInt(4, quantity);
                    pst.setInt(5,stid);
                    pst.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    ItemController.alertMessage(e.toString());
                }
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                ItemController.alertMessage(nfe.toString());
            }
        } else {
            String query = "Insert into Item(Item_Id,Category,Item_Name,Price_Per_Item,Quantity,Store_Id)Values(?,?,?,?,?,?)";
            try {
                int id = Integer.parseInt(Id);
                int price = Integer.parseInt(Price);
                int quantity = Integer.parseInt(Quantity);
                int stid=Integer.parseInt(storeid);
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setInt(1, id);
                    pst.setString(2, Category);
                    pst.setString(3, Name);
                    pst.setInt(4, price);
                    pst.setInt(5, quantity);
                    pst.setInt(6,stid);
                    pst.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    ItemController.alertMessage(e.toString());
                }
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                ItemController.alertMessage(nfe.toString());
            }
        }
    }

    public void DeleteItem(int id) { //Used to delete the selected item
        String query = "Delete from Item where Item_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            Transaction_Type_Controller.alertMessage(e.toString());
        }
    }

    public void UpdateItem(Item itemvar, String Id, String Category, String Name, String Price, String Quantity,String storeid) { //Used to update The selected item
        int id = itemvar.getItemId();
        String itemcategory = itemvar.getCategory();
        String itemname = itemvar.getItemName();
        int price = itemvar.getPricePerItem();
        int quantity = itemvar.getQuantity();
        int StoreId= itemvar.getStoreId();
        if (!Id.isEmpty()) {
            try {
                id = Integer.parseInt(Id);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                ItemController.alertMessage(nfe.toString());
            }
        }
        if (!Category.isEmpty()) {
            itemcategory = Category;
        }
        if (!Name.isEmpty()) {
            itemname = Name;
        }
        if (!Price.isEmpty()) {
            try {
                price = Integer.parseInt(Price);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                ItemController.alertMessage(nfe.toString());
            }
        }
        if (!Quantity.isEmpty()) {
            try {
                quantity = Integer.parseInt(Quantity);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                ItemController.alertMessage(nfe.toString());
            }
        }
        if (!storeid.isEmpty()) {
            try {
                StoreId = Integer.parseInt(storeid);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                ItemController.alertMessage(nfe.toString());
            }
        }
        String updatequery = "Update Item SET Item_Id=?, Category=?, Item_Name=?,Price_Per_Item=?,Quantity=?,Store_Id=? Where Item_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(updatequery)) {
            pst.setInt(1, id);
            pst.setString(2, itemcategory);
            pst.setString(3, itemname);
            pst.setInt(4, price);
            pst.setInt(5, quantity);
            pst.setInt(6,StoreId);
            pst.setInt(7, itemvar.getItemId());
            pst.executeUpdate();
        } catch (SQLException e) {
            ItemController.alertMessage(e.toString());
        }
    }

    //Transaction DB COMMANDS
    public String getItemName(String itemid) throws SQLException{
        String name="";
        this.query="Select Item_Name from Item Where Item_Id="+itemid;
        rs=stm.executeQuery(query);
        while(rs.next()){
            name=rs.getString("Item_Name");
        }
        return name;
    }


    public String getItemPrice(String itemid) throws SQLException{
        String price="";
        this.query="Select Price_Per_Item from Item Where Item_Id="+itemid;
        rs=stm.executeQuery(query);
        while(rs.next()){
            price=rs.getString("Price_Per_Item");
        }
        return price;

    }
    public void getTransactionDB(String type) throws SQLException { //Get the Inner Join Table for Transaction
        this.query = "select t.Transaction_Id,t.Item_Id,i.Item_Name,i.Category,i.Price_Per_Item,t.Transaction_Type_Id,tt.Transaction_Type,t.Date,t.Supplier_Id,s.Supplier_name,t.Quantity,t.Store_Id,t.Store_Id2 from Transaction t left join Item i on t.Item_Id=i.Item_Id left join Transaction_Type tt on tt.Transaction_Type_Id=t.Transaction_Type_Id left join Supplier s on s.Supplier_Id=t.Supplier_Id ORDER BY " + type;
        rs = stm.executeQuery(query);
    }
    public void GetitemId() throws SQLException { //Gets all the row from Item used for the Item choicebox
        this.query = "Select * from Item";
        rs = stm.executeQuery(query);
    }

    public void gettransactionType() throws SQLException { //Gets all the row from Transaction used for the transaction choicebox
        this.query = "Select * from Transaction_Type";
        rs = stm.executeQuery(query);
    }

    public void getSupplier() throws SQLException { //Gets all the row from supplier  used for the supplier choicebox
        this.query = "Select * from  Supplier";
        rs = stm.executeQuery(query);
    }

    public String getstoreid(String itemid) throws SQLException {
        String storeid="";
        this.query="Select Store_Id from Item Where Item_Id="+itemid;
        rs=stm.executeQuery(query);
        while(rs.next()){
            storeid=String.valueOf(rs.getInt("Store_Id"));
        }
        return storeid;

    }

    public int getItemStock(String itemid,String StoreId) throws SQLException { //Function to get stock for item which is used to check the buy and sell transaction inserted
        int originalstock = 0;
        this.query = "Select Quantity from Item Where Item_Id=" + itemid+" and Store_Id="+StoreId;
        rs = stm.executeQuery(query);
        while (rs.next()) {
            originalstock = rs.getInt("Quantity");
        }
        return originalstock;

    }

    public void updateStockequation(int qty, String priceperitem,String StoreId,String itemname) { //Update the specific item quantity in the item table
        this.query = "Update Item Set Quantity=Quantity+"+qty+" Where  Price_Per_Item="+priceperitem+" and Store_Id="+StoreId+" and Item_Name='"+itemname+"'";
        try {
            stm.executeUpdate(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
            TransactionController.alertMessage(e.toString());
        }
    }


    public void TransactionBasedOn(String type) throws SQLException { //Get all the distinct attribute based on the Type selected
        if (type == "Item_Name") {
            this.query = "select DISTINCT " + "i.Item_Name" + " from Transaction t left join Item i on t.Item_Id=i.Item_Id ";
            rs = stm.executeQuery(query);
        } else if (type == "Price_Per_Item") {
            this.query = "select DISTINCT " + "i.Price_Per_Item" + " from Transaction t left join Item i on t.Item_Id=i.Item_Id ";
            rs = stm.executeQuery(query);
        } else if (type == "Transaction_Type") {
            this.query = "select DISTINCT " + "tt.Transaction_Type" + " from Transaction t left join Transaction_Type tt on tt.Transaction_Type_Id=t.Transaction_Type_Id ";
            rs = stm.executeQuery(query);
        } else if (type == "Date") {
            this.query = "select DISTINCT " + "t.Date" + " from Transaction t ";
            rs = stm.executeQuery(query);
        } else if (type == "Supplier_name") {
            this.query = "select DISTINCT " + "s.Supplier_name" + " from Transaction t left join Supplier s on s.Supplier_Id=t.Supplier_Id";
            rs = stm.executeQuery(query);
        } else if (type == "Quantity") {
            this.query = "select DISTINCT " + "t.Quantity" + " from Transaction t left join Item i on t.Item_Id=i.Item_Id ";
            rs = stm.executeQuery(query);
        }
        else if (type == "Store_Id") {
            this.query = "select DISTINCT " + "t.Store_Id" + " from Transaction t  ";
            rs = stm.executeQuery(query);
        }else if (type == "Category") {
            this.query = "select DISTINCT " + "i.Category" + " from Transaction t left join Item i on t.Item_Id=i.Item_Id ";
            rs = stm.executeQuery(query);
        }

    }

    public void TransactionView(String type, String item) throws SQLException { //get all the row based on the distinct value selected
        if (item == "None") {
            getTransactionDB(type);
        } else if (type == "Item_Name") {
            this.query = "select t.Transaction_Id,t.Item_Id,i.Item_Name,i.Category,i.Price_Per_Item,t.Transaction_Type_Id,tt.Transaction_Type,t.Date,t.Supplier_Id,s.Supplier_name,t.Quantity,t.Store_Id,t.Store_Id2 from Transaction t left join Item i on t.Item_Id=i.Item_Id left join Transaction_Type tt on tt.Transaction_Type_Id=t.Transaction_Type_Id left join Supplier s on s.Supplier_Id=t.Supplier_Id Where i.Item_Name='" + item + "' ";
            rs = stm.executeQuery(query);
        } else if (type == "Price_Per_Item") {
            this.query = "select t.Transaction_Id,t.Item_Id,i.Item_Name,i.Category,i.Price_Per_Item,t.Transaction_Type_Id,tt.Transaction_Type,t.Date,t.Supplier_Id,s.Supplier_name,t.Quantity,t.Store_Id,t.Store_Id2 from Transaction t left join Item i on t.Item_Id=i.Item_Id left join Transaction_Type tt on tt.Transaction_Type_Id=t.Transaction_Type_Id left join Supplier s on s.Supplier_Id=t.Supplier_Id Where i.Price_Per_Item=" + item;
            rs = stm.executeQuery(query);
        } else if (type == "Transaction_Type") {
            this.query = "select t.Transaction_Id,t.Item_Id,i.Item_Name,i.Category,i.Price_Per_Item,t.Transaction_Type_Id,tt.Transaction_Type,t.Date,t.Supplier_Id,s.Supplier_name,t.Quantity,t.Store_Id,t.Store_Id2 from Transaction t left join Item i on t.Item_Id=i.Item_Id left join Transaction_Type tt on tt.Transaction_Type_Id=t.Transaction_Type_Id left join Supplier s on s.Supplier_Id=t.Supplier_Id Where tt.Transaction_Type=" + item;
            rs = stm.executeQuery(query);
        } else if (type == "Date") {
            this.query = "select t.Transaction_Id,t.Item_Id,i.Item_Name,i.Category,i.Price_Per_Item,t.Transaction_Type_Id,tt.Transaction_Type,t.Date,t.Supplier_Id,s.Supplier_name,t.Quantity,t.Store_Id,t.Store_Id2 from Transaction t left join Item i on t.Item_Id=i.Item_Id left join Transaction_Type tt on tt.Transaction_Type_Id=t.Transaction_Type_Id left join Supplier s on s.Supplier_Id=t.Supplier_Id Where t.Date='" + item + "'";
            rs = stm.executeQuery(query);
        } else if (type == "Supplier_name") {
            this.query = "select t.Transaction_Id,t.Item_Id,i.Item_Name,i.Category,i.Price_Per_Item,t.Transaction_Type_Id,tt.Transaction_Type,t.Date,t.Supplier_Id,s.Supplier_name,t.Quantity,t.Store_Id,t.Store_Id2 from Transaction t left join Item i on t.Item_Id=i.Item_Id left join Transaction_Type tt on tt.Transaction_Type_Id=t.Transaction_Type_Id left join Supplier s on s.Supplier_Id=t.Supplier_Id Where s.Supplier_name='" + item + "'";
            rs = stm.executeQuery(query);
        } else if (type == "Quantity") {
            this.query = "select t.Transaction_Id,t.Item_Id,i.Item_Name,i.Category,i.Price_Per_Item,t.Transaction_Type_Id,tt.Transaction_Type,t.Date,t.Supplier_Id,s.Supplier_name,t.Quantity,t.Store_Id,t.Store_Id2 from Transaction t left join Item i on t.Item_Id=i.Item_Id left join Transaction_Type tt on tt.Transaction_Type_Id=t.Transaction_Type_Id left join Supplier s on s.Supplier_Id=t.Supplier_Id Where t.Quantity=" + item;
            rs = stm.executeQuery(query);
        }
        else if (type == "Store_Id") {
            this.query = "select t.Transaction_Id,t.Item_Id,i.Item_Name,i.Category,i.Price_Per_Item,t.Transaction_Type_Id,tt.Transaction_Type,t.Date,t.Supplier_Id,s.Supplier_name,t.Quantity,t.Store_Id,t.Store_Id2 from Transaction t left join Item i on t.Item_Id=i.Item_Id left join Transaction_Type tt on tt.Transaction_Type_Id=t.Transaction_Type_Id left join Supplier s on s.Supplier_Id=t.Supplier_Id Where t.Store_Id=" + item;
            rs = stm.executeQuery(query);
        }else if (type == "Category") {
            this.query = "select t.Transaction_Id,t.Item_Id,i.Item_Name,i.Category,i.Price_Per_Item,t.Transaction_Type_Id,tt.Transaction_Type,t.Date,t.Supplier_Id,s.Supplier_name,t.Quantity,t.Store_Id,t.Store_Id2 from Transaction t left join Item i on t.Item_Id=i.Item_Id left join Transaction_Type tt on tt.Transaction_Type_Id=t.Transaction_Type_Id left join Supplier s on s.Supplier_Id=t.Supplier_Id Where i.Category='" + item+"'";
            rs = stm.executeQuery(query);
        }
    }
    public String getitemid(String storeid,String Price,String itemanme ) throws SQLException{
        String id="";
        this.query="Select Item_Id from Item Where Store_Id="+storeid+" and Price_Per_Item="+Price+" and Item_Name='"+itemanme+"'";
        rs=stm.executeQuery(query);
        while(rs.next()){
            id=String.valueOf(rs.getInt("Item_Id"));
        }
        return id;

    }




    public void InsertTransaction(String Id, String ItemId, String TransactionTypeId, String Date, String Supplier_Id, String Quantity,String storeId) throws SQLException { //Used to insert transaction
        int originalstock = getItemStock(ItemId,getstoreid(ItemId));
        if (Id.isEmpty()) {
            String query = "Insert into Transaction(Item_Id,Transaction_Type_Id,Date,Supplier_Id,Quantity,Store_Id,Store_Id2) Values(?,?,?,?,?,?,?)";
            if (TransactionTypeId.equals("1")) {
                try {
                    int itemid = Integer.parseInt(ItemId);
                    int transactiontypeid = Integer.parseInt(TransactionTypeId);
                    int supplierid = Integer.parseInt(Supplier_Id);
                    int quantity = Integer.parseInt(Quantity);
                    int StoreId= Integer.parseInt(getstoreid(ItemId));
                    try (PreparedStatement pst = conn.prepareStatement(query)) {
                        pst.setInt(1, itemid);
                        pst.setInt(2, transactiontypeid);
                        pst.setString(3, Date);
                        pst.setInt(4, supplierid);
                        pst.setInt(5, quantity);
                        pst.setInt(6, StoreId);
                        pst.setNull(7, Types.INTEGER);
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        TransactionController.alertMessage(e.toString());
                    }
                    updateStockequation(quantity,getItemPrice(ItemId),String.valueOf(StoreId),getItemName(ItemId));
                } catch (NumberFormatException nfe) {
                    System.out.println("NumberFormatException: " + nfe.getMessage());
                    TransactionController.alertMessage(nfe.toString());
                }
            } else if (TransactionTypeId.equals("2")) {
                try {
                    int itemid = Integer.parseInt(ItemId);
                    int transactiontypeid = Integer.parseInt(TransactionTypeId);
                    int quantity = Integer.parseInt(Quantity);
                    int StoreId= Integer.parseInt(getstoreid(ItemId));
                    if (quantity > originalstock) {
                        TransactionController.alertMessage(("The Quantity that is sold exceeds the current stock"));
                    } else {
                        try (PreparedStatement pst = conn.prepareStatement(query)) {
                            pst.setInt(1, itemid);
                            pst.setInt(2, transactiontypeid);
                            pst.setString(3, Date);
                            pst.setNull(4, Types.INTEGER);
                            pst.setInt(5, quantity);
                            pst.setInt(6, StoreId);
                            pst.setNull(7, Types.INTEGER);
                            pst.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            TransactionController.alertMessage(e.toString());
                        }
                        updateStockequation(-quantity,getItemPrice(ItemId),String.valueOf(StoreId),getItemName(ItemId));
                    }
                } catch (NumberFormatException nfe) {
                    TransactionController.alertMessage(nfe.toString());
                }
            }
            else if (TransactionTypeId.equals("3")) {
                try {
                    int itemid = Integer.parseInt(ItemId);
                    int transactiontypeid = Integer.parseInt(TransactionTypeId);
                    int quantity = Integer.parseInt(Quantity);
                    int StoreId= Integer.parseInt(getstoreid(ItemId));
                    int StoreId2= Integer.parseInt(storeId);
                    if (quantity > originalstock) {
                        TransactionController.alertMessage(("The Quantity that is sold exceeds the current stock"));
                    } else {
                        try (PreparedStatement pst = conn.prepareStatement(query)) {
                            pst.setInt(1, itemid);
                            pst.setInt(2, transactiontypeid);
                            pst.setString(3, Date);
                            pst.setNull(4, Types.INTEGER);
                            pst.setInt(5, quantity);
                            pst.setInt(6, StoreId);
                            pst.setInt(7, StoreId2);
                            pst.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            TransactionController.alertMessage(e.toString());
                        }
                        try {
                            updateStockequation(quantity, getItemPrice(ItemId),storeId,getItemName(ItemId));
                            updateStockequation(-quantity, getItemPrice(ItemId), String.valueOf(StoreId),getItemName(ItemId));
                        }catch (SQLException e){
                            e.printStackTrace();
                        }

                    }
                } catch (NumberFormatException nfe) {
                    TransactionController.alertMessage(nfe.toString());
                }
            }
        } else {
            String query = "Insert into Transaction(Transaction_Id,Item_Id,Transaction_Type_Id,Date,Supplier_Id,Quantity,Store_Id,Store_Id2) Values(?,?,?,?,?,?,?,?)";
            if (TransactionTypeId.equals("1")) {
                try {
                    int transactionid = Integer.parseInt(Id);
                    int itemid = Integer.parseInt(ItemId);
                    int transactiontypeid = Integer.parseInt(TransactionTypeId);
                    int supplierid = Integer.parseInt(Supplier_Id);
                    int quantity = Integer.parseInt(Quantity);
                    int StoreId= Integer.parseInt(getstoreid(ItemId));
                    try (PreparedStatement pst = conn.prepareStatement(query)) {
                        pst.setInt(1, transactionid);
                        pst.setInt(2, itemid);
                        pst.setInt(3, transactiontypeid);
                        pst.setString(4, Date);
                        pst.setInt(5, supplierid);
                        pst.setInt(6, quantity);
                        pst.setInt(7, StoreId);
                        pst.setNull(8, Types.INTEGER);
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        TransactionController.alertMessage(e.toString());
                    }
                    updateStockequation(quantity,getItemPrice(ItemId),String.valueOf(StoreId),getItemName(ItemId));
                } catch (NumberFormatException nfe) {
                    System.out.println("NumberFormatException: " + nfe.getMessage());
                    TransactionController.alertMessage(nfe.toString());
                }
            } else if (TransactionTypeId.equals("2")) {
                try {
                    int transactionid = Integer.parseInt(Id);
                    int itemid = Integer.parseInt(ItemId);
                    int transactiontypeid = Integer.parseInt(TransactionTypeId);
                    int quantity = Integer.parseInt(Quantity);
                    int StoreId= Integer.parseInt(getstoreid(ItemId));
                    if (quantity > originalstock) {
                        TransactionController.alertMessage(("The Quantity that is sold exceeds the current stock"));
                    } else {
                        try (PreparedStatement pst = conn.prepareStatement(query)) {
                            pst.setInt(1, transactionid);
                            pst.setInt(2, itemid);
                            pst.setInt(3, transactiontypeid);
                            pst.setString(4, Date);
                            pst.setNull(5, Types.INTEGER);
                            pst.setInt(6, quantity);
                            pst.setInt(7, StoreId);
                            pst.setNull(8, Types.INTEGER);
                            pst.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            TransactionController.alertMessage(e.toString());
                        }
                        updateStockequation(-quantity,getItemPrice(ItemId),String.valueOf(StoreId),getItemName(ItemId));
                    }
                } catch (NumberFormatException nfe) {
                    TransactionController.alertMessage(nfe.toString());
                }
            }else if (TransactionTypeId.equals("3")) {
                try {
                    int transactionid = Integer.parseInt(Id);
                    int itemid = Integer.parseInt(ItemId);
                    int transactiontypeid = Integer.parseInt(TransactionTypeId);
                    int quantity = Integer.parseInt(Quantity);
                    int StoreId= Integer.parseInt(getstoreid(ItemId));
                    int StoreId2= Integer.parseInt(storeId);
                    if (quantity > originalstock) {
                        TransactionController.alertMessage(("The Quantity that is sold exceeds the current stock"));
                    } else {
                        try (PreparedStatement pst = conn.prepareStatement(query)) {
                            pst.setInt(1, transactionid);
                            pst.setInt(2, itemid);
                            pst.setInt(3, transactiontypeid);
                            pst.setString(4, Date);
                            pst.setNull(5, Types.INTEGER);
                            pst.setInt(6, quantity);
                            pst.setInt(7, StoreId);
                            pst.setInt(8, StoreId2);
                            pst.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            TransactionController.alertMessage(e.toString());
                        }
                        try {
                            updateStockequation(quantity, getItemPrice(ItemId), storeId,getItemName(ItemId));
                            updateStockequation(-quantity, getItemPrice(ItemId), String.valueOf(StoreId),getItemName(ItemId));
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                } catch (NumberFormatException nfe) {
                    TransactionController.alertMessage(nfe.toString());
                }
            }

        }
    }
    public void AutoIncrementTransaction(String update){ //Change the auto increment
        try{
            int updatevalue=Integer.parseInt(update);
            String query="Alter Table Transaction Auto_Increment=?";
            try(PreparedStatement pst = conn.prepareStatement(query)){
                pst.setInt(1,updatevalue);
                pst.executeUpdate();
            }catch(SQLException e){
                TransactionController.alertMessage(e.toString());
            }
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
            TransactionController.alertMessage(nfe.toString());
        }
    }
    public void DeleteTransaction(Transaction trvar) throws SQLException { //Delete a transaction
        String query="Delete from Transaction Where Transaction_Id=?";
        if(trvar.getTransactionTypeId()==1){
            if(getItemStock(String.valueOf(trvar.getItemId()),String.valueOf(trvar.getStoreId()))-trvar.getQuantity()<0){
                TransactionController.alertMessage("The selected transaction cannot be deleted because the deleted buy quantity exceeds the Current stock");
                return;
            }
            else{
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setInt(1, trvar.getTransactionId());
                    pst.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Transaction_Type_Controller.alertMessage(e.toString());
                }
                updateStockequation(-trvar.getQuantity(),getItemPrice(String.valueOf(trvar.getItemId())),String.valueOf(trvar.getStoreId()),getItemName(String.valueOf(trvar.getItemId())));
            }
        }
        else if(trvar.getTransactionTypeId()==2){
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setInt(1, trvar.getTransactionId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                Transaction_Type_Controller.alertMessage(e.toString());
            }
            updateStockequation(trvar.getQuantity(),getItemPrice(String.valueOf(trvar.getItemId())),String.valueOf(trvar.getStoreId()),getItemName(String.valueOf(trvar.getItemId())));
        }
        else if(trvar.getTransactionTypeId()==3){
            if(getItemStock(String.valueOf(trvar.getItemId()),String.valueOf(trvar.getStoreId2()))-trvar.getQuantity()<0){
                TransactionController.alertMessage("The selected transaction cannot be deleted because the deleted buy quantity exceeds the Current stock");
                return;
            }
            else {
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setInt(1, trvar.getTransactionId());
                    pst.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Transaction_Type_Controller.alertMessage(e.toString());
                }
                updateStockequation(trvar.getQuantity(), getItemPrice(String.valueOf(trvar.getItemId())), String.valueOf(trvar.getStoreId()),getItemName(String.valueOf(trvar.getItemId())));
                updateStockequation(-trvar.getQuantity(), getItemPrice(String.valueOf(trvar.getItemId())), String.valueOf(trvar.getStoreId2()),getItemName(String.valueOf(trvar.getItemId())));
            }
        }
    }




    public void UpdateTransaction(Transaction trvar, String Id, String Date, String Supplier_Id, String Quantity) throws SQLException { //Update the selected transaction
        int transactionId = trvar.getTransactionId();
        int itemId, olditemid;
        itemId = olditemid = trvar.getItemId();
        int trtypeid;
        trtypeid = trvar.getTransactionTypeId();
        String date = trvar.getTransactionDate();
        int supplierid = trvar.getSupplierId();
        int qty, oldqty; qty=oldqty = trvar.getQuantity();
        int StoreId;StoreId=trvar.getStoreId();
        int StoreId2=trvar.getStoreId2();
        if (!Id.isEmpty()) {
            try {
                transactionId = Integer.parseInt(Id);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                TransactionController.alertMessage(nfe.toString());
            }
        }
        if (!Date.isEmpty()) {
            date = Date;
        }
        if (!Supplier_Id.isEmpty()) {
            try {
                supplierid = Integer.parseInt(Supplier_Id);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                TransactionController.alertMessage(nfe.toString());
            }
        }
        if (!Quantity.isEmpty()) {
            try {
                qty = Integer.parseInt(Quantity);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                TransactionController.alertMessage(nfe.toString());
            }
        }



        if (itemId != olditemid||(itemId==olditemid && qty!=oldqty)) { //Checks if updating any of table will create an error where the item quantity will be reduced below 0;
                if (trtypeid == 1) {
                    if(getItemStock(String.valueOf(olditemid),String.valueOf(StoreId))+qty-oldqty<0){
                        TransactionController.alertMessage("Current stock is less then 0 if the stock is updated");
                        return;
                    }
                } else if (trtypeid == 2) {
                    if((getItemStock(String.valueOf(olditemid),String.valueOf(StoreId)))+oldqty<qty){
                        TransactionController.alertMessage("Updated value is less than current stock");
                        return;
                    }
                    else {
                        updateStockequation(oldqty, getItemPrice(String.valueOf(olditemid)), String.valueOf(StoreId), getItemName(String.valueOf(olditemid)));

                    }
                }
                else if (trtypeid == 3) {
                    if((getItemStock(getitemid(String.valueOf(StoreId2),String.valueOf(getItemPrice(String.valueOf(olditemid))),getItemName(String.valueOf(olditemid))),String.valueOf(StoreId2))-oldqty<0)||(getItemStock(String.valueOf(olditemid),String.valueOf(StoreId))+oldqty-qty<0)){
                        TransactionController.alertMessage("Cant update since the updated transaction exceed the store stock");
                        return;
                    }
                    else {
                        try {
                            updateStockequation(-oldqty,getItemPrice(String.valueOf(olditemid)),String.valueOf(StoreId2),getItemName(String.valueOf(olditemid)));
                            updateStockequation(oldqty,getItemPrice(String.valueOf(olditemid)),String.valueOf(StoreId),getItemName(String.valueOf(olditemid)));
                        }catch(Exception e){
                           System.out.println(e);
                        }
                    }
                }
        }

        String query="Update Transaction SET Transaction_Id=?, Item_Id=?, Transaction_Type_Id=?, Date=?, Supplier_Id=?, Quantity=?,Store_Id=?,Store_Id2=? Where Transaction_Id=?";
        if (trtypeid==1) { //If the transaction type is buy
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setInt(1, transactionId);
                pst.setInt(2, itemId);
                pst.setInt(3, trtypeid);
                pst.setString(4, date);
                pst.setInt(5, supplierid);
                pst.setInt(6, qty);
                pst.setInt(7, StoreId);
                pst.setNull(8, Types.INTEGER);
                pst.setInt(9,trvar.getTransactionId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                TransactionController.alertMessage(e.toString());
            }
            updateStockequation(qty,getItemPrice(String.valueOf(itemId)),String.valueOf(StoreId),getItemName(String.valueOf(itemId)));
            updateStockequation(-oldqty,getItemPrice(String.valueOf(itemId)),String.valueOf(StoreId),getItemName(String.valueOf(itemId)));
        } else if (trtypeid==2) {//If the transaction type is sell
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setInt(1, transactionId);
                pst.setInt(2, itemId);
                pst.setInt(3, trtypeid);
                pst.setString(4, date);
                pst.setNull(5, Types.INTEGER);
                pst.setInt(6, qty);
                pst.setInt(7, StoreId);
                pst.setNull(8, Types.INTEGER);
                pst.setInt(9,trvar.getTransactionId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                TransactionController.alertMessage(e.toString());
            }
            updateStockequation(-qty,getItemPrice(String.valueOf(itemId)),String.valueOf(StoreId),getItemName(String.valueOf(itemId)));
        }

        else if(trtypeid==3){
            try {
                if (qty > getItemStock(String.valueOf(itemId),String.valueOf(StoreId))) {
                    TransactionController.alertMessage(("The Quantity that is being transfer exceeds the current stock"));
                } else {
                    try (PreparedStatement pst = conn.prepareStatement(query)) {
                        pst.setInt(1, transactionId);
                        pst.setInt(2, itemId);
                        pst.setInt(3, trtypeid);
                        pst.setString(4, date);
                        pst.setNull(5, Types.INTEGER);
                        pst.setInt(6, qty);
                        pst.setInt(7, StoreId);
                        pst.setInt(8, StoreId2);
                        pst.setInt(9,trvar.getTransactionId());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        TransactionController.alertMessage(e.toString());
                    }
                    updateStockequation(qty, getItemPrice(String.valueOf(itemId)), String.valueOf(StoreId2),getItemName(String.valueOf(itemId)));
                    updateStockequation(-qty, getItemPrice(String.valueOf(itemId)), String.valueOf(StoreId),getItemName(String.valueOf(itemId)));
                }
            } catch (NumberFormatException nfe) {
                TransactionController.alertMessage(nfe.toString());
            }
        }
    }
    //Store DB Commands

    public void getStoreTable() throws SQLException { //Gets The Store Table
        this.query = "Select * from Stores";
        rs = stm.executeQuery(query);
    }

    public void DeleteStore(int id) { //Used to Delete a store
        String query = "Delete from stores where Store_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            SupplierController.alertMessage((e.toString()));
            e.printStackTrace();
        }
    }

    public void InsertStore(String StoreId, String StoreName, String StoreAddress) throws SQLException { //Use to Insert a new Supplier
        if (StoreId.isEmpty()) {
            String query = "Insert into stores(Store_Name, Location)Values(?,?)";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setString(1, StoreName);
                pst.setString(2, StoreAddress);
                pst.executeUpdate();
            } catch (SQLException e) {
                SupplierController.alertMessage((e.toString()));
                e.printStackTrace();
            }
        } else {
            try {
                int StoreIdInt = Integer.parseInt(StoreId);
                String query = "Insert into stores(Store_Id, Store_Name, Location)Values(?,?,?)";
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setInt(1, StoreIdInt);
                    pst.setString(2, StoreName);
                    pst.setString(3, StoreAddress);
                    pst.executeUpdate();
                } catch (SQLException e) {
                    SupplierController.alertMessage((e.toString()));
                    e.printStackTrace();
                }
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                SupplierController.alertMessage(nfe.toString());
            }
        }

    }

    public void UpdateStore(Store storevar, String StoreId, String StoreName, String StoreAddress) { //used to update a supplier
        int id = storevar.getStoreId(); //Get the original store id
        String name = storevar.getStoreName(); //Get the original store name
        String address = storevar.getStoreAddress(); //Get the original store address

        if (!StoreId.isEmpty()) { //Check if the Store Id is filled out by the user
            try {
                id = Integer.parseInt(StoreId);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                SupplierController.alertMessage(nfe.toString());
            }
        }

        if (!StoreName.isEmpty()) {//Check if the Store name is filled out by the user
            name = StoreName;
        }

        if (!StoreAddress.isEmpty()) {//Check if the Store address is filled out by the user
            address = StoreAddress;
        }


        String updatequery = "Update stores SET Store_Id=?, Store_name=?, Location=? Where Store_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(updatequery);) {
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setString(3, address);
            pst.setInt(4, storevar.getStoreId());
            pst.executeUpdate();
        } catch (SQLException e) {
            SupplierController.alertMessage((e.toString()));
            e.printStackTrace();
        }

    }

    //Owner DB Commands
    public void getOwnerTable() throws SQLException { //Gets The Supplier Table
        this.query = "Select * from owner";
        rs = stm.executeQuery(query);
    }

    public void InsertOwner(String ownerId, String ownerName) throws SQLException { //Use to Insert a new Supplier
        if (ownerId.isEmpty()) {
            String query = "Insert into owner(Owner_Name)Values(?)";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setString(1, ownerName);
                pst.executeUpdate();
            } catch (SQLException e) {
                SupplierController.alertMessage((e.toString()));
                e.printStackTrace();
            }
        } else {
            try {
                int ownerIdInt = Integer.parseInt(ownerId);
                String query = "Insert into owner(Owner_Id, Owner_Name)Values(?,?)";
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setInt(1, ownerIdInt);
                    pst.setString(2, ownerName);
                    pst.executeUpdate();
                } catch (SQLException e) {
                    SupplierController.alertMessage((e.toString()));
                    e.printStackTrace();
                }
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                SupplierController.alertMessage(nfe.toString());
            }
        }

    }

    public void DeleteOwner(int id) { //Used to Delete a supplier
        String query = "Delete from owner where Owner_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            SupplierController.alertMessage((e.toString()));
            e.printStackTrace();
        }
    }

    public void UpdateOwner(Owner ownervar, String ownerId, String ownerName) { //used to update a supplier
        int id = ownervar.getOwnerId(); //Get the original supplier name
        String name = ownervar.getOwnerName(); //Get the original Supplier Id
        if (!ownerId.isEmpty()) { //Check if the Supplier Id is filled out by the user
            try {
                id = Integer.parseInt(ownerId);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                SupplierController.alertMessage(nfe.toString());
            }
        }
        if (!ownerName.isEmpty()) {//Check if the Supplier Name is filled out by the user
            name = ownerName;
        }
        String updatequery = "Update owner SET Owner_Id=?, Owner_Name=? Where Owner_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(updatequery);) {
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setInt(3, ownervar.getOwnerId());
            pst.executeUpdate();
        } catch (SQLException e) {
            SupplierController.alertMessage((e.toString()));
            e.printStackTrace();
        }

    }

    //Staff DB Commands
    public void getStaffTable() throws SQLException { //Gets The Staff Table
        this.query = "Select * from staff";
        rs = stm.executeQuery(query);
    }

    public void insertStaff(String staffId, String staffName, String storeId, String ownerId, String jobType, String salary){
        int storeIdInt = Integer.parseInt(storeId);
        int ownerIdInt = Integer.parseInt(ownerId);
        int salaryInt = Integer.parseInt(salary);
        if (staffId.isEmpty()){
            String query = "INSERT INTO staff(Staff_Name, Store_Id, Owner_Id, Job_Type, Salary) VALUES (?,?,?,?,?)";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setString(1, staffName);
                pst.setInt(2, storeIdInt);
                pst.setInt(3, ownerIdInt);
                pst.setString(4, jobType);
                pst.setInt(5, salaryInt);
                pst.executeUpdate();
            } catch (SQLException e) {
                SupplierController.alertMessage((e.toString()));
                e.printStackTrace();
            }
        }

        else {
            try {
                int staffIdInt = Integer.parseInt(staffId);
                String query = "INSERT INTO staff(Staff_Id, Staff_Name, Store_Id, Owner_Id, Job_Type, Salary) VALUES (?,?,?,?,?,?)";
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setInt(1, staffIdInt);
                    pst.setString(2, staffName);
                    pst.setInt(3, storeIdInt);
                    pst.setInt(4, ownerIdInt);
                    pst.setString(5, jobType);
                    pst.setInt(6, salaryInt);
                    pst.executeUpdate();
                } catch (SQLException e) {
                    SupplierController.alertMessage((e.toString()));
                    e.printStackTrace();
                }
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                SupplierController.alertMessage(nfe.toString());
            }
        }
    }

    public void updateStaff(Staff staffvar, String staffId, String staffName, String storeId, String ownerId, String jobType, String salaryMonthly) { //used to update a supplier
        int id = staffvar.getStaffId(); //Get the original staff id
        String name = staffvar.getStaffName(); //Get the original staff name
        int store = staffvar.getStoreId(); //Get the original owner id
        int owner = staffvar.getOwnerId();
        String job = staffvar.getJobType();
        int salary = staffvar.getSalaryMonthly();

        if (!staffId.isEmpty()) { //Check if the Store Id is filled out by the user
            try {
                id = Integer.parseInt(staffId);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                SupplierController.alertMessage(nfe.toString());
            }
        }

        if (!staffName.isEmpty()) {//Check if the Store name is filled out by the user
            name = staffName;
        }

        if (!storeId.isEmpty()) {//Check if the Store address is filled out by the user
            try {
                store = Integer.parseInt(storeId);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                SupplierController.alertMessage(nfe.toString());
            }
        }

        if (!ownerId.isEmpty()) {//Check if the Store address is filled out by the user
            try {
                owner = Integer.parseInt(ownerId);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                SupplierController.alertMessage(nfe.toString());
            }
        }

        if (!jobType.isEmpty()) {//Check if the Store name is filled out by the user
            job = jobType;
        }

        if (!salaryMonthly.isEmpty()) {//Check if the Store address is filled out by the user
            try {
                salary = Integer.parseInt(salaryMonthly);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                SupplierController.alertMessage(nfe.toString());
            }
        }

        String updatequery = "UPDATE staff SET Staff_Id=?, Staff_Name=?, Store_Id=?, Owner_Id=?, Job_Type=?, Salary=? Where Staff_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(updatequery);) {
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setInt(3, store);
            pst.setInt(4, owner);
            pst.setString(5, job);
            pst.setInt(6, salary);
            pst.setInt(7, staffvar.getStaffId());
            pst.executeUpdate();
        } catch (SQLException e) {
            SupplierController.alertMessage((e.toString()));
            e.printStackTrace();
        }

    }

    public void DeleteStaff(int id) { //Used to Delete a store
        String query = "Delete from staff where Staff_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            SupplierController.alertMessage((e.toString()));
            e.printStackTrace();
        }
    }

    //Ownership DB Commands
    public void getOwnershipTable() throws SQLException { //Gets The Staff Table
        this.query = "Select * from ownership";
        rs = stm.executeQuery(query);
    }

    public void insertOwnership(String ownershipId, String ownerId, String storeId) throws SQLException { //Use to Insert a new Ownership entry
        int ownerIdInt = Integer.parseInt(ownerId);
        int storeIdInt = Integer.parseInt(storeId);
        if (ownershipId.isEmpty()) {
            String query = "Insert into ownership(Owner_Id, Store_Id)Values(?,?)";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setInt(1, ownerIdInt);
                pst.setInt(2, storeIdInt);
                pst.executeUpdate();
            } catch (SQLException e) {
                SupplierController.alertMessage((e.toString()));
                e.printStackTrace();
            }
        } else {
            try {
                int ownershipIdInt = Integer.parseInt(ownershipId);
                String query = "Insert into ownership(Ownership_Id, Owner_Id, Store_Id)Values(?,?,?)";
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setInt(1, ownershipIdInt);
                    pst.setInt(2, ownerIdInt);
                    pst.setInt(3, storeIdInt);
                    pst.executeUpdate();
                } catch (SQLException e) {
                    SupplierController.alertMessage((e.toString()));
                    e.printStackTrace();
                }
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                SupplierController.alertMessage(nfe.toString());
            }
        }

    }

    public void updateOwnership(Ownership ownershipvar, String ownershipId, String ownerId, String storeId) { //used to update a supplier
        int id = ownershipvar.getOwnershipId(); //Get the original staff id
        int owner = ownershipvar.getOwnerId();
        int store = ownershipvar.getStoreId();

        if (!ownershipId.isEmpty()) { //Check if the Store Id is filled out by the user
            try {
                id = Integer.parseInt(ownershipId);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                SupplierController.alertMessage(nfe.toString());
            }
        }

        if (!ownerId.isEmpty()) {//Check if the Store address is filled out by the user
            try {
                owner = Integer.parseInt(ownerId);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                SupplierController.alertMessage(nfe.toString());
            }
        }

        if (!storeId.isEmpty()) {//Check if the Store address is filled out by the user
            try {
                store = Integer.parseInt(storeId);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
                SupplierController.alertMessage(nfe.toString());
            }
        }

        String updatequery = "UPDATE ownership SET Ownership_Id=?, Owner_Id=?, Store_Id=? Where Ownership_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(updatequery);) {
            pst.setInt(1, id);
            pst.setInt(2, owner);
            pst.setInt(3, store);
            pst.setInt(4, ownershipvar.getOwnershipId());
            pst.executeUpdate();
        } catch (SQLException e) {
            SupplierController.alertMessage((e.toString()));
            e.printStackTrace();
        }

    }

    public void deleteOwnership(int id) { //Used to Delete a store
        String query = "Delete from ownership where Ownership_Id=?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            SupplierController.alertMessage((e.toString()));
            e.printStackTrace();
        }
    }
}








