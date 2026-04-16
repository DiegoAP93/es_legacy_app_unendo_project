package com.eslegacy.admin.view;

import javax.swing.*;
import java.awt.*;
import com.eslegacy.admin.model.Jugador;
import com.eslegacy.admin.service.ApiClient;
import com.eslegacy.admin.util.DialogUtils;
import com.eslegacy.admin.util.UIStyle;

public class EditUserDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
    private JTextField nombreField;
    private JTextField correoField;
    private JPasswordField passwordField;

    private Jugador jugador;
    private Runnable onSuccess;

    public EditUserDialog(JFrame parent, Jugador jugador, Runnable onSuccess) {
        super(parent, "Editar Usuario", true);

        this.jugador = jugador;
        this.onSuccess = onSuccess;

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        add(createForm(), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);
    }
    
    private JPanel createForm() {

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(UIStyle.DARK_BG);

        usernameField = new JTextField(jugador.getNombreUsuario());
        usernameField.setEditable(false);

        nombreField = new JTextField(jugador.getNombreCompleto());
        correoField = new JTextField(jugador.getCorreo());
        passwordField = new JPasswordField();

        panel.add(createLabel("Username:"));
        panel.add(usernameField);

        panel.add(createLabel("Nombre completo:"));
        panel.add(nombreField);

        panel.add(createLabel("Correo:"));
        panel.add(correoField);

        panel.add(createLabel("Nueva contraseña:"));
        panel.add(passwordField);

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
        
        guardar.addActionListener(e -> guardarCambios());
        cancelar.addActionListener(e -> dispose());

        panel.add(guardar);
        panel.add(cancelar);

        return panel;
    }
    
    private void guardarCambios() {

        String nombre = nombreField.getText().trim();
        String correo = correoField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (nombre.isEmpty() || correo.isEmpty()) {
            DialogUtils.showError(this, "Nombre y correo son obligatorios");
            return;
        }

        boolean ok = ApiClient.editarUsuario(
                jugador.getNombreUsuario(),
                nombre,
                correo,
                password.isEmpty() ? null : password
        );

        if (ok) {
            DialogUtils.showInfo(this, "Usuario actualizado");

            if (onSuccess != null) onSuccess.run();

            dispose();
        } else {
            DialogUtils.showError(this, "Error al actualizar usuario");
        }
    }
}