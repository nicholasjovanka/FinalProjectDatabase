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
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    @FXML
    private AnchorPane ItemPane;

    @FXML
    private TableView<Item> ItemTableView;

    @FXML
    private ComboBox<String> BasedOnBox;

    @FXML
    private ComboBox<String> StoreIdBox;

    @FXML
    private TextField ItemIdField;

    @FXML
    private TextField CategoryField;

    @FXML
    private TextField ItemNameField;

    @FXML
    private TextField PriceField;

    @FXML
    private TextField QuantityField;
    @FXML
    private TextField AutoIncrementField;

    @FXML
    private ComboBox<String> SearchBarBox;

    @FXML
    private Button MainMenuButton;

    @FXML
    private Button InsertButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button UpdateButton;

    @FXML
    private TableColumn<Item,Integer> ItemIdColumn;

    @FXML
    private TableColumn<Item,String> CategoryColumn;

    @FXML
    private TableColumn<Item,String> ItemNameColumn;

    @FXML
    private TableColumn<Item,Integer> PriceColumn;
    @FXML
    private TableColumn<Item,Integer> StoreIdColumn;
    @FXML
    private TableColumn<Item,Integer> StoreId2Column;

    @FXML
    private DatePicker DatePick;
    @FXML
    private TableColumn<Item, Integer> QuantityColumn;

    private ObservableList<Item> oblist= FXCollections.observableArrayList(); //Observer List that will be used in Table View
    private ObservableList<String> filter= FXCollections.observableArrayList();
    private ObservableList<String> storeid= FXCollections.observableArrayList();
    public String lastselected="";
    public String lastmode="";
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("ItemId"));
        CategoryColumn.setCellValueFactory(new PropertyValueFactory<>("Category"));
        ItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<>("PricePerItem"));
        QuantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        StoreIdColumn.setCellValueFactory(new PropertyValueFactory<>("StoreId"));
        ItemTableView.setItems(oblist); //Sets oblist to the item table view
        BasedOnBox.getItems().add("Item_Id");
        BasedOnBox.getItems().add("Category");
        BasedOnBox.getItems().add("Item_Name");
        BasedOnBox.getItems().add("Price_Per_Item");
        BasedOnBox.getItems().add("Quantity");
        BasedOnBox.getItems().add("Store_Id");
        BasedOnBox.setVisibleRowCount(4);
        StoreIdBox.setItems(storeid);
        BasedOnBox.getSelectionModel().select("Item_Id"); //Sets the initial value
        SearchBarBox.disableProperty().bind(BasedOnBox.valueProperty().isEqualTo("Item_Id")); //Disable the Search Bar Box when Item_Id is selected
        SearchBarBox.setItems(filter);
        Refresh();
        QuantityField.setText("0");
    }

    public void Refresh(){ //Refresh The Screen
        ChangeSearchBar();
        ChangeFilterBar();
        StoreIdBox.getSelectionModel().select("None");

    }



    public void ChangeSearchBar() { //Gets all the rows from Item and load the distinct item from each attribute
        ItemTableView.getItems().clear();
        oblist.clear();
        filter.clear();
        storeid.clear();
        ItemTableView.getSelectionModel().clearSelection();
        QuantityField.setText("0");
        if(lastmode==BasedOnBox.getSelectionModel().getSelectedItem()&& SearchBarBox.getSelectionModel().getSelectedItem()!=null){
            SearchBarBox.getSelectionModel().select(lastselected);
            ItemTableView.getItems().clear();
        }
        else{
            SearchBarBox.getSelectionModel().select("None");
            ItemTableView.getItems().clear();
        }
        lastmode=BasedOnBox.getSelectionModel().getSelectedItem();
        try{
            MainMenuController.db.getItemDB(BasedOnBox.getSelectionModel().getSelectedItem());
            while(MainMenuController.db.rs.next()){
                oblist.add(new Item(MainMenuController.db.rs.getInt("Item_Id"), MainMenuController.db.rs.getString("Category"), MainMenuController.db.rs.getString("Item_Name"), MainMenuController.db.rs.getInt("Price_Per_Item"), MainMenuController.db.rs.getInt("Quantity"),MainMenuController.db.rs.getInt("Store_Id")));
            }
        }catch(SQLException e){
            e.printStackTrace();
            alertMessage(e.toString());
        }
        filter.add("None");
        try{
            MainMenuController.db.ItemBasedOn(BasedOnBox.getSelectionModel().getSelectedItem());
            while(MainMenuController.db.rs.next()){
                filter.add((String) MainMenuController.db.rs.getString(BasedOnBox.getSelectionModel().getSelectedItem()));
            }

        }catch(SQLException e){
            e.printStackTrace();
            alertMessage(e.toString());
        }
        try{
            MainMenuController.db.getStoreId();
            while(MainMenuController.db.rs.next()){
                storeid.add((String) MainMenuController.db.rs.getString("Store_Id"));
            }

        }catch(SQLException e){
            e.printStackTrace();
            alertMessage(e.toString());
        }
    }

    public void ChangeFilterBar(){ //Get the table based on the distinct value selected
            oblist.clear();
            String type = BasedOnBox.getSelectionModel().getSelectedItem();
            String item = SearchBarBox.getSelectionModel().getSelectedItem();
            try {
                MainMenuController.db.ItemView(type, item);
                if(item!=null){
                    lastselected=item;
                }

                while (MainMenuController.db.rs.next()) {
                    oblist.add(new Item(MainMenuController.db.rs.getInt("Item_Id"), MainMenuController.db.rs.getString("Category"), MainMenuController.db.rs.getString("Item_Name"), MainMenuController.db.rs.getInt("Price_Per_Item"), MainMenuController.db.rs.getInt("Quantity"),MainMenuController.db.rs.getInt("Store_Id")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                alertMessage(e.toString());
            }
        }


    public void BackToMenu(javafx.event.ActionEvent actionEvent) throws IOException { //Go back to main Menu
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/MainMenu.fxml"));
        ItemPane.getChildren().setAll(pane);
    }


    public static void alertMessage(String message){ //Used to display an alertbox message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void Insert(){ //Insert Method
        String id=ItemIdField.getText();
        String category=CategoryField.getText();
        String itemname=ItemNameField.getText();
        String price=PriceField.getText();
        String quantity=QuantityField.getText();
        String stid=StoreIdBox.getSelectionModel().getSelectedItem();
        ItemIdField.clear();CategoryField.clear();ItemNameField.clear();PriceField.clear();QuantityField.clear();
        MainMenuController.db.InsertItem(id,category,itemname,price,quantity,stid);
        Refresh();
    }
    public void Update(){ //Update Method
        String id=ItemIdField.getText();
        String category=CategoryField.getText();
        String itemname=ItemNameField.getText();
        String price=PriceField.getText();
        String quantity=QuantityField.getText();
        String stid="";
        if(!StoreIdBox.getSelectionModel().getSelectedItem().equals("None")){
            stid=StoreIdBox.getSelectionModel().getSelectedItem();
        }
        ItemIdField.clear();CategoryField.clear();ItemNameField.clear();PriceField.clear();QuantityField.clear();
        MainMenuController.db.UpdateItem(ItemTableView.getSelectionModel().getSelectedItem(),id,category,itemname,price,quantity,stid);
        Refresh();
    }
    public void Delete(){ //Delete Method
        int id=ItemTableView.getSelectionModel().getSelectedItem().getItemId();
        MainMenuController.db.DeleteItem(id);
        Refresh();
    }

    public void Change(){ //Change The auto Increment value
        String autoincrement=AutoIncrementField.getText();
        AutoIncrementField.clear();
        MainMenuController.db.AutoIncrementItem(autoincrement);
    }
}

