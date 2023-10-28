import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MyAccountController {

    @FXML
    private Button changePasswordButton;

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
}
