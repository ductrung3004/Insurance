import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.Vector;

public class Login extends JFrame {
    private JPanel login;

    private JTextField txtURL;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private JLabel title;

    private JButton btnRun;
    private JLabel logo;
    private JLabel user;
    private JLabel pass;
    private JLabel url;
    private JLabel authorize;
    private JLabel source;


    public Login() {
        initComponents();

    }

    public void initComponents(){
        setTitle("Varied Insurance Management System");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(838, 511));

        setResizable(false);
        title.setFont(new java.awt.Font("Cambria", 1, 24));
        title.setForeground(new java.awt.Color(43, 46, 135));

        authorize.setFont(new java.awt.Font("Cambria", 1, 18));
        authorize.setForeground(new java.awt.Color(43, 46, 135));

        url.setFont(new java.awt.Font("Cambria", 1, 18));
        url.setForeground(new java.awt.Color(43, 46, 135));

        user.setFont(new java.awt.Font("Cambria", 1, 18));
        user.setForeground(new java.awt.Color(43, 46, 135));

        pass.setFont(new java.awt.Font("Cambria", 1, 18));
        pass.setForeground(new java.awt.Color(43, 46, 135));

        source.setFont(new java.awt.Font("Cambria", 1, 12));
        source.setForeground(new java.awt.Color(43, 46, 135));
        setContentPane(login);
        setVisible(true);

        pack();
        setLocationRelativeTo(null);
        btnRun.addActionListener(e -> executeLogin());
        setVisible(false);

    }

    private void executeLogin(){
        String user = txtUsername.getText().trim();
        String pass = txtPassword.getText().trim();
        String url = txtURL.getText().trim();
        if (url.isEmpty()) {
            JOptionPane.showMessageDialog(login,
                    "Please enter the server ip.",
                    "Query Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (user.isEmpty()) {
            JOptionPane.showMessageDialog(login,
                    "Please enter username",
                    "Query Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (pass.isEmpty()) {
            JOptionPane.showMessageDialog(login,
                    "Please enter password.",
                    "Query Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://" + url + ":1433;databaseName=VariedInsurance;encrypt=true;trustServerCertificate=true;", user, pass);){
                 Account account = new Account("jdbc:sqlserver://" + url + ":1433;databaseName=VariedInsurance;encrypt=true;trustServerCertificate=true;", user, pass);
                 AppPanel appPanel = new AppPanel(account);
                 setVisible(false);
                 appPanel.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Login Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public JTextField getTxtURL() {
        return txtURL;
    }

}
