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

public class OwnershipController implements Initializable {

    @FXML
    private AnchorPane pane_ownership;

    @FXML
    private TableView<Ownership> tableView_ownership;

    @FXML
    private TableColumn<Ownership, Integer> col_ownershipId;

    @FXML
    private TableColumn<Ownership, Integer> col_ownerId;

    @FXML
    private TableColumn<Ownership, Integer> col_storeId;

    @FXML
    private Button button_mainMenu;

    @FXML
    private Button button_refresh;

    @FXML
    private Button button_insert;

    @FXML
    private Button button_update;

    @FXML
    private Button button_delete;

    @FXML
    private TextField text_ownershipId;

    @FXML
    private ComboBox<String> combo_ownerId;

    @FXML
    private ComboBox<String> combo_storeId;

    @FXML
    void backToMenu(MouseEvent event) throws IOException { //GO back to Menu
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/MainMenu.fxml"));
        pane_ownership.getChildren().setAll(pane);
    }

    ObservableList<Ownership> oblist = FXCollections.observableArrayList();
    ObservableList<String> oblistOwner = FXCollections.observableArrayList();
    ObservableList<String> oblistStore = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_ownershipId.setCellValueFactory(new PropertyValueFactory<>("ownershipId"));
        col_ownerId.setCellValueFactory(new PropertyValueFactory<>("ownerId"));
        col_storeId.setCellValueFactory(new PropertyValueFactory<>("storeId"));
        combo_ownerId.setItems(oblistOwner);
        combo_storeId.setItems(oblistStore);
        combo_ownerId.setVisibleRowCount(4);
        combo_storeId.setVisibleRowCount(4);
        tableView_ownership.setItems(oblist);
        updateScreen();
    }

    private void updateScreen() {
        tableView_ownership.getItems().clear();
        oblist.clear();
        oblistOwner.clear();
        oblistStore.clear();
        tableView_ownership.getSelectionModel().clearSelection();
        try {
            MainMenuController.db.getOwnershipTable();
            while (MainMenuController.db.rs.next()) {
                oblist.add(new Ownership(MainMenuController.db.rs.getInt("Ownership_Id"),
                        MainMenuController.db.rs.getInt("Owner_Id"),
                        MainMenuController.db.rs.getInt("Store_Id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            MainMenuController.db.getStoreTable();
            while (MainMenuController.db.rs.next()){
                oblistStore.add(String.valueOf(MainMenuController.db.rs.getInt("Store_Id")) + "-" + MainMenuController.db.rs.getString("Store_Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            MainMenuController.db.getOwnerTable();
            while (MainMenuController.db.rs.next()){
                oblistOwner.add(String.valueOf(MainMenuController.db.rs.getInt("Owner_Id")) + "-" + MainMenuController.db.rs.getString("Owner_Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert() throws SQLException{
        String ownershipId = text_ownershipId.getText();
        String ownerId = combo_ownerId.getSelectionModel().getSelectedItem();
        ownerId = ownerId.substring(0, getstripposition(ownerId));
        String storeId = combo_storeId.getSelectionModel().getSelectedItem();
        storeId = storeId.substring(0, getstripposition(storeId));

        if(ownerId == null || storeId == null){
            alertMessage("[Owner_Id] and [Store_Id] are required fields. Please fill them to proceed.");
        }
        else {
            text_ownershipId.clear();
            combo_ownerId.getSelectionModel().clearSelection();
            combo_storeId.getSelectionModel().clearSelection();
            MainMenuController.db.insertOwnership(ownershipId, ownerId, storeId);
            updateScreen();
        }
    }

    public void update(){
        Ownership object = tableView_ownership.getSelectionModel().getSelectedItem();
        String ownershipId = text_ownershipId.getText();

        String ownerId = combo_ownerId.getSelectionModel().getSelectedItem();
        if(ownerId == null){
            ownerId = "";
        }
        else {
            try {
                ownerId = ownerId.substring(0, getstripposition(ownerId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String storeId = combo_storeId.getSelectionModel().getSelectedItem();
        if(storeId == null){
            storeId = "";
        }
        else{
            try {
                storeId = storeId.substring(0, getstripposition(storeId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        text_ownershipId.clear();
        combo_ownerId.getSelectionModel().clearSelection();
        combo_storeId.getSelectionModel().clearSelection();
        MainMenuController.db.updateOwnership(object, ownershipId, ownerId, storeId);
        updateScreen();
    }

    public void deleteSelected(){ //For Delete
        int id=tableView_ownership.getSelectionModel().getSelectedItem().getOwnershipId();
        MainMenuController.db.deleteOwnership(id);
        updateScreen();
    }

    public void refresh(){
        updateScreen();
    }

    public int getstripposition(String a){
        return a.indexOf("-");
    } //Gets the position of the string "-" which is used to substring the string to get the position of the id

    public static void alertMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
