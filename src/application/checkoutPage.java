package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.List;

public class checkoutPage extends Pane {
    private double totalPrice;
    private List<BuyersView.Book> cartItems;
    private RadioButton pickupButton;
    private RadioButton shippingButton;
    private VBox shippingAddressForm;

    public checkoutPage(double totalPrice, List<BuyersView.Book> cartItems) {
        this.totalPrice = totalPrice;
        this.cartItems = cartItems;
        
        // Create main content
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setPrefWidth(800); // Set preferred width

        // Header
        Label headerLabel = new Label("Checkout");
        headerLabel.setFont(new Font("Arial", 24));
        
        // Order Summary
        VBox orderSummary = createOrderSummary();
        
        // Delivery Options
        VBox deliveryOptions = createDeliveryOptions();
        
        // Payment Form
        VBox paymentForm = createPaymentForm();
        
        // Buttons
        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20, 0, 20, 0));
        
        Button confirmButton = new Button("Place Order");
        confirmButton.setStyle("-fx-background-color: #8C1D40;"); // ASU Maroon
        confirmButton.setTextFill(Color.WHITE);
        confirmButton.setOnAction(e -> handleConfirmOrder());
        
        Button cancelButton = new Button("Return to Cart");
        cancelButton.setOnAction(e -> returnToBuyersView());
        
        buttons.getChildren().addAll(confirmButton, cancelButton);

        mainContent.getChildren().addAll(
            headerLabel,
            orderSummary,
            deliveryOptions,
            paymentForm,
            buttons
        );

        // Add everything to the main pane
        this.getChildren().add(mainContent);
        this.setStyle("-fx-background-color: white;");
    }

    private VBox createOrderSummary() {
        VBox summary = new VBox(10);
        summary.setPadding(new Insets(20));
        summary.setStyle("-fx-border-color: #CCCCCC; -fx-border-radius: 5;");

        Label summaryTitle = new Label("Order Summary");
        summaryTitle.setFont(new Font("Arial", 18));

        // List all items
        for (BuyersView.Book book : cartItems) {
            HBox item = new HBox(20);
            item.setAlignment(Pos.CENTER_LEFT);
            
            Label titleLabel = new Label(book.title);
            Label priceLabel = new Label(String.format("$%.2f", book.price));
            
            item.getChildren().addAll(titleLabel, new Pane(), priceLabel); // Pane adds flexible space
            HBox.setHgrow(item.getChildren().get(1), Priority.ALWAYS); // Make the space grow
            
            summary.getChildren().add(item);
        }

        // Total
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));
        
        HBox totalRow = new HBox(20);
        totalRow.setAlignment(Pos.CENTER_RIGHT);
        Label totalLabel = new Label(String.format("Total: $%.2f", totalPrice));
        totalLabel.setFont(new Font("Arial", 16));
        totalRow.getChildren().add(totalLabel);

        summary.getChildren().addAll(separator, totalRow);
        return summary;
    }

    private VBox createDeliveryOptions() {
        VBox deliveryBox = new VBox(15);
        deliveryBox.setPadding(new Insets(20));
        deliveryBox.setStyle("-fx-border-color: #CCCCCC; -fx-border-radius: 5;");

        Label deliveryTitle = new Label("Delivery Method");
        deliveryTitle.setFont(new Font("Arial", 18));

        ToggleGroup deliveryGroup = new ToggleGroup();
        
        pickupButton = new RadioButton("Pick Up at ASU Bookstore");
        pickupButton.setToggleGroup(deliveryGroup);
        pickupButton.setSelected(true);
        
        shippingButton = new RadioButton("Ship to Address");
        shippingButton.setToggleGroup(deliveryGroup);

        // Shipping Address Form
        shippingAddressForm = createShippingForm();
        shippingAddressForm.setVisible(false);

        // Toggle shipping form visibility
        shippingButton.setOnAction(e -> shippingAddressForm.setVisible(true));
        pickupButton.setOnAction(e -> shippingAddressForm.setVisible(false));

        deliveryBox.getChildren().addAll(deliveryTitle, pickupButton, shippingButton, shippingAddressForm);
        return deliveryBox;
    }

    private VBox createShippingForm() {
        VBox form = new VBox(10);
        form.setPadding(new Insets(15, 0, 0, 20));

        TextField name = new TextField();
        name.setPromptText("Full Name");

        TextField address = new TextField();
        address.setPromptText("Street Address");

        HBox cityStateZip = new HBox(10);
        
        TextField city = new TextField();
        city.setPromptText("City");
        
        TextField state = new TextField();
        state.setPromptText("State");
        state.setPrefWidth(70);
        
        TextField zip = new TextField();
        zip.setPromptText("ZIP Code");
        zip.setPrefWidth(100);

        cityStateZip.getChildren().addAll(city, state, zip);

        form.getChildren().addAll(name, address, cityStateZip);
        return form;
    }

    private VBox createPaymentForm() {
        VBox form = new VBox(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-border-color: #CCCCCC; -fx-border-radius: 5;");

        Label formTitle = new Label("Payment Information");
        formTitle.setFont(new Font("Arial", 18));

        TextField cardName = new TextField();
        cardName.setPromptText("Name on Card");

        TextField cardNumber = new TextField();
        cardNumber.setPromptText("Card Number");

        HBox expiryAndCvv = new HBox(20);
        
        TextField expiry = new TextField();
        expiry.setPromptText("MM/YY");
        expiry.setPrefWidth(100);

        TextField cvv = new TextField();
        cvv.setPromptText("CVV");
        cvv.setPrefWidth(70);

        expiryAndCvv.getChildren().addAll(expiry, cvv);

        form.getChildren().addAll(formTitle, cardName, cardNumber, expiryAndCvv);
        return form;
    }

    private void handleConfirmOrder() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Confirmation");
        alert.setHeaderText("Thank you for your order!");
        alert.setContentText("Your order has been placed successfully.");
        alert.showAndWait();

        // Return to BuyersView
        returnToBuyersView();
    }

    private void returnToBuyersView() {
        BuyersView buyersView = new BuyersView();
        Scene buyersScene = new Scene(buyersView, 800, 600);
        Stage mainStage = Main.getPrimaryStage();
        mainStage.setScene(buyersScene);
    }
}
