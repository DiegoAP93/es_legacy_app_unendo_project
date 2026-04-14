package com.eslegacy.admin.view;

import javax.swing.*;
import com.eslegacy.admin.service.ApiClient;
import com.eslegacy.admin.util.DialogUtils;
import com.eslegacy.admin.util.UIStyle;
import java.awt.*;

public class AddObjetoDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nombreField;
    private JTextArea descripcionArea;
    private JComboBox<String> categoriaCombo;
    private JTextField precioField;

    private Runnable onSuccess;

    public AddObjetoDialog(JFrame parent, Runnable onSuccess) {
        super(parent, "Añadir Objeto", true);

        this.onSuccess = onSuccess;

        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        add(createForm(), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private JPanel createForm() {

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(UIStyle.DARK_BG);

        nombreField = new JTextField();
        descripcionArea = new JTextArea(3, 20);
        categoriaCombo = new JComboBox<>(new String[]{
        	    "Arma",
        	    "Armadura",
        	    "Material",
        	    "Accesorio",
        	    "Consumible",
        	    "Importante",
        	    "Desarrollo de PJ"
        	});
        precioField = new JTextField();

        panel.add(createLabel("Nombre:"));
        panel.add(nombreField);

        panel.add(createLabel("Descripción:"));
        panel.add(new JScrollPane(descripcionArea));

        panel.add(createLabel("Categoría:"));
        panel.add(categoriaCombo);

        panel.add(createLabel("Precio:"));
        panel.add(precioField);

        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(UIStyle.GOLD_BG);
        return label;
    }

    private JPanel createButtons() {

        JPanel panel = new JPanel();
        
        panel.setBackground(UIStyle.DARK_BG);

        JButton guardar = new JButton("Guardar");
        JButton cancelar = new JButton("Cancelar");
        
        guardar.setBackground(UIStyle.GOLD_BG);
        guardar.setForeground(UIStyle.TEXT_DARK);
        cancelar.setBackground(UIStyle.GOLD_BG);
        cancelar.setForeground(UIStyle.TEXT_DARK);

        guardar.addActionListener(e -> guardarObjeto());
        cancelar.addActionListener(e -> dispose());

        panel.add(guardar);
        panel.add(cancelar);

        return panel;
    }

    private void guardarObjeto() {

        String nombre = nombreField.getText();
        String descripcion = descripcionArea.getText();
        String categoria = (String) categoriaCombo.getSelectedItem();

        Integer precio = null;

        try {
            if (!precioField.getText().isEmpty()) {
                precio = Integer.parseInt(precioField.getText());
            }
        } catch (NumberFormatException e) {
            DialogUtils.showError(this, "Precio inválido");
            return;
        }

        boolean creado = ApiClient.crearObjeto(nombre, descripcion, categoria, precio);

        if (creado) {
        	DialogUtils.showInfo(this, "Objeto creado correctamente.");

            if (onSuccess != null) {
                onSuccess.run();
            }

            dispose();
        } else {
            DialogUtils.showError(this, "Error al crear objeto");
        }
    }
}