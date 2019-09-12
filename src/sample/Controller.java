/**
 * Sample Skeleton for 'sample.fxml' Controller Class
 */
/**
 * Author: Andrew Mirauta
 * Date: September 12
 * Program: a program that receives an agent object from a database
 * and allows it to be edited by a user. Once edited it also allows the
 * user to save their changes to the database.
 */
package sample;


import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.plaf.nimbus.State;

public class Controller {
    DBConnection helper = new DBConnection();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cboAgent"
    private ComboBox<Agents> cboAgent; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgentId"
    private TextField tfAgentId; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgentFirst"
    private TextField tfAgentFirst; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgentMiddle"
    private TextField tfAgentMiddle; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgentLast"
    private TextField tfAgentLast; // Value injected by FXMLLoader

    @FXML // fx:id="tfBPhone"
    private TextField tfBPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tfEmail"
    private TextField tfEmail; // Value injected by FXMLLoader

    @FXML // fx:id="tfPosition"
    private TextField tfPosition; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgencyId"
    private TextField tfAgencyId; // Value injected by FXMLLoader

    @FXML // fx:id="btnSave"
    private Button btnSave; // Value injected by FXMLLoader

    @FXML // fx:id="btnEdit"
    private Button btnEdit; // Value injected by FXMLLoader

    @FXML
    private Button btnExit;

    @FXML
    void onActionBtnExit(ActionEvent event) { //exit button if no errors
        System.exit(0);
    }

    @FXML
    void onActionBtnEdit(ActionEvent event) {
        //edit button, when clicked enables editing on the following fields and enables save button while disabling itself
        tfAgentFirst.setEditable(true);
        tfAgentMiddle.setEditable(true);
        tfAgentLast.setEditable(true);
        tfBPhone.setEditable(true);
        tfPosition.setEditable(true);
        tfEmail.setEditable(true);
        tfAgencyId.setEditable(true);

        btnSave.setDisable(false);
        btnEdit.setDisable(true);
    }

   @FXML
    public void onActionBtnSave(ActionEvent event) throws SQLException { //save button, connects to db and populates the text fields
        //if the connection is made it saves to the database
       //connecton string
        Connection conn = helper.createConnection();
       //sql string
        String sql = "Update `Agents` set `AgtFirstName`=?, " +
                "`AgtMiddleInitial`=?, `AgtLastName`=?, `AgtBusPhone`=?, `AgtEmail`=?, `AgtPosition`=?, `AgencyId`=? " +
                "WHERE `AgentId`=?";
        //uses sql string
        PreparedStatement stmt = conn.prepareStatement(sql);
        //sets test fields to database agent object
        stmt.setString(1,tfAgentFirst.getText());
        stmt.setString(2, tfAgentMiddle.getText());
        stmt.setString(3, tfAgentLast.getText());
        stmt.setString(4, tfBPhone.getText());
        stmt.setString(5, tfEmail.getText());
        stmt.setString(6, tfPosition.getText());
        stmt.setInt(7, Integer.parseInt(tfAgencyId.getText()));
        stmt.setInt(8,Integer.parseInt(tfAgentId.getText()));
        //row variable that helps us check that the database is loading values
          int rows = stmt.executeUpdate();
          if (rows == 0)
           {//if no connection is made, show update failed
               Alert alert = new Alert(Alert.AlertType.ERROR,"Update failed.", ButtonType.OK );
               alert.show();
           }
           else
           {//if functional loadcombo is carried out and the user is informed
               Alert alert = new Alert(Alert.AlertType.INFORMATION, "Update successful.", ButtonType.OK);
               alert.show();
               loadcombo();
           }

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException {
        assert cboAgent != null : "fx:id=\"cboAgent\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfAgentId != null : "fx:id=\"tfAgentId\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfAgentFirst != null : "fx:id=\"tfAgentFirst\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfAgentMiddle != null : "fx:id=\"tfAgentMiddle\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfAgentLast != null : "fx:id=\"tfAgentLast\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfBPhone != null : "fx:id=\"tfBPhone\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfEmail != null : "fx:id=\"tfEmail\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfPosition != null : "fx:id=\"tfPosition\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfAgencyId != null : "fx:id=\"tfAgencyId\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'sample.fxml'.";

        loadcombo(); //initial load of agent fields into combo box
        Connection conn = helper.createConnection(); //connection string
        ArrayList<Agents>  agentsArrayList = null; //agent object instantiation
        try {//intial try to connect, query, and populate agent into an array
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from agents");
            agentsArrayList = new ArrayList<>();
            while (rs.next())
            {
                agentsArrayList.add(new Agents(rs.getInt(1),rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getInt(8)));
            }
        } catch (SQLException e) {//standard error catching
            e.printStackTrace();
        }

        //listener setup for the agent combo box
        cboAgent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Agents>() {
            @Override
            public void changed(ObservableValue<? extends Agents> observable, Agents oldValue, Agents newValue) {
                if (newValue != null) {//if not null set the text to agent values
                    tfAgentId.setText(newValue.getAgentId() + "");
                    tfAgentFirst.setText(newValue.getAgentFirst());
                    tfAgentMiddle.setText((newValue.getAgentMiddle()));
                    tfAgentLast.setText(newValue.getAgentLast());
                    tfBPhone.setText(newValue.getbPhone());
                    tfEmail.setText(newValue.getEmail());
                    tfPosition.setText(newValue.getPosition());
                    tfAgencyId.setText(newValue.getAgency() + "");
                }
            }
        });
    }

    //method that loads the combo box with agent fields
    private void loadcombo() throws SQLException {
        Connection conn =helper.createConnection(); //connection string using DB connection class
        Statement stmt = conn.createStatement(); //setting statement object
        ResultSet rs =stmt.executeQuery("select * from agents"); //sql query
        ArrayList<Agents> agentsArrayList = new ArrayList<>();//new array list ready to be loaded with agent
        while (rs.next())
        {//while rs has more fields to generate the loop keeps getting them
            agentsArrayList.add(new Agents(rs.getInt(1), rs.getString(2), rs.getString(3),
                     rs.getString(4), rs.getString(5), rs.getString(6),
                     rs.getString(7), rs.getInt(8)));
        }
        ObservableList<Agents> agents = FXCollections.observableArrayList(agentsArrayList); //puts agents into an observable list
        cboAgent.setItems(agents); //sets the combo box with the new agent fields

    }
}
