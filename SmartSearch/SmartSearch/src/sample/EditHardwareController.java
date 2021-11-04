package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class EditHardwareController implements Initializable {

    @FXML
    private JFXTextField fieldEditHardwareDia;

    @FXML
    private JFXTextField fieldEditHardwarePit;

    @FXML
    private JFXTextField fieldEditHardwareId;

    @FXML
    private JFXTextField fieldEditHardwareB;

    @FXML
    private JFXTextField fieldEditHardwareDk;

    @FXML
    private JFXTextField fieldEditHardwareK;

    @FXML
    private JFXTextField fieldEditHardwareA;

    @FXML
    private JFXTextField fieldEditHardwareS;

    @FXML
    private JFXTextField fieldEditHardwareLen;

    @FXML
    private JFXTextField fieldEditHardwareHead;

    @FXML
    private JFXTextField fieldEditHardwareSocket;

    @FXML
    private JFXTextField fieldEditHardwareThread;

    @FXML
    private JFXTextField fieldEditHardwareQuantity;

    @FXML
    private JFXButton btnHardwareUpdate;

    @FXML
    private JFXButton btnHardwareEditBack;

    //AddHardware
    @FXML
    private JFXTextField fieldAddHardwareDia;

    @FXML
    private JFXTextField fieldAddHardwarePit;

    @FXML
    private JFXButton btnHardwareAdd;

    @FXML
    private JFXButton btnHardwareBack;

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
    private JFXTextField fieldAddHardwareProID;

   CalHandler calHandler = new CalHandler();

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
            String value1 = fieldEditHardwareId.getText();
            String value2 = fieldEditHardwareDia.getText();
            String value3 = fieldEditHardwarePit.getText();
            String value4 = fieldEditHardwareB.getText();
            String value5 = fieldEditHardwareK.getText();
            String value6 = fieldEditHardwareDk.getText();
            String value7 = fieldEditHardwareA.getText();
            String value8 = fieldEditHardwareS.getText();
            String value9 = fieldEditHardwareLen.getText();
            String value10 = fieldEditHardwareHead.getText();
            String value11 = fieldEditHardwareSocket.getText();
            String value12 = fieldEditHardwareThread.getText();
            String value13 = fieldEditHardwareQuantity.getText();


            query = "UPDATE hardwares SET Hardware_Id='" + value1 + "',Diameter='" + value2 + "',Pitch='" + value3 + "',B='" + value4 + "' ,K='" + value5 + "' ,DK='" + value6 + "' ,A='" + value7 + "' ,S='" + value8 + "' ,Total_Length='" + value9 + "' ,Head='" + value10 + "' ,Socket='" + value11 + "' ,Thread='" + value12 + "' ,Quan='" + value13 + "' where Hardware_Id='" + value1 + "' ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();



            Node node = (Node) e.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();
            thisStage.hide();

            String toastMsg = "Updated";
            int toastMsgTime = 2500; //3.5 seconds
            int fadeInTime = 400; //0.5 seconds
            int fadeOutTime= 400; //0.5 seconds
            Toast.makeText(null, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);

        }catch (Exception b){
            JOptionPane.showMessageDialog(null, b + "\n Please Enter Valid Values");
        }


    }




    void setTextField( int hardwareId, String diameter, String pitch, String b, String k, String dk, String a, String s, String length, String head, String socket, String thread, int quan) {

        fieldEditHardwareId.setText(String.valueOf(hardwareId));
        fieldEditHardwareDia.setText(diameter);
        fieldEditHardwarePit.setText(pitch);
        fieldEditHardwareB.setText(b);
        fieldEditHardwareK.setText(k);
        fieldEditHardwareDk.setText(dk);
        fieldEditHardwareA.setText(a);
        fieldEditHardwareS.setText(s);
        fieldEditHardwareLen.setText(length);
        fieldEditHardwareHead.setText(head);
        fieldEditHardwareSocket.setText(socket);
        fieldEditHardwareThread.setText(thread);
        fieldEditHardwareQuantity.setText(String.valueOf(quan));


    }

    void setUpdate(boolean b) {
        this.update = b;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(btnHardwareEditBack != null) {
            btnHardwareEditBack.setOnAction(calHandler);
        }
    }

    private class CalHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            if(e.getSource() == btnHardwareEditBack){
                Node node = (Node) e.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.hide();
            }
        }
    }
}