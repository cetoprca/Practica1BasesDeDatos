package app.javafx;

import app.database.MySQL;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainMenuController {
    @FXML
    TextField emailTf;
    @FXML
    TextField passwordTf;
    @FXML
    PasswordField passwordPf;

    @FXML
    Button logInButt;

    @FXML
    RadioButton showPasswordRbut;


    @FXML
    private void initialize(){
        passwordTf.setVisible(false);
        passwordTf.setManaged(false);

        passwordTf.textProperty().bindBidirectional(passwordPf.textProperty());
    }

    @FXML
    private void logIn(){
        String email = emailTf.getText();
        String password = passwordPf.getText();
        boolean correct = false;

        MySQL mySQL = new MySQL();
        Connection mySQLConnection = mySQL.getConnection("localhost", "3306", "root", "toor", "p1dbcesar");
        ResultSet result = mySQL.executeQuery(mySQLConnection, "SELECT (COUNT(*) > 0) FROM USUARIO WHERE email = '" + email + "' AND password = '" + password + "'");
        try {
            while (result.next()){
                correct = result.getInt(1) == 1;
            }
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }

        if (correct){
            Stage stage = ((Stage) passwordPf.getScene().getWindow());
            stage.close();

            VisorCitasApp visorCitasApp = new VisorCitasApp();
            try {
                visorCitasApp.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void showPassword(){
        if (showPasswordRbut.isSelected()){
            passwordPf.setManaged(false);
            passwordPf.setVisible(false);
            passwordTf.setManaged(true);
            passwordTf.setVisible(true);

        }else {
            passwordPf.setManaged(true);
            passwordPf.setVisible(true);
            passwordTf.setManaged(false);
            passwordTf.setVisible(false);
        }
    }
}
