import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public  InsuranceManagementApp extends JFrame {
    private static final String DB_URL = "jdbc:sqlserver://truntrun.ddns.net:1433;databaseName=VariedInsurance;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "swallop";
    private static final String PASS = "Thinh@1607";

    private JTabbedPane tabbedPane;
    private JPanel policiesPanel, customersPanel, claimsPanel;

    public InsuranceManagementApp() {
        setTitle("Varied Insurance Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();

        // Initialize panels
        policiesPanel = createPoliciesPanel();
        customersPanel = createCustomersPanel();
        claimsPanel = createClaimsPanel();

        // Add panels to tabbed pane
        tabbedPane.addTab("Policies", policiesPanel);
        tabbedPane.addTab("Customers", customersPanel);
        tabbedPane.addTab("Claims", claimsPanel);

        // Add tabbed pane to frame
        add(tabbedPane);
    }

    private JPanel createPoliciesPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create table model and populate with policies data
        String[] columnNames = {"Policy Number", "Start Date", "Due Date", "Premium", "Coverage", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT PolicyNumber, StartDate, DueDate, PremiumAmount, CoverageAmount, Status FROM Policy")) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("PolicyNumber"));
                row.add(rs.getDate("StartDate"));
                row.add(rs.getDate("DueDate"));
                row.add(rs.getDouble("PremiumAmount"));
                row.add(rs.getDouble("CoverageAmount"));
                row.add(rs.getString("Status"));
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading policies: " + e.getMessage());
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCustomersPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table Model
        String[] columnNames = {"CustomerID", "Name", "Company", "Business", "Phone", "Email", "Agent ID"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // CustomerID is not editable
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

        addButton.addActionListener(e -> addCustomer(model));
        updateButton.addActionListener(e -> updateCustomer(table, model));
        deleteButton.addActionListener(e -> deleteCustomer(table, model));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadCustomersToTable(DefaultTableModel model) {
        model.setRowCount(0); // Clear existing rows
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
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
            JOptionPane.showMessageDialog(this, "Error loading customers: " + e.getMessage());
        }
    }

        private void deleteCustomer(JTable table, DefaultTableModel model) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a customer to delete");
                return;
            }

            int customerID = (int) model.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this customer?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
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

                        // Reload the table
                        loadCustomersToTable(model);

                        JOptionPane.showMessageDialog(this, "Customer Deleted Successfully");
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error deleting customer: " + e.getMessage());
                }
            }
        }

    private void addCustomer(DefaultTableModel model) {
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

        int result = JOptionPane.showConfirmDialog(this, inputPanel,
                "Add New Customer", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
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

                    // Reload the table
                    loadCustomersToTable(model);

                    JOptionPane.showMessageDialog(this, "Customer Added Successfully");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error adding customer: " + e.getMessage());
            }
        }
    }

    private void updateCustomer(JTable table, DefaultTableModel model) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer to update");
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

        int result = JOptionPane.showConfirmDialog(this, inputPanel,
                "Update Customer", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
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

                    // Reload the table
                    loadCustomersToTable(model);

                    JOptionPane.showMessageDialog(this, "Customer Updated Successfully");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error updating customer: " + e.getMessage());
            }
        }
    }

    private JPanel createClaimsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create table model and populate with claims data
        String[] columnNames = {"Claim Number", "Claim Date", "Amount", "Status", "Incident Details"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ClaimNumber, ClaimDate, ClaimAmount, ClaimStatus, IncidentDetails FROM Claim")) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("ClaimNumber"));
                row.add(rs.getDate("ClaimDate"));
                row.add(rs.getDouble("ClaimAmount"));
                row.add(rs.getString("ClaimStatus"));
                row.add(rs.getString("IncidentDetails"));
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading claims: " + e.getMessage());
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        // Ensure SQL Server JDBC driver is loaded


        // Run the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            InsuranceManagementApp app = new InsuranceManagementApp();
            app.setVisible(true);
        });
    }
}