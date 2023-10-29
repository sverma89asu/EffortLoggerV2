package tu14.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tu14.MainApp;
import tu14.api.Backend;
import tu14.api.PlaintextBearerAuthentication;
import tu14.api.RawData;
import tu14.api.request.GetRequest;
import tu14.input.ValidateInput;
import tu14.user.RawUserData;

import java.util.concurrent.CompletableFuture;

public class IndexController {

    public PasswordField password;
    public TextField password_visible;
    public TextField username;
    public ProgressBar progress_bar;
    public Label error_label;

    @FXML
    void togglePasswordVisibility(ActionEvent actionEvent) {
        boolean passwordShouldBeVisible = password.isVisible();

        if (passwordShouldBeVisible)
            password_visible.setText(password.getText());
        else
            password.setText(password_visible.getText());

        password.setVisible(!passwordShouldBeVisible);
        password_visible.setVisible(passwordShouldBeVisible);

        password.setManaged(!passwordShouldBeVisible);
        password_visible.setManaged(passwordShouldBeVisible);
    }

    @FXML
    public void submitLoginForm(ActionEvent actionEvent) {
        String usernameValue = username.getText();
        String passwordValue = password.isVisible() ? password.getText() : password_visible.getText();

        // TODO we want to validate our username & password here

        progress_bar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

        Backend.getInstance().send(new GetRequest().table("user"), RawUserData.class).thenAccept((raw) -> {
            if (!raw.ok()) {
                Platform.runLater(() -> {
                    System.err.println(raw.errorMessage);
                    error_label.setText("Failed to send GET request");
                    progress_bar.setProgress(0);
                });

                return;
            }

            for (RawUserData datum : raw.castSafe()) {
                if (datum.username.equals(usernameValue) && datum.password.equals(passwordValue)) {
                    // TODO set User singleton here

                    // set error message if we fail to progress pages
                    Platform.runLater(() -> {
                        error_label.setText("Unknown Error Unable to progress to next page");
                        progress_bar.setProgress(0);
                    });

                    MainApp.go("home.fxml");
                    return;
                }
            }

            Platform.runLater(() -> error_label.setText("Invalid username or password"));
        });
    }
}
