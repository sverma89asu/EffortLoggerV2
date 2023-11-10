package tu14;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tu14.logs.EffortLog;
import tu14.user.UserData;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {

    private static Stage stage;
    private static Scene prev = null;

    private static UserData user;
    private static ObservableList<EffortLog> logs;
    private static ObservableList<EffortLog> edited;

    public static UserData getUser() {
        return user;
    }

    public static ObservableList<EffortLog> getLogs() {
        return logs;
    }

    public static ObservableList<EffortLog> getEdited() {
        return edited;
    }

    @Override
    public void start(Stage stage) throws Exception {

        // setup services and singletons
        user = new UserData();
        logs = FXCollections.observableArrayList();
        edited = FXCollections.observableArrayList();

        Platform.setImplicitExit(true);

        MainApp.stage = stage;

        Parent root =
                FXMLLoader.load(Objects.requireNonNull(MainApp.class.getResource("main.fxml")));
        Scene rootScene = new Scene(root, stage.getMaxWidth() / 2, stage.getMaxHeight() / 2);

        stage.setTitle("Effort Logger V2");
        stage.setScene(rootScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}