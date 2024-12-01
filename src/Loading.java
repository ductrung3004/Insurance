import javax.swing.*;

import java.util.Objects;


public class Loading extends JFrame {
    private JLabel jLabel1;
    JLabel load;
    private JLabel title;
    private JPanel loading;

    public Loading() {
        initComponents();
    }


    private void initComponents() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(838, 511));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        loading.setBackground(new java.awt.Color(255, 255, 255));
        loading.setLayout(null);

        title.setBackground(new java.awt.Color(255, 255, 255));
        title.setFont(new java.awt.Font("Cambria", 1, 24));
        title.setForeground(new java.awt.Color(43, 46, 135));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setText("INSURANCE MANAGEMENT SYSTEM");
        loading.add(title);
        title.setBounds(0, 290, 840, 40);



        load.setFont(new java.awt.Font("Cambria", 0, 10)); // NOI18N
        load.setForeground(new java.awt.Color(255, 255, 255));
        load.setHorizontalAlignment(SwingConstants.CENTER);
        load.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/load-8510_128.gif")))); // NOI18N
        load.setLabelFor(loading);
        load.setHorizontalTextPosition(SwingConstants.CENTER);
        loading.add(load);
        load.setBounds(0, 360, 866, 126);

        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/logo-vector-IU-01.png")))); // NOI18N
        loading.add(jLabel1);
        jLabel1.setBounds(340, 60, 170, 200);

        getContentPane().add(loading);
        loading.setBounds(-1, 0, 840, 520);

        pack();
        setLocationRelativeTo(null);
    }


    public static void main(String[] args) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("MetroUI".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Loading.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Loading.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Loading.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Loading.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Loading().setVisible(true);
            }
        });

    }
}


