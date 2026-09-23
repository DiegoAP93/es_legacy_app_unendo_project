package com.eslegacy.admin.view;

import javax.swing.*;
import com.eslegacy.admin.model.Habilidad;
import com.eslegacy.admin.util.DialogUtils;
import com.eslegacy.admin.util.UIStyle;
import java.awt.*;
import java.util.function.Consumer;

public class AddHabilidadDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nombreField;
    private JTextArea descripcionArea;
    private JTextField cooldownField;

    public AddHabilidadDialog(JFrame parent, String tipo, Consumer<Habilidad> onSave) {
        super(parent, "Nueva Habilidad", true);

        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0,1,5,5));
        form.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        form.setBackground(UIStyle.DARK_BG);

        nombreField = new JTextField();
        descripcionArea = new JTextArea(4,20);
        cooldownField = new JTextField();

        form.add(createLabel("Nombre"));
        form.add(nombreField);

        form.add(createLabel("Descripción"));
        form.add(new JScrollPane(descripcionArea));

        if (tipo.equals("ACTIVA")) {
            form.add(createLabel("Cooldown"));
            form.add(cooldownField);
        }

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(UIStyle.DARK_BG);
        
        JButton guardar = new JButton("Guardar");
        JButton cancelar = new JButton("Cancelar");
        guardar.setBackground(UIStyle.GOLD_BG);
        guardar.setForeground(UIStyle.TEXT_DARK);
        cancelar.setBackground(UIStyle.DANGER_RED);
        cancelar.setForeground(Color.WHITE);
        
        guardar.addActionListener(e -> {

            Habilidad h = new Habilidad();
            h.setNombre(nombreField.getText());
            h.setDescripcion(descripcionArea.getText());
            h.setCategoria(tipo);

            if (!cooldownField.getText().isEmpty()) {
                h.setCooldown(Integer.parseInt(cooldownField.getText()));
            }

            onSave.accept(h);
            dispose();
        });
        
        cancelar.addActionListener(e -> dispose());

        add(form, BorderLayout.CENTER);
        bottomPanel.add(guardar, BorderLayout.EAST);
        bottomPanel.add(cancelar, BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
	    setMinimumSize(new Dimension(400, 400));
	    setLocationRelativeTo(parent);
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(UIStyle.GOLD_BG);
        return label;
    }
}