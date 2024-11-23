package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class sellersPage extends Pane {

    private VBox mainContent; // To dynamically update views
    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    public sellersPage() {
        // Sidebar
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #F5A623;");
        sidebar.setPrefWidth(200);

        // Sidebar Buttons
        Button dashboardButton = createSidebarButton("Dashboard");
        Button productListButton = createSidebarButton("Product List");
        Button addProductButton = createSidebarButton("Add More Products");
        Button myAccountButton = createSidebarButton("My Account");
        Button logOutButton = createSidebarButton("Log Out");

        // Add buttons to sidebar
        sidebar.getChildren().addAll(dashboardButton, productListButton, addProductButton, myAccountButton, logOutButton);

        // Main Content Area
        mainContent = new VBox();
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: #F9F9F9;");
        mainContent.getChildren().add(createHeaderLabel("Welcome to the Seller's Dashboard"));

        // Navigation logic
        dashboardButton.setOnAction(e -> showDashboard());
        productListButton.setOnAction(e -> showProductList());
        addProductButton.setOnAction(e -> showAddProductForm());

        // Root Layout
        HBox rootLayout = new HBox();
        rootLayout.getChildren().addAll(sidebar, mainContent);
        HBox.setHgrow(mainContent, Priority.ALWAYS);

        this.getChildren().add(rootLayout);
    }

    // Helper to create Sidebar Buttons
    private Button createSidebarButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(150);
        button.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000; -fx-font-size: 14px; -fx-border-radius: 5;");
        return button;
    }

    // Helper to create Header Labels
    private Label createHeaderLabel(String text) {
        Label label = new Label(text);
        label.setFont(new Font("Arial", 24));
        label.setStyle("-fx-text-fill: #000000;");
        return label;
    }

    // Show Dashboard (Placeholder)
    private void showDashboard() {
        mainContent.getChildren().clear();
        mainContent.getChildren().add(createHeaderLabel("Dashboard View (Under Construction)"));
    }

    // Show Product List
    private void showProductList() {
        mainContent.getChildren().clear();

        TableView<Book> table = createProductListTable();
        mainContent.getChildren().add(table);
    }

    // Create Product List Table
    private TableView<Book> createProductListTable() {
        TableView<Book> table = new TableView<>();

        // Fetch data from the database (placeholder for now)
        bookList.addAll(
            new Book("Java Programming", "Computer", "New", 45.99, "Listed"),
            new Book("Data Structures", "Math", "Used", 25.49, "Listed")
        );

        table.setItems(bookList);

        TableColumn<Book, String> nameCol = new TableColumn<>("Book Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Book, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Book, String> conditionCol = new TableColumn<>("Condition");
        conditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));

        TableColumn<Book, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Book, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Book, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = createActionButton("Edit");
            private final Button deleteButton = createActionButton("Delete");

            {
                editButton.setOnAction(e -> {
                    Book book = getTableView().getItems().get(getIndex());
                    showEditBookForm(book);
                });
                deleteButton.setOnAction(e -> {
                    Book book = getTableView().getItems().get(getIndex());
                    deleteBook(book);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5, editButton, deleteButton);
                    setGraphic(buttons);
                }
            }
        });

        table.getColumns().addAll(nameCol, categoryCol, conditionCol, priceCol, statusCol, actionCol);
        return table;
    }

    // Helper to create Action Buttons
    private Button createActionButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #F5A623; -fx-text-fill: #FFFFFF; -fx-font-size: 12px; -fx-border-radius: 5;");
        return button;
    }

    // Show Add Product Form
    private void showAddProductForm() {
        mainContent.getChildren().clear();

        VBox formLayout = createAddProductForm();
        mainContent.getChildren().add(formLayout);
    }

    // Create Add Product Form
    private VBox createAddProductForm() {
        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(20));

        TextField nameField = new TextField();
        nameField.setPromptText("Book Name");

        ComboBox<String> categoryField = new ComboBox<>();
        categoryField.getItems().addAll("Natural Science", "Math", "Computer", "English Language", "Other");

        ComboBox<String> conditionField = new ComboBox<>();
        conditionField.getItems().addAll("New", "Used");

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #F5A623; -fx-text-fill: #FFFFFF; -fx-font-size: 14px;");

        saveButton.setOnAction(e -> {
            String name = nameField.getText();
            String category = categoryField.getValue();
            String condition = conditionField.getValue();
            double price = Double.parseDouble(priceField.getText());

            // Mock Save Logic (Replace with database logic)
            bookList.add(new Book(name, category, condition, price, "Listed"));

            // Refresh product list
            showProductList();
        });

        formLayout.getChildren().addAll(nameField, categoryField, conditionField, priceField, saveButton);
        return formLayout;
    }

    // Delete Book (Mock Logic)
    private void deleteBook(Book book) {
        bookList.remove(book);
    }

    // Edit Book Form (Placeholder)
    private void showEditBookForm(Book book) {
        mainContent.getChildren().clear();
        mainContent.getChildren().add(createHeaderLabel("Edit Book View (Under Construction)"));
    }

    // Book Class (Mock Model)
    public static class Book {
        private String name;
        private String category;
        private String condition;
        private double price;
        private String status;

        public Book(String name, String category, String condition, double price, String status) {
            this.name = name;
            this.category = category;
            this.condition = condition;
            this.price = price;
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public String getCondition() {
            return condition;
        }

        public double getPrice() {
            return price;
        }

        public String getStatus() {
            return status;
        }
    }
}
