package tu14.controllers;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import tu14.MainApp;

import java.util.Objects;

public class MainController {
    public WebView webView;

    public void initialize() {
        WebEngine engine = webView.getEngine();
        engine.load(Objects.requireNonNull(MainApp.class.getResource("index.html")).toExternalForm());
    }
}
