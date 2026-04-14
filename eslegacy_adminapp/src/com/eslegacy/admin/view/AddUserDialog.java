package com.eslegacy.admin.view;

import javax.swing.*;

import com.eslegacy.admin.service.ApiClient;
import com.eslegacy.admin.util.DialogUtils;
import com.eslegacy.admin.util.FontManager;
import com.eslegacy.admin.util.UIStyle;

import java.awt.*;

public class AddUserDialog extends JDialog {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField nombreField;
    private JTextField correoField;
    private JComboBox<String> rolCombo;
    private Runnable onUserCreated;

    public AddUserDialog(JFrame parent, Runnable onUserCreated) {
        super(parent, "Añadir Usuario", true);
        this.onUserCreated = onUserCreated;

        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        add(createForm(), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private JPanel createForm() {

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(UIStyle.DARK_BG);

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        nombreField = new JTextField();
        correoField = new JTextField();
        rolCombo = new JComboBox<>(new String[]{"ADMIN", "USER"});

        panel.add(createLabel("Usuario:"));
        panel.add(usernameField);

        panel.add(createLabel("Contraseña:"));
        panel.add(passwordField);

        panel.add(createLabel("Nombre completo:"));
        panel.add(nombreField);

        panel.add(createLabel("Correo:"));
        panel.add(correoField);

        panel.add(createLabel("Rol:"));
        panel.add(rolCombo);

        return panel;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(UIStyle.GOLD_BG);
        label.setFont(FontManager.normal());
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

        guardar.addActionListener(e -> guardarUsuario());
        cancelar.addActionListener(e -> dispose());

        panel.add(guardar);
        panel.add(cancelar);

        return panel;
    }

    private void guardarUsuario() {

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String nombre = nombreField.getText();
        String correo = correoField.getText();
        String rol = (String) rolCombo.getSelectedItem();

        boolean creado = ApiClient.crearJugador(
                username, password, nombre, correo, rol
        );

        if (creado) {
            DialogUtils.showInfo(this, "Usuario creado correctamente");
            if (onUserCreated != null) {
                onUserCreated.run();
            }
            dispose();
        } else {
        	DialogUtils.showError(this, "Error al crear usuario");
        }

        dispose();
    }
}