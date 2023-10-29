import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChangePasswordController {

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Text changePasswordMessage;

    public void changePassword(ActionEvent event) {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Implement password change logic here, including checking for password requirements.

        // For simplicity, assume successful password change and navigate back to My Account.
        navigateToMyAccount();
    }

    private void navigateToMyAccount() {
        try {
            Parent myAccountRoot = FXMLLoader.load(getClass().getResource("myaccount.fxml"));
            Scene myAccountScene = new Scene(myAccountRoot, 400, 300);

            Stage stage = (Stage) newPasswordField.getScene().getWindow();
            stage.setScene(myAccountScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
