package com.eslegacy.admin.view;

import java.awt.*;
import javax.swing.*;
import com.eslegacy.admin.model.Objeto;
import com.eslegacy.admin.service.ApiClient;
import com.eslegacy.admin.util.DialogUtils;
import com.eslegacy.admin.util.UIStyle;

public class SelectObjetoDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField idField;
    private JFrame parent;
    private Runnable onSuccess;

    public SelectObjetoDialog(JFrame parent, Runnable onSuccess) {
        super(parent, "Seleccionar Objeto", true);

        this.parent = parent;
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
        
        JButton continuar = new JButton("Continuar");
        JButton cancelar = new JButton("Cancelar");
        
        continuar.setBackground(UIStyle.GOLD_BG);
        continuar.setForeground(UIStyle.TEXT_DARK);
        cancelar.setBackground(UIStyle.GOLD_BG);
        cancelar.setForeground(UIStyle.TEXT_DARK);

        continuar.addActionListener(e -> cargarObjeto());
        cancelar.addActionListener(e -> dispose());

        panel.add(continuar);
        panel.add(cancelar);

        return panel;
    }

    private void cargarObjeto() {

        int id;

        try {
            id = Integer.parseInt(idField.getText());
        } catch (NumberFormatException e) {
            DialogUtils.showError(this, "ID incorrecto");
            return;
        }

        Objeto obj = ApiClient.getObjetoById(id);

        if (obj == null) {
            DialogUtils.showError(this, "Objeto no encontrado");
            return;
        }

        new EditObjetoDialog(parent, obj, onSuccess).setVisible(true);
        dispose();
    }
}