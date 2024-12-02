import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class addCustomerFunction {



    void addCustomer(DefaultTableModel model, Account connectManage) {

        JTextField nameField = new JTextField(20);
        JTextField companyField = new JTextField(20);
        JTextField businessField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField agentIdField = new JTextField(20);
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Company:"));
        inputPanel.add(companyField);
        inputPanel.add(new JLabel("Business:"));
        inputPanel.add(businessField);
        inputPanel.add(new JLabel("Phone:"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Agent ID:"));
        inputPanel.add(agentIdField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel,
                "Add New Customer", JOptionPane.OK_CANCEL_OPTION);


        if (result == JOptionPane.OK_OPTION) {
            if (nameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Name is empty");
            }
            if (nameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Company is empty");
            }
            if (businessField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Business is empty");
            }
            if (phoneField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Phone number is empty");
            }
            if (emailField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Email is empty");
            }
            if (agentIdField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Agent ID is empty");
            }
            try (Connection conn = DriverManager.getConnection(connectManage.getUrl(), connectManage.getUsername(), connectManage.getPassword())) {
                String sql = "INSERT INTO Customer (Name, Company, Business, PhoneNumber, Email, AgentID) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";



                try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                    pstmt.setString(1, nameField.getText());

                    pstmt.setString(2, companyField.getText());

                    pstmt.setString(3, businessField.getText());

                    pstmt.setString(4, phoneField.getText());

                    pstmt.setString(5, emailField.getText());


                    pstmt.setInt(6, Integer.parseInt(agentIdField.getText()));


                    pstmt.executeUpdate();

                    CustomerTab customerTab = new CustomerTab();
                    customerTab.loadCustomersToTable(model, connectManage);


                    JOptionPane.showMessageDialog(null, "Customer Added Successfully");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error adding customer: " + e.getMessage());
            }
        }
    }
}
