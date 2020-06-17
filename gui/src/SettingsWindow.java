import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow {
  private JPanel rootPanel;
  private JTabbedPane tabbedPane1;
  private JTextField textField2;
  private JButton startButton;
  private JButton cancelButton;
  private JFormattedTextField a0FormattedTextField;
  private JFormattedTextField formattedTextField1;
  private JFormattedTextField formattedTextField2;
  private JFormattedTextField formattedTextField3;
  private JFormattedTextField formattedTextField4;


  public SettingsWindow() {
    cancelButton.addActionListener(new ExitAction());
  }

  public void setStartButtonListener(ActionListener listener) {
    this.startButton.addActionListener(listener);
  }

  public JPanel getRootPanel() {
    return rootPanel;
  }

  public void setVisible(boolean flag) {
    rootPanel.setVisible(flag);
  }


}
