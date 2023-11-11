package tu14.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tu14.MainApp;
import tu14.api.RawData;
import tu14.api.request.CreateRequest;
import tu14.api.request.GetRequest;
import tu14.api.request.UpdateRequest;
import tu14.logs.EffortLog;
import tu14.logs.LogOfLogs;
import tu14.logs.LogType;
import tu14.api.Backend;
import tu14.model.Deliverable;
import tu14.model.EffortCategory;
import tu14.model.LifeCycle;
import tu14.model.Project;
import tu14.user.RawUserData;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;

public class EffortController {
    public ComboBox<EffortCategory> effort_category;
    public ComboBox<Deliverable> deliverable;
    public ComboBox<LifeCycle> lifecycle;
    public ComboBox<Project> project;
    public Label activity_error_label;

    public Text clockStatus;

    private boolean isClockStarted = false;

    public Button startActivity;

    public Timestamp startTime;

    public Timestamp endTime;

    public void initialize(){
        List<Project> projects = new ArrayList<>();
        List<EffortCategory> effortCategories = new ArrayList<>();
        List<LifeCycle> lifeCycles = new ArrayList<>();
        List<Deliverable> deliverables = new ArrayList<>();
        Backend.getInstance().send(new GetRequest().table("project"), Project.class).thenAccept((raw) -> {
            if (!raw.ok()) {
                Platform.runLater(() -> {
                    System.err.println(raw.errorMessage);
                });

                return;
            }

            projects.addAll(raw.castSafe());
            project.getItems().addAll(projects);
        });
        Backend.getInstance().send(new GetRequest().table("deliverable"), Deliverable.class).thenAccept((raw) -> {
            if (!raw.ok()) {
                Platform.runLater(() -> {
                    System.err.println(raw.errorMessage);
                });

                return;
            }

            deliverables.addAll(raw.castSafe());
            deliverable.getItems().addAll(deliverables);
        });
        Backend.getInstance().send(new GetRequest().table("lifeCycle"), LifeCycle.class).thenAccept((raw) -> {
            if (!raw.ok()) {
                Platform.runLater(() -> {
                    System.err.println(raw.errorMessage);
                });

                return;
            }

            lifeCycles.addAll(raw.castSafe());
            lifecycle.getItems().addAll(lifeCycles);
        });
        Backend.getInstance().send(new GetRequest().table("effortCategory"), EffortCategory.class).thenAccept((raw) -> {
            if (!raw.ok()) {
                Platform.runLater(() -> {
                    System.err.println(raw.errorMessage);
                });

                return;
            }

            effortCategories.addAll(raw.castSafe());
            effort_category.getItems().addAll(effortCategories);
        });
    }

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
        
        // These were to print out the Effort Logs in the console for screencast
//        for (EffortLog log : MainApp.getEdited()) {
//            System.out.println(EffortLog.transform(log));
//        }
    }

    
    
    public void checkValidInputsAndSubmit(ActionEvent actionEvent) {
            Project selectedProject = project.getValue();
            if(selectedProject == null){
                activity_error_label.setText("Please select the Project");
                return;
            }
            LifeCycle selectedLifeCycle = lifecycle.getValue();
            if(selectedLifeCycle == null){
                activity_error_label.setText("Please select the Life Cycle Step");
                return;
            }
            EffortCategory selectedEffortCategory = effort_category.getValue();
            if(selectedEffortCategory == null){
                activity_error_label.setText("Please select the Effort Category");
                return;
            }
            Deliverable selectedDeliverable = deliverable.getValue();
            if(selectedDeliverable == null){
                activity_error_label.setText("Please select the Deliverable");
                return;
            }
        activity_error_label.setText("");
            isClockStarted = !isClockStarted;
            if(isClockStarted){
                startTime = new Timestamp(System.currentTimeMillis());
                startActivity.setText("Stop Activity");
                clockStatus.setText("The clock has started");
                clockStatus.setFill(Color.GREEN);
            }
            else{
                endTime = new Timestamp(System.currentTimeMillis());
                startActivity.setText("Start Activity");
                clockStatus.setText("The clock has stopped");
                clockStatus.setFill(Color.RED);
                try {
                    RawData<?> data = Backend.getInstance().send(
                            new CreateRequest().table("effortLog")
                                    .body(new tu14.model.EffortLog(0, startTime, endTime, selectedLifeCycle.getId(), selectedEffortCategory.getId(), selectedDeliverable.getId(), selectedProject.getId())), null).get();
                    if (data.ok()) {
                        clockStatus.setText("");
                    } else {
                        System.out.println(data.errorMessage);
                        activity_error_label.setText("The effort log could not be saved");
                    }
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        }

}
