package com.safealert;

import com.gluonhq.attach.position.Position;
import com.gluonhq.attach.position.PositionService;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class GameView extends View {

    private static String playerName = "Anonim";

    private final GridPane grid = new GridPane();
    private final Label statusLabel = new Label("Rândul lui X");
    private final Label userNameLabel = new Label("Player: ");
    private final Label scoreLabel = new Label("X: 0   O: 0");
    private final Button resetButton = new Button("Reset");

    private String currentPlayer = "X";
    private int xScore = 0;
    private int oScore = 0;

    private final SafeAlertLogic logic = new SafeAlertLogic();
    private final String uuid = UserIdentifier.getOrCreateUUID();
    private String name = playerName;

    public GameView() {
        getStylesheets().add("/style.css");

        userNameLabel.getStyleClass().add("label-medium");
        statusLabel.getStyleClass().add("label-large");
        scoreLabel.getStyleClass().add("score-label");
        resetButton.getStyleClass().add("reset-button");

        VBox layout = new VBox(20, userNameLabel, scoreLabel, statusLabel, grid, resetButton);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("root");

        setCenter(layout);

        userNameLabel.setText("Player: " + playerName);

        resetButton.setOnAction(e -> resetGame());
        setupGrid();
    }

    public static void setPlayerName(String name) {
        playerName = name;
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setTitleText("Tic Tac Toe");
	appBar.setStyle("-fx-background-color: #7B2CBF;");
    }

    private void setupGrid() {
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.getChildren().clear();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Label cell = new Label();
                cell.setPrefSize(100, 100);
                cell.getStyleClass().add("cell");

                final int r = row;
                final int c = col;

                cell.setOnMouseClicked(e -> handleMove(cell, r, c));
                grid.add(cell, col, row);
            }
        }
    }

    private void handleMove(Label cell, int row, int col) {
        if (!cell.getText().isEmpty() || logic.isGameOver()) return;

        cell.setText(currentPlayer);
        logic.makeMove(row, col, currentPlayer);

        if (logic.alertaDeTrimis()) {
            PositionService.create().ifPresent(service -> {
                Position position = service.getPosition();
                if (position != null) {
                    double lat = position.getLatitude();
                    double lng = position.getLongitude();
                    AlertSender.sendAlert(uuid, name, lat, lng);
                    logic.marcheazaAlertaTrimisa();
                    statusLabel.setText("📍 Alertă trimisă!");
                }
            });
        }

        if (logic.hasWinner()) {
            if (currentPlayer.equals("X")) xScore++;
            else oScore++;
            scoreLabel.setText("X: " + xScore + "   O: " + oScore);
            statusLabel.setText("🏆 Câștigător: " + currentPlayer);
        } else if (logic.isDraw()) {
            statusLabel.setText("🤝 Remiză!");
        } else {
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
            statusLabel.setText("Rândul lui: " + currentPlayer);
        }
    }

    private void resetGame() {
        logic.reset();
        currentPlayer = "X";
        setupGrid();
        statusLabel.setText("Rândul lui X");
    }
}
