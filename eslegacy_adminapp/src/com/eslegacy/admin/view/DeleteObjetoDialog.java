package com.eslegacy.admin.view;

import javax.swing.*;
import com.eslegacy.admin.util.DialogUtils;
import com.eslegacy.admin.service.ApiClient;
import com.eslegacy.admin.util.UIStyle;
import java.awt.*;

public class DeleteObjetoDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField idField;
    private Runnable onSuccess;

    public DeleteObjetoDialog(JFrame parent, Runnable onSuccess) {
        super(parent, "Eliminar Objeto", true);

        this.onSuccess = onSuccess;

        setSize(300, 150);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        add(createForm(), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private JPanel createForm() {

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(UIStyle.DARK_BG);

        JLabel label = new JLabel("ID Objeto:");
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
        cancelar.setBackground(UIStyle.DANGER_RED);
        cancelar.setForeground(Color.WHITE);

        eliminar.addActionListener(e -> eliminarObjeto());
        cancelar.addActionListener(e -> dispose());

        panel.add(eliminar);
        panel.add(cancelar);

        return panel;
    }

    private void eliminarObjeto() {

        int id;

        try {
            id = Integer.parseInt(idField.getText());
        } catch (NumberFormatException e) {
            DialogUtils.showError(this, "ID incorrecto");
            return;
        }

        boolean confirm = DialogUtils.confirm(
                this,
                "¿Seguro que quieres eliminar el objeto con ID " + id + "?",
                "Confirmar eliminación"
        );

        if (!confirm) return;
        
        boolean eliminado = ApiClient.eliminarObjeto(id);

        if (eliminado) {
            DialogUtils.showInfo(this, "Objeto eliminado correctamente");

            if (onSuccess != null) {
                onSuccess.run();
            }

            dispose();
        } else {
            DialogUtils.showError(this, "ID incorrecto o no existe");
        }
    }
}