import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class deleteCustomerFunction {


    void deleteCustomer(JTable table, DefaultTableModel model, Account connectManage) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(table, "Please select a customer to delete");
            return;
        }

        int customerID = (int) model.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(table,
                "Are you sure you want to delete this customer?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DriverManager.getConnection(connectManage.getUrl(), connectManage.getUsername(), connectManage.getPassword())) {
                // Delete related records first to avoid foreign key constraints
                try (PreparedStatement pstmtClaims = conn.prepareStatement("DELETE FROM Claim WHERE CustomerID = ?");
                     PreparedStatement pstmtPolicy = conn.prepareStatement("DELETE FROM Policy WHERE CustomerID = ?");
                     PreparedStatement pstmtCustomer = conn.prepareStatement("DELETE FROM Customer WHERE CustomerID = ?")) {

                    // Delete claims
                    pstmtClaims.setInt(1, customerID);
                    pstmtClaims.executeUpdate();

                    // Delete policies
                    pstmtPolicy.setInt(1, customerID);
                    pstmtPolicy.executeUpdate();

                    // Delete customer
                    pstmtCustomer.setInt(1, customerID);
                    pstmtCustomer.executeUpdate();

                    CustomerTab customerTab = new CustomerTab();
                    customerTab.loadCustomersToTable(model, connectManage);


                    JOptionPane.showMessageDialog(table, "Customer Deleted Successfully");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(table, "Error deleting customer: " + e.getMessage());
            }
        }
    }
}