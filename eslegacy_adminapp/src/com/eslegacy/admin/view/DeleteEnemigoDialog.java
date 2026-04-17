package com.eslegacy.admin.view;

import javax.swing.*;
import java.awt.*;

import com.eslegacy.admin.service.ApiClient;
import com.eslegacy.admin.util.DialogUtils;
import com.eslegacy.admin.util.UIStyle;

public class DeleteEnemigoDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField idField;
    private Runnable onSuccess;

    public DeleteEnemigoDialog(JFrame parent, Runnable onSuccess) {
        super(parent, "Eliminar Enemigo", true);

        this.onSuccess = onSuccess;

        setSize(300, 180);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        add(createForm(), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private JPanel createForm() {

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(UIStyle.DARK_BG);

        JLabel label = new JLabel("ID del Enemigo:");
        label.setForeground(UIStyle.GOLD_BG);

        idField = new JTextField();

        panel.add(label);
        panel.add(idField);

        return panel;
    }

    private JPanel createButtons() {

        JPanel panel = new JPanel();
        
        panel.setBackground(UIStyle.DARK_BG);

        JButton eliminar = new JButton("Eliminar");
        JButton cancelar = new JButton("Cancelar");

        eliminar.setBackground(UIStyle.GOLD_BG);
        eliminar.setForeground(UIStyle.TEXT_DARK);
        cancelar.setBackground(UIStyle.GOLD_BG);
        cancelar.setForeground(UIStyle.TEXT_DARK);

        eliminar.addActionListener(e -> eliminarEnemigo());
        cancelar.addActionListener(e -> dispose());

        panel.add(eliminar);
        panel.add(cancelar);

        return panel;
    }

    private void eliminarEnemigo() {

        int id;

        try {
            id = Integer.parseInt(idField.getText().trim());
        } catch (NumberFormatException e) {
            DialogUtils.showError(this, "ID inválido");
            return;
        }

        boolean confirm = DialogUtils.confirm(
                this,
                "¿Seguro que quieres eliminar el enemigo con ID " + id + "?",
                "Confirmar eliminación"
        );

        if (!confirm) return;

        boolean ok = ApiClient.eliminarEnemigo(id);

        if (ok) {
            DialogUtils.showInfo(this, "Enemigo eliminado correctamente");

            if (onSuccess != null) onSuccess.run();

            dispose();
        } else {
            DialogUtils.showError(this, "No existe ese enemigo.");
        }
    }
}