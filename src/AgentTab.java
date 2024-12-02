import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class AgentTab {


    JPanel createAgentTab(Account acount) {
        JPanel panel = new JPanel(new BorderLayout());

        //Columns
        String[] columnsNames = {"Agent ID", "Agent Name", "Phone Number", "Email", "License Number"};
        DefaultTableModel model = new DefaultTableModel(columnsNames, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        loadAgentToTable(model,acount);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }


    void loadAgentToTable(DefaultTableModel model, Account connectManage){

        try (Connection conn = DriverManager.getConnection(connectManage.getUrl(), connectManage.getUsername(), connectManage.getPassword());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT AgentID, AgentName, PhoneNumber, Email, LicenseNumber FROM Agent")) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("AgentID"));
                row.add(rs.getString("AgentName"));
                row.add(rs.getString("PhoneNumber"));
                row.add(rs.getString("Email"));
                row.add(rs.getString("LicenseNumber"));
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading policies: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
