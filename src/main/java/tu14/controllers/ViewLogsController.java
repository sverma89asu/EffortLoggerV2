package tu14.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tu14.MainApp;
import tu14.types.EffortLog;

public class ViewLogsController {

    public TableColumn<EffortLog, Integer> idCol;
    public TableColumn<EffortLog, String> startCol;
    public TableColumn<EffortLog, String> durationCol;
    public TableColumn<EffortLog, String> lifeCycleCol;
    public TableColumn<EffortLog, String> effortCol;
    public TableColumn<EffortLog, String> deliverableCol;
    public TableView<EffortLog> table;

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        lifeCycleCol.setCellValueFactory(new PropertyValueFactory<>("lifecycle"));
        effortCol.setCellValueFactory(new PropertyValueFactory<>("effort"));
        deliverableCol.setCellValueFactory(new PropertyValueFactory<>("deliverable"));

        ObservableList<EffortLog> logs = MainApp.getLogs();
        table.setItems(logs);
    }

    public void back(ActionEvent actionEvent) {
        MainApp.back();
    }
}
