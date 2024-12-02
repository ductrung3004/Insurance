import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.Statement;


public class AppPanel extends JFrame {

    private JPanel AppPanel;
    private JButton profileButton;
    private JButton logoutButton;
    private JButton manageButton;
    private JButton optionButton;
    private Manage manage;

    public void setManage(Account account){
        manage = new Manage(account);
    }

    public AppPanel(final Account account) {
        setResizable(false);
        setTitle("Varied Insurance Management System");
        setSize(838, 511);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(AppPanel);
        centerFrameOnScreen();

        manageButton.addActionListener(e -> executeManage(account));


        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppPanel.setVisible(false);
                setVisible(false);
                Main.main(new String[0]);
            }
        });
    }
    private void executeManage(final Account account){
        setManage(account);
        manage.setVisible(true);
    }

    private void centerFrameOnScreen() {
        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Calculate the center position
        int centerX = (screenSize.width - getWidth()) / 2;
        int centerY = (screenSize.height - getHeight()) / 2;

        // Set the location
        setLocation(centerX, centerY);
    }
}
