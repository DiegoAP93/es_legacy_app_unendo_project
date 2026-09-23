package com.eslegacy.admin.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.eslegacy.admin.model.Objeto;
import com.eslegacy.admin.util.UIStyle;

public class AddEnemigoDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nombreField;
    private JTextArea descripcionArea;
    private JTextArea habilidadesArea;
    private JList<Objeto> objetosList;

    private Runnable onSuccess;

    public AddEnemigoDialog(JFrame parent, Runnable onSuccess) {
        super(parent, "Añadir Enemigo", true);

        this.onSuccess = onSuccess;

        setSize(450, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        add(createForm(), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);
    }
    
    private JPanel createForm() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(UIStyle.DARK_BG);

        nombreField = new JTextField();
        descripcionArea = new JTextArea(3, 20);
        habilidadesArea = new JTextArea(3, 20);

        Objeto[] objetos = com.eslegacy.admin.service.ApiClient.getObjetos();

        objetosList = new JList<>(objetos);
        objetosList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        objetosList.setVisibleRowCount(5);

        panel.add(createLabel("Nombre:"));
        panel.add(nombreField);

        panel.add(Box.createVerticalStrut(10));

        panel.add(createLabel("Descripción:"));
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);

        JScrollPane descScroll = new JScrollPane(descripcionArea);
        descScroll.setPreferredSize(new Dimension(400, 80));

        panel.add(descScroll);

        panel.add(Box.createVerticalStrut(10));

        panel.add(createLabel("Ejemplos de habilidades:"));
        panel.add(new JScrollPane(habilidadesArea));

        panel.add(Box.createVerticalStrut(10));

        panel.add(createLabel("Objetos que suelta (Ctrl + click para múltiples):"));
        panel.add(new JScrollPane(objetosList));

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


        guardar.addActionListener(e -> guardarEnemigo());
        cancelar.addActionListener(e -> dispose());

        panel.add(guardar);
        panel.add(cancelar);

        return panel;
    }
    
    private void guardarEnemigo() {

        String nombre = nombreField.getText().trim();
        String descripcion = descripcionArea.getText().trim();
        String habilidades = habilidadesArea.getText().trim();

        if (nombre.isEmpty()) {
            com.eslegacy.admin.util.DialogUtils.showError(this, "El nombre es obligatorio");
            return;
        }

        com.eslegacy.admin.model.Enemigo enemigo =
                com.eslegacy.admin.service.ApiClient.crearEnemigo(
                        nombre,
                        descripcion,
                        habilidades
                );

        if (enemigo == null) {
            com.eslegacy.admin.util.DialogUtils.showError(this, "Error al crear enemigo");
            return;
        }

        int idEnemigo = enemigo.getIdEnemigo();

        List<Objeto> seleccionados = objetosList.getSelectedValuesList();

        for (Objeto obj : seleccionados) {
            com.eslegacy.admin.service.ApiClient.asignarObjetoAEnemigo(
                    idEnemigo,
                    obj.getIdObjeto()
            );
        }

        com.eslegacy.admin.util.DialogUtils.showInfo(this, "Enemigo creado correctamente");

        if (onSuccess != null) onSuccess.run();

        dispose();
    }
}
