package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class sellersPage extends Pane {

    public sellersPage() {
        // Sidebar
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #F5A623;");
        sidebar.setPrefWidth(200);

        // Sidebar Buttons
        Button dashboardButton = new Button("Dashboard");
        Button productListButton = new Button("Product List");
        Button addProductButton = new Button("Add More Products");
        Button myAccountButton = new Button("My Account");
        Button switchUserButton = new Button("Switch User");
        Button logOutButton = new Button("Log Out");

        // Styling sidebar buttons
        for (Button button : new Button[]{dashboardButton, productListButton, addProductButton, myAccountButton, switchUserButton, logOutButton}) {
            button.setPrefWidth(150);
            button.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000; -fx-font-size: 14px;");
        }

        // Add buttons to sidebar
        sidebar.getChildren().addAll(dashboardButton, productListButton, addProductButton, myAccountButton, switchUserButton, logOutButton);

        // Header
        HBox header = new HBox();
        header.setPadding(new Insets(20, 20, 20, 20));
        header.setStyle("-fx-background-color: #FFFFFF;");
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(20);

        Label headerLabel = new Label("Welcome back, User");
        headerLabel.setFont(new Font("Arial", 24));
        headerLabel.setStyle("-fx-text-fill: #000000;");

        Label subHeaderLabel = new Label("Here are today’s stats from your ASU Bookstore!");
        subHeaderLabel.setFont(new Font("Arial", 14));
        subHeaderLabel.setStyle("-fx-text-fill: #666666;");

        VBox headerText = new VBox(headerLabel, subHeaderLabel);
        headerText.setSpacing(5);

        // Placeholder for profile image (can be replaced with actual image logic)
        Button profileButton = new Button("User");
        profileButton.setStyle("-fx-background-color: #CCCCCC; -fx-text-fill: #000000; -fx-font-size: 14px;");

        header.getChildren().addAll(headerText, profileButton);
        HBox.setHgrow(headerText, Priority.ALWAYS);

        // Stats Section
        HBox statsBox = new HBox(20);
        statsBox.setPadding(new Insets(20));
        statsBox.setStyle("-fx-background-color: #F9F9F9;");
        statsBox.setAlignment(Pos.CENTER);

        // Stat Cards
        VBox totalSalesCard = createStatCard("Total Sales", "$9,328.55", "731 Orders", "+15.6%", "+1.4k this week", "#F5A623");
        VBox visitorsCard = createStatCard("Visitors", "12,302", "Avg. time: 4:30m", "+12.7%", "+1.2k this week", "#FFFFFF");
        VBox refundsCard = createStatCard("Refunds", "963", "2 Disputed", "-12.7%", "-213", "#FFFFFF");

        statsBox.getChildren().addAll(totalSalesCard, visitorsCard, refundsCard);

        // Main Layout
        VBox mainLayout = new VBox();
        mainLayout.getChildren().addAll(header, statsBox);

        // Root Layout
        HBox rootLayout = new HBox();
        rootLayout.getChildren().addAll(sidebar, mainLayout);
        HBox.setHgrow(mainLayout, Priority.ALWAYS);

        // Add root layout to this Pane
        this.getChildren().add(rootLayout);

        // Add Button Event Handlers
        dashboardButton.setOnAction(e -> loadDashboard(mainLayout));
        productListButton.setOnAction(e -> loadProductList(mainLayout));
        addProductButton.setOnAction(e -> openAddProductForm(mainLayout));
        myAccountButton.setOnAction(e -> openMyAccount(mainLayout));
        switchUserButton.setOnAction(e -> switchUser());
        logOutButton.setOnAction(e -> logOut());
    }

    // Helper Method to Create Stat Cards
    private VBox createStatCard(String title, String mainStat, String subText, String change, String weeklyChange, String bgColor) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 10; -fx-border-color: #E0E0E0; -fx-border-radius: 10;");
        card.setPrefWidth(200);

        Label titleLabel = new Label(title);
        titleLabel.setFont(new Font("Arial", 14));
        titleLabel.setStyle("-fx-text-fill: #000000;");

        Label mainStatLabel = new Label(mainStat);
        mainStatLabel.setFont(new Font("Arial", 24));
        mainStatLabel.setStyle("-fx-text-fill: #000000;");

        Label subTextLabel = new Label(subText);
        subTextLabel.setFont(new Font("Arial", 12));
        subTextLabel.setStyle("-fx-text-fill: #666666;");

        HBox changeBox = new HBox(10);
        changeBox.setAlignment(Pos.CENTER_LEFT);

        Label changeLabel = new Label(change);
        changeLabel.setFont(new Font("Arial", 12));
        changeLabel.setStyle("-fx-text-fill: #00AA00;"); // Green for positive change

        Label weeklyChangeLabel = new Label(weeklyChange);
        weeklyChangeLabel.setFont(new Font("Arial", 12));
        weeklyChangeLabel.setStyle("-fx-text-fill: #666666;");

        changeBox.getChildren().addAll(changeLabel, weeklyChangeLabel);

        card.getChildren().addAll(titleLabel, mainStatLabel, subTextLabel, changeBox);
        return card;
    }

    // Load Dashboard Content
    private void loadDashboard(VBox mainLayout) {
        Label dashboardLabel = new Label("Dashboard Content");
        dashboardLabel.setFont(new Font("Arial", 20));
        mainLayout.getChildren().setAll(dashboardLabel);
    }

    // Load Product List
    private void loadProductList(VBox mainLayout) {
        VBox productList = new VBox(10);
        productList.setPadding(new Insets(20));
        productList.getChildren().add(new Label("Product 1"));
        productList.getChildren().add(new Label("Product 2"));
        productList.getChildren().add(new Label("Product 3"));
        mainLayout.getChildren().setAll(productList);
    }

    // Open Add Product Form
    private void openAddProductForm(VBox mainLayout) {
        Label addProductLabel = new Label("Add Product Form");
        addProductLabel.setFont(new Font("Arial", 20));
        mainLayout.getChildren().setAll(addProductLabel);
    }

    // Open My Account Page
    private void openMyAccount(VBox mainLayout) {
    	myAccountPage myAccount = new myAccountPage();
        Scene myAccountPageScene = new Scene(myAccount, 800, 400);
        myAccount.setStyle("-fx-background-color: #F5DEB3;");
        Stage mainStage = Main.getPrimaryStage();
        mainStage.setScene(myAccountPageScene);
    }

    // Switch User Logic
    private void switchUser() {
        System.out.println("Switching user...");
        BuyersView buyersViewPage = new BuyersView();
        Scene buyersViewScene = new Scene(buyersViewPage, 800, 600);
        //sellersPage.setStyle("-fx-background-color: #F5DEB3;");
        Stage mainStage = Main.getPrimaryStage();
        mainStage.setScene(buyersViewScene);
    }

    // Log Out Logic
    private void logOut() {
        System.out.println("Logging out...");
        loginPage loginPage = new loginPage();
        Scene loginPageScene = new Scene(loginPage, 800, 600);
        loginPage.setStyle("-fx-background-color: #F5DEB3;");
        Stage mainStage = Main.getPrimaryStage();
        mainStage.setScene(loginPageScene);
    }
}