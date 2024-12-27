package start;

import presentation.MainView;

public class Start {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainView mainView = new MainView();
                mainView.setVisible(true);
            }
        });
    }
}