package com.eslegacy.admin.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.eslegacy.admin.model.Habilidad;
import com.eslegacy.admin.service.ApiClient;
import com.eslegacy.admin.util.DialogUtils;
import com.eslegacy.admin.util.UIStyle;
import java.awt.*;
import java.util.function.Consumer;

public class SelectHabilidadDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
    private Habilidad seleccionada;

    public SelectHabilidadDialog(JFrame parent, String tipo, Consumer<Habilidad> onSelect) {
        super(parent, "Seleccionar Habilidad", true);

        setLayout(new BorderLayout());

        String[] columnas = {"ID", "Nombre", "Cooldown"};

        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Habilidad[] habilidades = ApiClient.getHabilidadesPorTipo(tipo);

        for (Habilidad h : habilidades) {
            model.addRow(new Object[]{
                h.getIdHabilidad(),
                h.getNombre(),
                h.getCooldown() != null ? h.getCooldown() : "-"
            });
        }

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        scroll.getViewport().setBackground(UIStyle.DARK_BG);
        table.setBackground(UIStyle.DARK_BG);
        table.setForeground(Color.WHITE);
        table.setGridColor(UIStyle.LIGHT_GRAY);
        table.setSelectionBackground(UIStyle.GOLD_BG);
        table.setSelectionForeground(UIStyle.TEXT_DARK);
        
        JButton seleccionarBtn = new JButton("Seleccionar");
        seleccionarBtn.setBackground(UIStyle.GOLD_BG);
        seleccionarBtn.setForeground(UIStyle.TEXT_DARK);

        seleccionarBtn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                DialogUtils.showError(this, "Selecciona una habilidad");
                return;
            }

            int id = (int) model.getValueAt(row, 0);

            for (Habilidad h : habilidades) {
                if (h.getIdHabilidad() == id) {
                    onSelect.accept(h);
                    dispose();
                    return;
                }
            }
        });
        
        add(scroll, BorderLayout.CENTER);
        add(seleccionarBtn, BorderLayout.SOUTH);
        
        this.setBackground(UIStyle.DARK_BG);
        setSize(450, 360);
        setLocationRelativeTo(parent);
    }
}
