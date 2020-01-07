package Controller;
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

public class SupplierController implements Initializable {
    @FXML
    private AnchorPane SupplierPane;
    @FXML
    private TableView<Supplier> SupplierTableView;

    @FXML
    private TableColumn<Supplier, Integer> Supplier_Id;

    @FXML
    private TableColumn<Supplier, String> Supplier_Name;
    @FXML
    private Button InsertButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button UpdateButton;
    @FXML
    private Button Menu;
    @FXML
    private Button Refresh;
    @FXML
    private TextField IdText;
    @FXML
    private TextField NameText;
    ObservableList<Supplier> oblist = FXCollections.observableArrayList();

    @FXML
    void BackToMenu(MouseEvent event) throws IOException { //GO back to Menu
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/MainMenu.fxml"));
        SupplierPane.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { //Initialize the Supplier Table
        Supplier_Id.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));
        Supplier_Name.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
        SupplierTableView.setItems(oblist);
        updateScreen();

    }


    public void updateScreen() { //Get The Supplier Table Value
        SupplierTableView.getItems().clear();
        oblist.clear();
        SupplierTableView.getSelectionModel().clearSelection();
        try {
            MainMenuController.db.getSupplierTable();
            while (MainMenuController.db.rs.next()) {
                oblist.add(new Supplier(MainMenuController.db.rs.getInt("Supplier_Id"), MainMenuController.db.rs.getString("Supplier_name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void InsertClick() throws  SQLException{ //For Insert
        String Supplier_Id=IdText.getText();
        String Supplier_Name=NameText.getText();
        if(Supplier_Name.isEmpty()){
        alertMessage("You must fill all the required field to insert");
        }
        else {
            IdText.clear();
            NameText.clear();
            MainMenuController.db.InsertSupplier(Supplier_Id, Supplier_Name);
            updateScreen();
        }
    }

    public void DeleteSelected(){ //For Delete
        int id=SupplierTableView.getSelectionModel().getSelectedItem().getSupplierId();
        MainMenuController.db.DeleteSupplier(id);
        updateScreen();
    }

    public void Refresh(){
        updateScreen();
    }

    public void Update(){
        Supplier object=SupplierTableView.getSelectionModel().getSelectedItem();
        String Supplier_Id=IdText.getText();
        String Supplier_Name=NameText.getText();
        IdText.clear(); NameText.clear();
        MainMenuController.db.UpdateSupplier(object,Supplier_Id,Supplier_Name);
        updateScreen();

    }
    public static void alertMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

