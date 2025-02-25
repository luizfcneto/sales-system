package com.vsoftware;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.vsoftware.config.DatabaseConnection;
import com.vsoftware.view.MainScreen;

public class App {
	public static void main(String[] args) {
        if (testDatabaseConnection()) {
            startGUI();
        } else {
            showError("Falha na conexão com o banco de dados!");
        }
    }

    private static void startGUI() {
        SwingUtilities.invokeLater(() -> {
            MainScreen mainScreen = new MainScreen();
            mainScreen.setLocationRelativeTo(null);
            mainScreen.setVisible(true);
        });
    }

    private static boolean testDatabaseConnection() {
        return DatabaseConnection.getInstance().test();
    }

    private static void showError(String message) {
        JOptionPane.showMessageDialog(
            null,
            message,
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
        System.exit(1);
    }
}
