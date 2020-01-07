package Controller;

import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StoreController implements Initializable {

    @FXML
    private AnchorPane Pane_Store;

    @FXML
    private TableView<Store> TableView_Store;

    @FXML
    private TableColumn<Store, Integer> Col_StoreId;

    @FXML
    private TableColumn<Store, String> Col_StoreName;

    @FXML
    private TableColumn<Store, String> Col_StoreAddress;

    @FXML
    private Button Button_MainMenu;

    @FXML
    private Button Button_Delete;

    @FXML
    private Button Button_Update;

    @FXML
    private Button Button_Insert;

    @FXML
    private Button Button_Refresh;

    @FXML
    private TextField Text_Id;

    @FXML
    private TextField Text_Name;

    @FXML
    private TextField Text_Address;


    ObservableList<Store> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Col_StoreId.setCellValueFactory(new PropertyValueFactory<>("StoreId"));
        Col_StoreName.setCellValueFactory(new PropertyValueFactory<>("StoreName"));
        Col_StoreAddress.setCellValueFactory(new PropertyValueFactory<>("StoreAddress"));
        TableView_Store.setItems(oblist);
        updateScreen();

    }

    public void updateScreen(){
        TableView_Store.getItems().clear();
        oblist.clear();
        TableView_Store.getSelectionModel().clearSelection();
        try {
            MainMenuController.db.getStoreTable();
            while(MainMenuController.db.rs.next()){
                oblist.add(new Store(MainMenuController.db.rs.getInt("Store_Id"),
                        MainMenuController.db.rs.getString("Store_Name"),
                        MainMenuController.db.rs.getString("Location")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void Refresh(){
        updateScreen();
    }

    public void InsertClick() throws  SQLException{ //For Insert
        String Store_Id=Text_Id.getText();
        String Store_Name=Text_Name.getText();
        String Store_Address=Text_Address.getText();
        if(Store_Name.isEmpty() || Store_Address.isEmpty()) {
            alertMessage("[Store_Name] and [Store_Address] are required fields. Please fill them to proceed.");
        }
        else {
            Text_Id.clear();
            Text_Name.clear();
            Text_Address.clear();
            MainMenuController.db.InsertStore(Store_Id, Store_Name, Store_Address);
            updateScreen();
        }
    }

    public void DeleteSelected(){ //For Delete
        int id=TableView_Store.getSelectionModel().getSelectedItem().getStoreId();
        MainMenuController.db.DeleteStore(id);
        updateScreen();
    }

    public void Update(){
        Store object=TableView_Store.getSelectionModel().getSelectedItem();
        String Store_Id = Text_Id.getText();
        String Store_Name = Text_Name.getText();
        String Store_Address = Text_Address.getText();
        Text_Id.clear(); Text_Name.clear(); Text_Address.clear();
        MainMenuController.db.UpdateStore(object,Store_Id, Store_Name, Store_Address);
        updateScreen();

    }

    @FXML
    void BackToMenu(MouseEvent event) throws IOException { //GO back to Menu
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/MainMenu.fxml"));
        Pane_Store.getChildren().setAll(pane);
    }

    public static void alertMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
