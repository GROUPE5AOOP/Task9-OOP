import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {
    private JTextField tfFirstName, tfLastName, tfEmail, tfPhone, tfUsername;
    private JPasswordField pfPassword;

    public RegisterFrame() {
        super("Create Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Personal Info Panel
        JPanel personalPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        personalPanel.setBorder(new TitledBorder("Personal Information"));
        personalPanel.add(new JLabel("First name:"));
        tfFirstName = new JTextField();
        personalPanel.add(tfFirstName);
        personalPanel.add(new JLabel("Last name:"));
        tfLastName = new JTextField();
        personalPanel.add(tfLastName);

        // Contact Info Panel
        JPanel contactPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        contactPanel.setBorder(new TitledBorder("Contact Information"));
        contactPanel.add(new JLabel("Email:"));
        tfEmail = new JTextField();
        contactPanel.add(tfEmail);
        contactPanel.add(new JLabel("Phone:"));
        tfPhone = new JTextField();
        contactPanel.add(tfPhone);

        // Login Info Panel
        JPanel loginPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        loginPanel.setBorder(new TitledBorder("Login Information"));
        loginPanel.add(new JLabel("Username:"));
        tfUsername = new JTextField();
        loginPanel.add(tfUsername);
        loginPanel.add(new JLabel("Password:"));
        pfPassword = new JPasswordField();
        loginPanel.add(pfPassword);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRegister = new JButton("Register");
        btnRegister.setBackground(new Color(46, 139, 87));
        btnRegister.setForeground(Color.WHITE);

        JButton btnGoToLogin = new JButton("Back to Login");
        btnGoToLogin.setBackground(new Color(70, 130, 180));
        btnGoToLogin.setForeground(Color.WHITE);

        btnRegister.addActionListener(this::onRegister);
        btnGoToLogin.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        buttonPanel.add(btnGoToLogin);
        buttonPanel.add(btnRegister);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.add(personalPanel);
        centerPanel.add(contactPanel);
        centerPanel.add(loginPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void onRegister(ActionEvent e) {
        String firstName = tfFirstName.getText().trim();
        String lastName = tfLastName.getText().trim();
        String email = tfEmail.getText().trim();
        String phone = tfPhone.getText().trim();
        String username = tfUsername.getText().trim();
        String password = new String(pfPassword.getPassword());

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "INSERT INTO users (first_name, last_name, email, phone, username, password_hash) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, username);
            ps.setString(6, PasswordUtil.hash(password));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Account created successfully!");
            new LoginFrame().setVisible(true);
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving user: " + ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static class PasswordUtil {
        public static String hash(String input) {
            try {
                java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
                byte[] hashed = md.digest(input.getBytes(java.nio.charset.StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                for (byte b : hashed) {
                    sb.append(String.format("%02x", b));
                }
                return sb.toString();
            } catch (Exception e) {
                return input;
            }
        }
    }
}
