package com.eslegacy.admin.view;

import javax.swing.*;
import com.eslegacy.admin.service.ApiClient;
import com.eslegacy.admin.util.DialogUtils;
import com.eslegacy.admin.util.UIStyle;
import java.awt.*;

public class BlockDeleteUserDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
    private Runnable onSuccess;
    private String action;

    public BlockDeleteUserDialog(JFrame parent, String action, Runnable onSuccess) {
        super(parent, action, true);

        this.onSuccess = onSuccess;
        this.action = action;

        setSize(350, 180);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        add(createForm(), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private JPanel createForm() {

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(UIStyle.DARK_BG);

        JLabel label = new JLabel("Username:");
        label.setForeground(UIStyle.GOLD_BG);

        usernameField = new JTextField();

        panel.add(label);
        panel.add(usernameField);

        return panel;
    }

    private JPanel createButtons() {

        JPanel panel = new JPanel();
        
        panel.setBackground(UIStyle.DARK_BG);

        JButton ejecutar = new JButton(action);
        JButton cancelar = new JButton("Cancelar");
        
        ejecutar.setBackground(UIStyle.GOLD_BG);
        ejecutar.setForeground(UIStyle.TEXT_DARK);
        cancelar.setBackground(UIStyle.GOLD_BG);
        cancelar.setForeground(UIStyle.TEXT_DARK);

        ejecutar.addActionListener(e -> ejecutarAccion());
        cancelar.addActionListener(e -> dispose());

        panel.add(ejecutar);
        panel.add(cancelar);

        return panel;
    }

    private void ejecutarAccion() {

        String username = usernameField.getText();

        boolean success = false;

        if (action.contains("Bloquear")) {
            success = ApiClient.bloquearUsuario(username);
        } else if (action.contains("Eliminar")) {
            success = ApiClient.eliminarUsuario(username);
        }

        if (success) {
            DialogUtils.showInfo(this, action.toLowerCase() + " correcto.");

            if (onSuccess != null) {
                onSuccess.run();
            }

            dispose();
        } else {
        	DialogUtils.showError(this, "Error al " + action.toLowerCase()+".");
        }
    }
}