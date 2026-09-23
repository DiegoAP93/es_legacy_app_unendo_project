package com.eslegacy.admin.view;

import javax.swing.*;
import java.awt.*;
import com.eslegacy.admin.model.Enemigo;
import com.eslegacy.admin.service.ApiClient;
import com.eslegacy.admin.util.DialogUtils;
import com.eslegacy.admin.util.UIStyle;

public class SelectEnemigoDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField idField;
    private Runnable onSuccess;

    public SelectEnemigoDialog(JFrame parent, Runnable onSuccess) {
        super(parent, "Seleccionar Enemigo", true);

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

        JButton continuar = new JButton("Continuar");
        JButton cancelar = new JButton("Cancelar");
        
        continuar.setBackground(UIStyle.GOLD_BG);
        continuar.setForeground(UIStyle.TEXT_DARK);
        cancelar.setBackground(UIStyle.DANGER_RED);
        cancelar.setForeground(Color.WHITE);

        continuar.addActionListener(e -> buscarEnemigo());
        cancelar.addActionListener(e -> dispose());

        panel.add(continuar);
        panel.add(cancelar);

        return panel;
    }

    private void buscarEnemigo() {

        int id;

        try {
            id = Integer.parseInt(idField.getText().trim());
        } catch (NumberFormatException e) {
            DialogUtils.showError(this, "ID inválido");
            return;
        }

        Enemigo enemigo = ApiClient.getEnemigoById(id);

        if (enemigo == null) {
            DialogUtils.showError(this, "No existe ese enemigo");
            return;
        }

        new EditEnemigoDialog(
                (JFrame) getParent(),
                enemigo,
                onSuccess
        ).setVisible(true);

        dispose();
    }
}