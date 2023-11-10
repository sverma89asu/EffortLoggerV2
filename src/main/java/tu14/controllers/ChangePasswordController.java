package tu14.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import tu14.MainApp;
import tu14.api.Backend;
import tu14.api.PlaintextBearerAuthentication;
import tu14.api.RawData;
import tu14.api.request.DeleteRequest;
import tu14.api.request.UpdateRequest;
import tu14.user.RawUserData;

import java.util.regex.Pattern;
import java.util.regex.Matcher;



/**
 * Author: Shikha Verma
 * Date: 2023-10-27
 * Description: This Java class implements all the backend of change password for the effortlogger
 */
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
    private Text warningText;
    public void togglePasswordVisibilityNew() {
        // TODO extract password visibility toggles into a utility
        boolean passwordShouldBeVisible = newPasswordField.isVisible();

        if (passwordShouldBeVisible)
            newPasswordText.setText(newPasswordField.getText());
        else
            newPasswordField.setText(newPasswordText.getText());

        newPasswordField.setVisible(!passwordShouldBeVisible);
        newPasswordText.setVisible(passwordShouldBeVisible);

        newPasswordField.setManaged(!passwordShouldBeVisible);
        newPasswordText.setManaged(passwordShouldBeVisible);

        eyeButtonNewPassword.setText(passwordShouldBeVisible ? "Hide" : "Show");
    }

    public void togglePasswordVisibilityConfirm() {
        boolean passwordShouldBeVisible = confirmPasswordField.isVisible();

        if (passwordShouldBeVisible)
            confirmPasswordText.setText(confirmPasswordField.getText());
        else
            confirmPasswordField.setText(confirmPasswordText.getText());

        confirmPasswordField.setVisible(!passwordShouldBeVisible);
        confirmPasswordText.setVisible(passwordShouldBeVisible);

        confirmPasswordField.setManaged(!passwordShouldBeVisible);
        confirmPasswordText.setManaged(passwordShouldBeVisible);

        eyeButtonConfirmPassword.setText(passwordShouldBeVisible ? "Hide" : "Show");
    }

    //this function will run when user inputs new and confirmed password and clicks on change password button
    public void changePassword(ActionEvent event) {
        String newPassword = newPasswordField.isVisible() ? newPasswordField.getText() : newPasswordText.getText();
        String confirmPassword = confirmPasswordField.isVisible() ? confirmPasswordField.getText() : confirmPasswordText.getText();
        if(!newPassword.equals(confirmPassword)){
            warningText.setText("New password and confirm password should match");
        }
        else{
            boolean isValidPassword = newPassword.length() >= 8;

            // Check for at least one uppercase letter
            Pattern uppercasePattern = Pattern.compile("[A-Z]");
            Matcher uppercaseMatcher = uppercasePattern.matcher(newPassword);

            // Check for at least one lowercase letter
            Pattern lowercasePattern = Pattern.compile("[a-z]");
            Matcher lowercaseMatcher = lowercasePattern.matcher(newPassword);

            // Check for at least one numeric character
            Pattern digitPattern = Pattern.compile("\\d");
            Matcher digitMatcher = digitPattern.matcher(newPassword);

            // Check for at least one special character (non-alphanumeric)
            Pattern specialCharPattern = Pattern.compile("\\W");
            Matcher specialCharMatcher = specialCharPattern.matcher(newPassword);

            // Return true if all conditions are met
            isValidPassword &=  uppercaseMatcher.find() && lowercaseMatcher.find() &&
                    digitMatcher.find() && specialCharMatcher.find();

            if(!isValidPassword){
                warningText.setText("Please make sure the password is 8 characters long, contains at least one upper case, one lower case, one numeric and one special character");
                // NOTE could we use this as a guard? just return if it's invalid? instead of nesting the backend call?
            }
            else{
                try {
                    RawData<?> data = Backend.getInstance().send(
                            new UpdateRequest().table("user").id(MainApp.getUser().getId())
                                    .body(new RawUserData(MainApp.getUser(), newPassword)), null).get();

                    if (data.ok()) {
//                        MainApp.back();
                    } else {
                        System.out.println(data.errorMessage);
                        warningText.setText("Password could not be changed, please try again");
                    }
                } catch (Exception e) {
                    // NOTE it's usually a bad idea to catch all exceptions like this. Java explicitly has two exception types just for this purpose.
                    e.printStackTrace();
                }
            }
        }
    }


    public void back(ActionEvent actionEvent) {

//        MainApp.back();
    }
}
