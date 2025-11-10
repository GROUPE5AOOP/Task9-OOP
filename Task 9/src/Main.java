import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        try {
            // Apply Nimbus look & feel 
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Nimbus theme not available, using default.");
        }

        SwingUtilities.invokeLater(() -> {
            // Start with login form
            new LoginFrame().setVisible(true);
        });
    }
}
