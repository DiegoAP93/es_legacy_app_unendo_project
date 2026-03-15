package com.eslegacy.admin.view;

import javax.swing.*;
import java.awt.*;
import com.eslegacy.admin.service.ApiClient;
import com.eslegacy.admin.util.DialogUtils;
import com.eslegacy.admin.util.UIStyle;
import com.eslegacy.admin.model.LoginResponse;

public class LoginView extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginView() {

        setTitle("E&S Legacy - Admin");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout()); // para centrar panel

        // Panel central oscuro
        JPanel panelLogin = new JPanel();
        panelLogin.setPreferredSize(new Dimension(300, 300));
        panelLogin.setBackground(UIStyle.DARK_BG);
        panelLogin.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("INICIO DE SESIÓN", SwingConstants.CENTER);
        lblTitulo.setForeground(UIStyle.TEXT_GOLD);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelLogin.add(lblTitulo, gbc);

        // Usuario
        JLabel lblUsuario = new JLabel("USUARIO");
        lblUsuario.setForeground(UIStyle.TEXT_GOLD);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelLogin.add(lblUsuario, gbc);

        txtUsuario = new JTextField();

        gbc.gridy = 2;
        panelLogin.add(txtUsuario, gbc);

        // Password
        JLabel lblPassword = new JLabel("CONTRASEÑA");
        lblPassword.setForeground(UIStyle.TEXT_GOLD);

        gbc.gridy = 3;
        panelLogin.add(lblPassword, gbc);

        txtPassword = new JPasswordField();

        gbc.gridy = 4;
        panelLogin.add(txtPassword, gbc);

        // Botón
        btnLogin = new JButton("ENTRAR");
        btnLogin.setBackground(UIStyle.GOLD_BG);
        btnLogin.setForeground(UIStyle.TEXT_DARK);

        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panelLogin.add(btnLogin, gbc);
        
        btnLogin.addActionListener(e -> hacerLogin());

        add(panelLogin);
    }

    private void hacerLogin() {

        String username = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());

        LoginResponse response = ApiClient.login(username, password);

        if (response == null) {
            DialogUtils.showError(this, "Error de conexión con el servidor.");
            return;
        }

        if(!"ADMIN".equals(response.getRol())){

            DialogUtils.showWarning(this,
                    "No tienes permisos de administrador");

            return;
        }

        dispose();

        AdminDashboard dashboard =
                new AdminDashboard(response.getUsername());

        dashboard.setVisible(true);

    }

	public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }
}