package com.eslegacy.admin.view;

import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;
import com.eslegacy.admin.model.Personaje;
import com.eslegacy.admin.service.ApiClient;
import com.eslegacy.admin.util.DialogUtils;
import com.eslegacy.admin.util.UIStyle;

public class SelectPersonajeDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField idField;
	private Consumer<Personaje> onSelect;

    public SelectPersonajeDialog(JFrame parent, Consumer<Personaje> onSelect) {
        super(parent, "Seleccionar Objeto", true);
        this.onSelect = onSelect;

        setSize(300, 150);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        add(createForm(), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);

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

        continuar.addActionListener(e -> cargarPersonaje());
        cancelar.addActionListener(e -> dispose());

        panel.add(continuar);
        panel.add(cancelar);

        return panel;
    }
    
    private JPanel createForm() {

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(UIStyle.DARK_BG);

        JLabel label = new JLabel("ID Personaje:");
        label.setForeground(UIStyle.GOLD_BG);

        idField = new JTextField();

        panel.add(label);
        panel.add(idField);

        return panel;
    }

	private void cargarPersonaje() {
        int id;

        try {
            id = Integer.parseInt(idField.getText());
        } catch (NumberFormatException ex) {
            DialogUtils.showError(this, "ID inválido");
            return;
        }

        Personaje p = ApiClient.getPersonajeById(id);

        if (p != null) {
            onSelect.accept(p);
            dispose();
        } else {
            DialogUtils.showError(this, "El personaje no existe");
        }
	}
}
