package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class resetPasswordPage extends StackPane {

    public resetPasswordPage() {
        this.setStyle("-fx-background-color: #F5DEB3;");

        // Background rectangle
        Rectangle box = new Rectangle();
        box.setFill(Color.WHITE);
        box.setArcWidth(10);
        box.setArcHeight(10);
        box.widthProperty().bind(this.widthProperty().multiply(0.6)); // 60% of window width
        box.heightProperty().bind(this.heightProperty().multiply(0.6)); // 60% of window height

        // Title Label
        Label titleLabel = new Label("Reset Password");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #000000;");

        // Password Field
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", 14));
        PasswordField passwordField = new PasswordField();
        TextField visiblePasswordField = new TextField();
        visiblePasswordField.setManaged(false);
        visiblePasswordField.setVisible(false);
        passwordField.textProperty().bindBidirectional(visiblePasswordField.textProperty());

        Button togglePasswordButton = new Button("Show Password");
        togglePasswordButton.setOnAction(e -> {
            if (passwordField.isVisible()) {
                passwordField.setVisible(false);
                passwordField.setManaged(false);
                visiblePasswordField.setVisible(true);
                visiblePasswordField.setManaged(true);
                togglePasswordButton.setText("Hide Password");
            } else {
                passwordField.setVisible(true);
                passwordField.setManaged(true);
                visiblePasswordField.setVisible(false);
                visiblePasswordField.setManaged(false);
                togglePasswordButton.setText("Show Password");
            }
        });

        HBox passwordBox = new HBox(10, passwordLabel, passwordField, visiblePasswordField, togglePasswordButton);
        passwordBox.setAlignment(Pos.CENTER);

        // Confirm Password Field
        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Arial", 14));
        PasswordField confirmPasswordField = new PasswordField();
        TextField visibleConfirmPasswordField = new TextField();
        visibleConfirmPasswordField.setManaged(false);
        visibleConfirmPasswordField.setVisible(false);
        confirmPasswordField.textProperty().bindBidirectional(visibleConfirmPasswordField.textProperty());

        Button toggleConfirmPasswordButton = new Button("Show Confirm Password");
        toggleConfirmPasswordButton.setOnAction(e -> {
            if (confirmPasswordField.isVisible()) {
                confirmPasswordField.setVisible(false);
                confirmPasswordField.setManaged(false);
                visibleConfirmPasswordField.setVisible(true);
                visibleConfirmPasswordField.setManaged(true);
                toggleConfirmPasswordButton.setText("Hide Confirm Password");
            } else {
                confirmPasswordField.setVisible(true);
                confirmPasswordField.setManaged(true);
                visibleConfirmPasswordField.setVisible(false);
                visibleConfirmPasswordField.setManaged(false);
                toggleConfirmPasswordButton.setText("Show Confirm Password");
            }
        });

        HBox confirmPasswordBox = new HBox(10, confirmPasswordLabel, confirmPasswordField, visibleConfirmPasswordField, toggleConfirmPasswordButton);
        confirmPasswordBox.setAlignment(Pos.CENTER);

        VBox errorBox = new VBox(10);
        errorBox.setAlignment(Pos.BASELINE_LEFT);

        // Reset Password Button
        Button resetPasswordButton = new Button("Reset Password");
        resetPasswordButton.setOnAction(e -> {
            if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                Label notMatchLabel = new Label("Passwords do not match");
                notMatchLabel.setFont(new Font("Arial", 14));
                notMatchLabel.setMinWidth(200);
                if (!errorBox.getChildren().contains(notMatchLabel)) {
                    errorBox.getChildren().add(notMatchLabel);
                }
                return;
            }
            System.out.println("Password reset successfully!");
        });

        // Return to Login Button
        Button returnToLoginButton = new Button("Return to Login");
        returnToLoginButton.setOnAction(e -> {
            loginPage loginPage = new loginPage();
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(new Scene(loginPage, 800, 600)); // Adjust dimensions if needed
        });

        // Buttons Box (for Reset Password and Return to Login)
        HBox buttonsBox = new HBox(20, resetPasswordButton, returnToLoginButton);
        buttonsBox.setAlignment(Pos.CENTER);

        // Add all elements to a VBox
        VBox contentBox = new VBox(20, titleLabel, passwordBox, confirmPasswordBox, errorBox, buttonsBox);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(20));

        // Add contentBox to the resizable StackPane
        StackPane resizableBox = new StackPane(box, contentBox);
        resizableBox.setAlignment(Pos.CENTER);

        this.getChildren().add(resizableBox);
    }
}

