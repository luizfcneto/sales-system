package com.vsoftware.utils;

import java.awt.Dimension;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class WindowUtils {
	public static void centerWindow(JInternalFrame frame, JDesktopPane desktopPane) {
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = frame.getSize();

        if (frameSize.width == 0 || frameSize.height == 0) {
            frame.pack();
            frameSize = frame.getSize();
        }

        int x = (desktopSize.width - frameSize.width) / 2;
        int y = (desktopSize.height - frameSize.height) / 2;

        frame.setLocation(x, y);
        frame.toFront();

        try {
            frame.setSelected(true);
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }
}
