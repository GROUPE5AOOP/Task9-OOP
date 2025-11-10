import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private final JTextField tfUsername;
    private final JPasswordField pfPassword;

    public LoginFrame() {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Login Credentials"));
        formPanel.add(new JLabel("Username:"));
        tfUsername = new JTextField();
        formPanel.add(tfUsername);

        formPanel.add(new JLabel("Password:"));
        pfPassword = new JPasswordField();
        formPanel.add(pfPassword);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(46, 139, 87));
        btnLogin.setForeground(Color.WHITE);

        JButton btnBackRegister = new JButton("Create Account");
        btnBackRegister.setBackground(new Color(70, 130, 180));
        btnBackRegister.setForeground(Color.WHITE);

        btnLogin.addActionListener(this::onLogin);
        btnBackRegister.addActionListener(_ -> {
            new RegisterFrame().setVisible(true);
            dispose();
        });

        buttonPanel.add(btnBackRegister);
        buttonPanel.add(btnLogin);

        add(new JLabel("Welcome! Please log in below:", SwingConstants.CENTER), BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void onLogin(ActionEvent e) {
        String username = tfUsername.getText().trim();
        String password = new String(pfPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password are required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "SELECT id, first_name, last_name FROM users WHERE username = ? AND password_hash = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, RegisterFrame.PasswordUtil.hash(password));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");

                    DashboardFrame dashboard = new DashboardFrame(userId, firstName, lastName, username);
                    dashboard.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid username or password.",
                            "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error during login: "
                    + ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
