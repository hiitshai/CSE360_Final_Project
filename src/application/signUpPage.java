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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class signUpPage extends Pane {

    public signUpPage() {

        Rectangle testRectangle = new Rectangle();
        testRectangle.widthProperty().bind(this.widthProperty().multiply(0.8));
        testRectangle.heightProperty().bind(this.heightProperty().multiply(0.8));
        testRectangle.xProperty().bind(this.widthProperty().subtract(testRectangle.widthProperty()).divide(2));
        testRectangle.yProperty().bind(this.heightProperty().subtract(testRectangle.heightProperty()).divide(2));
        testRectangle.setFill(Color.WHITE);

        VBox signUpBox = new VBox(10);
        signUpBox.setPadding(new Insets(20));
        signUpBox.setAlignment(Pos.TOP_CENTER);
        signUpBox.prefWidthProperty().bind(testRectangle.widthProperty().subtract(40));

        Label signUpLabel = new Label("Sign Up");
        signUpLabel.setFont(new Font("Arial", 30));
        signUpLabel.setTextFill(Color.BLACK);

        Label emailLabel = new Label("EMAIL:");
        emailLabel.setFont(new Font("Arial", 14));
        emailLabel.setMinWidth(200);
        TextField emailTextField = new TextField();
        emailTextField.prefWidthProperty().bind(signUpBox.prefWidthProperty().subtract(120));

        HBox emailBox = new HBox(10, emailLabel, emailTextField);
        emailBox.setAlignment(Pos.BASELINE_LEFT);

        Label usernameLabel = new Label("USERNAME:");
        usernameLabel.setFont(new Font("Arial", 14));
        usernameLabel.setMinWidth(200);
        TextField usernameTextField = new TextField();
        usernameTextField.prefWidthProperty().bind(signUpBox.prefWidthProperty().subtract(120));

        HBox usernameBox = new HBox(10, usernameLabel, usernameTextField);
        usernameBox.setAlignment(Pos.BASELINE_LEFT);

        
        
        
        
        
        
        //Password Sections
        Label passwordLabel = new Label("PASSWORD:");
        passwordLabel.setFont(new Font("Arial", 14));
        passwordLabel.setMinWidth(200);
        PasswordField passwordField = new PasswordField();
        passwordField.prefWidthProperty().bind(signUpBox.prefWidthProperty().subtract(120));

        TextField visiblePasswordField = new TextField();
        visiblePasswordField.setManaged(false);
        visiblePasswordField.setVisible(false);
        visiblePasswordField.prefWidthProperty().bind(passwordField.prefWidthProperty());
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
        passwordBox.setAlignment(Pos.BASELINE_LEFT);

        Label confirmPasswordLabel = new Label("CONFIRM PASSWORD:");
        confirmPasswordLabel.setFont(new Font("Arial", 14));
        confirmPasswordLabel.setMinWidth(200);
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.prefWidthProperty().bind(signUpBox.prefWidthProperty().subtract(120));

        TextField visibleConfirmPasswordField = new TextField();
        visibleConfirmPasswordField.setManaged(false);
        visibleConfirmPasswordField.setVisible(false);
        visibleConfirmPasswordField.prefWidthProperty().bind(confirmPasswordField.prefWidthProperty());
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
        confirmPasswordBox.setAlignment(Pos.BASELINE_LEFT);

        Label roleLabel = new Label("Select Role:");
        roleLabel.setFont(new Font("Arial", 14));
        roleLabel.setMinWidth(200);

        CheckBox sellerCheckBox = new CheckBox("Seller");
        CheckBox buyerCheckBox = new CheckBox("Buyer");

        HBox roleBox = new HBox(10, roleLabel, sellerCheckBox, buyerCheckBox);
        roleBox.setAlignment(Pos.BASELINE_LEFT);
        
        VBox errorBox = new VBox(10);
        errorBox.setAlignment(Pos.BASELINE_LEFT);
        
        Button confirmButton = new Button("Join Now");
        confirmButton.setOnAction(e -> {
        	//"SELECT * from messages WHERE EmpIDNo = 'COMSCI0001'";
        	//Hbox ErrorBox = new HBox(1)
        	int sellerRole = 0;
        	int buyerRole = 0;
        	
			if(!passwordField.getText().equals(confirmPasswordField.getText())) {
				
				System.out.println(passwordField);
				System.out.println(confirmPasswordField);
				
				Label notMatchLabel = new Label("Passwords do not match");
				notMatchLabel.setFont(new Font("Arial", 14));
				notMatchLabel.setMinWidth(200);
				if(!errorBox.getChildren().contains(notMatchLabel)) {
					errorBox.getChildren().addAll(notMatchLabel);
					return;
					
				}
				else {
					errorBox.getChildren().remove(notMatchLabel);
				}
				return; 
			}
			
			//System.out.println("test to see if it exits");
        	
        	String email = emailTextField.getText();
        	//if(email != " ") {
	        	try(Statement emailStatement = DatabaseConnection.getConnection2DB().createStatement()) {
	        		String queryEmailCheck = "SELECT COUNT(*) FROM user WHERE email = '" + email.replace("'", "''") + "'";
	        		ResultSet resultSet = emailStatement.executeQuery(queryEmailCheck);
	        		
	        		if(resultSet.next()) {
	        			int counter = resultSet.getInt(1);
	        			if(counter > 0) {
	        				Label emailExist = new Label("Email Already Exists");
	        				emailExist.setFont(new Font("Arial", 14));
	        				emailExist.setMinWidth(200);
	        				errorBox.getChildren().addAll(emailExist);
	        				return;         			
	        				}
	        		}
	        	
	        	} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	//}
        	
        	String username = usernameTextField.getText();
        	try(Statement userStatement = DatabaseConnection.getConnection2DB().createStatement()) {
        		String queryUsernameCheck = "SELECT COUNT(*) FROM user WHERE username = '" + username.replace("'", "''") + "'";
        		ResultSet resultSetUsername = userStatement.executeQuery(queryUsernameCheck);
        		
        		if(resultSetUsername.next()) {
        			int counter = resultSetUsername.getInt(1);
        			if(counter > 0) {
        				Label usernameExist = new Label("Username Already Exists");
        				usernameExist.setFont(new Font("Arial", 14));
        				usernameExist.setMinWidth(200);
        				errorBox.getChildren().addAll(usernameExist);
        				return;         			
        				}
        		}
        	} catch (SQLException e1) {
        		e1.printStackTrace();
        	}
        	
        	String password = confirmPasswordField.getText();
        	
        	if(sellerCheckBox.isSelected()) {
        		sellerRole = 1;
        	}
        	
        	if(buyerCheckBox.isSelected()) {
        		buyerRole = 1;
        	}
        	
        	String insertUsers = "INSERT INTO user (email, username, password, is_buyer, is_seller) VALUES ('" +
                    email + "', '" + username + "', '" + password + "', " + buyerRole + ", " + sellerRole + ")";
        	DataManipulation.update(insertUsers);
        	
        	
        	
        	
        	/*
        	
            loginPage loginPage = new loginPage();
            Scene loginPageScene = new Scene(loginPage, 800, 400);
            loginPage.setStyle("-fx-background-color: #F5DEB3;");
            Stage mainStage = Main.getPrimaryStage();
            mainStage.setScene(loginPageScene);
            
            Stage popupStage = new Stage();
            popupStage.setTitle("Notification");

            Label messageLabel = new Label("Account Not Yet Created");
            messageLabel.setFont(new Font("Arial", 16));
            messageLabel.setPadding(new Insets(10));

            Button closeButton = new Button("Okay");
            closeButton.setOnAction(event -> popupStage.close());

            VBox popupLayout = new VBox(10, messageLabel, closeButton);
            popupLayout.setAlignment(Pos.CENTER);
            popupLayout.setPadding(new Insets(20));

            Scene popupScene = new Scene(popupLayout, 250, 150);
            popupStage.setScene(popupScene);

            popupStage.show(); */
        });

        Button returnToLoginButton = new Button("Return to Login");
        returnToLoginButton.setOnAction(e -> {
            loginPage loginPage = new loginPage();
            Scene loginPageScene = new Scene(loginPage, 800, 400);
            loginPage.setStyle("-fx-background-color: #F5DEB3;");
            Stage mainStage = Main.getPrimaryStage();
            mainStage.setScene(loginPageScene);
        });

        // Wrap button in HBox aligned to the right
        HBox bottomRightBox = new HBox(100, confirmButton, returnToLoginButton);
        bottomRightBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomRightBox.setPadding(new Insets(10, 0, 0, 0));  // Add padding to separate it from the above elements

        // Add all elements to the signUpBox
        signUpBox.getChildren().addAll(signUpLabel, emailBox, usernameBox, passwordBox, confirmPasswordBox, roleBox, bottomRightBox, errorBox);

        // Set layout position and add to main pane
        signUpBox.layoutXProperty().bind(testRectangle.xProperty().add(20));
        signUpBox.layoutYProperty().bind(testRectangle.yProperty().add(20));
        this.getChildren().addAll(testRectangle, signUpBox);
    }
}
