import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 * Author: Shikha Verma
 * Date: 2023-10-27
 * Description: This Java class implements all the backend of my account page for the effortlogger
 */
public class MyAccountController {

    @FXML
    private Button changePasswordButton;

    // this code will run after change password button is click on my account page
    public void changePassword(ActionEvent event) {
        try {
            Parent changePasswordRoot = FXMLLoader.load(getClass().getResource("changepassword.fxml"));
            Scene changePasswordScene = new Scene(changePasswordRoot, 400, 300);

            Stage stage = (Stage) changePasswordButton.getScene().getWindow();
            stage.setScene(changePasswordScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent event) {
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene loginScene = new Scene(loginRoot, 400, 300);

            Stage stage = (Stage) changePasswordButton.getScene().getWindow();
            stage.setScene(loginScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
