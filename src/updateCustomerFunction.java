import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class updateCustomerFunction {


    void updateCustomer(JTable table, DefaultTableModel model) {
        DatabaseConnectManage connectManage = new DatabaseConnectManage();
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(table, "Please select a customer to update");
            return;
        }

        int customerID = (int) model.getValueAt(selectedRow, 0);

        JTextField nameField = new JTextField((String) model.getValueAt(selectedRow, 1), 20);
        JTextField companyField = new JTextField((String) model.getValueAt(selectedRow, 2), 20);
        JTextField businessField = new JTextField((String) model.getValueAt(selectedRow, 3), 20);
        JTextField phoneField = new JTextField((String) model.getValueAt(selectedRow, 4), 20);
        JTextField emailField = new JTextField((String) model.getValueAt(selectedRow, 5), 20);
        JTextField agentIdField = new JTextField(model.getValueAt(selectedRow, 6).toString(), 20);

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

        int result = JOptionPane.showConfirmDialog(table, inputPanel,
                "Update Customer", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try (Connection conn = DriverManager.getConnection(connectManage.getUrl(), connectManage.getUsername(), connectManage.getPassword())) {
                String sql = "UPDATE Customer SET Name=?, Company=?, Business=?, " +
                        "PhoneNumber=?, Email=?, AgentID=? WHERE CustomerID=?";

                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, nameField.getText());
                    pstmt.setString(2, companyField.getText());
                    pstmt.setString(3, businessField.getText());
                    pstmt.setString(4, phoneField.getText());
                    pstmt.setString(5, emailField.getText());
                    pstmt.setInt(6, Integer.parseInt(agentIdField.getText()));
                    pstmt.setInt(7, customerID);

                    pstmt.executeUpdate();

                    CustomerTab customerTab = new CustomerTab();
                    customerTab.loadCustomersToTable(model);

                    JOptionPane.showMessageDialog(table, "Customer Updated Successfully");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(table, "Error updating customer: " + e.getMessage());
            }
        }
    }
}


