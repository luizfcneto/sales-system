package com.vsoftware.view;

import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import com.vsoftware.utils.WindowUtils;

public class WindowManager {
	public static final Dimension FORM_SIZE = new Dimension(400, 300);
    public static final Dimension LIST_SIZE = new Dimension(600, 400);
    
    private final JDesktopPane desktopPane;
    
    public WindowManager(JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;
    }

    public JInternalFrame createWindow(String title) {
        JInternalFrame window = new JInternalFrame(
            title,
            true,  // resizable
            true,  // closable
            true,  // maximizable
            true   // iconifiable
        );
        
        configureWindow(window);
        return window;
    }

    private void configureWindow(JInternalFrame window) {
        desktopPane.add(window);
        WindowUtils.centerWindow(window, desktopPane);
    }

    public void showWindow(JInternalFrame window) {
        try {
            window.setVisible(true);
            WindowUtils.centerWindow(window, desktopPane);
            window.toFront();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public JInternalFrame findWindowByTitle(String title) {
        return Arrays.stream(desktopPane.getAllFrames())
            .filter(frame -> frame.getTitle().equals(title))
            .findFirst()
            .orElse(null);
    }

    public void closeAllWindows() {
        Arrays.stream(desktopPane.getAllFrames()).forEach(JInternalFrame::dispose);
    }

    public void tileWindows() {
        JInternalFrame[] frames = desktopPane.getAllFrames();
        if (frames.length == 0) return;

        int cols = (int) Math.sqrt(frames.length);
        int rows = (int) Math.ceil((double) frames.length / cols);
        
        int width = desktopPane.getWidth() / cols;
        int height = desktopPane.getHeight() / rows;
        
        int x = 0;
        int y = 0;
        
        for (JInternalFrame frame : frames) {
            frame.setSize(width, height);
            frame.setLocation(x, y);
            
            x += width;
            if (x + width > desktopPane.getWidth()) {
                x = 0;
                y += height;
            }
        }
    }
}
