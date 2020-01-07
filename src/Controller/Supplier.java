package Controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Supplier { //Supplier Class that is used as an object for the table column in the Supplier Controller
    private SimpleIntegerProperty SupplierId;
    private SimpleStringProperty SupplierName;

    Supplier(int id,String name){
        this.SupplierId=new SimpleIntegerProperty(id);
        this.SupplierName=new SimpleStringProperty(name);
    };

    public int getSupplierId(){
        return SupplierId.get();
    }
    public void setSupplierId(int supplierId){
        this.SupplierId = new SimpleIntegerProperty(supplierId);
    }

    public String getSupplierName(){
        return SupplierName.get();
    }
    public void setSupplierName(String name){
        this.SupplierName = new SimpleStringProperty(name);
    }

}