import javax.swing.*;

public class GraphWindow {
  private JComboBox comboBox1;
  private JButton saveToFileButton;
  private JPanel rootPanel;
  private JButton exitButton;

  public JPanel getRootPanel() {
    return rootPanel;
  }

  public void setVisible(boolean flag) {
    rootPanel.setVisible(flag);
  }
}
