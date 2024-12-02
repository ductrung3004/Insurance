import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class PoliciesTab {


    JPanel createPoliciesPanel(Account connectManage) {
        JPanel panel = new JPanel(new BorderLayout());

        // Define column names for the table
        String[] columnNames = {"Policy Number", "Start Date", "Due Date", "Premium", "Coverage", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Load data into the table
        try (Connection conn = DriverManager.getConnection(connectManage.getUrl(), connectManage.getUsername(), connectManage.getPassword());
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
            JOptionPane.showMessageDialog(null, "Error loading policies: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }


        // Create table and add it to a scroll pane
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
}
