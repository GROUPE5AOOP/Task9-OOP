import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame(int userId, String firstName, String lastName, String username) {
        super("Main Dashboard");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Welcome banner
        JLabel welcome = new JLabel("Welcome, " + firstName + " " + lastName + " (" + username + ")!", SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcome.setForeground(new Color(46, 139, 87));

        // Info area
        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        info.setText(
                "You are now on the main dashboard.\n\n" +
                        "Here are your details:\n" +
                        "- User ID: " + userId + "\n" +
                        "- Name: " + firstName + " " + lastName + "\n" +
                        "- Username: " + username + "\n\n" +
                        "This is where you can extend the application with menus,\n" +
                        "tabs, and features such as profile management, reports, and notifications.\n\n" +
                        "Recent Activity:\n" +
                        "- Last login: Today at 08:45 AM\n" +
                        "- Tasks completed: 12\n" +
                        "- Pending tasks: 3\n\n" +
                        "Tips & Insights:\n" +
                        "1. Explore the Reports tab to visualize your performance trends.\n" +
                        "2. Use the Settings menu to customize your dashboard preferences.\n" +
                        "3. Keep your profile updated to ensure smooth communication.\n\n" +
                        "Did you know?\n" +
                        "Our platform allows you to track your progress, connect with peers, \n" +
                        "and receive personalized suggestions to enhance your workflow. \n" +
                        "Stay consistent, check notifications regularly, and make the most \n" +
                        "out of all the features available here.\n\n" +
                        "We hope you enjoy using the dashboard and that it helps you stay \n" +
                        "organized, productive, and informed every day!"
        );

//        info.setText(
//                "You are now on the main dashboard.\n\n" +
//                        "Here are your details:\n" +
//                        "- User ID: " + userId + "\n" +
//                        "- Name: " + firstName + " " + lastName + "\n" +
//                        "- Username: " + username + "\n\n" +
//                        "This is where you can extend the application with menus,\n" +
//                        "tabs, and features such as profile management, reports, etc."
//        );

        // Scrollable info panel
        JScrollPane scrollPane = new JScrollPane(info);

        // Action buttons
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(20, 87, 220));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.addActionListener(_ -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        actions.add(btnLogout);

        // Add components
        add(welcome, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);
    }

}
