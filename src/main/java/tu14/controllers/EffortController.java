package tu14.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tu14.MainApp;
import tu14.input.ValidateInput;
import tu14.logs.EffortLog;
import tu14.logs.LogOfLogs;
import tu14.logs.LogType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class EffortController {
    public TextField effort_category;
    public TextField deliverable;
    public TextField lifecycle;
    public TextField project;
    public Label activity_error_label;

    public void generateLogs(ActionEvent actionEvent) {
        // In the real application we'd grep these from the db
        List<EffortLog> logs = MainApp.getLogs();

        for (int i = 0; i < 10; i++) {
            logs.add(EffortLog.constructRandom());
        }
    }

    public void viewLogs(ActionEvent actionEvent) {
        MainApp.go("viewlogs.fxml");
    }

    public void editLogs(ActionEvent actionEvent) {
        ArrayList<LogType> logs =
                new ArrayList<>(MainApp.getLogs().stream().map(EffortLog::transform).toList());

        ArrayList<LogType> edited =
                new ArrayList<>(MainApp.getEdited().stream().map(EffortLog::transform).toList());

        LogOfLogs.EditLog(logs, edited);

        MainApp.getLogs().clear();
        MainApp.getLogs().addAll(logs.stream().map(EffortLog::transform).toList());
        MainApp.getEdited().addAll(edited.stream().map(EffortLog::transform).toList());
    }

    public void checkValidInputsAndSubmit(ActionEvent actionEvent) {
        boolean effortValid = ValidateInput.effortCategoryValidate(effort_category.getText());
        boolean lifecycleValid = ValidateInput.lifeCycleValidate(lifecycle.getText());
        boolean deliverableValid = ValidateInput.deliverableValidate(deliverable.getText());

        if (effortValid && lifecycleValid && deliverableValid) {
            System.out.println("Yay test success");
            activity_error_label.setText("");
        } else {
            activity_error_label.setText("One or more fields was invalid");
        }
    }
}
