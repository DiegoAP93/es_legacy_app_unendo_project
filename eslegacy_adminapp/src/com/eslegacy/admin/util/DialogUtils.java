package com.eslegacy.admin.util;

import javax.swing.*;
import java.awt.*;

public class DialogUtils {

	public static void showInfo(Component parent, String message) {

	    JOptionPane pane = new JOptionPane(
	            message,
	            JOptionPane.INFORMATION_MESSAGE
	    );

	    JDialog dialog = createStyledDialog(parent, pane, "Información");
	    dialog.setVisible(true);
	}

	public static void showError(Component parent, String message) {

	    JOptionPane pane = new JOptionPane(
	            message,
	            JOptionPane.ERROR_MESSAGE
	    );

	    JDialog dialog = createStyledDialog(parent, pane, "Error");
	    dialog.setVisible(true);
	}

	public static void showWarning(Component parent, String message) {

	    JOptionPane pane = new JOptionPane(
	            message,
	            JOptionPane.WARNING_MESSAGE
	    );

	    JDialog dialog = createStyledDialog(parent, pane, "Aviso");
	    dialog.setVisible(true);
	}

    public static boolean confirm(Component parent, String message, String title) {

        JOptionPane pane = new JOptionPane(
                message,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION
        );

        JDialog dialog = createStyledDialog(parent, pane, title);
        dialog.setVisible(true);

        Object result = pane.getValue();

        return result != null && (int) result == JOptionPane.YES_OPTION;
    }
    
    private static JDialog createStyledDialog(Component parent, JOptionPane pane, String title) {

        JDialog dialog = pane.createDialog(parent, title);

        dialog.getContentPane().setBackground(UIStyle.DARK_BG);

        dialog.getRootPane().setBackground(UIStyle.DARK_BG);

        pane.setBackground(UIStyle.DARK_BG);
        pane.setOpaque(true);

        ((JComponent) dialog.getContentPane()).setOpaque(true);

        styleComponents(pane);

        return dialog;
    }
    
    private static void styleComponents(Component comp) {

        if (comp instanceof JPanel) {
            comp.setBackground(UIStyle.DARK_BG);
        }

        if (comp instanceof JLabel) {
            comp.setForeground(UIStyle.TEXT_GOLD);
        }

        if (comp instanceof JButton btn) {
            btn.setBackground(UIStyle.GOLD_BG);
            btn.setForeground(UIStyle.TEXT_DARK);
            btn.setFocusPainted(false);
        }

        if (comp instanceof Container container) {
            for (Component c : container.getComponents()) {
                styleComponents(c);
            }
        }
    }
}