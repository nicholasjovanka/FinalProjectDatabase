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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Transaction_Type_Controller implements Initializable {
    @FXML
    private AnchorPane TransactionTypePane;
    @FXML
    private TableView<TransactionType> TransactionTypeTableView;

    @FXML
    private TableColumn<TransactionType, Integer> Transaction_Type_Id;

    @FXML
    private TableColumn<TransactionType, String> Transaction_Type;
    @FXML
    private Button InsertButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button Menu;
    @FXML
    private Button Refresh;
    @FXML
    private TextField IdText;
    @FXML
    private TextField TypeText;
    ObservableList<TransactionType> oblist = FXCollections.observableArrayList();

    @FXML
    void BackToMenu(MouseEvent event) throws IOException { //Change to Main Menu
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/MainMenu.fxml"));
        TransactionTypePane.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Transaction_Type_Id.setCellValueFactory(new PropertyValueFactory<>("TransactionTypeId"));
        Transaction_Type.setCellValueFactory(new PropertyValueFactory<>("TransactionType"));
        TransactionTypeTableView.setItems(oblist);
        ArrayList<String>mode=new ArrayList<>();
        updateScreen();

    }


    public void updateScreen() { //Gets all the table from Transaction_Type
        TransactionTypeTableView.getItems().clear();
        oblist.clear();
        TransactionTypeTableView.getSelectionModel().clearSelection();
        try {
            MainMenuController.db.getTransactionTypeTable();
            while (MainMenuController.db.rs.next()) {
                oblist.add(new TransactionType(MainMenuController.db.rs.getInt("Transaction_Type_Id"), MainMenuController.db.rs.getString("Transaction_Type")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void InsertClick() throws  SQLException{ //For Insert
        String Id=IdText.getText();
        String Type=TypeText.getText();
        if(Type.isEmpty()){
            alertMessage("Please insert all the required field");
        }
        else {
            IdText.clear();
            TypeText.clear();
            MainMenuController.db.InsertTransactionType(Id, Type);
            updateScreen();
        }
    }

    public void DeleteSelected(){ //Delete the selected Transaction Type
        int id=TransactionTypeTableView.getSelectionModel().getSelectedItem().getTransactionTypeId();
        MainMenuController.db.DeleteTransactionType(id);
        updateScreen();
    }

    public void Refresh(){
        updateScreen();
    }


    public static void alertMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

//    private void AlertUpdate(String)

}

