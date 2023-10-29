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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;


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

    //this function will run when user inputs new and confirmed password and clicks on change password button
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
                try {
                    // Define the URL of the API endpoint
                    String url = "https://cse360.flerp.dev/tables/user";

                    // Create a URL object
                    URL obj = new URL(url);

                    // Open a connection to the URL
                    HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

                    // Set the request method to PUT
                    connection.setRequestMethod("POST");

                    // Set request headers
                    connection.setRequestProperty("Authorization", "Bearer dGVtcG9yYXJ5IGFsc29fdGVtcG9yYXJ5");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("X-Data-ID", Integer.toString(Main.userData.getId()));

                    // Enable input/output streams for sending data
                    connection.setDoOutput(true);

                    // Create a Map for the request body
                    Map<String, String> requestBodyMap = new HashMap<>();
                    requestBodyMap.put("username", Main.userData.getUsername());
                    requestBodyMap.put("password", newPassword);

                    // Convert the Map to a JSON string
                    String jsonRequestBody = new Gson().toJson(requestBodyMap);

                    // Write the request body data
                    try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
                        out.writeBytes(jsonRequestBody);
                        out.flush();
                    }

                    // Get the HTTP response code
                    int responseCode = connection.getResponseCode();

                    // Read the response from the server
                    if(responseCode == HttpURLConnection.HTTP_OK){
                        connection.disconnect();
                        navigateToMyAccount();
                    }
                    else{
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        StringBuffer response = new StringBuffer();

                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();

                        // Parse the JSON response using Jackson
                        System.out.println(response);
                        warningText.setText("Password could not be changed, please try again");
                    }
                    // Close the connection


                } catch (Exception e) {
                    e.printStackTrace();
                }
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
