package com.eslegacy.admin.util;

import javax.swing.*;
import java.awt.*;

public class DialogUtils {

    public static void showInfo(Component parent, String message) {

        applyStyle();

        JOptionPane.showMessageDialog(
                parent,
                createStyledLabel(message),
                "Información",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void showError(Component parent, String message) {

        applyStyle();

        JOptionPane.showMessageDialog(
                parent,
                createStyledLabel(message),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void showWarning(Component parent, String message) {

        applyStyle();

        JOptionPane.showMessageDialog(
                parent,
                createStyledLabel(message),
                "Aviso",
                JOptionPane.WARNING_MESSAGE
        );
    }

    public static boolean confirm(Component parent, String message, String title) {

        applyStyle();

        int result = JOptionPane.showConfirmDialog(
                parent,
                createStyledLabel(message),
                title,
                JOptionPane.YES_NO_OPTION
        );

        return result == JOptionPane.YES_OPTION;
    }

    private static JLabel createStyledLabel(String text) {

        JLabel label = new JLabel(text);
        label.setForeground(UIStyle.TEXT_GOLD);

        return label;
    }

    private static void applyStyle() {

        UIManager.put("OptionPane.background", UIStyle.DARK_BG);
        UIManager.put("Panel.background", UIStyle.DARK_BG);

        UIManager.put("OptionPane.messageForeground", UIStyle.TEXT_GOLD);

        UIManager.put("Button.background", UIStyle.GOLD_BG);
        UIManager.put("Button.foreground", UIStyle.TEXT_DARK);
    }
}