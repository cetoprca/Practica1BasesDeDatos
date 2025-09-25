package app.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

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
