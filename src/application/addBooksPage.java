package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AddBooksPage extends Pane {

    public AddBooksPage() {
        // Sidebar
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #F5A623;");
        sidebar.setPrefWidth(200);

        // Sidebar Title
        HBox sidebarHeader = new HBox(10);
        Button closeSidebarButton = new Button("X");
        closeSidebarButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-font-size: 14px;");
        Label sidebarTitle = new Label("ASU Bookstore");
        sidebarTitle.setFont(new Font("Arial", 18));
        sidebarTitle.setTextFill(Color.BLACK);
        sidebarHeader.getChildren().addAll(closeSidebarButton, sidebarTitle);

        // Sidebar Buttons
        Button dashboardButton = createSidebarButton("Dashboard");
        Button productListButton = createSidebarButton("Product List");
        Button addProductButton = createSidebarButton("Add More Products");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Separator divider = new Separator();
        divider.setStyle("-fx-background-color: black;");

        Button myAccountButton = createSidebarButton("My Account", "#800000");
        Button switchUserButton = createSidebarButton("Switch User", "#800000");
        Button logOutButton = createSidebarButton("Log Out", "#800000");

        // Add elements to sidebar
        sidebar.getChildren().addAll(sidebarHeader, dashboardButton, productListButton, addProductButton, spacer, divider, myAccountButton, switchUserButton, logOutButton);

        // Main Content Area
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: white;");
        content.setAlignment(Pos.TOP_CENTER);

        Label pageTitle = new Label("Add Books to The Bookstore");
        pageTitle.setFont(new Font("Arial", 24));
        pageTitle.setTextFill(Color.BLACK);

        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(20));
        formBox.setStyle("-fx-background-color: #D3D3D3; -fx-background-radius: 10;");
        formBox.setPrefSize(400, 400);

        // Form Fields
        TextField bookNameField = new TextField();
        bookNameField.setPromptText("Enter Book Name");

        TextField categoryField = new TextField();
        categoryField.setPromptText("Enter Category");

        ComboBox<String> conditionDropdown = new ComboBox<>();
        conditionDropdown.getItems().addAll("New", "Used");
        conditionDropdown.setPromptText("Select Condition");

        TextField originalPriceField = new TextField();
        originalPriceField.setPromptText("Enter Original Price");

        TextField usedPriceField = new TextField();
        usedPriceField.setPromptText("Enter Used Price");

        ComboBox<String> statusDropdown = new ComboBox<>();
        statusDropdown.getItems().addAll("Active", "Inactive");
        statusDropdown.setPromptText("Select Listing Status");

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-size: 14px;");

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-size: 14px;");

        buttonBox.getChildren().addAll(saveButton, cancelButton);

        // Add fields to form
        formBox.getChildren().addAll(bookNameField, categoryField, conditionDropdown, originalPriceField, usedPriceField, statusDropdown, buttonBox);

        content.getChildren().addAll(pageTitle, formBox);

        // Root Layout
        HBox rootLayout = new HBox();
        rootLayout.getChildren().addAll(sidebar, content);
        HBox.setHgrow(content, Priority.ALWAYS);

        this.getChildren().add(rootLayout);

        // Event Handlers
        saveButton.setOnAction(e -> handleSave(bookNameField, categoryField, conditionDropdown, originalPriceField, usedPriceField, statusDropdown));
        cancelButton.setOnAction(e -> handleCancel());
    }

    private Button createSidebarButton(String text) {
        return createSidebarButton(text, "#FFFFFF");
    }

    private Button createSidebarButton(String text, String bgColor) {
        Button button = new Button(text);
        button.setPrefWidth(150);
        button.setStyle("-fx-background-color: " + bgColor + "; -fx-text-fill: black; -fx-font-size: 14px;");
        return button;
    }

    private void handleSave(TextField bookNameField, TextField categoryField, ComboBox<String> conditionDropdown, TextField originalPriceField, TextField usedPriceField, ComboBox<String> statusDropdown) {
        String bookName = bookNameField.getText();
        String category = categoryField.getText();
        String condition = conditionDropdown.getValue();
        String originalPrice = originalPriceField.getText();
        String usedPrice = usedPriceField.getText();
        String status = statusDropdown.getValue();

        if (bookName.isEmpty() || category.isEmpty() || condition == null || originalPrice.isEmpty() || usedPrice.isEmpty() || status == null) {
            showAlert("Error", "All fields must be filled out.");
            return;
        }

        // Save logic placeholder
        showAlert("Success", "Book added successfully.");
    }

    private void handleCancel() {
        showAlert("Cancelled", "Book addition cancelled.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        Stage stage = new Stage();
        Scene scene = new Scene(new AddBooksPage(), 800, 600);
        stage.setScene(scene);
        stage.setTitle("Add Books to Bookstore");
        stage.show();
    }
}
