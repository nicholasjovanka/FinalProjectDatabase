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

public class OwnerController implements Initializable {

    @FXML
    private AnchorPane Pane_Owner;

    @FXML
    private TableView<Owner> TableView_Owner;

    @FXML
    private TableColumn<?, ?> Col_OwnerId;

    @FXML
    private TableColumn<?, ?> Col_OwnerName;

    @FXML
    private Button Button_Insert;

    @FXML
    private Button Button_Delete;

    @FXML
    private Button Button_Menu;

    @FXML
    private Button Button_Refresh;

    @FXML
    private TextField Text_Id;

    @FXML
    private Button Button_Update;

    @FXML
    private TextField Text_Name;

    ObservableList<Owner> oblist = FXCollections.observableArrayList();

    @FXML
    void BackToMenu(MouseEvent event) throws IOException { //GO back to Menu
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/MainMenu.fxml"));
        Pane_Owner.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { //Initialize the Owner Table
        Col_OwnerId.setCellValueFactory(new PropertyValueFactory<>("OwnerId"));
        Col_OwnerName.setCellValueFactory(new PropertyValueFactory<>("OwnerName"));
        TableView_Owner.setItems(oblist);
        updateScreen();

    }


    public void updateScreen() { //Get The Owner Table Value
        TableView_Owner.getItems().clear();
        oblist.clear();
        TableView_Owner.getSelectionModel().clearSelection();
        try {
            MainMenuController.db.getOwnerTable();
            while (MainMenuController.db.rs.next()) {
                oblist.add(new Owner(MainMenuController.db.rs.getInt("Owner_Id"), MainMenuController.db.rs.getString("Owner_Name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void InsertClick() throws  SQLException{ //For Insert
        String Owner_Id=Text_Id.getText();
        String Owner_Name=Text_Name.getText();
        if(Owner_Name.isEmpty()){
            alertMessage("You must fill all the required field to insert");
        }
        else {
            Text_Id.clear();
            Text_Name.clear();
            MainMenuController.db.InsertOwner(Owner_Id, Owner_Name);
            updateScreen();
        }
    }

    public void DeleteSelected(){ //For Delete
        int id=TableView_Owner.getSelectionModel().getSelectedItem().getOwnerId();
        MainMenuController.db.DeleteOwner(id);
        updateScreen();
    }

    public void Refresh(){
        updateScreen();
    }

    public void Update(){
        Owner object=TableView_Owner.getSelectionModel().getSelectedItem();
        String Owner_Id=Text_Id.getText();
        String Owner_Name=Text_Name.getText();
        Text_Id.clear(); Text_Name.clear();
        MainMenuController.db.UpdateOwner(object,Owner_Id,Owner_Name);
        updateScreen();

    }
    public static void alertMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

}