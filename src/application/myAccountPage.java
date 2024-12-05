package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class myAccountPage extends Pane {

    public myAccountPage() {
        // Outer container rectangle
        Rectangle mainRectangle = new Rectangle();
        mainRectangle.widthProperty().bind(this.widthProperty().multiply(0.8));
        mainRectangle.heightProperty().bind(this.heightProperty().multiply(0.8));
        mainRectangle.xProperty().bind(this.widthProperty().subtract(mainRectangle.widthProperty()).divide(2));
        mainRectangle.yProperty().bind(this.heightProperty().subtract(mainRectangle.heightProperty()).divide(2));
        mainRectangle.setFill(Color.WHITE);

        // Left VBox for account information
        VBox leftBox = new VBox(15);
        leftBox.setPadding(new Insets(20));
        leftBox.setAlignment(Pos.TOP_LEFT);
        leftBox.prefWidthProperty().bind(mainRectangle.widthProperty().multiply(0.5));
        leftBox.prefHeightProperty().bind(mainRectangle.heightProperty());
        leftBox.layoutXProperty().bind(mainRectangle.xProperty());
        leftBox.layoutYProperty().bind(mainRectangle.yProperty());

        Label accountTitle = new Label("My Account");
        accountTitle.setFont(new Font("Arial", 20));
        accountTitle.setStyle("-fx-font-weight: bold;");

        Label usernameLabel = new Label("Username: " + loginPage.usernameString);
        usernameLabel.setFont(new Font("Arial", 16));

        Label emailLabel = new Label("Email: " + loginPage.emailString);
        emailLabel.setFont(new Font("Arial", 16));

        Label roleLabel = new Label("Role: " + loginPage.roleString);
        roleLabel.setFont(new Font("Arial", 16));

        leftBox.getChildren().addAll(accountTitle, usernameLabel, emailLabel, roleLabel);

        // Right VBox for buttons
        VBox rightBox = new VBox(20);
        rightBox.setPadding(new Insets(20));
        rightBox.setAlignment(Pos.TOP_CENTER);
        rightBox.prefWidthProperty().bind(mainRectangle.widthProperty().multiply(0.5));
        rightBox.prefHeightProperty().bind(mainRectangle.heightProperty());
        rightBox.layoutXProperty().bind(mainRectangle.xProperty().add(mainRectangle.widthProperty().multiply(0.5)));
        rightBox.layoutYProperty().bind(mainRectangle.yProperty());

        Button changeUsernameButton = new Button("Change Username");
        Button changePasswordButton = new Button("Change Password");
        Button deleteAccountButton = new Button("Delete Account");

        changeUsernameButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-size: 14px;");
        changePasswordButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-size: 14px;");
        deleteAccountButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-size: 14px;");

        changeUsernameButton.setPrefWidth(150);
        changePasswordButton.setPrefWidth(150);
        deleteAccountButton.setPrefWidth(150);

        rightBox.getChildren().addAll(changeUsernameButton, changePasswordButton, deleteAccountButton);

        // Separator line
        Line separatorLine = new Line();
        separatorLine.startYProperty().bind(mainRectangle.yProperty());
        separatorLine.endYProperty().bind(mainRectangle.yProperty().add(mainRectangle.heightProperty()));
        separatorLine.startXProperty().bind(mainRectangle.xProperty().add(mainRectangle.widthProperty().multiply(0.5)));
        separatorLine.endXProperty().bind(separatorLine.startXProperty());
        separatorLine.setStroke(Color.BLACK);

        // Footer button
        Button goBackButton = new Button("Go Back");
        goBackButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000; -fx-font-size: 14px;");
        goBackButton.setPrefWidth(100);
        goBackButton.setLayoutX(mainRectangle.getLayoutX() + 20);
        goBackButton.layoutYProperty().bind(mainRectangle.yProperty().add(mainRectangle.heightProperty().add(10)));

        goBackButton.setOnAction(e -> {
            loginPage loginPage = new loginPage();
            Scene loginPageScene = new Scene(loginPage, 800, 600);
            loginPage.setStyle("-fx-background-color: #F5DEB3;");
            Stage mainStage = Main.getPrimaryStage();
            mainStage.setScene(loginPageScene);
        });

        // Adding components to the Pane
        this.getChildren().addAll(mainRectangle, leftBox, separatorLine, rightBox, goBackButton);
        this.setStyle("-fx-background-color: #F5DEB3;"); // Set background color for the scene
    }
}

