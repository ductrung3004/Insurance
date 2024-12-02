import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;


public class Manage extends JFrame {



    private JTabbedPane tabbedPane;
    private PoliciesTab policiesPanel;
    private CustomerTab customersPanel;
    private ClaimsTab claimsPanel;
    private AgentTab agentPanel;
    private QueryTab queryPanel;
    private SearchTab searchPanel;

    public Manage(Account account){
        setResizable(false);
        setTitle("Varied Insurance Management System");
        setSize(838, 511);
        centerFrameOnScreen();

        tabbedPane = new JTabbedPane();
        policiesPanel = new PoliciesTab();
        customersPanel = new CustomerTab();
        claimsPanel = new ClaimsTab();
        agentPanel = new AgentTab();
        queryPanel = new QueryTab(account);
        searchPanel = new SearchTab(account);

        tabbedPane.addTab("Customer", customersPanel.createCustomersPanel(account));
        tabbedPane.addTab("Policy", policiesPanel.createPoliciesPanel(account));
        tabbedPane.addTab("Claim", claimsPanel.createClaimsPanel(account));
        tabbedPane.addTab("Agent", agentPanel.createAgentTab(account));
        tabbedPane.addTab("Query",queryPanel.createQueryTab());
        tabbedPane.addTab("Search",searchPanel.createSearchTab());
        add(tabbedPane);
    }


    private void centerFrameOnScreen() {
        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Calculate the center position
        int centerX = (screenSize.width - getWidth()) / 2;
        int centerY = (screenSize.height - getHeight()) / 2;

        // Set the location
        setLocation(centerX, centerY);
    }


}
