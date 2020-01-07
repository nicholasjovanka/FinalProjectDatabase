package Controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Store {
    private int StoreId;
    private String StoreName, StoreAddress;

    public Store(int storeId, String storeName, String storeAddress) {
        this.StoreId = storeId;
        this.StoreName = storeName;
        this.StoreAddress = storeAddress;
    }

    public int getStoreId() {
        return StoreId;
    }

    public void setStoreId(int storeId) {
        StoreId = storeId;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getStoreAddress() {
        return StoreAddress;
    }

    public void setStoreAddress(String storeAddress) {
        StoreAddress = storeAddress;
    }
}
