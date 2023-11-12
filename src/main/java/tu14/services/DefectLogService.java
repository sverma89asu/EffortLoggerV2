package tu14.services;

import javafx.stage.FileChooser;
import netscape.javascript.JSObject;
import tu14.MainApp;
import tu14.api.Backend;
import tu14.api.request.CreateRequest;
import tu14.api.request.DeleteRequest;
import tu14.api.request.GetRequest;
import tu14.api.request.UpdateRequest;
import tu14.model.DefectLog;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DefectLogService {

    private final List<DefectLog> logs = new ArrayList<>();

    public DefectLogService() {
        Backend.getInstance().send(new GetRequest().table("defectLog"), DefectLog.class).thenAccept((raw) -> logs.addAll(raw.castSafe()));
        System.out.println("B");
    }

    public boolean createDefectLog(String name, String description, int project,
                                   int lifeCycleIncluded, int defectCategory) {
        System.out.println("A");

        DefectLog log = new DefectLog(name, description, project, lifeCycleIncluded,
                                      defectCategory);

        try {
            var data =
                    Backend.getInstance().send(new CreateRequest().table("defectLog").body(log),
                                               DefectLog.class).get();

            this.logs.addAll(data.castSafe());

            return data.ok();
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }

    public boolean deleteLog(int id) {
        try {
            Backend.getInstance().send(new DeleteRequest().table("defectlog").id(id), null).get();
            return true;
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }

    public boolean saveDefectLogs(JSObject editedLogs, int length) {
        for (int i = 0; i < length; i++) {
            JSObject jsLog = (JSObject) editedLogs.getSlot(i);
            DefectLog log =
                    logs.stream().filter((l) -> l.getId() == (int) jsLog.getMember("id")).findFirst().get();

            log.description = (String) jsLog.getMember("description");
            log.fix = (String) jsLog.getMember("fix");
            log.status = (boolean) jsLog.getMember("status");
            log.defectCategory = (int) jsLog.getMember("defectCategory");
            log.lifeCycleIncluded = (int) jsLog.getMember("lifeCycleIncluded");
            log.lifeCycleExcluded = (Integer) jsLog.getMember("lifeCycleExcluded");
            log.project = (int) jsLog.getMember("project");

            try {
                Backend.getInstance().send(new UpdateRequest().table("defectlog").id(log.getId()).body(log), null).get();
            } catch (InterruptedException | ExecutionException e) {
                return false;
            }
        }

        return true;
    }

    public int exportDefectLogs() {

        if (logs.isEmpty()) return 1;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Defect Logs");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), "Downloads"));
        fileChooser.setInitialFileName("defectlogs" + DateTimeFormatter.BASIC_ISO_DATE.format(LocalDate.now()) + ".csv");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV File",
                                                                                 "*" + ".csv"),
                                                 new FileChooser.ExtensionFilter("All Files", "*" +
                                                         ".*"));
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

        csv.addAll(logs.stream().map(DefectLog::toCSV).toList());

        try {
            Files.write(selectedFile.toPath(), csv, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return -1;
        }

        return 0;
    }
}
