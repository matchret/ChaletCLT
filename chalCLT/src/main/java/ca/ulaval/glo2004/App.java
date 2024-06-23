package ca.ulaval.glo2004;

import ca.ulaval.glo2004.gui.MainWindow;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            JFrame mainFrame = mainWindow.getMainFrame();
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(1300, 800);
            mainFrame.setVisible(true);
        });
    }
}

