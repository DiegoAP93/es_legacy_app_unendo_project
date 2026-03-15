package com.eslegacy.admin.view;

import javax.swing.*;

import com.eslegacy.admin.util.DialogUtils;
import com.eslegacy.admin.util.UIStyle;

import java.awt.*;

public class AdminDashboard extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JPanel crudPanel;
	private CardLayout cardLayout;

    public AdminDashboard(String username) {

        setTitle("E&S Legacy - Panel de Administración");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);
        add(createContent(username), BorderLayout.CENTER);
    }

    private JPanel createHeader() {

        JPanel header = new JPanel();
        header.setPreferredSize(new Dimension(1000, 60));
        header.setBackground(UIStyle.LIGHT_GRAY);

        return header;
    }

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(180, 600));
        sidebar.setBackground(UIStyle.DARK_BG);
        sidebar.setLayout(new BorderLayout());

        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(4, 1));
        menu.setBackground(UIStyle.DARK_BG);

        JButton personajesBtn = createMenuButton("PERSONAJES");
        JButton objetosBtn = createMenuButton("OBJETOS");
        JButton enemigosBtn = createMenuButton("ENEMIGOS");
        JButton usuariosBtn = createMenuButton("USUARIOS");
        personajesBtn.addActionListener(e -> mostrarVista("PERSONAJES"));
        objetosBtn.addActionListener(e -> mostrarVista("OBJETOS"));
        enemigosBtn.addActionListener(e -> mostrarVista("ENEMIGOS"));
        usuariosBtn.addActionListener(e -> mostrarVista("USUARIOS"));
        menu.add(personajesBtn);
        menu.add(objetosBtn);
        menu.add(enemigosBtn);
        menu.add(usuariosBtn);

        JButton salir = new JButton("SALIR");
        salir.setBackground(UIStyle.GOLD_BG);
        salir.setForeground(UIStyle.TEXT_DARK);
        salir.addActionListener(e -> cerrarSesion());
        
        JPanel bottom = new JPanel();
        bottom.setBackground(UIStyle.DARK_BG);
        bottom.add(salir);

        sidebar.add(menu, BorderLayout.NORTH);
        sidebar.add(bottom, BorderLayout.SOUTH);

        return sidebar;
    }

    private void cerrarSesion() {

        if(DialogUtils.confirm(this,
                "¿Seguro que quieres cerrar sesión?",
                "Cerrar sesión")) {

            dispose();

            LoginView login = new LoginView();
            login.setVisible(true);
        }
    }

	private JButton createMenuButton(String text) {

        JButton btn = new JButton(text);
        btn.setBackground(UIStyle.GOLD_BG);
        btn.setFocusPainted(false);

        return btn;
    }

    private JPanel createContent(String username) {

        JPanel mainPanel = new JPanel(new BorderLayout());

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        JPanel welcomePanel = new JPanel(new GridBagLayout());

        JLabel welcome = new JLabel("BIENVENID@, " + username);

        welcomePanel.add(welcome);

        contentPanel.add(welcomePanel, "WELCOME");

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(createCrudPanel(), BorderLayout.EAST);

        return mainPanel;
    }
    
    private JPanel createCrudPanel() {
    	// TODO estilos del panel y botones.
        crudPanel = new JPanel();
        crudPanel.setPreferredSize(new Dimension(200, 600));
        crudPanel.setLayout(new GridLayout(6,1,10,10));

        crudPanel.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));

        crudPanel.add(new JButton("Crear"));
        crudPanel.add(new JButton("Editar"));
        crudPanel.add(new JButton("Eliminar"));
        crudPanel.add(new JButton("Ver Detalle"));

        return crudPanel;
    }
    
    private void mostrarVista(String vista) {

        JPanel panel = new JPanel();
        panel.add(new JLabel("Vista: " + vista));

        contentPanel.add(panel, vista);
        cardLayout.show(contentPanel, vista);
    }
}