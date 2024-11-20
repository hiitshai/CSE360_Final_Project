package buyerconfirmation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class bookstoreGUI {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("ASU Bookstore");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(null);

        // Set the background color
        frame.getContentPane().setBackground(new Color(255, 204, 0)); // Gold background

        // Add the logo
        ImageIcon logoIcon = new ImageIcon("path_to_logo_image.png"); // Replace with the actual path to your logo
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(20, 20, 100, 100); // Adjust size and position
        frame.add(logoLabel);

        // Title
        JLabel title = new JLabel("The ASU Bookstore");
        title.setFont(new Font("Serif", Font.BOLD, 24));
        title.setForeground(new Color(128, 0, 0)); // Maroon text
        title.setBounds(150, 40, 300, 30);
        frame.add(title);

        // Delivery Section
        JLabel deliveryLabel = new JLabel("Delivery");
        deliveryLabel.setFont(new Font("Serif", Font.BOLD, 18));
        deliveryLabel.setForeground(Color.BLACK);
        deliveryLabel.setBounds(20, 140, 200, 30);
        frame.add(deliveryLabel);

        JCheckBox pickUpOption = new JCheckBox("Pick Up");
        pickUpOption.setBounds(40, 180, 100, 20);
        pickUpOption.setBackground(new Color(255, 204, 0)); // Match background
        frame.add(pickUpOption);

        JCheckBox shipToAddressOption = new JCheckBox("Ship to an Address");
        shipToAddressOption.setBounds(40, 210, 200, 20);
        shipToAddressOption.setBackground(new Color(255, 204, 0));
        frame.add(shipToAddressOption);

        // Text fields for shipping details
        JTextField firstNameField = new JTextField("First Name");
        firstNameField.setBounds(40, 240, 200, 25);
        frame.add(firstNameField);

        JTextField lastNameField = new JTextField("Last Name");
        lastNameField.setBounds(40, 270, 200, 25);
        frame.add(lastNameField);

        JTextField countryField = new JTextField("Country");
        countryField.setBounds(40, 300, 200, 25);
        frame.add(countryField);

        JTextField addressField = new JTextField("Street Address");
        addressField.setBounds(40, 330, 200, 25);
        frame.add(addressField);

        JTextField aptField = new JTextField("Apt, suite, floor");
        aptField.setBounds(40, 360, 200, 25);
        frame.add(aptField);

        JTextField cityField = new JTextField("City");
        cityField.setBounds(40, 390, 200, 25);
        frame.add(cityField);

        JTextField stateField = new JTextField("State");
        stateField.setBounds(40, 420, 90, 25);
        frame.add(stateField);

        JTextField zipField = new JTextField("Zip Code");
        zipField.setBounds(140, 420, 100, 25);
        frame.add(zipField);

        // Payment Section
        JLabel paymentLabel = new JLabel("Payment");
        paymentLabel.setFont(new Font("Serif", Font.BOLD, 18));
        paymentLabel.setForeground(Color.BLACK);
        paymentLabel.setBounds(20, 460, 200, 30);
        frame.add(paymentLabel);

        JRadioButton creditCardOption = new JRadioButton("Credit Card / Debit Card");
        creditCardOption.setBounds(40, 500, 200, 20);
        creditCardOption.setBackground(new Color(255, 204, 0));
        frame.add(creditCardOption);

        JTextField cardNameField = new JTextField("Name on Card");
        cardNameField.setBounds(40, 530, 200, 25);
        frame.add(cardNameField);

        JTextField cardNumberField = new JTextField("Card Number");
        cardNumberField.setBounds(40, 560, 200, 25);
        frame.add(cardNumberField);

        JTextField expirationField = new JTextField("Expiration Date");
        expirationField.setBounds(40, 590, 90, 25);
        frame.add(expirationField);

        JTextField cvvField = new JTextField("CVV");
        cvvField.setBounds(140, 590, 60, 25);
        frame.add(cvvField);

        // Order Summary
        JLabel summaryLabel = new JLabel("Order Summary");
        summaryLabel.setFont(new Font("Serif", Font.BOLD, 18));
        summaryLabel.setForeground(Color.BLACK);
        summaryLabel.setBounds(300, 140, 200, 30);
        frame.add(summaryLabel);

        JLabel totalLabel = new JLabel("Total: $133.98");
        totalLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        totalLabel.setForeground(Color.BLACK);
        totalLabel.setBounds(300, 180, 200, 20);
        frame.add(totalLabel);

        JButton checkoutButton = new JButton("Check Out");
        checkoutButton.setBounds(300, 220, 120, 30);
        checkoutButton.setBackground(new Color(128, 0, 0)); // Maroon button
        checkoutButton.setForeground(Color.WHITE);
        frame.add(checkoutButton);

        // Footer buttons
        JButton myAccountButton = new JButton("My Account");
        myAccountButton.setBounds(300, 300, 120, 30);
        myAccountButton.setBackground(new Color(128, 0, 0));
        myAccountButton.setForeground(Color.WHITE);
        frame.add(myAccountButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(300, 340, 120, 30);
        logoutButton.setBackground(new Color(128, 0, 0));
        logoutButton.setForeground(Color.WHITE);
        frame.add(logoutButton);

        JButton switchUserButton = new JButton("Switch User");
        switchUserButton.setBounds(300, 380, 120, 30);
        switchUserButton.setBackground(new Color(128, 0, 0));
        switchUserButton.setForeground(Color.WHITE);
        frame.add(switchUserButton);

        // Event Listener for Checkout
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Order Submitted!");
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}
