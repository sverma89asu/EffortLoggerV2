package tu14.services;

import javafx.stage.FileChooser;
import netscape.javascript.JSObject;
import tu14.MainApp;
import tu14.api.Backend;
import tu14.api.request.CreateRequest;
import tu14.api.request.DeleteRequest;
import tu14.api.request.GetRequest;
import tu14.api.request.UpdateRequest;
import tu14.api.tables.Tables;
import tu14.model.EffortLog;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EffortLogService {

    private final List<EffortLog> logs = new ArrayList<>();
    public boolean clockRunning = false;
    private Instant clockStartTime;
    private Instant clockEndTime;

    public EffortLogService() {
        Backend.getInstance().send(new GetRequest().table("effortLog"), EffortLog.class).thenAccept((raw) -> logs.addAll(raw.castSafe()));
    }

    public void startClock() {
        this.clockRunning = true;
        this.clockStartTime = Instant.now();
    }

    private void stopClock() {
        this.clockRunning = false;
        this.clockEndTime = Instant.now();
    }

    public boolean deleteLog(int id) {
        try {
            Backend.getInstance().send(new DeleteRequest().table(Tables.EffortLog).id(id), null).get();
            return true;
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }

    public boolean completeEffortLog(int lifeCycle, int effortCategory, int deliverable,
                                     int project) {

        if (!this.clockRunning) {
            return false;
        } else {
            this.stopClock();
            this.clockRunning = false;
        }

        EffortLog log = new EffortLog(this.clockStartTime, this.clockEndTime, lifeCycle,
                                      effortCategory, deliverable, project);

        try {
            var data =
                    Backend.getInstance().send(new CreateRequest().table(Tables.EffortLog).body(log), EffortLog.class).get();

            this.logs.addAll(data.castSafe());

            return data.ok();
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }

    public boolean saveEffortLogs(JSObject editedLogs, int length) {
        for (int i = 0; i < length; i++) {
            JSObject jsLog = (JSObject) editedLogs.getSlot(i);
            EffortLog log =
                    logs.stream().filter((l) -> l.getId() == (int) jsLog.getMember("id")).findFirst().get();

            log.project = (int) jsLog.getMember("project");
            log.effortCategory = (int) jsLog.getMember("effortCategory");
            log.deliverable = (int) jsLog.getMember("deliverable");
            log.lifeCycle = (int) jsLog.getMember("lifeCycle");

            try {
                Backend.getInstance().send(new UpdateRequest().table(Tables.EffortLog).id(log.getId()).body(log), null).get();
            } catch (InterruptedException | ExecutionException e) {
                return false;
            }
        }

        return true;
    }

    public int exportEffortLogs() {

        if (logs.isEmpty()) return 1;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Effort Logs");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), "Downloads"));
        fileChooser.setInitialFileName("effortlogs" + DateTimeFormatter.BASIC_ISO_DATE.format(LocalDate.now()) + ".csv");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV File", "*" +
                ".csv"), new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showSaveDialog(MainApp.getStage());

        if (selectedFile == null) {
            System.out.println("No file chosen");
            return 2;
        }

        try {
            if (!selectedFile.createNewFile()) return 3;
        } catch (IOException e) {
            return -1;
        }

        List<String> csv = new ArrayList<>(List.of(logs.get(0).toCSVHeader()));

        csv.addAll(logs.stream().map(EffortLog::toCSV).toList());

        try {
            Files.write(selectedFile.toPath(), csv, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return -1;
        }

        return 0;
    }
}
