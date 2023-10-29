import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.scene.control.Button;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Author: Shikha Verma
 * Date: 2023-10-27
 * Description: This Java class implements the login functionality for the effortlogger
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text loginMessage;

    @FXML
    private Text warningText;

    @FXML
    private Button eyeButton;

    @FXML
    private  TextField passwordText;

    private boolean passwordVisible = false;

    public void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;

        if (passwordVisible) {
            eyeButton.setText("Hide");
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            passwordText.setVisible(true);
            passwordText.setManaged(true);
            passwordText.setText(passwordField.getText());
        } else {
            eyeButton.setText("Show");
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            passwordText.setVisible(false);
            passwordText.setManaged(false);
        }
    }
    public void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean isLoginSuccess = false;
        try {
            // Define the URL you want to send the GET request to
            String url = "https://cse360.flerp.dev/tables/user";

            // Create a URL object
            URL requestUrl = new URL(url);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Set the Authorization header
            String authorizationHeader = "Bearer dGVtcG9yYXJ5IGFsc29fdGVtcG9yYXJ5";
            connection.setRequestProperty("Authorization", authorizationHeader);

            // Get the response code
            int responseCode = connection.getResponseCode();

            // Check if the response code is HTTP 200 (OK)
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response content
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuffer response = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the JSON response using Jackson
                System.out.println(response);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonArray = objectMapper.readTree(response.toString());

                // Iterate through the JSON array and access individual objects
                for (JsonNode jsonNode : jsonArray) {
                    String username1 = jsonNode.get("username").asText();
                    String password1 = jsonNode.get("password").asText();
                    int id = jsonNode.get("id").asInt();
                    if(username.equals(username1) && password.equals(password1)){
                        Main.userData.setUsername(username);
                        Main.userData.setId(id);
                        isLoginSuccess = true;
                        navigateToMyAccount();
                        break;
                    }
                }
                if(!isLoginSuccess){
                    warningText.setText("Invalid username or password.");
                }
            } else {
                System.out.println("Failed to send GET request. Response Code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Implement login logic here.

        // For simplicity, assume successful login and navigate to the My Account page
    }

    private void navigateToMyAccount() {
        try {
            Parent myAccountRoot = FXMLLoader.load(getClass().getResource("myaccount.fxml"));
            Scene myAccountScene = new Scene(myAccountRoot, 400, 300);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(myAccountScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
