package com.vsoftware.view;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import com.vsoftware.utils.WindowUtils;

public class WindowManager {
	private JDesktopPane desktopPane;
	
	public WindowManager(JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;
    }

    public JInternalFrame createWindow(String titulo) {
        JInternalFrame window = new JInternalFrame(titulo, true, true, true, true);
        desktopPane.add(window);
        centerWindow(window);
        return window;
    }

    private void centerWindow(JInternalFrame frame) {
        WindowUtils.centerWindow(frame, desktopPane);
    }

    public void showWindow(JInternalFrame frame) {
        WindowUtils.centerWindow(frame, desktopPane);
        frame.setVisible(true);
    }

    public void closeWindow(JInternalFrame frame) {
        frame.dispose();
    }
}
