import javax.swing.*;
public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            AppPanel app = new AppPanel();
            app.setVisible(true);
        });
    }
}