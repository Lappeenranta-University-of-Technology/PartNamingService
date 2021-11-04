package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class EditProjectController implements Initializable {

    @FXML
    private JFXTextField editProjectID;
    @FXML
    private JFXTextField editProjectName;
    @FXML
    private JFXTextField editProjectZone;
    @FXML
    private JFXTextField editProjectScope;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Project project = null;
    private boolean update;
    int ProjectID;




    @FXML
    private void getUpdate(ActionEvent e){
        try {
            connection = DBConnection.ConnectDb();
            String value1 = editProjectID.getText();
            String value2 = editProjectName.getText();
            String value3 = editProjectZone.getText();
            String value4 = editProjectScope.getText();


            query = "UPDATE project SET Project_Id='" + value1 + "',Project_Name='" + value2 + "',Project_Zone='" + value3 + "',Project_Scope='" + value4 + "' where Project_Id='" + value1 + "' ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();

              String toastMsg = "Updated";
            int toastMsgTime = 2500; //3.5 seconds
            int fadeInTime = 400; //0.5 seconds
            int fadeOutTime= 400; //0.5 seconds
            Toast.makeText(null, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);

            Node node = (Node) e.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();
            thisStage.hide();

        }catch (Exception b){
            JOptionPane.showMessageDialog(null, b);
        }


        }




    void setTextField(int id, String name,String adress, String email) {

        editProjectID.setText(String.valueOf(id));
        editProjectName.setText(name);
        editProjectZone.setText(adress);
        editProjectScope.setText(email);

    }

    void setUpdate(boolean b) {
        this.update = b;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}