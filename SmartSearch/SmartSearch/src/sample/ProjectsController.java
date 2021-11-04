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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
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

public class ProjectsController implements Initializable {

    @FXML
    private JFXButton editProjectRefresh;

    @FXML
    private JFXButton btnProjectRefresh;

    @FXML
    private JFXButton btnProjectEdit;

    @FXML
    private JFXButton btnProjectDelete;

    @FXML
    private JFXButton homeNewProject;

    @FXML
    private JFXButton projectDetails;
    @FXML
    private JFXButton projectsToHome;

    @FXML
    private JFXButton projectsDetailsToHome;

    @FXML
    private JFXButton delProjectDetails;
    @FXML
    private JFXButton editProjectDetails;
    @FXML
    private JFXButton addProjectDetails;

    @FXML
    private JFXButton backEditHard;

    @FXML
    private JFXButton backAddHard;



    @FXML
    public Text projectName;

    @FXML
    public Text projectZone;

    @FXML
    public Text projectScope;

    @FXML
    private JFXTextField addProjectScope;

    @FXML
    private JFXTextField addProjectZone;

    @FXML
    private JFXTextField addProjectName;

    @FXML
    private JFXTextField addProjectID;

    @FXML
    public JFXTextField editProjectScope;

    @FXML
    public JFXTextField editProjectZone;

    @FXML
    public JFXTextField editProjectName;

    @FXML
    public JFXTextField editProjectID;

    //projectTable

    private int serialNO;

    Project project = null;

    @FXML
    private JFXTextField projectSearchBar;

    @FXML
    private TableView<Project> projectTable;

    @FXML
    private TableColumn<Project, Integer> proIDTable;

    @FXML
    private TableColumn<Project, String> proNameTable;

    @FXML
    private TableColumn<Project, String> proZoneTable;

    @FXML
    private TableColumn<Project, String> proScopeTable;

    public static String idd;

    
//   public String fieldProId;
//    public String fieldProName;
//    public String fieldProZone;
//    public String fieldProScope;

    @FXML
    public void addProjects(){
        conn = DBConnection.ConnectDb();
        String sql = "INSERT INTO `project`(`Project_Name`, `Project_Zone`, `Project_Scope`) VALUES (?,?,?)";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, addProjectName.getText());
            pst.setString(2, addProjectZone.getText());
            pst.setString(3, addProjectScope.getText());
            pst.execute();

            String toastMsg = "Added";
            int toastMsgTime = 2500; //3.5 seconds
            int fadeInTime = 400; //0.5 seconds
            int fadeOutTime= 400; //0.5 seconds
            Toast.makeText(null, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }



    @FXML
   public void getSelected (MouseEvent e){
        System.out.println("Selected");
        index = projectTable.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(index <= -1){

            return;
        }

//        fieldProId = (proIDTable.getCellData(index).toString());
//        fieldProName = (proNameTable.getCellData(index).toString());
//        fieldProZone = (proZoneTable.getCellData(index).toString());
//        fieldProScope = (proScopeTable.getCellData(index).toString());


        btnProjectEdit.setOnAction(
                event -> {
                    project = projectTable.getSelectionModel().getSelectedItem();
                    FXMLLoader loader = new FXMLLoader ();
                    loader.setLocation(getClass().getResource("FXML/EditProject.fxml"));
                    try {
                        loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    EditProjectController addStudentController = loader.getController();
                    addStudentController.setUpdate(true);
                    addStudentController.setTextField(project.getID(), project.getProject(),
                            project.getZone(), project.getScope());
                    Parent parent = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(parent));
                    stage.show();

                });

        btnProjectRefresh.setOnAction(
                actionEvent -> {
                    refreshTable();
                }
        );

        btnProjectDelete.setOnAction(
                actionEvent -> {
                    conn= DBConnection.ConnectDb();
                    String sqll = "delete from hardwares where Project_ID = ?";
                    String sql = "delete from project where Project_ID = ?";
                    try{
                        pstt = conn.prepareStatement(sqll);
                        pstt.setString(1, (proIDTable.getCellData(index).toString()));
                        pstt.execute();
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, (proIDTable.getCellData(index).toString()));
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

        projectDetails.setOnAction(
                actionEvent -> {
                    Node node = (Node) actionEvent.getSource();
                    Stage thisStage = (Stage) node.getScene().getWindow();
                    thisStage.hide();
                    project = projectTable.getSelectionModel().getSelectedItem();
                    FXMLLoader loader = new FXMLLoader ();
                    loader.setLocation(getClass().getResource("FXML/ProjectDetails.fxml"));
                    try {
                        loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    ShowAllController addController = loader.getController();
                    addController.setTextField(project.getID(), project.getProject(),
                            project.getZone(), project.getScope());

                    Parent parent = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(parent));
                    stage.show();
                    }

        );
    }








    //projectTable




    //projectDetailTable
    @FXML
    private JFXTextField prodetailSearchBar;

    @FXML
    private TableView<Hardware> proDetailTable;

    @FXML
    private TableColumn<?, ?> proDetailID;

    @FXML
    private TableColumn<?, ?> proDetailDia;

    @FXML
    private TableColumn<?, ?> proDetailPit;

    @FXML
    private TableColumn<?, ?> proDetailTor;

    @FXML
    private TableColumn<?, ?> proDetailB;

    @FXML
    private TableColumn<?, ?> proDetailS;

    @FXML
    private TableColumn<?, ?> proDetailLen;

    @FXML
    private TableColumn<?, ?> proDetailHead;

    @FXML
    private TableColumn<?, ?> proDetailSocket;

    @FXML
    private TableColumn<?, ?> proDetailThread;
    //projectDetailTable

    ObservableList<Project> listP;

    int index = -1;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    PreparedStatement pstt = null;


    CalHandler calHandler = new CalHandler();

     void refreshTable(){
        proIDTable.setCellValueFactory(new PropertyValueFactory<Project, Integer>("ID"));
        proNameTable.setCellValueFactory(new PropertyValueFactory<Project, String>("Project"));
        proZoneTable.setCellValueFactory(new PropertyValueFactory<Project, String>("Zone"));
        proScopeTable.setCellValueFactory(new PropertyValueFactory<Project, String>("Scope"));
        System.out.println("ABCD");
        listP = DBConnection.getDataprojects();
        System.out.print(listP);
        projectTable.setItems(listP);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        
        //PROJECTS
        if(projectDetails!= null) {
            projectDetails.setOnAction(calHandler);
            projectsToHome.setOnAction(calHandler);
            btnProjectDelete.setOnAction(calHandler);
            btnProjectEdit.setOnAction(calHandler);

            refreshTable();

            // Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Project> filteredData = new FilteredList<>(listP, b -> true);

            // 2. Set the filter Predicate whenever the filter changes.
            projectSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(project -> {

                    // If filter text is empty, display all persons.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Converting to lower case
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (project.getProject().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;//Filter matches hardware_name
                    } else if (project.getZone().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;//Filter matches hardware_type
                    } else
                        return false;//Nothing matches
                });
            });

            // 3. Wrap the FilteredList in a SortedList.
            SortedList<Project> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            // 	  Otherwise, sorting the TableView would have no effect.
            sortedData.comparatorProperty().bind(projectTable.comparatorProperty());

            // 5. Add sorted (and filtered) data to the table.
            projectTable.setItems(sortedData);

        }
            //ProjectDetails
        if(projectsDetailsToHome!= null) {
            projectsDetailsToHome.setOnAction(calHandler);
        }

         //HardwareDetails
        if(editProjectDetails!= null) {
            editProjectDetails.setOnAction(calHandler);
            addProjectDetails.setOnAction(calHandler);
        }

        //EditHardware
        if(backEditHard!= null) {
            backEditHard.setOnAction(calHandler);
        }

        //AddHardware
        if(backAddHard!= null) {
            backAddHard.setOnAction(calHandler);
        }

        //NewProject
        if(homeNewProject!= null) {
            homeNewProject.setOnAction(calHandler);
        }

        //EditProject
        if(btnProjectEdit!= null) {
            btnProjectEdit.setOnAction(calHandler);
        }



    }




    private Callback<TableColumn<Project, Void>, TableCell<Project, Void>> indexCellFactory() {
        return t -> new TableCell<Project, Void>() {

            @Override
            public void updateIndex(int i) {
                super.updateIndex(i);
                setText(isEmpty() ? "" : Integer.toString(i + 1));
                serialNO = i;
            }

        };
    }

    class CalHandler implements EventHandler<javafx.event.ActionEvent> {

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
                if(e.getSource() == projectsToHome){
                    Node node = (Node) e.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
//                    thisStage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/Main.fxml"));
                    Parent root11 = (Parent) fxmlLoader.load();
//                    Stage stage = new Stage();
                    stage.setScene(new Scene(root11));
                }else if(e.getSource() == projectsDetailsToHome){
                    Node node = (Node) e.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
//                    thisStage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/Projects.fxml"));
                    Parent root11 = (Parent) fxmlLoader.load();
//                    Stage stage = new Stage();
                    stage.setScene(new Scene(root11));
                }else if(e.getSource() == editProjectDetails){
                    Node node = (Node) e.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
//                    thisStage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/EditHardware.fxml"));
                    Parent root11 = (Parent) fxmlLoader.load();
//                    Stage stage = new Stage();
                    stage.setScene(new Scene(root11));
                }else if(e.getSource() == addProjectDetails){
                    Node node = (Node) e.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
//                    thisStage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/AddHardware.fxml"));
                    Parent root11 = (Parent) fxmlLoader.load();
//                    Stage stage = new Stage();
                    stage.setScene(new Scene(root11));
                }else if(e.getSource() == backEditHard || e.getSource() == backAddHard){
                    Node node = (Node) e.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
//                    thisStage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/ProjectDetails.fxml"));
                    Parent root11 = (Parent) fxmlLoader.load();
//                    Stage stage = new Stage();
                    stage.setScene(new Scene(root11));
                }else if(e.getSource() == homeNewProject){
                    Node node = (Node) e.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
//                    thisStage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/Main.fxml"));
                    Parent root11 = (Parent) fxmlLoader.load();
//                    Stage stage = new Stage();
                    stage.setScene(new Scene(root11));
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
