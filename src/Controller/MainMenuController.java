package Controller;

import DB.DB;
import Main.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    public static DB db=new DB();

    @FXML
    private AnchorPane MainMenuPane;
    @FXML
    private Pane InsidePane;


    @FXML
    private Button StoreButton;

    @FXML
    private Button OwnerButton;

    @FXML
    private Button StaffButton;

    @FXML
    private Button SupplierButton;

    @FXML
    private Button ItemButton;

    @FXML
    private Button TransactionTypeButton;

    @FXML
    private Button TransactionButton;

    @FXML
    void SupplierScene(MouseEvent event) throws IOException { //Change to Supplier Pane
        AnchorPane pane=FXMLLoader.load(getClass().getResource("../GUI/Supplier.fxml"));
        MainMenuPane.getChildren().setAll(pane);
    }
    @FXML
    void TransactionTypeScene(MouseEvent event) throws IOException { //Change to Transaction Type Pane
        AnchorPane pane=FXMLLoader.load(getClass().getResource("../GUI/Transaction_Type.fxml"));
        MainMenuPane.getChildren().setAll(pane);
    }
    @FXML
    void ItemMenu(MouseEvent event) throws IOException, SQLException { //Change to ItemMenu pane
        AnchorPane pane=FXMLLoader.load(getClass().getResource("../GUI/Item.fxml"));
        MainMenuPane.getChildren().setAll(pane);
    }
    @FXML
    void TransactionMenu(MouseEvent event) throws IOException, SQLException {// Change to TransactionMenu Pane
        AnchorPane pane=FXMLLoader.load(getClass().getResource("../GUI/Transaction.fxml"));
        MainMenuPane.getChildren().setAll(pane);
    }
    @FXML
    void OwnerScene(MouseEvent event) throws IOException, SQLException {// Change to TransactionMenu Pane
        AnchorPane pane=FXMLLoader.load(getClass().getResource("../GUI/Owner.fxml"));
        MainMenuPane.getChildren().setAll(pane);
    }
    @FXML
    void StoreScene(MouseEvent event) throws IOException, SQLException {// Change to TransactionMenu Pane
        AnchorPane pane=FXMLLoader.load(getClass().getResource("../GUI/Store.fxml"));
        MainMenuPane.getChildren().setAll(pane);
    }
    @FXML
    void OwnershipScene(MouseEvent event) throws IOException, SQLException {// Change to TransactionMenu Pane
        AnchorPane pane=FXMLLoader.load(getClass().getResource("../GUI/Ownership.fxml"));
        MainMenuPane.getChildren().setAll(pane);
    }
    @FXML
    void StaffScene(MouseEvent event) throws IOException, SQLException {// Change to TransactionMenu Pane
        AnchorPane pane=FXMLLoader.load(getClass().getResource("../GUI/Staff.fxml"));
        MainMenuPane.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            db.connectDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
