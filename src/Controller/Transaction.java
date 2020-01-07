package Controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Transaction {
    private SimpleIntegerProperty TransactionId;
    private SimpleIntegerProperty ItemId;
    private SimpleStringProperty ItemName;
    private SimpleIntegerProperty TransactionTypeId;
    private SimpleStringProperty TransactionType;
    private SimpleStringProperty TransactionDate;
    private SimpleIntegerProperty SupplierId;
    private SimpleStringProperty SupplierName;
    private SimpleStringProperty ItemCategory;
    private SimpleIntegerProperty Price;
    private SimpleIntegerProperty Quantity;
    private SimpleIntegerProperty StoreId;
    private SimpleIntegerProperty StoreId2;


    Transaction(int TrId,int ItId,String ItName,int TrTId,String TrTiName,String date,int SId,String SIName,int price,int qty,int stid,int stid2,String category){
        this.TransactionId=new SimpleIntegerProperty(TrId);
        this.ItemId=new SimpleIntegerProperty(ItId);
        this.ItemName=new SimpleStringProperty(ItName);
        this.TransactionTypeId=new SimpleIntegerProperty(TrTId);
        this.TransactionType=new SimpleStringProperty(TrTiName);
        this.TransactionDate=new SimpleStringProperty(date);
        this.SupplierId=new SimpleIntegerProperty(SId);
        this.SupplierName=new SimpleStringProperty(SIName);
        this.Price=new SimpleIntegerProperty(price);
        this.Quantity=new SimpleIntegerProperty(qty);
        this.StoreId=new SimpleIntegerProperty(stid);
        this.StoreId2=new SimpleIntegerProperty(stid2);
        this.ItemCategory=new SimpleStringProperty(category);
    }
    public int getTransactionId() {
        return TransactionId.get();
    }

    public void setTransactionId(int transactionId) {
        this.TransactionId=new SimpleIntegerProperty(transactionId);
    }

    public int getItemId() {
        return ItemId.get();
    }

    public void setItemId(int item_Id) {
        this.ItemId=new SimpleIntegerProperty(item_Id);
    }

    public String getItemName() {
        return ItemName.get();
    }
    public void setItemName(String itemName) {
        this.ItemName=new SimpleStringProperty(itemName);
    }

    public int getTransactionTypeId() {
        return TransactionTypeId.get();
    }

    public void setTransactionTypeId(int transactionTypeId) {
        this.TransactionTypeId=new SimpleIntegerProperty(transactionTypeId);
    }
    public String getTransactionType(){
        return TransactionType.get();
    }
    public void setTransactionType(String type){
        this.TransactionType = new SimpleStringProperty(type);
    }

    public String getTransactionDate() {
        return TransactionDate.get();
    }


    public void setTransactionDate(String date) {
        this.TransactionDate=new SimpleStringProperty(date);
    }

    public int getSupplierId() {
        return SupplierId.get();
    }

    public void setSupplierId(int supplierId) {
        this.SupplierId=new SimpleIntegerProperty(supplierId);
    }

    public String getSupplierName(){
        return SupplierName.get();
    }
    public void setSupplierName(String name){
        this.SupplierName = new SimpleStringProperty(name);
    }
    public int getPrice() {
        return Price.get();
    }

    public void setPrice(int price) {
        this.Price=new SimpleIntegerProperty(price);
    }
    public int getQuantity() {
        return Quantity.get();
    }

    public void setQuantity(int quantity) {
        this.Quantity=new SimpleIntegerProperty(quantity);
    }
    public int getStoreId() {
        return StoreId.get();
    }

    public void setStoreId(int storeId) {
        this.StoreId= new SimpleIntegerProperty(storeId);
    }
    public int getStoreId2() {
        return StoreId2.get();
    }

    public void setStoreId2(int storeId) {
        this.StoreId2= new SimpleIntegerProperty(storeId);
    }

    public String getItemCategory(){
        return ItemCategory.get();
    }
    public void setItemCategory(String ctgry){
        this.ItemCategory = new SimpleStringProperty(ctgry);
    }
}
