package tu14.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import tu14.MainApp;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class IndexController {

    public PasswordField password;
    public TextField password_visible;
    public TextField username;
    public ProgressBar progress_bar;


    @FXML
    void togglePasswordVisibility(ActionEvent actionEvent) {
        boolean passwordShouldBeVisible = password.isVisible();

        if (passwordShouldBeVisible)
            password_visible.setText(password.getText());
        else
            password.setText(password_visible.getText());

        password.setVisible(!passwordShouldBeVisible);
        password_visible.setVisible(passwordShouldBeVisible);
    }

    @FXML
    public void submitLoginForm(ActionEvent actionEvent) throws ExecutionException, InterruptedException {
        String usernameValue = username.getText();
        String passwordValue = password.isVisible() ? password.getText() : password_visible.getText();

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
        }).get();

        progress_bar.setProgress(0);
        MainApp.go("home.fxml");
    }


}
