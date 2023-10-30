package tu14.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import tu14.MainApp;
import tu14.logs.EffortLog;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EffortController {
    public TextField effort_category;
    public TextField deliverable;
    public TextField lifecycle;
    public TextField project;

    @FXML
    public void initialize() {

    }

    public void generateLogs(ActionEvent actionEvent) {
        // In the real application we'd grep these from the db
        List<EffortLog> logs = MainApp.getLogs();

        Random gen = ThreadLocalRandom.current();
        long now = Instant.now().toEpochMilli() / 1000;

        for (int i = 0; i < 10; i++) {
            Instant start = Instant.ofEpochSecond(gen.nextLong(0, now));

            logs.add(new EffortLog(start,
                                   Instant.ofEpochSecond(gen.nextLong(start.getEpochSecond(), now)),
                                   "Random " + gen.nextInt(0, 100),
                                   "Random " + gen.nextInt(0, 100),
                                   "Random " + gen.nextInt(0, 100)));
        }
    }

    public void viewLogs(ActionEvent actionEvent) {
        MainApp.go("viewlogs.fxml");
    }
}
