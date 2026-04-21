package com.eslegacy.admin.view;

import javax.swing.*;
import com.eslegacy.admin.model.Habilidad;
import com.eslegacy.admin.util.DialogUtils;
import com.eslegacy.admin.util.UIStyle;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AddPersonajeDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private JPanel mainPanel;
	private CardLayout cardLayout;
	private String currentCard = "FORMULARIO";
	private Map<String, Habilidad> habilidadesSeleccionadas = new HashMap<>();

	public AddPersonajeDialog(JFrame parent, Runnable onSuccess) {
	    super(parent, "Añadir Personaje", true);

	    this.onSuccess = onSuccess;

	    setLayout(new BorderLayout());

	    cardLayout = new CardLayout();
	    mainPanel = new JPanel(cardLayout);

	    mainPanel.add(createForm(), "FORMULARIO");
	    mainPanel.add(createHabilidadesPanel(), "HABILIDADES");
	    mainPanel.setBackground(UIStyle.DARK_BG);

	    JScrollPane scroll = new JScrollPane(mainPanel);
	    scroll.setBorder(null);

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

        nombreField = new JTextField();
        panel.add(createFieldBlock("Nombre:", nombreField));

        claseCombo = new JComboBox<>(new String[]{
            "Atacante", "Balanceado", "Healer", "Especialista", "Tanque", "Único"
        });
        panel.add(createFieldBlock("Clase:", claseCombo));

        rarezaCombo = new JComboBox<>(new String[]{
            "SSR", "SR", "R"
        });
        panel.add(createFieldBlock("Rareza:", rarezaCombo));

        historiaArea = new JTextArea();
        historiaArea.setLineWrap(true);
        historiaArea.setWrapStyleWord(true);

        JScrollPane historiaScroll = new JScrollPane(historiaArea);
        historiaScroll.setPreferredSize(new Dimension(400, 120));
        historiaScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        panel.add(createFieldBlock("Historia:", historiaScroll));

        ataqueField = new JTextField();
        panel.add(createFieldBlock("Ataque Básico:", ataqueField));

        vidaField = new JTextField();
        panel.add(createFieldBlock("Puntos de Vida:", vidaField));

        iniciativaField = new JTextField();
        panel.add(createFieldBlock("Iniciativa:", iniciativaField));

        origenField = new JTextField();
        panel.add(createFieldBlock("Origen:", origenField));

        arquetipoField = new JTextField();
        panel.add(createFieldBlock("Arquetipo:", arquetipoField));
        
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setOpaque(false);

        return panel;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(UIStyle.GOLD_BG);
        return label;
    }
    
    private JPanel createFieldBlock(String labelText, Component field) {

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
    
    private JPanel createButtons() {

        JPanel panel = new JPanel();
        
        panel.setBackground(UIStyle.DARK_BG);

        JButton siguiente = new JButton("Siguiente");
        JButton atras = new JButton("Atrás");
        JButton cancelar = new JButton("Cancelar");
        
        siguiente.setBackground(UIStyle.GOLD_BG);
        siguiente.setForeground(UIStyle.TEXT_DARK);
        atras.setBackground(UIStyle.GOLD_BG);
        atras.setForeground(UIStyle.TEXT_DARK);
        cancelar.setBackground(UIStyle.GOLD_BG);
        cancelar.setForeground(UIStyle.TEXT_DARK);

        siguiente.addActionListener(e -> {

            if (getCurrentCard().equals("FORMULARIO")) {
                validarYContinuar();
            } else {
                guardarPersonaje();
            }
        });

        atras.addActionListener(e -> {
            cardLayout.show(mainPanel, "FORMULARIO");
            currentCard = "FORMULARIO";
        });

        cancelar.addActionListener(e -> dispose());

        panel.add(atras);
        panel.add(siguiente);
        panel.add(cancelar);

        return panel;
    }
    
    private void validarYContinuar() {

        if (nombreField.getText().trim().isEmpty()) {
            DialogUtils.showError(this, "El nombre es obligatorio");
            return;
        }

        cardLayout.show(mainPanel, "HABILIDADES");
        currentCard = "HABILIDADES";
    }
    
    private JPanel createHabilidadesPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        panel.setBackground(UIStyle.DARK_BG);

        panel.add(createSkillRow("Activa 1", "ACTIVA"));
        panel.add(createSkillRow("Activa 2", "ACTIVA"));
        panel.add(createSkillRow("Activa 3", "ACTIVA"));

        panel.add(Box.createVerticalStrut(10));

        panel.add(createSkillRow("Pasiva 1", "PASIVA"));
        panel.add(createSkillRow("Pasiva 2", "PASIVA"));

        panel.add(Box.createVerticalStrut(10));

        panel.add(createSkillRow("Talento 1", "TALENTO"));
        panel.add(createSkillRow("Talento 2", "TALENTO"));

        panel.add(Box.createVerticalStrut(10));

        panel.add(createSkillRow("Definitiva", "DEFINITIVA"));

        return panel;
    }
    
    private JPanel createSkillRow(String label, String tipo) {

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setBackground(UIStyle.DARK_BG);

        JLabel l = new JLabel(label);
        l.setForeground(UIStyle.GOLD_BG);

        JButton crear = new JButton("Crear");
        crear.setBackground(UIStyle.GOLD_BG);
        crear.setForeground(UIStyle.TEXT_DARK);

        row.add(l);
        row.add(crear);

        crear.addActionListener(e -> {
            new AddHabilidadDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                tipo,
                habilidad -> {
                    habilidadesSeleccionadas.put(label, habilidad);
                    DialogUtils.showInfo(this, label + " guardada");
                }
            ).setVisible(true);
        });

        if (tipo.equals("PASIVA") || tipo.equals("TALENTO")) {

            JButton seleccionar = new JButton("Seleccionar");
            seleccionar.setBackground(UIStyle.GOLD_BG);
            seleccionar.setForeground(UIStyle.TEXT_DARK);
            
            // TODO
            /*seleccionar.addActionListener(e -> {
                new SelectHabilidadDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    tipo,
                    habilidad -> {
                        habilidadesSeleccionadas.put(label, habilidad);
                        DialogUtils.showInfo(this, label + " seleccionada");
                    }
                ).setVisible(true);
            });*/

            row.add(seleccionar);
        }

        return row;
    }
    
    private void guardarPersonaje() {
        DialogUtils.showInfo(this, "Aquí se guardará TODO el personaje con habilidades");
    }
    
    private String getCurrentCard() {
        return currentCard;
    }
}