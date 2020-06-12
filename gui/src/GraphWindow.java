import javax.swing.*;
import java.awt.event.ActionListener;

public class GraphWindow {
  private JComboBox comboBox1;
  private JButton saveToFileButton;
  private JPanel rootPanel;
  private JButton exitButton;
  private JButton reRunButton;

  /**
   * Constructor.
   */
  public GraphWindow(){
    exitButton.addActionListener(new ExitAction());
  }

  public JPanel getRootPanel() {
    return rootPanel;
  }

  public void setReRunButtonListener(ActionListener listener) {
    this.reRunButton.addActionListener(listener);
  }

  public void setVisible(boolean flag) {
    rootPanel.setVisible(flag);
  }
}
