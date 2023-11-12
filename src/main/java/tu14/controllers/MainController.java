package tu14.controllers;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import tu14.MainApp;
import tu14.api.Backend;
import tu14.api.request.GetRequest;
import tu14.api.tables.Tables;
import tu14.services.EffortLogService;
import tu14.user.RawUserData;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MainController {
    public WebView webView;

    public void initialize() {
        webView.setContextMenuEnabled(false);

        WebEngine engine = webView.getEngine();
        engine.load(Objects.requireNonNull(MainApp.class.getResource("index.html")).toExternalForm());

        engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            JSObject window = (JSObject) engine.executeScript("window");
            window.setMember("app", this);
            window.setMember("service_EffortLog", new EffortLogService());
        });
    }

    public void log(String string) {
        System.out.println(string);
    }

    public Boolean login(String username, String password) {
        try {
            return Backend.getInstance().send(new GetRequest().table(Tables.Users),
                                              RawUserData.class).thenApply((raw) -> {
                if (!raw.ok()) return null;

                for (RawUserData datum : raw.castSafe()) {
                    if (datum.username.equals(username) && datum.password.equals(password)) {
                        // TODO set User singleton here

                        MainApp.getUser().setId(datum.id);
                        MainApp.getUser().setUsername(datum.username);

                        // set error message if we fail to progress pages


    //                    MainApp.go("myaccount.fxml");
                        return true;
                    }
                }

                return false;
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }
}
