package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuyersView extends Pane {
    private VBox cartItems;
    private double totalPrice = 0.0;
    private List<Book> booksInCart;
    private Label totalLabel;
    private int addToCartCounter = 0;
    private ScrollPane bookListScrollPane;
    private VBox bookListContainer;

    public BuyersView() {
        // Initialize variables first
        booksInCart = new ArrayList<>();
        cartItems = new VBox(10);
        cartItems.setPadding(new Insets(10));
        totalLabel = new Label("Total: $0.00");
        addToCartCounter = 0;
        
        // Main rectangle
        Rectangle mainRectangle = new Rectangle();
        mainRectangle.widthProperty().bind(this.widthProperty().multiply(0.8));
        mainRectangle.heightProperty().bind(this.heightProperty().multiply(0.8));
        mainRectangle.xProperty().bind(this.widthProperty().subtract(mainRectangle.widthProperty()).divide(2));
        mainRectangle.yProperty().bind(this.heightProperty().subtract(mainRectangle.heightProperty()).divide(2));
        mainRectangle.setFill(Color.WHITE);

        // Main content container
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setAlignment(Pos.TOP_CENTER);

        // Header
        Label headerLabel = new Label("ASU Bookstore - Browse Books");
        headerLabel.setFont(new Font("Arial", 24));
        headerLabel.setTextFill(Color.MAROON);

        // Initialize book list container
        bookListContainer = new VBox(10);
        bookListContainer.setPadding(new Insets(10));
        
        // Initialize ScrollPane with the book list container
        bookListScrollPane = new ScrollPane(bookListContainer);
        bookListScrollPane.setFitToWidth(true);
        bookListScrollPane.setPrefViewportHeight(400);
        bookListScrollPane.setStyle("-fx-background: white;");

        // Create search and filters
        VBox searchAndFilters = createSearchAndFilters();
        
        // Create bottom bar
        HBox bottomBar = createBottomBar();

        // Add all components to main content
        mainContent.getChildren().addAll(headerLabel, searchAndFilters, bookListScrollPane, bottomBar);
        
        // Position main content
        mainContent.layoutXProperty().bind(mainRectangle.xProperty().add(20));
        mainContent.layoutYProperty().bind(mainRectangle.yProperty().add(20));
        mainContent.prefWidthProperty().bind(mainRectangle.widthProperty().subtract(40));

        // Add everything to the main pane
        this.getChildren().addAll(mainRectangle, mainContent);
        this.setStyle("-fx-background-color: #F5DEB3;");

        // Load initial books
        loadBooks();
    }

    private VBox createSearchAndFilters() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));

        // Search field
        TextField searchField = new TextField();
        searchField.setPromptText("Search for books...");
        searchField.setPrefWidth(300);

        // Filters
        HBox filters = new HBox(20);
        filters.setAlignment(Pos.CENTER_LEFT);

        // Category filter
        ComboBox<String> categoryFilter = new ComboBox<>();
        categoryFilter.getItems().addAll(
            "All Categories",
            "Natural Science Books",
            "Computer Books",
            "Math Books",
            "English Language Books",
            "Other Books"
        );
        categoryFilter.setValue("All Categories");

        // Condition filter
        ComboBox<String> conditionFilter = new ComboBox<>();
        conditionFilter.getItems().addAll(
            "All Conditions",
            "Used Like New",
            "Moderate Use",
            "Heavy Use"
        );
        conditionFilter.setValue("All Conditions");

        // Year filter
        ComboBox<String> yearFilter = new ComboBox<>();
        yearFilter.getItems().addAll(
            "All Years",
            "2024",
            "2023",
            "2022",
            "2021"
        );
        yearFilter.setValue("All Years");

        // Search button
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #8C1D40; -fx-text-fill: white;");

        // Event handlers
        searchButton.setOnAction(e -> performSearch(
            searchField.getText(),
            categoryFilter.getValue(),
            conditionFilter.getValue(),
            yearFilter.getValue()
        ));

        // Add components to filters
        filters.getChildren().addAll(
            new Label("Category:"), categoryFilter,
            new Label("Condition:"), conditionFilter,
            new Label("Year:"), yearFilter,
            searchButton
        );

        // Add components to container
        container.getChildren().addAll(searchField, filters);
        return container;
    }

    private void loadBooks() {
        try {
            Connection conn = DatabaseConnection.getConnection2DB();
            String query = "SELECT * FROM books WHERE available = 1";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            bookListContainer.getChildren().clear();

            while (rs.next()) {
                bookListContainer.getChildren().add(createBookCard(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("category"),
                    rs.getString("condition"),
                    rs.getDouble("price")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Could not load books. Please try again later.");
        }
    }

    private void performSearch(String searchText, String category, String condition, String year) {
        try {
            Connection conn = DatabaseConnection.getConnection2DB();
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM books WHERE available = 1");
            List<Object> parameters = new ArrayList<>();
            
            if (searchText != null && !searchText.trim().isEmpty()) {
                queryBuilder.append(" AND title LIKE ?");
                parameters.add("%" + searchText + "%");
            }
            
            if (!"All Categories".equals(category)) {
                queryBuilder.append(" AND category = ?");
                parameters.add(category);
            }
            
            if (!"All Conditions".equals(condition)) {
                queryBuilder.append(" AND `condition` = ?");
                parameters.add(condition);
            }
            
            if (!"All Years".equals(year)) {
                queryBuilder.append(" AND publication_year = ?");
                parameters.add(Integer.parseInt(year));
            }
            
            PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString());
            
            for (int i = 0; i < parameters.size(); i++) {
                if (parameters.get(i) instanceof String) {
                    pstmt.setString(i + 1, (String)parameters.get(i));
                } else if (parameters.get(i) instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer)parameters.get(i));
                }
            }
            
            ResultSet rs = pstmt.executeQuery();
            
            bookListContainer.getChildren().clear();
            
            while (rs.next()) {
                bookListContainer.getChildren().add(createBookCard(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("category"),
                    rs.getString("condition"),
                    rs.getDouble("price")
                ));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Search Error", "Could not perform search. Please try again.");
        }
    }

    private HBox createBookCard(int bookId, String title, String category, String condition, double price) {
        HBox card = new HBox(20);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-border-color: #CCCCCC; -fx-border-radius: 5;");

        VBox details = new VBox(5);
        Label titleLabel = new Label(title);
        titleLabel.setFont(new Font("Arial", 16));
        Label categoryLabel = new Label("Category: " + category);
        Label conditionLabel = new Label("Condition: " + condition);
        Label priceLabel = new Label(String.format("Price: $%.2f", price));

        details.getChildren().addAll(titleLabel, categoryLabel, conditionLabel, priceLabel);

        Button addToCartBtn = new Button("Add to Cart");
        addToCartBtn.setStyle("-fx-background-color: #8C1D40; -fx-text-fill: white;");
        addToCartBtn.setOnAction(e -> addToCart(new Book(bookId, title, category, condition, price)));

        card.getChildren().addAll(details, addToCartBtn);
        return card;
    }

    private HBox createBottomBar() {
        HBox bottomBar = new HBox(20);
        bottomBar.setPadding(new Insets(10));
        bottomBar.setAlignment(Pos.CENTER_RIGHT);

        Button viewCartBtn = new Button("View Cart (" + addToCartCounter + ")");
        viewCartBtn.setStyle("-fx-background-color: #8C1D40; -fx-text-fill: white;");
        viewCartBtn.setOnAction(e -> showCart());

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: #8C1D40; -fx-text-fill: white;");
        logoutBtn.setOnAction(e -> {
            loginPage loginPage = new loginPage();
            Scene loginScene = new Scene(loginPage, 800, 600);
            Stage mainStage = Main.getPrimaryStage();
            mainStage.setScene(loginScene);
        });

        bottomBar.getChildren().addAll(totalLabel, viewCartBtn, logoutBtn);
        return bottomBar;
    }

    private void addToCart(Book book) {
        addToCartCounter++;
        booksInCart.add(book);
        totalPrice += book.price;
        totalLabel.setText(String.format("Total: $%.2f", totalPrice));

        VBox itemBox = new VBox(5);
        itemBox.setPadding(new Insets(5));
        itemBox.setStyle("-fx-border-color: #CCCCCC; -fx-border-width: 0 0 1 0;");

        Label titleLabel = new Label(book.title);
        Label priceLabel = new Label(String.format("$%.2f", book.price));

        itemBox.getChildren().addAll(titleLabel, priceLabel);
        cartItems.getChildren().add(itemBox);
    }

    private void showCart() {
        if (booksInCart.isEmpty()) {
            showAlert("Empty Cart", "Your cart is empty!");
            return;
        }

        Stage cartStage = new Stage();
        VBox cartView = new VBox(10);
        cartView.setPadding(new Insets(20));
        cartView.setStyle("-fx-background-color: white;");

        Label cartTitle = new Label("Shopping Cart");
        cartTitle.setFont(new Font("Arial", 20));

        Button checkoutBtn = new Button("Proceed to Checkout");
        checkoutBtn.setStyle("-fx-background-color: #8C1D40; -fx-text-fill: white;");
        checkoutBtn.setOnAction(e -> {
            cartStage.close();
            proceedToCheckout();
        });

        cartView.getChildren().addAll(cartTitle, cartItems, totalLabel, checkoutBtn);

        Scene cartScene = new Scene(cartView, 400, 600);
        cartStage.setScene(cartScene);
        cartStage.setTitle("Shopping Cart");
        cartStage.show();
    }

    private void proceedToCheckout() {
        if (booksInCart.isEmpty()) {
            showAlert("Empty Cart", "Your cart is empty!");
            return;
        }

        try {
            checkoutPage checkout = new checkoutPage(totalPrice, booksInCart);
            Scene checkoutScene = new Scene(checkout, 800, 600);
            Stage mainStage = Main.getPrimaryStage();
            mainStage.setScene(checkoutScene);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not proceed to checkout: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static class Book {
        public final int id;
        public final String title;
        public final String category;
        public final String condition;
        public final double price;

        public Book(int id, String title, String category, String condition, double price) {
            this.id = id;
            this.title = title;
            this.category = category;
            this.condition = condition;
            this.price = price;
        }
    }
}
