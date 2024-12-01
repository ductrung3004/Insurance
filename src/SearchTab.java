import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class SearchTab {
    private JPanel mainPanel;

    private JTextArea txtResult;
    DatabaseConnectManage connectManage = new DatabaseConnectManage();
    private JRadioButton customerRadioButton;
    private JRadioButton agentRadioButton;
    private JButton btnSearch;
    private JTextField txtName;
    private JTable table1;

    private ButtonGroup group;
    public SearchTab() {
        initComponents();
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());

        // Query input panel
        JPanel queryInputPanel = new JPanel(new BorderLayout());
        JPanel RadioButton = new JPanel(new FlowLayout());
        btnSearch = new JButton("Search");

        agentRadioButton = new JRadioButton("Agent");
        customerRadioButton = new JRadioButton("Customer");

        group = new ButtonGroup();
        group.add(customerRadioButton);
        group.add(agentRadioButton);

        queryInputPanel.add(btnSearch, BorderLayout.EAST);
        queryInputPanel.add(txtName, BorderLayout.CENTER);

        RadioButton.add(agentRadioButton);
        RadioButton.add(customerRadioButton);

        queryInputPanel.add(RadioButton, BorderLayout.WEST);


        // Result area
        txtResult = new JTextArea();
        txtResult.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtResult);

        // Add components to main panel
        mainPanel.add(queryInputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add action listener
        btnSearch.addActionListener(e -> executeQuery());
    }


    private void executeQuery() {
        String Name = txtName.getText().trim();
        // Input validation
        if (Name.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel,
                    "Please enter Name of selected table.",
                    "Query Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate query to prevent destructive operations
        try {

            if (agentRadioButton.isSelected()) {

                String queryText = "select * from Agent where AgentName like '%" + Name + "%'";
                String results = loadDatabaseQuery(queryText);
                txtResult.setText(results);
            }

                else if (customerRadioButton.isSelected()) {

                String queryText = "select * from Customer where Name like '%" + Name + "%'";
                String results = loadDatabaseQuery(queryText);
                txtResult.setText(results);
            }
            else {
                JOptionPane.showMessageDialog(mainPanel,"Choose Agent/Customer");
            }

        } catch (SQLException ex) {
            DatabaseConnectManage.class.getName();
            txtResult.setText("Error executing query: " + ex.getMessage());
        }
    }

    private boolean isQuerySafe(String query) {
        // Basic query safety check
        String upperQuery = query.toUpperCase().trim();
        return upperQuery.startsWith("SELECT") &&
                !upperQuery.contains("DROP") &&
                !upperQuery.contains("DELETE") &&
                !upperQuery.contains("INSERT") &&
                !upperQuery.contains("UPDATE");
    }


    private String loadDatabaseQuery(String sqlQuery) throws SQLException {
        try (Connection con = DriverManager.getConnection(connectManage.getUrl(), connectManage.getUsername(), connectManage.getPassword());
             PreparedStatement pstmt = con.prepareStatement(sqlQuery);
             ResultSet rs = pstmt.executeQuery()) {

            StringBuilder results = new StringBuilder();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Print column names
            for (int i = 1; i <= columnCount; i++) {
                results.append(metaData.getColumnName(i)).append("\t");
            }
            results.append("\n");

            // Print row data
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    results.append(rs.getString(i)).append("\t");
                }
                results.append("\n");
            }
            return results.toString();
        }
    }
    public JPanel createSearchTab() {
        return mainPanel;
    }

}


