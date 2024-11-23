package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MyCartPage extends Pane {
    private VBox cartItemsBox;
    private Label totalPriceLabel;
    private double totalPrice = 0.0;

    public MyCartPage() {
        // Beige background
        this.setStyle("-fx-background-color: #F5DEB3;");

        // Title
        Label title = new Label("My Cart");
        title.setFont(new Font("Arial", 30));
        title.setTextFill(Color.BLACK);

        // Separator line
        Separator separator = new Separator();
        separator.setPrefWidth(600);

        // VBox for the cart items
        cartItemsBox = new VBox(10);
        cartItemsBox.setPadding(new Insets(10));
        cartItemsBox.setAlignment(Pos.TOP_LEFT);

        // Order Summary Section
        VBox orderSummaryBox = new VBox(10);
        Label orderSummaryLabel = new Label("Order Summary");
        orderSummaryLabel.setFont(new Font("Arial", 20));
        orderSummaryLabel.setTextFill(Color.BLACK);

        totalPriceLabel = new Label("Total: $0.00");
        totalPriceLabel.setFont(new Font("Arial", 16));

        Button checkOutButton = new Button("Check Out");
        checkOutButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white;");
        checkOutButton.setOnAction(e -> handleCheckOut());

        Button removeAllButton = new Button("Remove All Items");
        removeAllButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white;");
        removeAllButton.setOnAction(e -> handleRemoveAllItems());

        orderSummaryBox.getChildren().addAll(orderSummaryLabel, totalPriceLabel, checkOutButton, removeAllButton);
        orderSummaryBox.setAlignment(Pos.TOP_CENTER);
        orderSummaryBox.setPadding(new Insets(10));
        orderSummaryBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");
        orderSummaryBox.setPrefWidth(200);

        // Bottom buttons
        HBox bottomButtons = new HBox(10);
        bottomButtons.setAlignment(Pos.BOTTOM_RIGHT);
        bottomButtons.setPadding(new Insets(10));

        Button myAccountButton = new Button("My Account");
        myAccountButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white;");
        myAccountButton.setOnAction(e -> handleMyAccount());

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white;");
        logoutButton.setOnAction(e -> handleLogout());

        Button switchUserButton = new Button("Switch User");
        switchUserButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white;");
        switchUserButton.setOnAction(e -> handleSwitchUser());

        bottomButtons.getChildren().addAll(myAccountButton, logoutButton, switchUserButton);

        // Main layout
        HBox mainLayout = new HBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().addAll(cartItemsBox, orderSummaryBox);
        mainLayout.setAlignment(Pos.TOP_LEFT);

        VBox root = new VBox(10, title, separator, mainLayout, bottomButtons);
        root.setPadding(new Insets(10));
        this.getChildren().add(root);

        // Load sample cart items
        loadSampleCartItems();
    }

    private void loadSampleCartItems() {
        List<CartItem> sampleItems = new ArrayList<>();
        sampleItems.add(new CartItem("Book 1", "Author 1", 20.99, "New", "cover1.png"));
        sampleItems.add(new CartItem("Book 2", "Author 2", 15.49, "Used", "cover2.png"));

        for (CartItem item : sampleItems) {
            addCartItem(item);
        }
    }

    private void addCartItem(CartItem item) {
        HBox itemBox = new HBox(10);
        itemBox.setPadding(new Insets(10));
        itemBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white;");

        // Book details
        VBox bookDetails = new VBox(5);
        Label titleLabel = new Label(item.getTitle());
        titleLabel.setFont(new Font("Arial", 16));

        Label authorLabel = new Label("Author: " + item.getAuthor());
        Label conditionLabel = new Label("Condition: " + item.getCondition());
        ImageView coverImageView = new ImageView(new Image(item.getCoverImage()));
        coverImageView.setFitHeight(100);
        coverImageView.setFitWidth(70);

        bookDetails.getChildren().addAll(titleLabel, authorLabel, conditionLabel);

        // Price and quantity controls
        VBox priceQuantityBox = new VBox(5);
        Label priceLabel = new Label(String.format("$%.2f", item.getPrice()));
        Spinner<Integer> quantitySpinner = new Spinner<>(1, 10, 1);
        quantitySpinner.valueProperty().addListener((obs, oldValue, newValue) -> updateTotalPrice(item, oldValue, newValue));

        priceQuantityBox.getChildren().addAll(priceLabel, quantitySpinner);

        // Buttons
        VBox actionButtons = new VBox(5);
        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white;");
        removeButton.setOnAction(e -> handleRemoveItem(itemBox, item));

        Button saveForLaterButton = new Button("Save for Later");
        saveForLaterButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white;");
        saveForLaterButton.setOnAction(e -> handleSaveForLater(itemBox, item));

        actionButtons.getChildren().addAll(removeButton, saveForLaterButton);

        itemBox.getChildren().addAll(coverImageView, bookDetails, priceQuantityBox, actionButtons);
        cartItemsBox.getChildren().add(itemBox);

        // Update total price
        totalPrice += item.getPrice();
        updateTotalPriceLabel();
    }

    private void updateTotalPrice(CartItem item, int oldQuantity, int newQuantity) {
        totalPrice += (newQuantity - oldQuantity) * item.getPrice();
        updateTotalPriceLabel();
    }

    private void updateTotalPriceLabel() {
        totalPriceLabel.setText(String.format("Total: $%.2f", totalPrice));
    }

    private void handleRemoveItem(HBox itemBox, CartItem item) {
        cartItemsBox.getChildren().remove(itemBox);
        totalPrice -= item.getPrice();
        updateTotalPriceLabel();
    }

    private void handleSaveForLater(HBox itemBox, CartItem item) {
        cartItemsBox.getChildren().remove(itemBox);
        totalPrice -= item.getPrice();
        updateTotalPriceLabel();
        // Add logic to save the item for later
    }

    private void handleRemoveAllItems() {
        cartItemsBox.getChildren().clear();
        totalPrice = 0.0;
        updateTotalPriceLabel();
    }

    private void handleCheckOut() {
        // Implement checkout logic
        System.out.println("Checking out...");
    }

    private void handleMyAccount() {
        // Implement navigation to My Account
        System.out.println("Navigating to My Account...");
    }

    private void handleLogout() {
        // Implement logout logic
        System.out.println("Logging out...");
    }

    private void handleSwitchUser() {
        // Implement switch user logic
        System.out.println("Switching user...");
    }

    // Sample CartItem class
    static class CartItem {
        private String title;
        private String author;
        private double price;
        private String condition;
        private String coverImage;

        public CartItem(String title, String author, double price, String condition, String coverImage) {
            this.title = title;
            this.author = author;
            this.price = price;
            this.condition = condition;
            this.coverImage = coverImage;
        }

        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public double getPrice() { return price; }
        public String getCondition() { return condition; }
        public String getCoverImage() { return coverImage; }
    }
}
