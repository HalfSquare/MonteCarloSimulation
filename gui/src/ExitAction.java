import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A Simple exit action listener for buttons that
 * exit the application.
 *
 * @author Max
 */
public class ExitAction implements ActionListener {

    /**
     * Invoked when button is clicked.
     *
     * @param e action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
}
