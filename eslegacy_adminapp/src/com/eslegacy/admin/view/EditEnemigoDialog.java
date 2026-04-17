package com.eslegacy.admin.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import com.eslegacy.admin.model.Enemigo;
import com.eslegacy.admin.model.Objeto;
import com.eslegacy.admin.service.ApiClient;
import com.eslegacy.admin.util.DialogUtils;
import com.eslegacy.admin.util.UIStyle;

public class EditEnemigoDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nombreField;
    private JTextArea descripcionArea;
    private JTextArea habilidadesArea;
    private JList<Objeto> objetosList;

    private Enemigo enemigo;
    private Runnable onSuccess;

    public EditEnemigoDialog(JFrame parent, Enemigo enemigo, Runnable onSuccess) {
        super(parent, "Editar Enemigo", true);

        this.enemigo = enemigo;
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

        nombreField = new JTextField(enemigo.getNombre());
        descripcionArea = new JTextArea(enemigo.getDescripcion());
        habilidadesArea = new JTextArea(enemigo.getEjemplosHabilidades());

        Objeto[] todos = ApiClient.getObjetos();

        objetosList = new JList<>(todos);
        objetosList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        preselectObjects(todos);

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

        panel.add(createLabel("Objetos (Ctrl + click):"));
        panel.add(new JScrollPane(objetosList));

        return panel;
    }

    private void preselectObjects(Objeto[] todos) {

        List<Integer> indices = new ArrayList<>();

        for (int i = 0; i < todos.length; i++) {
            for (Objeto obj : enemigo.getObjetos()) {
                if (obj.getIdObjeto() == todos[i].getIdObjeto()) {
                    indices.add(i);
                }
            }
        }

        int[] array = indices.stream().mapToInt(i -> i).toArray();
        objetosList.setSelectedIndices(array);
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
        
        guardar.addActionListener(e -> guardarCambios());
        cancelar.addActionListener(e -> dispose());

        panel.add(guardar);
        panel.add(cancelar);

        return panel;
    }

    private void guardarCambios() {

        String nombre = nombreField.getText().trim();
        String descripcion = descripcionArea.getText().trim();
        String habilidades = habilidadesArea.getText().trim();

        if (nombre.isEmpty()) {
            DialogUtils.showError(this, "El nombre es obligatorio");
            return;
        }

        boolean ok = ApiClient.editarEnemigo(
                enemigo.getIdEnemigo(),
                nombre,
                descripcion,
                habilidades
        );

        if (!ok) {
            DialogUtils.showError(this, "Error al actualizar enemigo");
            return;
        }

        List<Objeto> seleccionados = objetosList.getSelectedValuesList();
        List<Objeto> actuales = enemigo.getObjetos();

        for (Objeto obj : seleccionados) {

            boolean yaExiste = false;

            for (Objeto actual : actuales) {
                if (actual.getIdObjeto() == obj.getIdObjeto()) {
                    yaExiste = true;
                    break;
                }
            }

            if (!yaExiste) {
                ApiClient.asignarObjetoAEnemigo(
                        enemigo.getIdEnemigo(),
                        obj.getIdObjeto()
                );
            }
        }

        DialogUtils.showInfo(this, "Enemigo actualizado correctamente");

        if (onSuccess != null) onSuccess.run();

        dispose();
    }
}