import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class ClaimsTab extends Component {

    private final DatabaseConnectManage connectManage;
    public ClaimsTab() {
        connectManage = new DatabaseConnectManage();
    }

    JPanel createClaimsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create table model and populate with claims data
        String[] columnNames = {"Claim Number", "Claim Date", "Amount", "Status", "Incident Details"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try (Connection conn = DriverManager.getConnection(connectManage.getUrl(), connectManage.getUsername(), connectManage.getPassword());
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
}
