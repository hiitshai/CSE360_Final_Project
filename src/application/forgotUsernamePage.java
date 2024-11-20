package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class forgotUsernamePage extends Pane {
	
	public forgotUsernamePage() {
		
		Button returnToLoginButton = new Button("Return to Login");
        returnToLoginButton.setOnAction(e -> {
            loginPage loginPage = new loginPage();
            Scene loginPageScene = new Scene(loginPage, 800, 400);
            loginPage.setStyle("-fx-background-color: #F5DEB3;");
            Stage mainStage = Main.getPrimaryStage();
            mainStage.setScene(loginPageScene);
        });
        
        this.getChildren().setAll(returnToLoginButton);
	}

}
