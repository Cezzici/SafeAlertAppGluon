package com.safealert;

import com.gluonhq.charm.glisten.application.MobileApplication;
import javafx.scene.Scene;

public class Main extends MobileApplication {

    @Override
    public void init() {
        addViewFactory("INTRO", IntroView::new);
        addViewFactory("GAME", GameView::new);
    }

    @Override
    public void postInit(Scene scene) {
        // Aplică stilul personalizat (inclusiv pentru AppBar)
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
	scene.getWindow().setWidth(450);
	scene.getWindow().setHeight(800);
        switchView("INTRO");
    }
}
