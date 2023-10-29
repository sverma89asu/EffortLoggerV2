package tu14.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tu14.MainApp;


/**
 * Author: Shikha Verma
 * Date: 2023-10-27
 * Description: This Java class implements all the backend of my account page for the effortlogger
 */
public class MyAccountController {


    // this code will run after change password button is click on my account page
    public void changePassword(ActionEvent event) {
        MainApp.go("changepassword.fxml");
    }

    public void logout(ActionEvent event) {
        MainApp.go("index.fxml");
    }
}
