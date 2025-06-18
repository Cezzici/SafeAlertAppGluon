package com.safealert;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class IntroView extends View {

    public IntroView() {
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/logo.png")));
        logo.setFitWidth(120);
        logo.setPreserveRatio(true);

        Label title = new Label("👋 Bine ai venit! Cine joacă?");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        TextField nameInput = new TextField();
        nameInput.setPromptText("Introdu numele tău");
        nameInput.setMaxWidth(200);

        Button startButton = new Button("🎮 Începe jocul");
        startButton.setStyle("-fx-background-color: #7B2CBF; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 12;");

        VBox layout = new VBox(20, logo, title, nameInput, startButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 40; -fx-background-color: #FAF9FE;");

        setCenter(layout);

        startButton.setOnAction(e -> {
            String playerName = nameInput.getText().trim();
            if (!playerName.isEmpty()) {
                GameView.setPlayerName(playerName);
                MobileApplication.getInstance().switchView("GAME");
            } else {
                nameInput.setPromptText("⚠️ Numele nu poate fi gol");
            }
        });
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setTitleText("TicTacToe - Start");
	appBar.setStyle("-fx-background-color: #7B2CBF;");
    }
}
