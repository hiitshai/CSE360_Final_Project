package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

public class forgotPasswordPage extends StackPane {

    public forgotPasswordPage() {
        this.setStyle("-fx-background-color: #F5DEB3;");

        Rectangle box = new Rectangle();
        box.setFill(Color.WHITE);
        box.widthProperty().bind(this.widthProperty().multiply(0.6)); // 40% of window width
        box.heightProperty().bind(this.heightProperty().multiply(0.5)); // 30% of window height

        Label titleLabel = new Label("Forgot Password?");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #000000;");

        Label emailLabel = new Label("Enter email:");
        emailLabel.setFont(new Font("Arial", 16));
        emailLabel.setAlignment(Pos.BASELINE_LEFT);

        TextField emailTextField = new TextField();
        emailTextField.setMaxWidth(200);
        
        
        VBox errorBox = new VBox(10);
        errorBox.setAlignment(Pos.BASELINE_LEFT);

        // Return to Login Button
        Button confirmButton = new Button("Send Recovery Email");
        confirmButton.setOnAction(e -> {
        	
        	String email = emailTextField.getText();
        	
        	try (Statement emailStatement = DatabaseConnection.getConnection2DB().createStatement()) {
        		String queryEmailCheck = "SELECT COUNT(*) FROM user WHERE email = '" + email.replace("'", "''") + "'";
                ResultSet resultSet = emailStatement.executeQuery(queryEmailCheck);

                if (resultSet.next() && resultSet.getInt(1) > 0) {
                	
                	resetPasswordPage resetPasswordPage = new resetPasswordPage(email);
                    Scene resetPasswordScene = new Scene(resetPasswordPage, 800, 600);
                    Stage mainStage = Main.getPrimaryStage();
                    mainStage.setScene(resetPasswordScene);

                } else {
                    Label emailExist = new Label("Email doesn't exist");
                    emailExist.setFont(new Font("Arial", 14));
                    emailExist.setMinWidth(200);
                    errorBox.getChildren().add(emailExist);
                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
	
        });
        
    	Button returnButton = new Button("Return to Login");
    	returnButton.setOnAction(e ->  {
    		loginPage loginPage = new loginPage();
            Scene loginScene = new Scene(loginPage, 800, 600);
            Stage mainStage = Main.getPrimaryStage();
            mainStage.setScene(loginScene);
    	});

        VBox contentBox = new VBox(15, titleLabel, emailLabel, emailTextField, confirmButton, returnButton, errorBox);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.prefWidthProperty().bind(box.widthProperty().multiply(0.8)); // Inside padding
        contentBox.prefHeightProperty().bind(box.heightProperty().multiply(0.8));


        StackPane resizableBox = new StackPane(box, contentBox);
        resizableBox.setAlignment(Pos.CENTER);

        // Add the resizable box to the main layout
        this.getChildren().add(resizableBox);
    }
}
