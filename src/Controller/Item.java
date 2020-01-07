package Controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item { //Item Class with Setter Getter That will be used For The Tableview value for Item Controller
    private SimpleIntegerProperty ItemId;
    private SimpleStringProperty Category;
    private SimpleStringProperty ItemName;
    private SimpleIntegerProperty PricePerItem;
    private SimpleIntegerProperty Quantity;
    private SimpleIntegerProperty StoreId;

    Item(int id, String ctgry,String item, int price, int qty,int stid){
        this.ItemId=new SimpleIntegerProperty(id);
        this.Category=new SimpleStringProperty(ctgry);
        this.ItemName=new SimpleStringProperty(item);
        this.PricePerItem=new SimpleIntegerProperty(price);
        this.Quantity=new SimpleIntegerProperty(qty);
        this.StoreId=new SimpleIntegerProperty(stid);

    }

    public int getItemId() {
        return ItemId.get();
    }

    public void setItemId(int itemId) {
        this.ItemId= new SimpleIntegerProperty(itemId);
    }

    public String getCategory() {
        return Category.get();
    }


    public void setCategory(String category) {
        this.Category=new SimpleStringProperty(category);
    }

    public String getItemName() {
        return ItemName.get();
    }


    public void setItemName(String itemName) {
        this.ItemName=new SimpleStringProperty(itemName);
    }

    public int getPricePerItem() {
        return PricePerItem.get();
    }



    public void setPricePerItem(int pricePerItem) {
        this.PricePerItem=new SimpleIntegerProperty(pricePerItem);
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
}