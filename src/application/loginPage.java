package application;

import javafx.geometry.Insets;

import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class loginPage extends Pane {
    
	private boolean isValidCredentials(String username, String password) {
	    String query = "SELECT password FROM user WHERE username = ?";

	    try (PreparedStatement preparedStatement = DatabaseConnection.getConnection2DB().prepareStatement(query)) {
	        preparedStatement.setString(1, username);

	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            String storedPassword = resultSet.getString("password");
	            return storedPassword.equals(password);
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }

	    return false;
	}


	
    public loginPage() {
    	
    
    	// Main rectangle that centers and resizes
        Rectangle testRectangle = new Rectangle();
        testRectangle.widthProperty().bind(this.widthProperty().multiply(0.7)); // 70% of window width
        testRectangle.heightProperty().bind(this.heightProperty().multiply(0.7)); // 70% of window height
        testRectangle.xProperty().bind(this.widthProperty().subtract(testRectangle.widthProperty()).divide(2));
        testRectangle.yProperty().bind(this.heightProperty().subtract(testRectangle.heightProperty()).divide(2));
        testRectangle.setFill(Color.MAROON);

        // Left rectangle (login area)
        Rectangle loginRectangle = new Rectangle();
        loginRectangle.widthProperty().bind(testRectangle.widthProperty().multiply(0.5)); // 50% of testRectangle's width
        loginRectangle.heightProperty().bind(testRectangle.heightProperty());
        loginRectangle.setFill(Color.WHITE);

        loginRectangle.xProperty().bind(testRectangle.xProperty());
        loginRectangle.yProperty().bind(testRectangle.yProperty());
        
        
        // Right side rectangle
        Rectangle signUpRectangle = new Rectangle();
        signUpRectangle.widthProperty().bind(testRectangle.widthProperty().multiply(0.5)); // 50% of testRectangle's width
        signUpRectangle.heightProperty().bind(testRectangle.heightProperty());
        signUpRectangle.setFill(Color.MAROON);

        signUpRectangle.xProperty().bind(testRectangle.xProperty().add(testRectangle.widthProperty().multiply(0.5)));
        signUpRectangle.yProperty().bind(testRectangle.yProperty()); // Align top with testRectangle

        /*
        SignIn side of the login page. VBox for the login side, everything is stacked on to the loginBox
        */
        
        VBox loginBox = new VBox(10);
        loginBox.setPadding(new Insets(20));
        loginBox.setAlignment(Pos.TOP_LEFT);
        loginBox.prefWidthProperty().bind(loginRectangle.widthProperty().subtract(40)); // Bind width to fit within loginRectangle
        
        Label signInLabel = new Label("Sign In");
        signInLabel.setFont(new Font("Arial", 25));
        signInLabel.prefWidthProperty().bind(loginBox.prefWidthProperty());
        
        
        Label usernameLabel = new Label("USERNAME:");
        usernameLabel.setFont(new Font("Arial", 14));
        TextField usernameField = new TextField();
        usernameField.prefWidthProperty().bind(loginBox.prefWidthProperty());
        
        Label passwordLabel = new Label("PASSWORD:");
        passwordLabel.setFont(new Font("Arial", 14));
        PasswordField passwordField = new PasswordField();
        passwordField.prefWidthProperty().bind(loginBox.prefWidthProperty());
        
        TextField visiblePasswordField = new TextField();
        visiblePasswordField.setManaged(false);
        visiblePasswordField.setVisible(false);
        visiblePasswordField.prefWidthProperty().bind(passwordField.prefWidthProperty());
        passwordField.textProperty().bindBidirectional(visiblePasswordField.textProperty()); //Matches the location of the password field with the textfield. 

        // Toggle Button to show/hide password
        Button toggleButton = new Button("Show Password");
        toggleButton.setOnAction(e -> {
        	if (passwordField.isVisible()) {
                passwordField.setVisible(false);
                passwordField.setManaged(false);
                visiblePasswordField.setVisible(true);
                visiblePasswordField.setManaged(true);
                toggleButton.setText("Hide Password");
            } 
        	
        	else {
                passwordField.setVisible(true);
                passwordField.setManaged(true);
                visiblePasswordField.setVisible(false);
                visiblePasswordField.setManaged(false);
                toggleButton.setText("Show Password");
            }
        });
        
        Button forgotPasswordButton = new Button("Forgot Password?");
        Button forgotUsernameButton = new Button("Forgot Username?");

        forgotPasswordButton.setOnAction(e -> {
        	forgotPasswordPage forgotPassword = new forgotPasswordPage();
            Scene forgotPasswordScene = new Scene(forgotPassword, 800, 600);
            forgotPassword.setStyle("-fx-background-color: #F5DEB3;");            
            Stage mainStage = Main.getPrimaryStage();
            mainStage.setScene(forgotPasswordScene);
        });

        forgotUsernameButton.setOnAction(e -> {
        	forgotUsernamePage forgotUsername = new forgotUsernamePage();
            Scene forgotUsernameScene = new Scene(forgotUsername, 800, 600);
            forgotUsername.setStyle("-fx-background-color: #F5DEB3;");            
            Stage mainStage = Main.getPrimaryStage();
            mainStage.setScene(forgotUsernameScene);
        });
        
        Button loginButton = new Button("Login");
        loginButton.prefWidthProperty().bind(loginBox.prefWidthProperty());
        
        loginButton.setOnAction(e-> {
        	/*if (usernameField.getText().equals("test") && passwordField.getText().equals("test")) {
        		myAccountPage myAccount = new myAccountPage();
                Scene myAccountScene = new Scene(myAccount, 800, 400);
                myAccount.setStyle("-fx-background-color: #F5DEB3;");            
                Stage mainStage = Main.getPrimaryStage();
                mainStage.setScene(myAccountScene);
        	}*/
        	String username = usernameField.getText();
            String password = passwordField.getText();

            if (isValidCredentials(username, password)) {
                try {
                    String query = "SELECT is_admin FROM user WHERE username = ?";
                    
                    PreparedStatement preparedStatement = DatabaseConnection.getConnection2DB().prepareStatement(query);
                    preparedStatement.setString(1, username);
                    
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        boolean isAdmin = resultSet.getBoolean("is_admin");
                        if (!isAdmin) {
                        	
                        	sellersPage sellersPage = new sellersPage();
                            Scene sellersPageScene = new Scene(sellersPage, 800, 600);
                            sellersPage.setStyle("-fx-background-color: #F5DEB3;");
                            Stage mainStage = Main.getPrimaryStage();
                            mainStage.setScene(sellersPageScene);
                        	
                        	
                        	/*buyersConfirmationPage confirmationPage = new buyersConfirmationPage();
                        	Scene confirmationPageScene = new Scene(confirmationPage, 800, 600);
                        	Stage mainStage = Main.getPrimaryStage(); // Your main application's stage
                        	mainStage.setScene(confirmationPageScene);
                        	mainStage.show();*/
                        	
                            // User is not an admin, proceed to account page
                            /*myAccountPage myAccount = new myAccountPage();
                            Scene myAccountScene = new Scene(myAccount, 800, 400);
                            myAccount.setStyle("-fx-background-color: #F5DEB3;");
                            Stage mainStage = Main.getPrimaryStage();
                            mainStage.setScene(myAccountScene);*/
                        } else {
                            Label errorLabel = new Label("Admin not implement fix this later");
                            errorLabel.setTextFill(Color.RED);
                            loginBox.getChildren().add(errorLabel);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Label errorLabel = new Label("An error occurred. Please try again.");
                    errorLabel.setTextFill(Color.RED);
                    loginBox.getChildren().add(errorLabel);
                }
            } else {
                Label errorLabel = new Label("Invalid username or password.");
                errorLabel.setTextFill(Color.RED);
                loginBox.getChildren().add(errorLabel);
            	}
        	}
        );

        loginBox.getChildren().addAll(signInLabel, usernameLabel, usernameField, passwordLabel, passwordField, visiblePasswordField, toggleButton, forgotPasswordButton, forgotUsernameButton, loginButton);
        loginBox.layoutXProperty().bind(loginRectangle.xProperty().add(20)); 
        loginBox.layoutYProperty().bind(loginRectangle.yProperty().add(20)); 
        
    
        /*
         Sign up Side of the login page. 
         */
        
        VBox signUpBox = new VBox(10);
        signUpBox.setPadding(new Insets(20));
        signUpBox.setAlignment(Pos.CENTER);
        signUpBox.prefWidthProperty().bind(signUpRectangle.widthProperty().subtract(40));

        
        Label signUpLabel = new Label("Don't have an Account?");
        signUpLabel.setFont(new Font("Arial", 30));
        signUpLabel.setTextFill(Color.BLACK);
        signUpLabel.prefWidthProperty().bind(signUpBox.prefWidthProperty());
        signUpLabel.setWrapText(true);
        signUpLabel.setAlignment(Pos.CENTER);
        
        Button signUpButton = new Button("Sign Up");
        signUpButton.prefWidthProperty().bind(signUpBox.prefWidthProperty());
        
        signUpButton.setOnAction(e -> {
            signUpPage signUp = new signUpPage();
            Scene signUpScene = new Scene(signUp, 800, 600);
            signUp.setStyle("-fx-background-color: #F5DEB3;");            
            Stage mainStage = Main.getPrimaryStage();
            mainStage.setScene(signUpScene);
        });

        signUpBox.getChildren().addAll(signUpLabel, signUpButton);
        signUpBox.layoutXProperty().bind(signUpRectangle.xProperty().add(20)); 
        signUpBox.layoutYProperty().bind(signUpRectangle.yProperty().add(20)); 
        
        // Add rectangles and loginBox to the Pane
        this.getChildren().addAll(testRectangle, loginRectangle, signUpRectangle, loginBox, signUpBox);
        this.setStyle("-fx-background-color: #F5DEB3;"); // Light beige background
    }
}
