import javax.swing.*;
import java.awt.*;
import java.sql.*;


public class QueryTab {
    private JPanel mainPanel;
    private JTextField txtQuery;
    private JButton btnRun;
    private JTextArea txtResult;
    private Account connectManage ;

    public QueryTab(Account account) {
        connectManage = account;
        initComponents();
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());

        // Query input panel
        JPanel queryInputPanel = new JPanel(new BorderLayout());
        txtQuery = new JTextField();
        btnRun = new JButton("Run");
        queryInputPanel.add(new JLabel("Query: "), BorderLayout.WEST);
        queryInputPanel.add(txtQuery, BorderLayout.CENTER);
        queryInputPanel.add(btnRun, BorderLayout.EAST);

        // Result area
        txtResult = new JTextArea();
        txtResult.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtResult);

        // Add components to main panel
        mainPanel.add(queryInputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add action listener
        btnRun.addActionListener(e -> executeQuery());
    }


    private void executeQuery() {
        String queryText = txtQuery.getText().trim();

        // Input validation
        if (queryText.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel,
                    "Please enter a valid SQL query.",
                    "Query Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate query to prevent destructive operations
        if (!isQuerySafe(queryText)) {
            JOptionPane.showMessageDialog(mainPanel,
                    "Unsafe query detected. Only SELECT queries are allowed.",
                    "Security Warning",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String results = loadDatabaseQuery(queryText);
            txtResult.setText(results);
        } catch (SQLException ex) {
            Account.class.getName();
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

    public JPanel createQueryTab() {
        return mainPanel;
    }
}