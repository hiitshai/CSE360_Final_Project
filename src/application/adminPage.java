package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class adminPage extends Pane {

    private VBox mainContent; // For dynamically updating views

    public adminPage() {
        // Sidebar
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #F5A623;");
        sidebar.setPrefWidth(200);

        // Sidebar Buttons
        Button manageUsersButton = createSidebarButton("Manage Users");
        Button manageTransactionsButton = createSidebarButton("Manage Transactions");
        Button analyticsButton = createSidebarButton("View Analytics");
        Button logOutButton = createSidebarButton("Log Out");

        // Add buttons to sidebar
        sidebar.getChildren().addAll(manageUsersButton, manageTransactionsButton, analyticsButton, logOutButton);

        // Main Content Area
        mainContent = new VBox();
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: #F9F9F9;");
        mainContent.getChildren().add(createHeaderLabel("Welcome to the Admin Dashboard"));

        // Navigation logic
        manageUsersButton.setOnAction(e -> showManageUsers());
        manageTransactionsButton.setOnAction(e -> showManageTransactions());
        analyticsButton.setOnAction(e -> showAnalytics());

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

    // Show Manage Users
    private void showManageUsers() {
        mainContent.getChildren().clear();

        TableView<User> userTable = createUserTable();
        mainContent.getChildren().addAll(
            createHeaderLabel("Manage Users"),
            new Separator(),
            userTable
        );
    }

    private TableView<User> createUserTable() {
        TableView<User> table = new TableView<>();

        ObservableList<User> users = FXCollections.observableArrayList(
            new User("John Doe", "johndoe@gmail.com", "Buyer"),
            new User("Jane Smith", "janesmith@gmail.com", "Seller"),
            new User("Admin", "admin@system.com", "Admin")
        );

        table.setItems(users);

        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<User, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = createActionButton("Delete");

            {
                deleteButton.setOnAction(e -> {
                    User user = getTableView().getItems().get(getIndex());
                    deleteUser(user);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5, deleteButton);
                    buttons.setAlignment(Pos.CENTER);
                    setGraphic(buttons);
                }
            }
        });

        table.getColumns().addAll(nameCol, emailCol, roleCol, actionCol);
        return table;
    }

    private void deleteUser(User user) {
        // Logic to delete user from the database
        System.out.println("Deleted user: " + user.getName());
    }

    // Show Manage Transactions
    private void showManageTransactions() {
        mainContent.getChildren().clear();

        Label header = createHeaderLabel("Transaction Management");
        TableView<Transaction> transactionTable = createTransactionTable();

        mainContent.getChildren().addAll(
            header,
            new Separator(),
            transactionTable
        );
    }

    private TableView<Transaction> createTransactionTable() {
        TableView<Transaction> table = new TableView<>();

        ObservableList<Transaction> transactions = FXCollections.observableArrayList(
            new Transaction("T001", "John Doe", "Jane Smith", "Java Book, Data Structures", 100.50, "2024-11-20"),
            new Transaction("T002", "Alice", "Bob", "Algorithms", 45.99, "2024-11-21"),
            new Transaction("T003", "Chris", "Diana", "Networking Guide", 75.25, "2024-11-22")
        );

        table.setItems(transactions);

        TableColumn<Transaction, String> idCol = new TableColumn<>("Transaction ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));

        TableColumn<Transaction, String> buyerCol = new TableColumn<>("Buyer Name");
        buyerCol.setCellValueFactory(new PropertyValueFactory<>("buyerName"));

        TableColumn<Transaction, String> sellerCol = new TableColumn<>("Seller Name");
        sellerCol.setCellValueFactory(new PropertyValueFactory<>("sellerName"));

        TableColumn<Transaction, String> itemsCol = new TableColumn<>("Items Purchased");
        itemsCol.setCellValueFactory(new PropertyValueFactory<>("items"));

        TableColumn<Transaction, Double> priceCol = new TableColumn<>("Total Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        TableColumn<Transaction, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.getColumns().addAll(idCol, buyerCol, sellerCol, itemsCol, priceCol, dateCol);
        return table;
    }

    // Show Analytics
    private void showAnalytics() {
        mainContent.getChildren().clear();

        Label header = createHeaderLabel("Analytics");
        mainContent.getChildren().addAll(
            header,
            new Separator(),
            new Label("Analytics charts and reports coming soon...")
        );
    }

    // Helper to create Action Buttons
    private Button createActionButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #F5A623; -fx-text-fill: #FFFFFF; -fx-font-size: 12px; -fx-border-radius: 5;");
        return button;
    }

    // User Class (Mock Model)
    public static class User {
        private String name;
        private String email;
        private String role;

        public User(String name, String email, String role) {
            this.name = name;
            this.email = email;
            this.role = role;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getRole() {
            return role;
        }
    }

    // Transaction Class (Mock Model)
    public static class Transaction {
        private String transactionId;
        private String buyerName;
        private String sellerName;
        private String items;
        private double totalPrice;
        private String date;

        public Transaction(String transactionId, String buyerName, String sellerName, String items, double totalPrice, String date) {
            this.transactionId = transactionId;
            this.buyerName = buyerName;
            this.sellerName = sellerName;
            this.items = items;
            this.totalPrice = totalPrice;
            this.date = date;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public String getBuyerName() {
            return buyerName;
        }

        public String getSellerName() {
            return sellerName;
        }

        public String getItems() {
            return items;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public String getDate() {
            return date;
        }
    }
}