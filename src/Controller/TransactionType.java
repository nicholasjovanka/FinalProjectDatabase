package Controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TransactionType {
    private SimpleIntegerProperty TransactionTypeId;
    private SimpleStringProperty TransactionType;

    TransactionType(int id, String type){
        this.TransactionTypeId=new SimpleIntegerProperty(id);
        this.TransactionType=new SimpleStringProperty(type);
    };

    public int getTransactionTypeId(){
        return TransactionTypeId.get();
    }
    public void setTransactionTypeId(int TypeId){
        this.TransactionTypeId = new SimpleIntegerProperty(TypeId);
    }

    public String getTransactionType(){
        return TransactionType.get();
    }
    public void setTransactionType(String type){
        this.TransactionType = new SimpleStringProperty(type);
    }

}