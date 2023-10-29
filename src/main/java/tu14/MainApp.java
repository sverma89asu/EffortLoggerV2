package tu14;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tu14.user.UserData;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {

    private static Stage stage;
    private static Scene prev = null;

    private static UserData user;

    public static UserData getUser() {
        return user;
    }

    @Override
    public void start(Stage stage) throws Exception {

        // setup services and singletons
        user = new UserData();

        Platform.setImplicitExit(true);

        MainApp.stage = stage;

        Parent root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("index.fxml")));
        Scene rootScene = new Scene(root, stage.getMaxWidth() / 2, stage.getMaxHeight() / 2);

        stage.setTitle("Effort Logger V2");
        stage.setScene(rootScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void go(String fxml) {
        Platform.runLater(() -> {
            Parent root;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(MainApp.class.getResource(fxml)));
            } catch (IOException e) {
                System.err.println("Error: resource " + fxml + " not accessible");
                return;
            }

            Scene current = stage.getScene();
            prev = current;

            Scene rootScene = new Scene(root, current.getWidth(), current.getHeight());

            stage.setScene(rootScene);
        });
    }

    public static void back() {
        if (prev != null) {
            Platform.runLater(() -> {
                Scene current = stage.getScene();
                stage.setScene(prev);
                prev = current;
            });
        }
    }
}