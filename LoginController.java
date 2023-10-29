import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text loginMessage;

    public void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Implement login logic here.

        // For simplicity, assume successful login and navigate to the My Account page.
        navigateToMyAccount();
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
