package tu14.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tu14.MainApp;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

        // TODO actual login code
        // the below is just some placeholder wait code

        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }).thenAccept((nil) -> {
            // TODO if login fail, set error label
            // NOTE the Platform.runLater is to prevent an issue in JavaFX threads where you get a whole bunch of
            // errors.
            Platform.runLater(() -> {
                error_label.setText("Uh-oh! Random Error");
                progress_bar.setProgress(0);
            });

            MainApp.go("home.fxml");
        });


    }


}
