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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Controller implements Initializable {

    private int serialNO;

    @FXML
    private ImageView i;

    @FXML
    private JFXButton search;

    @FXML
    private JFXButton btnIn;

    @FXML
    private JFXButton viewProjects;

    @FXML
    private JFXButton newProject;

    @FXML
    private JFXButton searchToHome;

    @FXML
    private JFXButton allSearchToEdit;

    @FXML
    private JFXButton ok;

    @FXML
    private JFXButton settings;

    @FXML
    private JFXButton importFile;

    @FXML
    private JFXButton allSearchRefresh;

    @FXML
    private JFXButton btnSettingsToHome;

    @FXML
    private JFXTextField searchBar;

    @FXML
    private JFXTextField pswd;

    File file;

    @FXML
    private Text longName;

    @FXML
    private Text originalNameLabel;




    //TABLE

    @FXML
    private TableColumn<Hardware, Integer> hardwareQuantityTable;

    @FXML
    private TableView<Hardware> table;

    @FXML
    private TableColumn<Hardware, Void> serialNumber;

    @FXML
    private TableColumn<Hardware, Integer> hardwareIdTable;

    @FXML
    private TableColumn<Hardware, String> hardwareNameTable;

    @FXML
    private TableColumn<Hardware, Integer> colProjectId;

    @FXML
    private TableColumn<Hardware, String> diameterTable;

    @FXML
    private TableColumn<Hardware, String> lengthTable;

    @FXML
    private TableColumn<Hardware, String> pitchTable;

    @FXML
    private TableColumn<Hardware, String> headTable;
    //TABLE


    //TABLEDETAILS
    @FXML
    private Text dSNo;

    @FXML
    private Text dCat;

    @FXML
    private JFXTextField enterProId;

    @FXML
    private Text fProId;

    @FXML
    private Text fDia;

    @FXML
    private Text fPit;

    @FXML
    private Text fB;

    @FXML
    private Text fK;

    @FXML
    private Text fDK;

    @FXML
    private Text fProZone;

    @FXML
    private Text fProScope;

    @FXML
    private Text fA;

    @FXML
    private Text fS;

    @FXML
    private Text fLen;

    @FXML
    private Text fHead;

    @FXML
    private Text fSocket;

    @FXML
    private Text fThread;
    //TABLEDETAILS


    @FXML
    private JFXButton resetProjectId;

    @FXML
    private JFXButton resetHardwareId;

    Hardware hardware = null;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    String password = "111";




    private Connection connection;
    private DBConnection dbConntect;

    //observable list to store data
    ObservableList<Hardware> listH;




    CalHandler calHandler = new CalHandler();


    @FXML
    void refreshTable(){
        hardwareIdTable.setCellValueFactory(new PropertyValueFactory<Hardware, Integer>("ID"));
        hardwareNameTable.setCellValueFactory(new PropertyValueFactory<Hardware, String>("Name"));
        hardwareQuantityTable.setCellValueFactory(new PropertyValueFactory<Hardware, Integer>("Quan"));


        serialNumber.setCellFactory(indexCellFactory());

        listH = (ObservableList<Hardware>) DBConnection.getDataAllHardware();
        table.setItems(listH);

//
//            Hardware item1 = new Hardware(/*"Screw DIN-7991ISO","10642 z","conical",*/"Bolt M25x60Hex","2.15", "Bolt","Course", "25", "60", "1.0", "Hex");
//            Hardware item2 = new Hardware(/*"Screw DIN-7991ISO","10642 z","conical",*/"Bolt M30x50Hex","5", "Bolt","Course", "55", "50", "2", "Hex");
//            Hardware item3 = new Hardware(/*"Screw DIN-7991ISO","10642 z","conical",*/"Bolt M66x77Hex","7", "Bolt","Course", "66", "77", "1", "Hex");
//            Hardware item4 = new Hardware(/*"Screw DIN-7991ISO","10642 z","conical",*/"Bolt M22x55Hex","1.3", "Bolt","Course", "55", "22",  "2", "Hex");

//            dataList.addAll(item1, item2, item3, item4);

//             Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Hardware> filteredData = new FilteredList<>(listH, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(hardware -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Converting to lower case
                String lowerCaseFilter = newValue.toLowerCase();

                if (hardware.getDiameter().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;//Filter matches hardware_name
                } else
                    return false;//Nothing matches
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Hardware> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
    }

//    @FXML
//    void loadFile() throws SQLException {
////        FileChooser chooser = new FileChooser();
////        chooser.setTitle("Select an xlsx file");
////        chooser.getExtensionFilters().addAll(
////                new FileChooser.ExtensionFilter("Database", "*.xlsx"));
////        Window own = null;
////        File f = chooser.showSaveDialog(own);
////        if (f != null) {
////            Files.write(f.toPath(), content.getBytes(LogWriter.DEFAULT_CHARSET));
////        }
//        conn = DBConnection.ConnectDb();
//            String query = "INSERT INTO `hardwares`(`Project_Id`, `Diameter`, `Pitch`, `B`, `K`, `DK`, `A`, `S`, `Total_Length`, `Head`, `Socket`, `Thread`, `Quan`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
//
//                pst = conn.prepareStatement(query);
//        try{
////                File excelFile = new File("C:\\Users\\hassa\\Desktop\\Book1.xlsx");
//                FileInputStream f = new FileInputStream("C:\\Users\\hassa\\Desktop\\Book1.xlsx");
//
//                XSSFWorkbook wb = new XSSFWorkbook(f);
//                XSSFSheet sheet = wb.getSheetAt(0);
//                Row row;
//                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//                    row = sheet.getRow(i);
//                    pst.setInt(1, Integer.parseInt(enterProId.getText()));
//                    pst.setString(2, row.getCell(0).getStringCellValue());
//                    pst.setString(3, row.getCell(1).getStringCellValue());
//                    pst.setString(4, row.getCell(2).getStringCellValue());
//                    pst.setString(5, row.getCell(3).getStringCellValue());
//                    pst.setString(6, row.getCell(4).getStringCellValue());
//                    pst.setString(7, row.getCell(5).getStringCellValue());
//                    pst.setString(8, row.getCell(6).getStringCellValue());
//                    pst.setString(9, row.getCell(7).getStringCellValue());
//                    pst.setString(10, row.getCell(8).getStringCellValue());
//                    pst.setString(11, row.getCell(9).getStringCellValue());
//                    pst.setString(12, row.getCell(10).getStringCellValue());
//                    pst.setString(13, row.getCell(11).getStringCellValue());
//                    pst.execute();
//                }
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Information Dialog");
//                alert.setHeaderText(null);
//                alert.setContentText("Imported in Database");
//                alert.showAndWait();
//
//                wb.close();
//            } catch (FileNotFoundException fileNotFoundException) {
//            fileNotFoundException.printStackTrace();
//        } catch (IOException ioException) {
//            ioException.printStackTrace();
//        }
//
//    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(resetProjectId!= null) {
            resetProjectId.setOnAction(actionEvent -> {
                System.out.println("Trying to add");
                conn = DBConnection.ConnectDb();
                System.out.println("Trying to add2");

                String sql = "ALTER TABLE project AUTO_INCREMENT = 1";
                try {
                    pst = conn.prepareStatement(sql);
                    pst.execute();

                    String toastMsg = "Successfully Reset";
                    int toastMsgTime = 2500; //3.5 seconds
                    int fadeInTime = 400; //0.5 seconds
                    int fadeOutTime = 400; //0.5 seconds
                    Toast.makeText(null, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e + "\n Make Sure to Delete All Projects Before Resetting");
                }
            });
        }

        if(resetHardwareId!= null) {
            resetHardwareId.setOnAction(actionEvent -> {
                System.out.println("Trying to add");
                conn = DBConnection.ConnectDb();
                System.out.println("Trying to add2");

                String sql = "ALTER TABLE hardwares AUTO_INCREMENT = 1";
                try {
                    pst = conn.prepareStatement(sql);
                    pst.execute();

                    String toastMsg = "Successfully Reset";
                    int toastMsgTime = 2500; //3.5 seconds
                    int fadeInTime = 400; //0.5 seconds
                    int fadeOutTime = 400; //0.5 seconds
                    Toast.makeText(null, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e + "\n Make Sure to Delete All Hardware Before Resetting");
                }
            });
        }

        //Main
        if (search!= null) {
            search.setOnAction(calHandler);
            viewProjects.setOnAction(calHandler);
            newProject.setOnAction(calHandler);
            settings.setOnAction(calHandler);
            importFile.setOnAction(calHandler);
        }

        if(btnSettingsToHome!=null){
            btnSettingsToHome.setOnAction(calHandler);
        }

        if(searchToHome!=null) {
            searchToHome.setOnAction(calHandler);
        }

        if(btnIn!= null){
            btnIn.setOnAction(calHandler);
        }



        //SearchWindow
        if(hardwareNameTable!= null) {
            refreshTable();

//            table.setOnMouseClicked((MouseEvent event) -> {
//                if (event.getClickCount() > 0) {
//                    onClick();
//                }
//            });

        }



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

    @FXML
    private void onClick(MouseEvent e) {
        // check the table's selected item and get selected item
        if (table.getSelectionModel().getSelectedItem() != null) {
            Hardware selectedHardware = table.getSelectionModel().getSelectedItem();
            String hId = String.valueOf(selectedHardware.getID());
            fProId.setText(String.valueOf(selectedHardware.getProID()));
//            listH = DBConnection.getSelectedProject(selectedHardware);
//            dSNo.setText(String.valueOf(selectedHardware.));
//            dCat.setText(selectedHardware.getType());
            fDia.setText(String.valueOf(selectedHardware.getDiameter()));
            fPit.setText(String.valueOf(selectedHardware.getPitch()));
            fB.setText(String.valueOf(selectedHardware.getB()));
            fK.setText(selectedHardware.getK());
            fDK.setText(selectedHardware.getDK());
            fA.setText(selectedHardware.getA());
            fS.setText(selectedHardware.getS());
            fLen.setText(selectedHardware.getLength());
            fHead.setText(selectedHardware.getHead());
            fSocket.setText(selectedHardware.getSocket());
            fThread.setText(selectedHardware.getType());
//            longName.setText(selectedHardware.getLongName());


            allSearchToEdit.setOnAction(
                    event -> {
                        hardware = table.getSelectionModel().getSelectedItem();
                        Node node = (Node) e.getSource();
                        Stage Pstage = (Stage) node.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader();
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

        }//if


    }


    class CalHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            try {
                if (e.getSource() == search) {
                    Node node = (Node) e.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
//                    thisStage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/SearchWindow.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
//                    Stage stage = new Stage();
//                    JMetro jMetro = new JMetro(root1, Style.DARK);
                    stage.setScene(new Scene(root1));
                }else if(e.getSource() == searchToHome){
                    Node node = (Node) e.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
//                    thisStage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/Main.fxml"));
                    Parent root11 = (Parent) fxmlLoader.load();
//                    Stage stage = new Stage();
                    stage.setScene(new Scene(root11));
                }else if(e.getSource() == settings){
                    Node node = (Node) e.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
//                    thisStage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/SettingsOrignal.fxml"));
                    Parent root11 = (Parent) fxmlLoader.load();
//                    Stage stage = new Stage();
                    stage.setScene(new Scene(root11));
                }else if(e.getSource() == btnSettingsToHome){
                    Node node = (Node) e.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
//                    thisStage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/Main.fxml"));
                    Parent root11 = (Parent) fxmlLoader.load();
//                    Stage stage = new Stage();
                    stage.setScene(new Scene(root11));
                }else if(e.getSource() == allSearchToEdit){
                    Node node = (Node) e.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
//                    thisStage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/EditHardware.fxml"));
                    Parent root11 = (Parent) fxmlLoader.load();
//                    Stage stage = new Stage();
                    stage.setScene(new Scene(root11));
                }else if(e.getSource() == viewProjects){
                    Node node = (Node) e.getSource();
                    Stage thisStage = (Stage) node.getScene().getWindow();
                    thisStage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/Projects.fxml"));
                    Parent root2 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(root2));
                    stage.setResizable(false);
                    stage.show();
                }else if(e.getSource() == newProject) {
                    Node node = (Node) e.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
//                    thisStage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/NewProject.fxml"));
                    Parent root11 = (Parent) fxmlLoader.load();
//                    Stage stage = new Stage();
                    stage.setScene(new Scene(root11));
                }
                else if(e.getSource() == importFile) {
                    String jdbcURL = "jdbc:mysql://localhost/smart_search";
                    String username = "root";
                    String password = "";

                    String excelFilePath = "C:/Users/hassa/Desktop/Book1.xlsx";

                    int batchSize = 20;

                    Connection connection = null;

                    try {
                        long start = System.currentTimeMillis();

                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Open Resource File");
                        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
                        Window primaryStage = null;
                        File selectedFile = fileChooser.showOpenDialog(primaryStage);
                        if (selectedFile != null) {
                            file = selectedFile;
//            }

                            Workbook workbook = new XSSFWorkbook(file);

                            Sheet firstSheet = workbook.getSheetAt(0);
                            Iterator<Row> rowIterator = firstSheet.iterator();

                            connection = DriverManager.getConnection(jdbcURL, username, password);
                            connection.setAutoCommit(false);

                            String sql = "INSERT INTO `hardwares`(`Project_Id`, `Diameter`, `Pitch`, `B`, `K`, `DK`, `A`, `S`, `Total_Length`, `Head`, `Socket`, `Type`, `Quan`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                            PreparedStatement statement = connection.prepareStatement(sql);

                            int count = 0;

                            rowIterator.next(); // skip the header row

                            while (rowIterator.hasNext()) {
                                Row nextRow = rowIterator.next();
                                Iterator<Cell> cellIterator = nextRow.cellIterator();

                                while (cellIterator.hasNext()) {
                                    Cell nextCell = cellIterator.next();

                                    int columnIndex = nextCell.getColumnIndex();

                                    switch (columnIndex) {
                                        case 0:
                                            int proId = (int) nextCell.getNumericCellValue();
                                            statement.setInt(1, proId);
                                            break;
                                        case 1:
                                            String dia = nextCell.getStringCellValue();
                                            statement.setString(2, dia);
                                            break;
                                        case 2:
                                            String pit = nextCell.getStringCellValue();
                                            statement.setString(3, pit);
                                            break;
                                        case 3:
                                            String b = nextCell.getStringCellValue();
                                            statement.setString(4, b);
                                            break;
                                        case 4:
                                            String k = nextCell.getStringCellValue();
                                            statement.setString(5, k);
                                            break;
                                        case 5:
                                            String dk = nextCell.getStringCellValue();
                                            statement.setString(6, dk);
                                            break;
                                        case 6:
                                            String a = nextCell.getStringCellValue();
                                            statement.setString(7, a);
                                            break;
                                        case 7:
                                            String s = nextCell.getStringCellValue();
                                            statement.setString(8, s);
                                            break;
                                        case 8:
                                            String l = nextCell.getStringCellValue();
                                            statement.setString(9, l);
                                            break;
                                        case 9:
                                            String head = nextCell.getStringCellValue();
                                            statement.setString(10, head);
                                            break;
                                        case 10:
                                            String socket = nextCell.getStringCellValue();
                                            statement.setString(11, socket);
                                            break;
                                        case 11:
                                            String type = nextCell.getStringCellValue();
                                            statement.setString(12, type);
                                            break;

                                        case 12:
                                            int quan = (int) nextCell.getNumericCellValue();
                                            statement.setInt(13, quan);
                                    }

                                }

                                statement.addBatch();

                                if (count % batchSize == 0) {
                                    statement.executeBatch();
                                }

                            }

                            workbook.close();

                            // execute the remaining queries
                            statement.executeBatch();

                            connection.commit();
                            connection.close();

                            long end = System.currentTimeMillis();
                            System.out.printf("Import done in %d ms\n", (end - start));
                            String toastMsg = "Imported";
                            int toastMsgTime = 2500; //3.5 seconds
                            int fadeInTime = 400; //0.5 seconds
                            int fadeOutTime= 400; //0.5 seconds
                            Toast.makeText(null, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);

                        }
                    } catch (IOException ex1) {
                        System.out.println("Error reading file");
                        JOptionPane.showMessageDialog(null, ex1 + "\n Please Select Valid File");
                        ex1.printStackTrace();
                    } catch (SQLException ex2) {
                        System.out.println("Database error");
                        JOptionPane.showMessageDialog(null, ex2 + "\n Please Select Valid File");
                        ex2.printStackTrace();
                    } catch (InvalidFormatException er) {
                        JOptionPane.showMessageDialog(null, er + "\n Please Select Valid File");
                        er.printStackTrace();
                    }catch (Exception d) {
                        JOptionPane.showMessageDialog(null, d + "\n Please Select Valid File");
                    }

                }
//                else if(e.getSource() == btnIn) {
//                    System.out.println("456");
//                    System.out.println(pswd.getText());
//                    String pswd2 = pswd.getText();
//                    if(pswd2 == password){
//                        System.out.println("123");
//
//                    Node node = (Node) e.getSource();
//                    Stage stage = (Stage) node.getScene().getWindow();
////                    thisStage.hide();
//                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/Main.fxml"));
//                    Parent root11 = (Parent) fxmlLoader.load();
////                    Stage stage = new Stage();
//                    stage.setScene(new Scene(root11));
//                }
//                }
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

    @FXML
    private void info() throws ClassNotFoundException {
        try {

                Stage stage = (Stage) i.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/info.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                stage.setScene(new Scene(root1));
                stage.show();

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }
    @FXML
    public void killInfo() throws IOException {
        Stage stage = (Stage) ok.getScene().getWindow();
//                    thisStage.hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/Main.fxml"));
        Parent root11 = (Parent) fxmlLoader.load();
//                    Stage stage = new Stage();
        stage.setScene(new Scene(root11));

    }

}

