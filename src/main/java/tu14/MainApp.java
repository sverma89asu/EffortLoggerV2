package tu14;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tu14.user.UserData;

import java.util.Objects;

public class MainApp extends Application {

    private static Stage stage;

    private static UserData user;

    public static UserData getUser() {
        return user;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        // setup services and singletons
        user = new UserData();

        Platform.setImplicitExit(true);

        MainApp.stage = stage;

        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApp.class.getResource("main.fxml")));
        Scene rootScene = new Scene(root, stage.getMaxWidth() / 2, stage.getMaxHeight() / 2);

        stage.setTitle("Effort Logger V2");
        stage.setScene(rootScene);
        stage.show();
    }


}