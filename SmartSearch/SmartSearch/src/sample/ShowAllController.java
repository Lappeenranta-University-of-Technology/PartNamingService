package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowAllController implements Initializable {

    private int serialNO;

    @FXML
    private JFXTextField prodetailSearchBar;

    @FXML
    private TableView<Hardware> proDetailTable;

    @FXML
    private TableColumn<Hardware, Void> serialNumber;

    @FXML
    private TableColumn<Hardware, Integer> proDetailID;

    @FXML
    private TableColumn<Hardware, String> proDetailDia;

    @FXML
    private TableColumn<Hardware, String> proDetailPit;

    @FXML
    private TableColumn<Hardware, String> proDetailB;

    @FXML
    private TableColumn<Hardware, String> proDetailK;

    @FXML
    private TableColumn<Hardware, String> proDetailDK;

    @FXML
    private TableColumn<Hardware, String> proDetailA;

    @FXML
    private TableColumn<Hardware, String> proDetailS;

    @FXML
    private TableColumn<Hardware, String> proDetailLen;

    @FXML
    private TableColumn<Hardware, String> proDetailHead;

    @FXML
    private TableColumn<Hardware, String> proDetailSocket;

    @FXML
    private TableColumn<Hardware, String> proDetailThread;

    @FXML
    private TableColumn<Hardware, Integer> proDetailQuantity;

    @FXML
    private JFXButton projectsDetailsToHome;

    @FXML
    private JFXButton btnImport;

    @FXML
    private JFXButton delProjectDetails;

    @FXML
    private JFXButton editProjectDetails;

    @FXML
    private JFXButton refreshProjectDetails;

    @FXML
    private JFXButton addProjectDetails;

    @FXML
    private Text projectId;

    @FXML
    private Text projectName;

    @FXML
    private Text projectZone;

    @FXML
    private Text projectScope;

    @FXML
    private JFXTextField fieldAddHardwareDia;

    @FXML
    private JFXTextField fieldAddHardwarePit;

    @FXML
    private JFXButton btnHardwareAdd;

    @FXML
    private JFXButton btnHardwareBack;

    @FXML
    private JFXTextField fieldAddHardwareProID;

    @FXML
    private JFXTextField fieldAddHardwareB;

    @FXML
    private JFXTextField fieldAddHardwareDk;

    @FXML
    private JFXTextField fieldAddHardwareK;

    @FXML
    private JFXTextField fieldAddHardwareA;

    @FXML
    private JFXTextField fieldAddHardwareS;

    @FXML
    private JFXTextField fieldAddHardwareLen;

    @FXML
    private JFXTextField fieldAddHardwareHead;

    @FXML
    private JFXTextField fieldAddHardwareSocket;

    @FXML
    private JFXTextField fieldAddHardwareThread;

    @FXML
    private JFXTextField fieldAddHardwareQuantity;


    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Hardware hardware = null;
    private boolean update;
    int ProjectID;

    String idd;
    ObservableList<Hardware> listH;


    void setTextField(int id, String name, String zone, String scope) {
        idd = String.valueOf(id);
        System.out.println("ABCD");
        listH = DBConnection.getDataHardware(idd);
        System.out.println(idd + "after abcd");
        proDetailTable.setItems(listH);
        projectId.setText(String.valueOf(id));
        projectName.setText(name);
        projectZone.setText(zone);
        projectScope.setText(scope);

        System.out.println("ddddddddddddddddddddddddddddddddddddddddddddddddddddd");

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Hardware> filteredData = new FilteredList<>(listH, b -> true);
        System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        System.out.println(listH);

        // 2. Set the filter Predicate whenever the filter changes.
        prodetailSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(hard -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Converting to lower case
                String lowerCaseFilter = newValue.toLowerCase();

                if (hard.getHead().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;//Filter matches hardware_name
                } else if (hard.getDiameter().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;//Filter matches hardware_type
                } else
                    return false;//Nothing matches
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Hardware> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(proDetailTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        proDetailTable.setItems(sortedData);

    }

    private Callback<TableColumn<Hardware, Void>, TableCell<Hardware, Void>> indexCellFactory() {
        return t -> new TableCell<Hardware, Void>() {

            @Override
            public void updateIndex(int i) {
                super.updateIndex(i);
                setText(isEmpty() ? "" : Integer.toString(i + 1));
                serialNO = i;
            }

        };
    }



    int index = -1;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    CalHandler calHandler = new CalHandler();

    void refreshTable(){

        proDetailID.setCellValueFactory(new PropertyValueFactory<Hardware, Integer>("ID"));
        proDetailDia.setCellValueFactory(new PropertyValueFactory<Hardware, String>("Diameter"));
        proDetailPit.setCellValueFactory(new PropertyValueFactory<Hardware, String>("Pitch"));
        proDetailB.setCellValueFactory(new PropertyValueFactory<Hardware, String>("B"));
        proDetailK.setCellValueFactory(new PropertyValueFactory<Hardware, String>("K"));
        proDetailDK.setCellValueFactory(new PropertyValueFactory<Hardware, String>("DK"));
        proDetailA.setCellValueFactory(new PropertyValueFactory<Hardware, String>("A"));
        proDetailS.setCellValueFactory(new PropertyValueFactory<Hardware, String>("S"));
        proDetailLen.setCellValueFactory(new PropertyValueFactory<Hardware, String>("Length"));
        proDetailHead.setCellValueFactory(new PropertyValueFactory<Hardware, String>("Head"));
        proDetailSocket.setCellValueFactory(new PropertyValueFactory<Hardware, String>("Socket"));
        proDetailThread.setCellValueFactory(new PropertyValueFactory<Hardware, String>("Type"));
        proDetailQuantity.setCellValueFactory(new PropertyValueFactory<Hardware, Integer>("Quan"));

        serialNumber.setCellFactory(indexCellFactory());

        listH = DBConnection.getDataHardware(idd);
        proDetailTable.setItems(listH);

        System.out.println("ddddddddddddddddddddddddddddddddddddddddddddddddddddd");

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Hardware> filteredData = new FilteredList<>(listH, b -> true);
        System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        System.out.println(listH);

        // 2. Set the filter Predicate whenever the filter changes.
        prodetailSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(hard -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Converting to lower case
                String lowerCaseFilter = newValue.toLowerCase();

                if (hard.getHead().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;//Filter matches hardware_name
                } else if (hard.getDiameter().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;//Filter matches hardware_type
                } else
                    return false;//Nothing matches
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Hardware> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(proDetailTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        proDetailTable.setItems(sortedData);


    }

    @FXML
    public void addHardware(){
        System.out.println("Trying to add");
        conn = DBConnection.ConnectDb();
        System.out.println("Trying to add2");

        String sql = "INSERT INTO `hardwares`(`Project_Id`, `Diameter`, `Pitch`, `B`, `K`, `DK`, `A`, `S`, `Total_Length`, `Head`, `Socket`, `Type`, `Quan`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
            pst = conn.prepareStatement(sql);
            System.out.println("Trying to add4");
            pst.setString(1, fieldAddHardwareProID.getText());
            pst.setString(2, fieldAddHardwareDia.getText());
            pst.setString(3, fieldAddHardwarePit.getText());
            pst.setString(4, fieldAddHardwareB.getText());
            pst.setString(5, fieldAddHardwareK.getText());
            pst.setString(6, fieldAddHardwareDk.getText());
            pst.setString(7, fieldAddHardwareA.getText());
            pst.setString(8, fieldAddHardwareS.getText());
            pst.setString(9, fieldAddHardwareLen.getText());
            pst.setString(10, fieldAddHardwareHead.getText());
            pst.setString(11, fieldAddHardwareSocket.getText());
            pst.setString(12, fieldAddHardwareThread.getText());
            pst.setString(13, fieldAddHardwareQuantity.getText());

            System.out.println("Trying to add5");
            pst.execute();

            String toastMsg = "Added";
            int toastMsgTime = 2500; //3.5 seconds
            int fadeInTime = 400; //0.5 seconds
            int fadeOutTime= 400; //0.5 seconds
            Toast.makeText(null, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + "\n Please Enter Valid Values");
        }
    }




    @FXML
    public void getSelected (MouseEvent e){
        System.out.println("Selected");
        index = proDetailTable.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(index <= -1){

            return;
        }

//        fieldProId = (proIDTable.getCellData(index).toString());
//        fieldProName = (proNameTable.getCellData(index).toString());
//        fieldProZone = (proZoneTable.getCellData(index).toString());
//        fieldProScope = (proScopeTable.getCellData(index).toString());



        editProjectDetails.setOnAction(
                event -> {
                    hardware = proDetailTable.getSelectionModel().getSelectedItem();
                    Node node = (Node) e.getSource();
                    Stage Pstage = (Stage) node.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader ();
                    loader.setLocation(getClass().getResource("FXML/EditHardware.fxml"));
                    try {
                        loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    EditHardwareController addHardwareController = loader.getController();
                    addHardwareController.setTextField(hardware.getID(), hardware.getDiameter(),
                            hardware.getPitch(), hardware.getB(), hardware.getK(), hardware.getDK(),
                            hardware.getA(), hardware.getS(), hardware.getLength(), hardware.getHead(),
                            hardware.getSocket(), hardware.getType(), hardware.getQuan());
                    Parent parent = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(parent));
                    stage.initOwner(Pstage);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.show();

                });

        refreshProjectDetails.setOnAction(
                actionEvent -> {
                    refreshTable();
                }
        );

        delProjectDetails.setOnAction(
                actionEvent -> {


                    conn= DBConnection.ConnectDb();
                    String sql = "delete from hardwares where Hardware_Id = ?";
                    try{
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, (proDetailID.getCellData(index).toString()));
                        pst.execute();
                        String toastMsg = "Deleted";
                        int toastMsgTime = 2500; //3.5 seconds
                        int fadeInTime = 400; //0.5 seconds
                        int fadeOutTime= 400; //0.5 seconds
                        Toast.makeText(null, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
                    }catch (Exception c){
                        JOptionPane.showMessageDialog(null, c);
                    }
                }
        );


    }

//    @FXML
//    private void getUpdate(ActionEvent e) {
//        try {
//            connection = DBConnection.ConnectDb();
//            String value1 = editProjectID.getText();
//            String value2 = editProjectName.getText();
//            String value3 = editProjectZone.getText();
//            String value4 = editProjectScope.getText();
//
//
//            query = "UPDATE project SET Project_Id='" + value1 + "',Project_Name='" + value2 + "',Project_Zone='" + value3 + "',Project_Scope='" + value4 + "' where Project_Id='" + value1 + "' ";
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.execute();
//
//            JOptionPane.showMessageDialog(null, "Updated");
//
//            Node node = (Node) e.getSource();
//            Stage thisStage = (Stage) node.getScene().getWindow();
//            thisStage.hide();
//
//        } catch (Exception b) {
//            JOptionPane.showMessageDialog(null, b);
//        }
//
//
//    }




    void setUpdate(boolean b) {
        this.update = b;

    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        if(projectsDetailsToHome!= null) {
            System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
            projectsDetailsToHome.setOnAction(calHandler);
            editProjectDetails.setOnAction(calHandler);
            addProjectDetails.setOnAction(calHandler);
            refreshProjectDetails.setOnAction(calHandler);
            System.out.println("cccccccccccccccccccccccccccccccccccccccccccccccccccccc");
            refreshTable();


        }



        if(btnHardwareBack!= null) {
            btnHardwareBack.setOnAction(calHandler);
        }

    }

    class CalHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            try {
//                if (e.getSource() == projectDetails) {
//                    Node node = (Node) e.getSource();
//                    Stage thisStage = (Stage) node.getScene().getWindow();
//                    thisStage.hide();
//                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/ProjectDetails.fxml"));
//                    Parent root1 = (Parent) fxmlLoader.load();
////                    Stage stage = new Stage();
//                    thisStage.setScene(new Scene(root1));
//                    thisStage.setResizable(false);
//                    thisStage.show();
               if(e.getSource() == projectsDetailsToHome){
                    Node node = (Node) e.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
//                    thisStage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/Projects.fxml"));
                    Parent root11 = (Parent) fxmlLoader.load();
//                    Stage stage = new Stage();
                    stage.setScene(new Scene(root11));
                }else if (e.getSource() == addProjectDetails){
                   Node node = (Node) e.getSource();
                   Stage Pstage = (Stage) node.getScene().getWindow();
                   FXMLLoader loader = new FXMLLoader ();
                   loader.setLocation(getClass().getResource("FXML/AddHardware.fxml"));
                   try {
                       loader.load();
                   } catch (IOException ex) {
                       Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                   }
                   Parent parent = loader.getRoot();
                   Stage stage = new Stage();
                   stage.setScene(new Scene(parent));
                   stage.initOwner(Pstage);
                   stage.initModality(Modality.WINDOW_MODAL);
                   stage.show();
                }else if(e.getSource() == btnHardwareBack){
                   Node node = (Node) e.getSource();
                   Stage stage = (Stage) node.getScene().getWindow();
                    stage.hide();
               }else if(e.getSource() == refreshProjectDetails){
                   refreshTable();
               }
            }//try

            catch(Exception x){
                // System.out.println("PANGY NA LO");
                Alert a = new Alert(Alert.AlertType.NONE);

                // set alert type
                a.setAlertType(Alert.AlertType.WARNING);

                a.setContentText("Only perform one operation at a time \n Clear your wrong operations by hitting C button ");


                // show the dialog
                a.show();
            }//catch
        }//handler
    }//inner class
}
