package com.epam.lab.spider.controller.oauth;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class OAuthDialog extends Application {

    private static final Logger logger = Logger.getLogger(OAuthDialog.class);
    private static OAuthConfiguration conf;

    public static String open(OAuthConfiguration configuration) {
        conf = configuration;
        Application.launch(OAuthDialog.class);
        return conf.response;
    }

    @Override
    public void start(final Stage stage) throws Exception {
        logger.info("Открытие диалога авторизации OAuth 2.0");
        final Group root = new Group();
        Scene scene = new Scene(root, conf.width, conf.height, Color.WHITE);
        stage.setScene(scene);
        WebView webView = new WebView();
        webView.setPrefSize(conf.width, conf.height);
        final WebEngine webEngine = webView.getEngine();
        webEngine.load(conf.authorization);
        webEngine.getLoadWorker().stateProperty()
                .addListener(new ChangeListener<Object>() {

                    @Override
                    public void changed(ObservableValue<? extends Object> arg0,
                                        Object arg1, Object arg2) {
                        stage.setTitle(webEngine.getTitle());
                        if (webEngine.getLocation().contains(conf.token)) {
                            stage.close();
                            conf.response = webEngine.getLocation();
                        }
                    }
                });
        stage.getIcons().add(conf.icon);
        root.getChildren().addAll(webView);
        stage.show();
    }

}
