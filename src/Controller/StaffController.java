package Controller;

import Main.Main;
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

public class StaffController implements Initializable {

    @FXML
    private AnchorPane Pane_Staff;

    @FXML
    private ComboBox<String> Combo_OwnerId;

    @FXML
    private ComboBox<String> Combo_StoreId;

    @FXML
    private TableView<Staff> TableView_Staff;

    @FXML
    private TableColumn<Staff, Integer> Col_StaffId;

    @FXML
    private TableColumn<Staff, String> Col_StaffName;

    @FXML
    private TableColumn<Staff, Integer> Col_StoreId;

    @FXML
    private TableColumn<Staff, Integer> Col_OwnerId;

    @FXML
    private TableColumn<Staff, String> Col_JobType;

    @FXML
    private TableColumn<Staff, Integer> Col_Salary;

    @FXML
    private TextField Text_StaffId;

    @FXML
    private TextField Text_StaffName;

    @FXML
    private TextField Text_JobType;

    @FXML
    private TextField Text_Salary;

    @FXML
    private Button Button_Insert;

    @FXML
    private Button Button_Update;

    @FXML
    private Button Button_Delete;

    @FXML
    private Button Button_MainMenu;

    @FXML
    private Button Button_Refresh;

    @FXML
    void BackToMenu(MouseEvent event) throws IOException { //GO back to Menu
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../GUI/MainMenu.fxml"));
        Pane_Staff.getChildren().setAll(pane);
    }

    ObservableList<Staff> oblist = FXCollections.observableArrayList();
    ObservableList<String> oblistStore = FXCollections.observableArrayList();
    ObservableList<String> oblistOwner = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Col_StaffId.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        Col_StaffName.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        Col_StoreId.setCellValueFactory(new PropertyValueFactory<>("storeId"));
        Col_OwnerId.setCellValueFactory(new PropertyValueFactory<>("ownerId"));
        Col_JobType.setCellValueFactory(new PropertyValueFactory<>("jobType"));
        Col_Salary.setCellValueFactory(new PropertyValueFactory<>("salaryMonthly"));
        Combo_StoreId.setItems(oblistStore);
        Combo_OwnerId.setItems(oblistOwner);
        Combo_StoreId.setVisibleRowCount(4);
        Combo_OwnerId.setVisibleRowCount(4);
        TableView_Staff.setItems(oblist);
        updateScreen();

    }

    private void updateScreen() {
        TableView_Staff.getItems().clear();
        oblist.clear();
        oblistStore.clear();
        oblistOwner.clear();
        TableView_Staff.getSelectionModel().clearSelection();
        try {
            MainMenuController.db.getStaffTable();
            while (MainMenuController.db.rs.next()) {
                oblist.add(new Staff(MainMenuController.db.rs.getInt("Staff_Id"),
                        MainMenuController.db.rs.getString("Staff_Name"),
                        MainMenuController.db.rs.getInt("Store_Id"),
                        MainMenuController.db.rs.getInt("Owner_Id"),
                        MainMenuController.db.rs.getString("Job_Type"),
                        MainMenuController.db.rs.getInt("Salary")));
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

    public void InsertClick() throws  SQLException{ //For Insert
        String Staff_Id = Text_StaffId.getText();
        String Staff_Name = Text_StaffName.getText();
        String Store_Id = Combo_StoreId.getSelectionModel().getSelectedItem();
        Store_Id = Store_Id.substring(0, getstripposition(Store_Id));
        String Owner_Id = Combo_OwnerId.getSelectionModel().getSelectedItem();
        Owner_Id = Owner_Id.substring(0, getstripposition(Owner_Id));
        String Job_Type = Text_JobType.getText();
        String Salary = Text_Salary.getText();

        if(Staff_Name.isEmpty() || Job_Type.isEmpty() || Salary.isEmpty() || Store_Id == null || Owner_Id == null) {
            alertMessage("[Staff_Id], [Staff_Name], [Store_Id], [Owner_Id], [Job_Type], and [Monthly_Salary] are required fields. Please fill them to proceed.");
        }
        else {
            Text_StaffId.clear();
            Text_StaffName.clear();
            Combo_StoreId.getSelectionModel().clearSelection();
            Combo_OwnerId.getSelectionModel().clearSelection();
            Text_JobType.clear();
            Text_Salary.clear();
            MainMenuController.db.insertStaff(Staff_Id, Staff_Name, Store_Id, Owner_Id, Job_Type, Salary);
            updateScreen();
        }
    }

    public void Update(){
        Staff object = TableView_Staff.getSelectionModel().getSelectedItem();
        String staff_Id = Text_StaffId.getText();
        String staff_Name = Text_StaffName.getText();

        String store_Id = Combo_StoreId.getSelectionModel().getSelectedItem();
        if(store_Id == null){
            store_Id = "";
        }
        else{
            try {
                store_Id = store_Id.substring(0, getstripposition(store_Id));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String owner_Id = Combo_OwnerId.getSelectionModel().getSelectedItem();
        if(owner_Id == null){
            owner_Id = "";
        }
        else {
            try {
                owner_Id = owner_Id.substring(0, getstripposition(owner_Id));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String job_Type = Text_JobType.getText();
        String salary = Text_Salary.getText();

        Text_StaffId.clear();
        Text_StaffName.clear();
        Combo_StoreId.getSelectionModel().clearSelection();
        Combo_OwnerId.getSelectionModel().clearSelection();
        Text_JobType.clear();
        Text_Salary.clear();
        MainMenuController.db.updateStaff(object, staff_Id, staff_Name, store_Id, owner_Id, job_Type, salary);
        updateScreen();
    }

    public void DeleteSelected(){ //For Delete
        int id=TableView_Staff.getSelectionModel().getSelectedItem().getStaffId();
        MainMenuController.db.DeleteStaff(id);
        updateScreen();
    }

    public void Refresh(){
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
