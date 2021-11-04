package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Iterator;
import java.util.ResourceBundle;




public class ImportController implements Initializable {


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
    private JFXButton delProjectDetails;

    @FXML
    private JFXButton editProjectDetails;

    @FXML
    private JFXButton addProjectDetails;

    @FXML
    private JFXButton refreshProjectDetails;

    @FXML
    private JFXButton btnImport;

    @FXML
    private JFXTextField projectId;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    File file;
    ObservableList<Hardware> hard = FXCollections.observableArrayList();


    @FXML
    public void load() {
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

            }
        } catch (IOException ex1) {
            System.out.println("Error reading file");
            ex1.printStackTrace();
        } catch (SQLException ex2) {
            System.out.println("Database error");
            ex2.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }


//
//    private List<Hardware> extractDataFromFile(File file)
//    {
//        List<Hardware> hardData= new ArrayList();
//
//        try {
//            Workbook workbook = new XSSFWorkbook(file);
//            Sheet sheet = workbook.getSheetAt(0);
//
////            ExcelFile workbook = ExcelFile.load(file.getAbsolutePath());
////            ExcelWorksheet sheet = workbook.getWorksheet(0);
//
//            for (int i = 0; i < sheet.getLastRowNum(); i++) {
//                Row row = sheet.getRow(i);
//
//                Hardware hard = null;
//                for (int t = 0; t < row.getLastCellNum(); t++) {
//                    Cell cell = row.getCell(t);
//                    switch (t) {
//                        case 0:
//                            Double project_id =  cell.getNumericCellValue();
//                            hard.setProID(project_id.intValue());
//                            System.out.println("row: " + i + "  cell: " + t + " - " + cell.getNumericCellValue());
//                            break;
//                        case 1:
//                            hard.setDiameter(cell.getStringCellValue());
//                            System.out.println("row: " + i + "  cell: " + t + " - " + cell.getStringCellValue());
//                            break;
//                        case 2:
//                            hard.setPitch(cell.getStringCellValue());
//                            System.out.println("row: " + i + "  cell: " + t + " - " + cell.getStringCellValue());
//                            break;
//                        case 3:
//                            hard.setB(cell.getStringCellValue());
//                            System.out.println("row: " + i + "  cell: " + t + " - " + cell.getStringCellValue());
//                            break;
//                        case 4:
//                            hard.setK(cell.getStringCellValue());
//                            System.out.println("row: " + i + "  cell: " + t + " - " + cell.getStringCellValue());
//                            break;
//                        case 5:
//                            hard.setDK(cell.getStringCellValue());
//                            System.out.println("row: " + i + "  cell: " + t + " - " + cell.getStringCellValue());
//                            break;
//                        case 6:
//                            hard.setA(cell.getStringCellValue());
//                            System.out.println("row: " + i + "  cell: " + t + " - " + cell.getStringCellValue());
//                            break;
//                        case 7:
//                            hard.setS(cell.getStringCellValue());
//                            System.out.println("row: " + i + "  cell: " + t + " - " + cell.getStringCellValue());
//                            break;
//                        case 8:
//                            hard.setLength(cell.getStringCellValue());
//                            System.out.println("row: " + i + "  cell: " + t + " - " + cell.getStringCellValue());
//                            break;
//                        case 9:
//                            hard.setHead(cell.getStringCellValue());
//                            System.out.println("row: " + i + "  cell: " + t + " - " + cell.getStringCellValue());
//                            break;
//                        case 10:
//                            hard.setSocket(cell.getStringCellValue());
//                            System.out.println("row: " + i + "  cell: " + t + " - " + cell.getStringCellValue());
//                            break;
//                        case 11:
//                            hard.setThread(cell.getStringCellValue());
//                            System.out.println("row: " + i + "  cell: " + t + " - " + cell.getStringCellValue());
//                            break;
//                        case 12:
//                            Double q =  cell.getNumericCellValue();
//                            hard.setQuan(q.intValue());
//                            System.out.println("row: " + i + "  cell: " + t + " - " + cell.getNumericCellValue());
//                            break;
//
//                    }
//                }
//
//                hardData.add(hard);
//            }
//        }
//        catch (IOException | InvalidFormatException ex) {
//            ex.printStackTrace();
//        }
//
//        return hardData;
//    }



//        proDetailID.setCellValueFactory(new PropertyValueFactory("ID"));
//        proDetailDia.setCellValueFactory(new PropertyValueFactory("Diameter"));
//        proDetailPit.setCellValueFactory(new PropertyValueFactory("Pitch"));
//        proDetailB.setCellValueFactory(new PropertyValueFactory("B"));
//        proDetailK.setCellValueFactory(new PropertyValueFactory("K"));
//        proDetailDK.setCellValueFactory(new PropertyValueFactory("Dk"));
//        proDetailA.setCellValueFactory(new PropertyValueFactory("A"));
//        proDetailS.setCellValueFactory(new PropertyValueFactory("S"));
//        proDetailLen.setCellValueFactory(new PropertyValueFactory("Length"));
//        proDetailHead.setCellValueFactory(new PropertyValueFactory("Head"));
//        proDetailSocket.setCellValueFactory(new PropertyValueFactory("Socket"));
//        proDetailThread.setCellValueFactory(new PropertyValueFactory("Thread"));
//        proDetailQuantity.setCellValueFactory(new PropertyValueFactory("Quantity"));
//        proDetailTable.setItems(hard);
//        tableView.getColumns().setAll(tableColumnName, tableColumnAge, tableColumnSex);

//        btnImport.setOnAction((
//                ActionEvent event) -> {
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("Open Resource File");
//            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
//            Window primaryStage = null;
//            File selectedFile = fileChooser.showOpenDialog(primaryStage);
//            if (selectedFile != null) {
//                file = selectedFile;
//                List<Hardware> personsData = extractDataFromFile(file);
//                hard.addAll(personsData);
//            }
//        });

        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


//    static {
//        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
//    }
//
//    @FXML
//        public void load (ActionEvent event) throws IOException {
//        try{
//            String query = "INSERT INTO `hardwares`(`Project_Id`, `Diameter`, `Pitch`, `B`, `K`, `DK`, `A`, `S`, `Total_Length`, `Head`, `Socket`, `Thread`, `Quan`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
//            conn = DBConnection.ConnectDb();
//            pst = conn.prepareStatement(query);
//
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("Open file");
//            File file = fileChooser.showOpenDialog(proDetailTable.getScene().getWindow());
//
//            ExcelFile workbook = ExcelFile.load(file.getAbsolutePath());
//            ExcelWorksheet sheet = workbook.getWorksheet(0);
//        ExcelRow row;
//        for(int i = 1; i<=sheet.getLastRowNum(); i++){
//            row = sheet.getRow(i);
//            pst.setInt(1, (int) row.getCell(0).getNumericCellValue());
//            pst.setString(2, row.getCell(1).getStringCellValue());
//            pst.setString(3, row.getCell(2).getStringCellValue());
//            pst.setString(4, row.getCell(3).getStringCellValue());
//            pst.setString(5, row.getCell(4).getStringCellValue());
//            pst.setString(6, row.getCell(5).getStringCellValue());
//            pst.setString(7, row.getCell(6).getStringCellValue());
//            pst.setString(8, row.getCell(7).getStringCellValue());
//            pst.setString(9, row.getCell(8).getStringCellValue());
//            pst.setString(10, row.getCell(9).getStringCellValue());
//            pst.setString(11, row.getCell(10).getStringCellValue());
//            pst.setString(12, row.getCell(11).getStringCellValue());
//            pst.setInt(13, (int) row.getCell(12).getNumericCellValue());
//            pst.execute();
//        }
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Information Dialog");
//        alert.setHeaderText(null);
//        alert.setContentText("Hardware are Successfully loaded");
//        alert.showAndWait();
//
//
//
//    }catch(
//    SQLException t ){
//        Logger.getLogger(ShowAllController.class.getName()).log(Level.SEVERE, null, t);
//    } catch (IOException ioException) {
//        ioException.printStackTrace();
//    } catch (
//    InvalidFormatException invalidFormatException) {
//        invalidFormatException.printStackTrace();
//    }
//
//
//
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//    }
//}


    }
