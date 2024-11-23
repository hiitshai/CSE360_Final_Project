package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class buyersConfirmationPage extends VBox {

    public buyersConfirmationPage() {
        // Styling the layout
        this.setPadding(new Insets(20));
        this.setSpacing(20);
        this.setAlignment(Pos.TOP_LEFT);
        this.setStyle("-fx-background-color: #FFCC00;"); // Gold background

        // Title
        Label title = new Label("The ASU Bookstore");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #800000;"); // Maroon text
        this.getChildren().add(title);

        // Delivery Section
        Label deliveryLabel = new Label("Delivery");
        deliveryLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        this.getChildren().add(deliveryLabel);

        CheckBox pickUpOption = new CheckBox("Pick Up");
        CheckBox shipToAddressOption = new CheckBox("Ship to an Address");

        VBox deliveryOptions = new VBox(10, pickUpOption, shipToAddressOption);
        deliveryOptions.setAlignment(Pos.TOP_LEFT);
        this.getChildren().add(deliveryOptions);

        // Address Fields
        TextField firstNameField = new TextField("First Name");
        TextField lastNameField = new TextField("Last Name");
        TextField countryField = new TextField("Country");
        TextField addressField = new TextField("Street Address");
        TextField aptField = new TextField("Apt, suite, floor");
        TextField cityField = new TextField("City");
        TextField stateField = new TextField("State");
        TextField zipField = new TextField("Zip Code");

        VBox addressBox = new VBox(10, firstNameField, lastNameField, countryField, addressField, aptField, cityField, stateField, zipField);
        addressBox.setAlignment(Pos.TOP_LEFT);
        this.getChildren().add(addressBox);

        // Payment Section
        Label paymentLabel = new Label("Payment");
        paymentLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        this.getChildren().add(paymentLabel);

        RadioButton creditCardOption = new RadioButton("Credit Card / Debit Card");
        TextField cardNameField = new TextField("Name on Card");
        TextField cardNumberField = new TextField("Card Number");
        TextField expirationField = new TextField("Expiration Date");
        TextField cvvField = new TextField("CVV");

        VBox paymentBox = new VBox(10, creditCardOption, cardNameField, cardNumberField, expirationField, cvvField);
        paymentBox.setAlignment(Pos.TOP_LEFT);
        this.getChildren().add(paymentBox);

        // Order Summary
        Label summaryLabel = new Label("Order Summary");
        summaryLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label totalLabel = new Label("Total: $133.98");
        totalLabel.setStyle("-fx-font-size: 16px;");
        this.getChildren().addAll(summaryLabel, totalLabel);

        // Buttons
        Button checkoutButton = new Button("Check Out");
        checkoutButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white;");

        Button myAccountButton = new Button("My Account");
        myAccountButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white;");

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white;");

        Button switchUserButton = new Button("Switch User");
        switchUserButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white;");

        HBox buttonBox = new HBox(10, checkoutButton, myAccountButton, logoutButton, switchUserButton);
        buttonBox.setAlignment(Pos.TOP_LEFT);
        this.getChildren().add(buttonBox);

        // Button Actions
        checkoutButton.setOnAction(e -> {
            // Show confirmation message
            System.out.println("Order Submitted!");
        });

        myAccountButton.setOnAction(e -> {
            // Logic to navigate to the "My Account" page
            System.out.println("Navigating to My Account");
        });

        logoutButton.setOnAction(e -> {
            // Logic to logout
            System.out.println("Logging out...");
        });

        switchUserButton.setOnAction(e -> {
            // Logic to switch user
            System.out.println("Switching user...");
        });
    }
}
