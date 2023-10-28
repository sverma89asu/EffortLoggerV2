import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ChangePasswordController {

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private TextField newPasswordText;

    @FXML
    private Button eyeButtonNewPassword;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField confirmPasswordText;

    @FXML
    private Button eyeButtonConfirmPassword;

    @FXML
    private Text changePasswordMessage;

    @FXML
    private Text warningText;

    private boolean passwordVisibleNew = false;

    private boolean passwordVisibleConfirm = false;
    public void togglePasswordVisibilityNew() {
        passwordVisibleNew = !passwordVisibleNew;

        if (passwordVisibleNew) {
            eyeButtonNewPassword.setText("Hide");
            newPasswordField.setVisible(false);
            newPasswordField.setManaged(false);
            newPasswordText.setVisible(true);
            newPasswordText.setManaged(true);
            newPasswordText.setText(newPasswordField.getText());
        } else {
            eyeButtonNewPassword.setText("Show");
            newPasswordField.setVisible(true);
            newPasswordField.setManaged(true);
            newPasswordText.setVisible(false);
            newPasswordText.setManaged(false);
        }
    }

    public void togglePasswordVisibilityConfirm() {
        passwordVisibleConfirm = !passwordVisibleConfirm;

        if (passwordVisibleConfirm) {
            eyeButtonConfirmPassword.setText("Hide");
            confirmPasswordField.setVisible(false);
            confirmPasswordField.setManaged(false);
            confirmPasswordText.setVisible(true);
            confirmPasswordText.setManaged(true);
            confirmPasswordText.setText(confirmPasswordField.getText());
        } else {
            eyeButtonConfirmPassword.setText("Show");
            confirmPasswordField.setVisible(true);
            confirmPasswordField.setManaged(true);
            confirmPasswordText.setVisible(false);
            confirmPasswordText.setManaged(false);
        }
    }
    public void changePassword(ActionEvent event) {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if(!newPassword.equals(confirmPassword)){
            warningText.setText("New password and confirm password should match");
        }
        else{
            boolean isValidPassword = true;
            if (newPassword.length() < 8) {
                isValidPassword = false;
            }

            // Check for at least one uppercase letter
            Pattern uppercasePattern = Pattern.compile("[A-Z]");
            Matcher uppercaseMatcher = uppercasePattern.matcher(newPassword);

            // Check for at least one lowercase letter
            Pattern lowercasePattern = Pattern.compile("[a-z]");
            Matcher lowercaseMatcher = lowercasePattern.matcher(newPassword);

            // Check for at least one numeric character
            Pattern digitPattern = Pattern.compile("[0-9]");
            Matcher digitMatcher = digitPattern.matcher(newPassword);

            // Check for at least one special character (non-alphanumeric)
            Pattern specialCharPattern = Pattern.compile("[^A-Za-z0-9]");
            Matcher specialCharMatcher = specialCharPattern.matcher(newPassword);

            // Return true if all conditions are met
            isValidPassword &=  uppercaseMatcher.find() && lowercaseMatcher.find() &&
                    digitMatcher.find() && specialCharMatcher.find();

            if(!isValidPassword){
                warningText.setText("Please make sure the password is 8 characters long, contains at least one upper case, one lower case, one numeric and one special character");
            }
            else{
                navigateToMyAccount();
            }
        }
        // Implement password change logic here, including checking for password requirements.

        // For simplicity, assume successful password change and navigate back to My Account.

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
