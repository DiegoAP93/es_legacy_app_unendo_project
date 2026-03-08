package com.eslegacy.admin.view;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;

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
        header.setBackground(Color.LIGHT_GRAY);

        return header;
    }

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(180, 600));
        sidebar.setBackground(new Color(50, 50, 50));
        sidebar.setLayout(new BorderLayout());

        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(4, 1));
        menu.setBackground(new Color(50, 50, 50));

        menu.add(createMenuButton("PERSONAJES"));
        menu.add(createMenuButton("OBJETOS"));
        menu.add(createMenuButton("ENEMIGOS"));
        menu.add(createMenuButton("USUARIOS"));

        JButton salir = new JButton("SALIR");

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(50, 50, 50));
        bottom.add(salir);

        sidebar.add(menu, BorderLayout.NORTH);
        sidebar.add(bottom, BorderLayout.SOUTH);

        return sidebar;
    }

    private JButton createMenuButton(String text) {

        JButton btn = new JButton(text);
        btn.setBackground(new Color(199, 150, 55));
        btn.setFocusPainted(false);

        return btn;
    }

    private JPanel createContent(String username) {

        contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());

        JLabel welcome = new JLabel("BIENVENID@, " + username);
        welcome.setFont(new Font("Arial", Font.BOLD, 22));

        contentPanel.add(welcome);

        return contentPanel;
    }
}