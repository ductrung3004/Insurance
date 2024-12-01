import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class CustomerTab {


    JPanel createCustomersPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table Model
        String[] columnNames = {"CustomerID", "Name", "Company", "Business", "Phone", "Email", "Agent ID"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Load Customers
        loadCustomersToTable(model);

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Customer");
        JButton updateButton = new JButton("Update Customer");
        JButton deleteButton = new JButton("Delete Customer");

        addCustomerFunction addCustomer = new addCustomerFunction();
        deleteCustomerFunction deleteCustomer = new deleteCustomerFunction();
        updateCustomerFunction updateCustomer = new updateCustomerFunction();
        addButton.addActionListener(e -> addCustomer.addCustomer(model));
        updateButton.addActionListener(e -> updateCustomer.updateCustomer(table, model));
        deleteButton.addActionListener(e -> deleteCustomer.deleteCustomer(table, model));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    void loadCustomersToTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        DatabaseConnectManage connectManage = new DatabaseConnectManage();
        model.setRowCount(0); // Clear existing rows
        try (Connection conn = DriverManager.getConnection(connectManage.getUrl(), connectManage.getUsername(), connectManage.getPassword());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT CustomerID, Name, Company, Business, PhoneNumber, Email, AgentID FROM Customer")) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("CustomerID"));
                row.add(rs.getString("Name"));
                row.add(rs.getString("Company"));
                row.add(rs.getString("Business"));
                row.add(rs.getString("PhoneNumber"));
                row.add(rs.getString("Email"));
                row.add(rs.getInt("AgentID"));
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(table, "Error loading customers: " + e.getMessage());
        }
    }
}


