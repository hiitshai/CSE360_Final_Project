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

    public BuyersView() {
        booksInCart = new ArrayList<>();
        
        // Main rectangle (matching your style)
        Rectangle testRectangle = new Rectangle();
        testRectangle.widthProperty().bind(this.widthProperty().multiply(0.8));
        testRectangle.heightProperty().bind(this.heightProperty().multiply(0.8));
        testRectangle.xProperty().bind(this.widthProperty().subtract(testRectangle.widthProperty()).divide(2));
        testRectangle.yProperty().bind(this.heightProperty().subtract(testRectangle.heightProperty()).divide(2));
        testRectangle.setFill(Color.WHITE);

        // Main content container
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setAlignment(Pos.TOP_CENTER);

        // Header
        Label headerLabel = new Label("ASU Bookstore - Browse Books");
        headerLabel.setFont(new Font("Arial", 24));
        headerLabel.setTextFill(Color.MAROON);

        // Search and Filters
        VBox searchAndFilters = createSearchAndFilters();
        
        // Book listing
        ScrollPane bookListing = createBookListing();
        
        // Cart preview and navigation
        HBox bottomBar = createBottomBar();

        mainContent.getChildren().addAll(headerLabel, searchAndFilters, bookListing, bottomBar);

        // Position content within rectangle
        mainContent.layoutXProperty().bind(testRectangle.xProperty().add(20));
        mainContent.layoutYProperty().bind(testRectangle.yProperty().add(20));
        mainContent.prefWidthProperty().bind(testRectangle.widthProperty().subtract(40));

        this.getChildren().addAll(testRectangle, mainContent);
        this.setStyle("-fx-background-color: #F5DEB3;");

        cartItems = new VBox(10);
        cartItems.setPadding(new Insets(10));
        totalLabel = new Label("Total: $0.00");
    }

    private VBox createSearchAndFilters() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));

        // Search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search for books...");
        searchField.prefWidthProperty().bind(container.widthProperty().multiply(0.7));

        // Filter boxes
        HBox filters = new HBox(20);
        filters.setAlignment(Pos.CENTER);

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

        ComboBox<String> conditionFilter = new ComboBox<>();
        conditionFilter.getItems().addAll(
            "All Conditions",
            "Used Like New",
            "Moderate Use",
            "Heavy Use"
        );
        conditionFilter.setValue("All Conditions");

        ComboBox<String> yearFilter = new ComboBox<>();
        yearFilter.getItems().addAll(
            "All Years",
            "2024",
            "2023",
            "2022",
            "2021"
        );
        yearFilter.setValue("All Years");

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #8C1D40;"); // ASU Maroon
        searchButton.setTextFill(Color.WHITE);
        searchButton.setOnAction(e -> searchBooks(
            searchField.getText(),
            categoryFilter.getValue(),
            conditionFilter.getValue(),
            yearFilter.getValue()
        ));

        filters.getChildren().addAll(
            new Label("Category:"), categoryFilter,
            new Label("Condition:"), conditionFilter,
            new Label("Year:"), yearFilter,
            searchButton
        );

        container.getChildren().addAll(searchField, filters);
        return container;
    }

    private ScrollPane createBookListing() {
        VBox bookList = new VBox(10);
        bookList.setPadding(new Insets(10));

        try {
            Connection conn = DatabaseConnection.getConnection2DB();
            String query = "SELECT * FROM books WHERE available = 1";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bookList.getChildren().add(createBookCard(
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

        ScrollPane scrollPane = new ScrollPane(bookList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(400);
        return scrollPane;
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
        addToCartBtn.setStyle("-fx-background-color: #8C1D40;"); // ASU Maroon
        addToCartBtn.setTextFill(Color.WHITE);
        addToCartBtn.setOnAction(e -> addToCart(new Book(bookId, title, category, condition, price)));

        card.getChildren().addAll(details, addToCartBtn);
        return card;
    }

    private HBox createBottomBar() {
        HBox bottomBar = new HBox(20);
        bottomBar.setPadding(new Insets(10));
        bottomBar.setAlignment(Pos.CENTER_RIGHT);

        Button viewCartBtn = new Button("View Cart (" + booksInCart.size() + ")");
        viewCartBtn.setStyle("-fx-background-color: #8C1D40;");
        viewCartBtn.setTextFill(Color.WHITE);
        viewCartBtn.setOnAction(e -> showCart());

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
            loginPage loginPage = new loginPage();
            Scene loginScene = new Scene(loginPage, 800, 400);
            Stage mainStage = Main.getPrimaryStage();
            mainStage.setScene(loginScene);
        });

        bottomBar.getChildren().addAll(totalLabel, viewCartBtn, logoutBtn);
        return bottomBar;
    }

    private void addToCart(Book book) {
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
        checkoutBtn.setStyle("-fx-background-color: #8C1D40;");
        checkoutBtn.setTextFill(Color.WHITE);
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
        checkoutpage checkout = new checkoutpage(totalPrice);
        Scene checkoutScene = new Scene(checkout, 800, 600);
        Stage mainStage = Main.getPrimaryStage();
        mainStage.setScene(checkoutScene);
    }

    private void searchBooks(String searchText, String category, String condition, String year) {
        // Implement search functionality using the database
        try {
            Connection conn = DatabaseConnection.getConnection2DB();
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM books WHERE available = 1");
            
            List<String> conditions = new ArrayList<>();
            if (!searchText.isEmpty()) {
                conditions.add("title LIKE '%" + searchText + "%'");
            }
            if (!"All Categories".equals(category)) {
                conditions.add("category = '" + category + "'");
            }
            if (!"All Conditions".equals(condition)) {
                conditions.add("condition = '" + condition + "'");
            }
            if (!"All Years".equals(year)) {
                conditions.add("publication_year = " + year);
            }
            
            if (!conditions.isEmpty()) {
                queryBuilder.append(" AND ").append(String.join(" AND ", conditions));
            }
            
            PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString());
            ResultSet rs = pstmt.executeQuery();
            
            // Update the book listing with search results
            VBox bookList = new VBox(10);
            while (rs.next()) {
                bookList.getChildren().add(createBookCard(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("category"),
                    rs.getString("condition"),
                    rs.getDouble("price")
                ));
            }
            
            // Update the scroll pane content
            ((ScrollPane) this.getChildren().get(1)).setContent(bookList);
            
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Search Error", "Could not perform search. Please try again.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static class Book {
        int id;
        String title;
        String category;
        String condition;
        double price;

        public Book(int id, String title, String category, String condition, double price) {
            this.id = id;
            this.title = title;
            this.category = category;
            this.condition = condition;
            this.price = price;
        }
    }
}

