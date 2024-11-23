package buyercart;

import javax.swing.*;
import java.awt.*;

public class BuyerCartGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("ASU Bookstore");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(255, 228, 181)); // ASU gold background

        // Fonts and Colors
        Font titleFont = new Font("Arial", Font.BOLD, 20);
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonFont = new Font("Arial", Font.PLAIN, 12);
        Color maroon = new Color(139, 0, 0);

        // Logo (Optional: Replace with actual ASU logo if available)
        JLabel logoLabel;
        try {
            logoLabel = new JLabel(new ImageIcon(BuyerCartGUI.class.getResource("/images/asu_logo.png"))); // ASU logo path
        } catch (Exception e) {
            logoLabel = new JLabel("Logo Not Found");
        }
        logoLabel.setBounds(30, 20, 100, 100); // Adjust size and position
        frame.add(logoLabel);

        // My Cart Title
        JLabel cartTitle = new JLabel("My Cart");
        cartTitle.setFont(titleFont);
        cartTitle.setBounds(150, 30, 200, 30);
        frame.add(cartTitle);

        // Separator line
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBounds(150, 65, 900, 5);
        separator.setBackground(Color.BLACK);
        frame.add(separator);

        // Panel for the cart items
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        cartPanel.setBackground(new Color(255, 228, 181));
        cartPanel.setBounds(150, 70, 600, 500);
        frame.add(cartPanel);

        // Order Summary
        JLabel orderSummaryTitle = new JLabel("Order Summary");
        orderSummaryTitle.setFont(titleFont);
        orderSummaryTitle.setBounds(800, 100, 200, 30);
        frame.add(orderSummaryTitle);

        JLabel totalLabel = new JLabel("Total: $133.98");
        totalLabel.setFont(titleFont);
        totalLabel.setBounds(800, 150, 200, 30);
        frame.add(totalLabel);

        JButton checkoutButton = new JButton("Check Out");
        checkoutButton.setFont(buttonFont);
        checkoutButton.setBounds(800, 200, 150, 40);
        checkoutButton.setBackground(maroon);
        checkoutButton.setForeground(Color.WHITE);
        frame.add(checkoutButton);

        JButton removeAllButton = new JButton("Remove all items");
        removeAllButton.setFont(buttonFont);
        removeAllButton.setBounds(800, 260, 150, 40);
        removeAllButton.setBackground(maroon);
        removeAllButton.setForeground(Color.WHITE);
        frame.add(removeAllButton);

        // Add books to the cart
        addBook(cartPanel, "Computer Organization & Design by Patterson", 74.99, "Buy New", "/images/mathbookcover.png");
        addBook(cartPanel, "Intro to Algorithms by Cormen", 58.99, "Buy Used", "/images/mathbookcover.png");

        // Remove All Items
        removeAllButton.addActionListener(e -> {
            cartPanel.removeAll();
            cartPanel.revalidate();
            cartPanel.repaint();
            totalLabel.setText("Total: $0.00");
        });

        // Bottom Navigation
        JButton myAccountButton = new JButton("My Account");
        myAccountButton.setFont(buttonFont);
        myAccountButton.setBounds(200, 600, 150, 30);
        myAccountButton.setBackground(maroon);
        myAccountButton.setForeground(Color.WHITE);
        frame.add(myAccountButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(buttonFont);
        logoutButton.setBounds(400, 600, 150, 30);
        logoutButton.setBackground(maroon);
        logoutButton.setForeground(Color.WHITE);
        frame.add(logoutButton);

        JButton switchUserButton = new JButton("Switch User");
        switchUserButton.setFont(buttonFont);
        switchUserButton.setBounds(600, 600, 150, 30);
        switchUserButton.setBackground(maroon);
        switchUserButton.setForeground(Color.WHITE);
        frame.add(switchUserButton);

        frame.setVisible(true);
    }

    private static void addBook(JPanel cartPanel, String bookTitle, double price, String selection, String imagePath) {
        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(null);
        bookPanel.setPreferredSize(new Dimension(600, 150));
        bookPanel.setBackground(new Color(255, 228, 181));

        // Add Book Cover
        JLabel bookCoverLabel;
        try {
            bookCoverLabel = new JLabel(new ImageIcon(BuyerCartGUI.class.getResource(imagePath)));
        } catch (Exception e) {
            bookCoverLabel = new JLabel("Image Not Found");
        }
        bookCoverLabel.setBounds(10, 10, 100, 120);
        bookPanel.add(bookCoverLabel);

        JLabel bookTitleLabel = new JLabel(bookTitle);
        bookTitleLabel.setBounds(120, 10, 400, 20);
        bookPanel.add(bookTitleLabel);

        JLabel selectionLabel = new JLabel("Selection: " + selection);
        selectionLabel.setBounds(120, 40, 200, 20);
        bookPanel.add(selectionLabel);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(350, 40, 70, 20);
        bookPanel.add(quantityLabel);

        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        quantitySpinner.setBounds(420, 40, 50, 20);
        bookPanel.add(quantitySpinner);

        JLabel priceLabel = new JLabel(String.format("$%.2f", price));
        priceLabel.setBounds(120, 70, 100, 20);
        bookPanel.add(priceLabel);

        JButton removeButton = new JButton("Remove");
        removeButton.setBounds(500, 40, 100, 30);
        removeButton.setBackground(new Color(139, 0, 0));
        removeButton.setForeground(Color.WHITE);
        bookPanel.add(removeButton);

        removeButton.addActionListener(e -> {
            cartPanel.remove(bookPanel);
            cartPanel.revalidate();
            cartPanel.repaint();
        });

        cartPanel.add(bookPanel);
        cartPanel.revalidate();
    }
}
