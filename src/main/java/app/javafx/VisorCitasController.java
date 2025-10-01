package app.javafx;

import app.database.MySQL;
import app.objects.Appoint;
import app.objects.Patient;
import app.objects.Specialty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class VisorCitasController {
    @FXML
    TextField appointNumTf;
    @FXML
    TextField dniTf;
    @FXML
    TextField nameTf;
    @FXML
    TextField addressTf;
    @FXML
    TextField telephoneTf;

    @FXML
    DatePicker datePicker;

    @FXML
    ChoiceBox<String> specialtyCb;

    @FXML
    TableView<Appoint> patientAppointsTv;

    @FXML
    TableColumn<Appoint, String> appointNumTc;
    @FXML
    TableColumn<Appoint, Date> dateTc;
    @FXML
    TableColumn<Appoint, String> specialtyTc;

    @FXML
    Button seePatientAppointsBt;

    @FXML
    Button uploadAppointBt;
    @FXML
    Button deleteAppointBt;
    @FXML
    Button modifyAppointBt;
    @FXML
    Button newAppointBt;

    List<Specialty> specialties = getSpecialties();

    HashMap<String, Integer> specialtyIds = new HashMap<>();

    List<Appoint> appoints = new ArrayList<>();

    ObservableList<Appoint> appointsTable = FXCollections.observableList(appoints);

    @FXML
    private void initialize(){
        ObservableList<String> specialtyNames = FXCollections.observableList(new ArrayList<>());

        for (Specialty specialty : specialties){
            specialtyNames.add(specialty.especialidad.get());
            specialtyIds.put(specialty.especialidad.get(), specialty.id.get());
        }

        specialtyCb.setItems(specialtyNames);

        patientAppointsTv.setItems(appointsTable);
        appointNumTc.setCellValueFactory(data -> data.getValue().numCita.asString());
        specialtyTc.setCellValueFactory(data -> {
            for (Specialty specialty : specialties){
                if (specialty.id.get() == data.getValue().idEspecialidad.get()){
                    return specialty.especialidad;
                }
            }

            return new SimpleStringProperty("Especialidad no encontrada");
        });
        dateTc.setCellValueFactory(data -> data.getValue().fecha);

    }
    @FXML
    private void seePatientAppoints(){
        String dni = dniTf.getText();
        appoints = getPatientAppoints(dni);
        appointsTable.setAll(appoints);
    }
    @FXML
    private void modifyAppoint(){

    }
    @FXML
    private void newAppoint(){
        int appointNum = getMaxAppointNum();

        appointNumTf.setText(String.valueOf(appointNum+1));
    }
    @FXML
    private void deleteAppoint(){
        int index = patientAppointsTv.getFocusModel().getFocusedItem().numCita.get();

        MySQL mySQL = MySQL.getInstance("localhost", "3306", "root", "toor", "P1DBCESAR");
        Connection mySQLConnection = mySQL.getConnection();

        mySQL.execute(mySQLConnection, "DELETE FROM CITA WHERE idCita = " + index);

        seePatientAppoints();
    }
    @FXML
    private void uploadAppoint(){
        MySQL mySQL = MySQL.getInstance("localhost", "3306", "root", "toor", "P1DBCESAR");
        Connection mySQLConnection = mySQL.getConnection();

        Patient patient = getPatient(dniTf.getText());
        Specialty specialty = null;
        if (specialtyCb.getValue() != null){
            specialty = new Specialty(specialtyIds.get(specialtyCb.getValue()), specialtyCb.getValue());
        }

        if (patient != null && specialty != null && datePicker.getValue() != null){
            mySQL.execute(mySQLConnection, "INSERT INTO CITA(idPaciente, idEspecialidad, fecha) VALUES(" + patient.id + ", " + specialty.id.get() + ", '" + datePicker.getValue() + "')");
        }
    }

    @FXML
    private void checkKey(KeyEvent event){
        if (event.getCode() == KeyCode.TAB || event.getCode() == KeyCode.ENTER){
            setPatientData();
        }
    }

    private int getMaxAppointNum(){
        MySQL mySQL = MySQL.getInstance("localhost", "3306", "root", "toor", "P1DBCESAR");
        Connection mySQLConnection = mySQL.getConnection();
        ResultSet resultSet = mySQL.executeQuery(mySQLConnection, "SELECT MAX(IDCITA) FROM CITA");
        int appointNum;
        try {
            resultSet.next();
            appointNum = resultSet.getInt(1);
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }

        return appointNum;
    }

    private void setPatientData(){
        String dni = dniTf.getText();

        Patient patient = getPatient(dni);

        nameTf.setText(patient.nombre);
        addressTf.setText(patient.direccion);
        telephoneTf.setText(patient.telefono);
    }

    private Patient getPatient(String dni){
        MySQL mySQL = MySQL.getInstance("localhost", "3306", "root", "toor", "P1DBCESAR");
        Connection mySQLConnection = mySQL.getConnection();
        ResultSet resultSet = mySQL.executeQuery(mySQLConnection, "SELECT * FROM PACIENTE WHERE DNI = '" + dni + "'");

        Patient patient;
        try {
            resultSet.next();
            patient = new Patient(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4), String.valueOf(resultSet.getInt(5)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return patient;
    }

    private List<Appoint> getPatientAppoints(String dni){
        List<Appoint> appointList = new ArrayList<>();

        MySQL mySQL = MySQL.getInstance("localhost", "3306", "root", "toor", "P1DBCESAR");
        Connection mySQLConnection = mySQL.getConnection();
        ResultSet result = mySQL.executeQuery(mySQLConnection, "SELECT * FROM CITA WHERE idPaciente = (SELECT idPaciente FROM PACIENTE WHERE DNI = '" + dni + "')");

        try {
            while (result.next()){
                appointList.add(new Appoint(result.getInt(1), result.getInt(2), result.getInt(3), result.getDate(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return appointList;
    }

    private List<Specialty> getSpecialties(){
        List<Specialty> specialtyList = new ArrayList<>();

        MySQL mySQL = MySQL.getInstance("localhost", "3306", "root", "toor", "P1DBCESAR");
        Connection mySQLConnection = mySQL.getConnection();
        ResultSet result = mySQL.executeQuery(mySQLConnection, "SELECT * FROM ESPECIALIDAD");

        try {
            while (result.next()){
                specialtyList.add(new Specialty(result.getInt(1), result.getString(2)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return specialtyList;
    }

}
