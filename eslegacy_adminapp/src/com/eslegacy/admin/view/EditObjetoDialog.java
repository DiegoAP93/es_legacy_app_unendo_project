package com.eslegacy.admin.view;

import java.awt.*;
import javax.swing.*;
import com.eslegacy.admin.model.Objeto;
import com.eslegacy.admin.service.ApiClient;
import com.eslegacy.admin.util.DialogUtils;
import com.eslegacy.admin.util.UIStyle;

public class EditObjetoDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nombreField;
    private JTextArea descripcionArea;
    private JComboBox<String> categoriaCombo;
    private JTextField precioField;

    private Objeto objeto;
    private Runnable onSuccess;

    public EditObjetoDialog(JFrame parent, Objeto objeto, Runnable onSuccess) {
        super(parent, "Editar Objeto", true);

        this.objeto = objeto;
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

        nombreField = new JTextField(objeto.getNombre());
        nombreField.setEditable(false);

        descripcionArea = new JTextArea(objeto.getDescripcion());

        categoriaCombo = new JComboBox<>(new String[]{
                "Arma", "Armadura", "Material", "Accesorio",
                "Consumible", "Importante", "Desarrollo de PJ"
        });

        categoriaCombo.setSelectedItem(mapCategoriaUI(objeto.getCategoria()));

        precioField = new JTextField(
                objeto.getPrecio() != null ? objeto.getPrecio().toString() : ""
        );

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
        cancelar.setBackground(UIStyle.DANGER_RED);
        cancelar.setForeground(Color.WHITE);
        
        guardar.addActionListener(e -> guardarCambios());
        cancelar.addActionListener(e -> dispose());

        panel.add(guardar);
        panel.add(cancelar);

        return panel;
    }

    private void guardarCambios() {
    	String nombre = objeto.getNombre();
    	String descripcion = descripcionArea.getText().isEmpty()
    	        ? objeto.getDescripcion()
    	        : descripcionArea.getText();
        String categoria = mapCategoria((String) categoriaCombo.getSelectedItem());

        Integer precio = null;
        try {
            if (!precioField.getText().isEmpty()) {
                precio = Integer.parseInt(precioField.getText());
            }
        } catch (NumberFormatException e) {
            DialogUtils.showError(this, "Precio inválido");
            return;
        }

        boolean ok = ApiClient.editarObjeto(
                objeto.getIdObjeto(),
                nombre,
                descripcion,
                categoria,
                precio
        );

        if (ok) {
            DialogUtils.showInfo(this, "Objeto actualizado");

            if (onSuccess != null) onSuccess.run();

            dispose();
        } else {
            DialogUtils.showError(this, "Error al actualizar");
        }
    }

    private String mapCategoria(String cat) {
        return cat;
    }

    private String mapCategoriaUI(String cat) {
        if (cat == null || cat.isEmpty()) return "Material";
        return cat.substring(0,1).toUpperCase() + cat.substring(1);
    }
}
