package com.eslegacy.admin.view;

import java.awt.*;
import javax.swing.*;
import com.eslegacy.admin.model.Personaje;
import com.eslegacy.admin.service.ApiClient;
import com.eslegacy.admin.util.DialogUtils;
import com.eslegacy.admin.util.UIStyle;

public class EditPersonajeDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Personaje personaje;
    private Runnable onSuccess;

    private JTextField nombreField;
    private JComboBox<String> claseCombo;
    private JComboBox<String> rarezaCombo;
    private JTextArea historiaArea;
    private JTextField ataqueField;
    private JTextField vidaField;
    private JTextField iniciativaField;
    private JTextField origenField;
    private JTextField arquetipoField;

    public EditPersonajeDialog(JFrame parent, Personaje personaje, Runnable onSuccess) {
        super(parent, "Editar Personaje", true);

        this.personaje = personaje;
        this.onSuccess = onSuccess;

        setLayout(new BorderLayout());

	    getContentPane().setBackground(UIStyle.DARK_BG);
	
	    JScrollPane scroll = new JScrollPane(createForm());
	    scroll.setBorder(null);
	
	    scroll.getViewport().setBackground(UIStyle.DARK_BG);
	    scroll.setBackground(UIStyle.DARK_BG);
	
	    add(scroll, BorderLayout.CENTER);
	    add(createButtons(), BorderLayout.SOUTH);
	
	    pack();
	    setMinimumSize(new Dimension(550, 650));
	    setLocationRelativeTo(parent);
    }
    
    private JPanel createForm() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        panel.setBackground(UIStyle.DARK_BG);

        nombreField = new JTextField(personaje.getNombre());
        nombreField.setEditable(false);

        claseCombo = new JComboBox<>(new String[]{
            "Atacante", "Balanceado", "Healer", "Especialista", "Tanque", "Único"
        });
        claseCombo.setSelectedItem(personaje.getClase());

        rarezaCombo = new JComboBox<>(new String[]{"SSR","SR","R"});
        rarezaCombo.setSelectedItem(personaje.getRareza());

        historiaArea = new JTextArea(personaje.getHistoria());
        historiaArea.setLineWrap(true);
        historiaArea.setWrapStyleWord(true);

        JScrollPane historiaScroll = new JScrollPane(historiaArea);
	    historiaScroll.setPreferredSize(new Dimension(400, 150));
	    historiaScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
	    historiaScroll.setMinimumSize(new Dimension(400, 150));

        ataqueField = new JTextField(personaje.getAtaqueBasico());
        vidaField = new JTextField(
            personaje.getPuntosVida() != null ? personaje.getPuntosVida().toString() : ""
        );
        iniciativaField = new JTextField(personaje.getIniciativa());
        origenField = new JTextField(personaje.getOrigen());
        arquetipoField = new JTextField(personaje.getArquetipo());

        panel.add(createField("Nombre:", nombreField));
        panel.add(createField("Clase:", claseCombo));
        panel.add(createField("Rareza:", rarezaCombo));
        panel.add(createField("Historia:", historiaScroll));
        panel.add(createField("Ataque:", ataqueField));
        panel.add(createField("Vida:", vidaField));
        panel.add(createField("Iniciativa:", iniciativaField));
        panel.add(createField("Origen:", origenField));
        panel.add(createField("Arquetipo:", arquetipoField));
        
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setOpaque(false);

        return panel;
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

        guardar.addActionListener(e -> guardar());
        cancelar.addActionListener(e -> dispose());

        panel.add(guardar);
        panel.add(cancelar);

        return panel;
    }
    
    private JPanel createField(String labelText, Component field) {

        JPanel block = new JPanel();
        block.setLayout(new BoxLayout(block, BoxLayout.Y_AXIS));
        block.setAlignmentX(Component.LEFT_ALIGNMENT);
        block.setOpaque(false);

        JLabel label = createLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        ((JComponent) field).setAlignmentX(Component.LEFT_ALIGNMENT);

        Dimension pref = field.getPreferredSize();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, pref.height));

        block.add(label);
        block.add(Box.createVerticalStrut(5));
        block.add(field);
        block.add(Box.createVerticalStrut(15));

        return block;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(UIStyle.GOLD_BG);
        return label;
    }
    
    
    private void guardar() {

        Integer vida = null;

        try {
            if (!vidaField.getText().isEmpty()) {
                vida = Integer.parseInt(vidaField.getText());
            }
        } catch (NumberFormatException e) {
            DialogUtils.showError(this, "Vida inválida");
            return;
        }

        boolean ok = ApiClient.editarPersonaje(
                personaje.getIdPersonaje(),
                (String) claseCombo.getSelectedItem(),
                (String) rarezaCombo.getSelectedItem(),
                historiaArea.getText(),
                ataqueField.getText(),
                vida,
                iniciativaField.getText(),
                origenField.getText(),
                arquetipoField.getText()
        );

        if (ok) {
            DialogUtils.showInfo(this, "Personaje actualizado");
            if (onSuccess != null) onSuccess.run();
            dispose();
        } else {
            DialogUtils.showError(this, "Error al actualizar");
        }
    }
}