package cue.edu.co.vote.view;

import cue.edu.co.vote.database.VoterDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;

public class RegisterView {

    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20, 20, 20, 20));
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("root-register");

        Label voterNameLabel = new Label("Your Name:");
        TextField voterNameField = new TextField();

        Label voterIdLabel = new Label("Your Identification:");
        TextField voterIdField = new TextField();

        Button registerButton = new Button("Register");
        registerButton.setPrefSize(200, 40);
        registerButton.getStyleClass().add("button-register");

        registerButton.setOnAction(event -> {
            String voterName = voterNameField.getText();
            String voterId = voterIdField.getText();
            if (!voterName.isEmpty() && !voterId.isEmpty()) {
                try {
                    VoterDAO voterDAO = new VoterDAO();
                    if (!voterDAO.isVoterValid(voterId)) {
                        voterDAO.insertVoter(voterName, voterId);
                        showAlert("Registration Successful", "Voter registered successfully!");
                    } else {
                        showAlert("Registration Error", "Voter ID already exists.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Registration Error", "An error occurred during registration.");
                }
            } else {
                showAlert("Input Error", "Please enter both your name and ID.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setPrefSize(200, 40);
        backButton.getStyleClass().add("button");
        backButton.setOnAction(event -> {
            MainView mainView = new MainView();
            mainView.start(primaryStage);
        });

        root.getChildren().addAll(voterNameLabel, voterNameField, voterIdLabel, voterIdField, registerButton, backButton);

        Scene scene = new Scene(root, 400, 400);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/cue/edu/co/vote/style.css")).toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Register Voter");
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Stage alertStage = new Stage();
        VBox alertRoot = new VBox(10);
        alertRoot.setPadding(new Insets(20, 20, 20, 20));
        alertRoot.setAlignment(Pos.CENTER);
        alertRoot.getStyleClass().add("root");

        Label messageLabel = new Label(message);
        Button okButton = new Button("OK");
        okButton.getStyleClass().add("button");
        okButton.setOnAction(event -> alertStage.close());

        alertRoot.getChildren().addAll(messageLabel, okButton);

        Scene alertScene = new Scene(alertRoot, 300, 200);

        alertScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/cue/edu/co/vote/style.css")).toExternalForm());

        alertStage.setTitle(title);
        alertStage.setScene(alertScene);
        alertStage.show();
}
}