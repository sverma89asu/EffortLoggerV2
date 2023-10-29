import api.Backend;
import api.PlaintextBearerAuthentication;
import api.RawData;
import api.request.GetRequest;
import api.request.UpdateRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.scene.control.Button;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Author: Shikha Verma
 * Date: 2023-10-27
 * Description: This Java class implements the login functionality for the effortlogger
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text loginMessage;

    @FXML
    private Text warningText;

    @FXML
    private Button eyeButton;

    @FXML
    private  TextField passwordText;

    private boolean passwordVisible = false;

    public void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;

        if (passwordVisible) {
            eyeButton.setText("Hide");
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            passwordText.setVisible(true);
            passwordText.setManaged(true);
            passwordText.setText(passwordField.getText());
        } else {
            eyeButton.setText("Show");
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            passwordText.setVisible(false);
            passwordText.setManaged(false);
        }
    }
    public void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean isLoginSuccess = false;
        try {

            //TODO centralized Backend() instances
            //NOTE: Christoph, maybe impl a factory/singleton thingy?
            Backend backend = new Backend(new PlaintextBearerAuthentication("dGVtcG9yYXJ5IGFsc29fdGVtcG9yYXJ5"));

            RawData<RawUserData> data = backend.send(new GetRequest().table("user"), RawUserData.class).get();

            //TODO this whole thing needs to shift to backend soon
            if (data.ok()) {
                for (RawUserData datum : data.castSafe()) {
                    if (datum.username.equals(username) && datum.password.equals(password)) {
                        Main.userData.setId(datum.id);
                        Main.userData.setUsername(datum.username);

                        isLoginSuccess = true;
                        navigateToMyAccount();
                    }
                }

                if (!isLoginSuccess) {
                    warningText.setText("Invalid username or password");
                }
            } else {
                System.out.println(data.errorMessage);
                warningText.setText("Failed to send GET request");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Implement login logic here.

        // For simplicity, assume successful login and navigate to the My Account page
    }

    private void navigateToMyAccount() {
        try {
            Parent myAccountRoot = FXMLLoader.load(getClass().getResource("myaccount.fxml"));
            Scene myAccountScene = new Scene(myAccountRoot, 400, 300);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(myAccountScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
