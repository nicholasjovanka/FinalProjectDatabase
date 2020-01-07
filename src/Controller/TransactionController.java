package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {
    @FXML
    private AnchorPane TransactionPane;

    @FXML
    private TableView<Transaction> TransactionTableView;

    @FXML
    private TableColumn<Transaction, Integer> TransactionIdColumn;

    @FXML
    private TableColumn<Transaction, Integer> ItemIdColumn;
    @FXML
    private TableColumn<Transaction, String> ItemCategoryColumn;

    @FXML
    private TableColumn<Transaction, String> ItemNameColumn;

    @FXML
    private TableColumn<Transaction, Integer> TransactionTypeIdColumn;

    @FXML
    private TableColumn<Transaction, String> TransactionTypeColumn;

    @FXML
    private TableColumn<Transaction, String> DateColumn;

    @FXML
    private TableColumn<Transaction, Integer> SupplierIdColumn;

    @FXML
    private TableColumn<Transaction, String> SupplierNameColumn;

    @FXML
    private TableColumn<Transaction, Integer> QuantityColumn;

    @FXML
    private TableColumn<Transaction, Integer> PriceColumn;

    @FXML
    private TableColumn<Transaction,Integer> StoreIdColumn;
    @FXML
    private TableColumn<Transaction,Integer> StoreId2Column;
    @FXML
    private ComboBox<String> BasedOnBox;

    @FXML
    private ComboBox<String> SearchBarBox;

    @FXML
    private ComboBox<String> ItemIdBox;

    @FXML
    private ComboBox<String> TransactionTypeBox;
    @FXML
    private ComboBox<String> StoreIdBox;


    @FXML
    private TextField DateField;

    @FXML
    private TextField QuantityField;

    @FXML
    private TextField AutoIncrementField;

    @FXML
    private ComboBox<String> SupplierIdBox;

    @FXML
    private Button InsertButton;

    @FXML
    private Button UpdateButton;

    @FXML
    private Button MainMenuButton;

    @FXML
    private Button RefreshButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private TextField TransactionIdField;
    public String lastselected="";
    public String lastmode="";
    public ObservableList<Transaction> oblist= FXCollections.observableArrayList();
    public ObservableList<String> itemidlist= FXCollections.observableArrayList();
    public ObservableList<String> transactiontypelist= FXCollections.observableArrayList();
    public ObservableList<String> supplierlist= FXCollections.observableArrayList();
    private ObservableList<String> filter= FXCollections.observableArrayList();
    private ObservableList<String> storeid= FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TransactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("TransactionId"));
        ItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("ItemId"));
        ItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        ItemCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("ItemCategory"));
        TransactionTypeIdColumn.setCellValueFactory(new PropertyValueFactory<>("TransactionTypeId"));
        TransactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("TransactionType"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<>("TransactionDate"));
        SupplierIdColumn.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));
        SupplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
        QuantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        StoreIdColumn.setCellValueFactory(new PropertyValueFactory<>("StoreId"));
        StoreId2Column.setCellValueFactory(new PropertyValueFactory<>("StoreId2"));
        TransactionTableView.setItems(oblist);
        BasedOnBox.getItems().add("Transaction_Id");
        BasedOnBox.getItems().add("Item_Id");
        BasedOnBox.getItems().add("Item_Name");
        BasedOnBox.getItems().add("Category");
        BasedOnBox.getItems().add("Price_Per_Item");
        BasedOnBox.getItems().add("Transaction_Type_Id");
        BasedOnBox.getItems().add("Date");
        BasedOnBox.getItems().add("Supplier_Id");
        BasedOnBox.getItems().add("Supplier_name");
        BasedOnBox.getItems().add("Quantity");
        BasedOnBox.getItems().add("Store_Id");
        BasedOnBox.setVisibleRowCount(4);//Sets the maximum visible row in the choicebox that appear before it needs to be scrolled down by the user
        BasedOnBox.getSelectionModel().select("Transaction_Id");
        SearchBarBox.disableProperty().bind(BasedOnBox.valueProperty().isEqualTo("Transaction_Id").or(BasedOnBox.valueProperty().isEqualTo("Item_Id")).or(BasedOnBox.valueProperty().isEqualTo("Transaction_Type_Id")).or(BasedOnBox.valueProperty().isEqualTo("Supplier_Id")));
        SearchBarBox.setItems(filter);
        ItemIdBox.setItems(itemidlist);
        SupplierIdBox.disableProperty().bind(TransactionTypeBox.valueProperty().isEqualTo("2-Sell").or(TransactionTypeBox.valueProperty().isEqualTo("3-Transfer")));
        TransactionTypeBox.setItems(transactiontypelist);
        SupplierIdBox.setItems(supplierlist);
        SearchBarBox.setVisibleRowCount(4);
        ItemIdBox.setVisibleRowCount(4);
        SupplierIdBox.setVisibleRowCount(4);
        TransactionTypeBox.setVisibleRowCount(4);
        StoreIdBox.setItems(storeid);
        StoreIdBox.setVisibleRowCount(4);
        StoreIdBox.disableProperty().bind(TransactionTypeBox.valueProperty().isEqualTo("2-Sell").or(TransactionTypeBox.valueProperty().isEqualTo("1-Buy")));
        Refresh();

    }
    public void ChangeSearchBar(){
        oblist.clear();
        filter.clear();
        filter.add("None");
        TransactionTableView.getSelectionModel().clearSelection();
        if(lastmode==BasedOnBox.getSelectionModel().getSelectedItem()&& SearchBarBox.getSelectionModel().getSelectedItem()!=null){
            SearchBarBox.getSelectionModel().select(lastselected);
            TransactionTableView.getItems().clear();
        }
        else{
            SearchBarBox.getSelectionModel().select("None");
            TransactionTableView.getItems().clear();
        }
        lastmode=BasedOnBox.getSelectionModel().getSelectedItem();
        try{
            MainMenuController.db.getTransactionDB(BasedOnBox.getSelectionModel().getSelectedItem());
            while(MainMenuController.db.rs.next()){
                oblist.add(new Transaction(MainMenuController.db.rs.getInt("Transaction_Id"), MainMenuController.db.rs.getInt("Item_Id"), MainMenuController.db.rs.getString("Item_Name"), MainMenuController.db.rs.getInt("Transaction_Type_Id"), MainMenuController.db.rs.getString("Transaction_Type"), MainMenuController.db.rs.getTimestamp("Date").toString(), MainMenuController.db.rs.getInt("Supplier_Id"), MainMenuController.db.rs.getString("Supplier_name"), MainMenuController.db.rs.getInt("Price_Per_Item"), MainMenuController.db.rs.getInt("Quantity"),MainMenuController.db.rs.getInt("Store_Id"), MainMenuController.db.rs.getInt("Store_Id2"),MainMenuController.db.rs.getString("Category")));
            }
        }catch(SQLException e){
            e.printStackTrace();
            alertMessage(e.toString());
        }
        try{
            MainMenuController.db.TransactionBasedOn(BasedOnBox.getSelectionModel().getSelectedItem());
            while(MainMenuController.db.rs.next()){
                filter.add((String) MainMenuController.db.rs.getString(BasedOnBox.getSelectionModel().getSelectedItem()));
            }

        }catch(SQLException e){
            e.printStackTrace();
            alertMessage(e.toString());
        }
    }
    public void ChangeFilterBar(){
        oblist.clear();
        String type = BasedOnBox.getSelectionModel().getSelectedItem();
        String item="None";
        if(!SearchBarBox.isDisabled()) {
            item = SearchBarBox.getSelectionModel().getSelectedItem();
        }
        try {
            MainMenuController.db.TransactionView(type, item);
            if(item!=null){
                lastselected=item;
            }
            while (MainMenuController.db.rs.next()) {
                oblist.add(new Transaction(MainMenuController.db.rs.getInt("Transaction_Id"), MainMenuController.db.rs.getInt("Item_Id"), MainMenuController.db.rs.getString("Item_Name"), MainMenuController.db.rs.getInt("Transaction_Type_Id"), MainMenuController.db.rs.getString("Transaction_Type"), MainMenuController.db.rs.getTimestamp("Date").toString(), MainMenuController.db.rs.getInt("Supplier_Id"), MainMenuController.db.rs.getString("Supplier_name"), MainMenuController.db.rs.getInt("Price_Per_Item"), MainMenuController.db.rs.getInt("Quantity"),MainMenuController.db.rs.getInt("Store_Id"),MainMenuController.db.rs.getInt("Store_Id2"),MainMenuController.db.rs.getString("Category")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            alertMessage(e.toString());
        }
    }
    public void Refresh(){
        ChangeSearchBar();
        ChangeFilterBar();
        RefreshComboBox();
        SupplierIdBox.getSelectionModel().select("None-");
        TransactionTypeBox.getSelectionModel().select("None-");
        ItemIdBox.getSelectionModel().select("None-");
        StoreIdBox.getSelectionModel().select("None-");
    }



    public void BackToMenu(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/MainMenu.fxml"));
        TransactionPane.getChildren().setAll(pane);
    }



    public void RefreshComboBox(){
        supplierlist.clear();
        try{
            MainMenuController.db.getSupplier();
            supplierlist.add("None-");
            while(MainMenuController.db.rs.next()){
                supplierlist.add(String.valueOf(MainMenuController.db.rs.getInt("Supplier_Id"))+"-"+ MainMenuController.db.rs.getString("Supplier_name"));
            }
        }catch(SQLException e){
            e.printStackTrace();
            alertMessage(e.toString());
        }
        transactiontypelist.clear();
        try{
            MainMenuController.db.gettransactionType();
            transactiontypelist.add("None-");
            while(MainMenuController.db.rs.next()){
                transactiontypelist.add(String.valueOf(MainMenuController.db.rs.getInt("Transaction_Type_Id"))+"-"+ MainMenuController.db.rs.getString("Transaction_Type"));
            }
        }catch(SQLException e){
            e.printStackTrace();
            alertMessage(e.toString());
        }
        itemidlist.clear();
        try{
            MainMenuController.db.GetitemId();
            itemidlist.add("None-");
            while(MainMenuController.db.rs.next()){
                itemidlist.add((String.valueOf(MainMenuController.db.rs.getInt("Item_Id")) +"-Name "+ MainMenuController.db.rs.getString("Item_Name")+"-Price "+String.valueOf(MainMenuController.db.rs.getInt("Price_Per_Item"))+"-Quantity "+String.valueOf(MainMenuController.db.rs.getInt("Quantity"))));
            }
        }catch(SQLException e){
            e.printStackTrace();
            alertMessage(e.toString());
        }
        storeid.clear();
        try{
            MainMenuController.db.getStoreId();
            storeid.add("None-");
            while(MainMenuController.db.rs.next()){
                storeid.add((String) MainMenuController.db.rs.getString("Store_Id"));
            }

        }catch(SQLException e){
            e.printStackTrace();
            alertMessage(e.toString());
        }

    }
    public void GetCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:s");
        Date now = new Date();
        String strDate = sdf.format(now);
        DateField.clear();
        DateField.setText(strDate);
    }

    public void Insert() throws SQLException{
        String SupplierId="";
        String id=TransactionIdField.getText();
        String ItemId=ItemIdBox.getSelectionModel().getSelectedItem().substring(0,getstripposition(ItemIdBox.getSelectionModel().getSelectedItem()));
        String TransactionTypeId=TransactionTypeBox.getSelectionModel().getSelectedItem().substring(0,getstripposition(TransactionTypeBox.getSelectionModel().getSelectedItem()));
        String Date=DateField.getText();
        String storeid="";
        if(!SupplierIdBox.isDisabled()){
            SupplierId=SupplierIdBox.getSelectionModel().getSelectedItem().substring(0,getstripposition(SupplierIdBox.getSelectionModel().getSelectedItem()));
        }
        if(!StoreIdBox.isDisabled()){
            storeid=StoreIdBox.getSelectionModel().getSelectedItem();
        }
        String Quantity=QuantityField.getText();
        if(ItemId.equals("None")||TransactionTypeId.equals("None")||SupplierId.equals("None")||Date.equals("")){
            alertMessage("Cannot have none as value in ItemId,TransactionTypeId, and SupplierId for insert and Date cannot be empty");
        }
        else {
            TransactionIdField.clear();
            DateField.clear();
            QuantityField.clear();
            MainMenuController.db.InsertTransaction(id, ItemId, TransactionTypeId, Date, SupplierId, Quantity,storeid);
            Refresh();
        }
    }

    public void Update() throws SQLException{
        String SupplierId="";
        String id=TransactionIdField.getText();
        String Date=DateField.getText();
        if(!SupplierIdBox.getSelectionModel().getSelectedItem().equals("None-")){
            SupplierId=SupplierIdBox.getSelectionModel().getSelectedItem().substring(0,getstripposition(SupplierIdBox.getSelectionModel().getSelectedItem()));
        }
        String Quantity=QuantityField.getText();
        TransactionIdField.clear();DateField.clear();QuantityField.clear();
        MainMenuController.db.UpdateTransaction(TransactionTableView.getSelectionModel().getSelectedItem(),id,Date,SupplierId,Quantity);
        TransactionTableView.getSelectionModel().clearSelection();
        Refresh();

    }

    public int getstripposition(String a){
        return a.indexOf("-");
    } //Gets the position of the string "-" which is used to substring the string to get the position of the id

    public void Delete() throws SQLException{
    MainMenuController.db.DeleteTransaction(TransactionTableView.getSelectionModel().getSelectedItem());
    TransactionTableView.getSelectionModel().clearSelection();
    Refresh();
    }
    public static void alertMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void Change(){
        String autoincrement=AutoIncrementField.getText();
        AutoIncrementField.clear();
        MainMenuController.db.AutoIncrementTransaction(autoincrement);
    }
}
