import javax.swing.*;
import java.awt.*;


public class AppPanel extends JFrame {
    private JTabbedPane tabbedPane;
    private PoliciesTab policiesPanel;
    private CustomerTab customersPanel;
    private ClaimsTab claimsPanel;
    private AgentTab agentPanel;
    private QueryTab queryPanel;
    private SearchTab searchPanel;

public AppPanel(){
    setTitle("Varied Insurance Management System");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    centerFrameOnScreen();


    tabbedPane = new JTabbedPane();
    policiesPanel = new PoliciesTab();
    customersPanel = new CustomerTab();
    claimsPanel = new ClaimsTab();
    agentPanel = new AgentTab();
    queryPanel = new QueryTab();
    searchPanel = new SearchTab();

    tabbedPane.addTab("Customer", customersPanel.createCustomersPanel());
    tabbedPane.addTab("Policy", policiesPanel.createPoliciesPanel());
    tabbedPane.addTab("Claim", claimsPanel.createClaimsPanel());
    tabbedPane.addTab("Agent", agentPanel.createAgentTab());
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
