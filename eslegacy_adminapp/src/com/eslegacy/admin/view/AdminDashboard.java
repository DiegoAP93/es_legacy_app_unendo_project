package com.eslegacy.admin.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.eslegacy.admin.model.Personaje;
import com.eslegacy.admin.model.Objeto;
import com.eslegacy.admin.model.Enemigo;
import com.eslegacy.admin.model.Jugador;
import com.eslegacy.admin.service.ApiClient;
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
	    btn.setForeground(UIStyle.TEXT_DARK);
	    btn.setFocusPainted(false);
	    btn.setBorder(BorderFactory.createCompoundBorder(
	    	    BorderFactory.createLineBorder(UIStyle.DARK_BG),
	    	    BorderFactory.createEmptyBorder(15, 20, 15, 20)
	    	));
	    btn.setHorizontalAlignment(SwingConstants.LEFT);
	    
	    return btn;
    }
	
	private JButton createActionButton(String text) {

	    JButton btn = new JButton(text);
	    btn.setBackground(UIStyle.GOLD_BG);
	    btn.setForeground(UIStyle.TEXT_DARK);
	    btn.setFocusPainted(false);
	    btn.setBorder(BorderFactory.createCompoundBorder(
	    	    BorderFactory.createLineBorder(UIStyle.DARK_BG),
	    	    BorderFactory.createEmptyBorder(15, 20, 15, 20)
	    	));
	    btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
	    
	    return btn;
	}
	
	private JButton createDangerButton(String text) {

	    JButton btn = new JButton(text);
	    btn.setBackground(UIStyle.DANGER_RED);
	    btn.setForeground(Color.WHITE);
	    btn.setFocusPainted(false);
	    btn.setBorder(BorderFactory.createCompoundBorder(
	    	    BorderFactory.createLineBorder(UIStyle.DARK_BG),
	    	    BorderFactory.createEmptyBorder(15, 20, 15, 20)
	    	));
	    btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
	    
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
        crudPanel = new JPanel();
        crudPanel.setVisible(false);
        crudPanel.setPreferredSize(new Dimension(200, 600));
        crudPanel.setLayout(new BoxLayout(crudPanel, BoxLayout.Y_AXIS));
        
        crudPanel.setBackground(Color.WHITE);
        crudPanel.setBorder(BorderFactory.createCompoundBorder(
        	    BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY),
        	    BorderFactory.createEmptyBorder(20, 10, 20, 10)
        	));

        crudPanel.add(createActionButton("Crear"));
        crudPanel.add(Box.createVerticalStrut(10));

        crudPanel.add(createActionButton("Editar"));
        crudPanel.add(Box.createVerticalStrut(10));

        crudPanel.add(createDangerButton("Eliminar"));
        crudPanel.add(Box.createVerticalStrut(10));

        crudPanel.add(createActionButton("Ver Detalle"));

        return crudPanel;
    }
    
    private void mostrarVista(String vista) {
        for (Component comp : contentPanel.getComponents()) {
            if (vista.equals(comp.getName())) {
                cardLayout.show(contentPanel, vista);
                return;
            }
        }

        JPanel panel;

        switch (vista) {
            case "PERSONAJES":
                panel = createPersonajesView();
                break;
            case "OBJETOS":
            	panel = createObjetosView();
            	break;
            case "ENEMIGOS":
            	panel = createEnemigosView();
            	break;
            case "USUARIOS":
            	panel = createJugadoresView();
            	break;

            default:
                panel = new JPanel();
                panel.add(new JLabel("Vista: " + vista));
                break;
        }

        panel.setName(vista);

        contentPanel.add(panel, vista);
        cardLayout.show(contentPanel, vista);

        configurarCrudPanel(vista);
        crudPanel.setVisible(true);
    }
    
    private JPanel createPersonajesView() {

        JPanel panel = new JPanel(new BorderLayout());

        String[] columnas = {"ID", "Nombre", "Rareza", "Clase"};

        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Personaje[] personajes = ApiClient.getPersonajes();

        for (Personaje p : personajes) {
            model.addRow(new Object[]{
                p.getIdPersonaje(),
                p.getNombre(),
                p.getRareza(),
                p.getClase()
            });
        }
        
        JTable table = new JTable(model);
        
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(table);

        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }
    
    private JPanel createObjetosView() {

        JPanel panel = new JPanel(new BorderLayout());

        String[] columnas = {"ID", "Nombre", "Categoría", "Precio"};

        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Objeto[] objetos = ApiClient.getObjetos();

        for (Objeto o : objetos) {
            model.addRow(new Object[]{
                o.getIdObjeto(),
                o.getNombre(),
                o.getCategoria(),
                o.getPrecio()
            });
        }
        
        JTable table = new JTable(model);
        
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(table);

        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }
    
    private JPanel createEnemigosView() {

        JPanel panel = new JPanel(new BorderLayout());

        String[] columnas = {"ID", "Nombre", "Ejemplos de habilidades"};

        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Enemigo[] enemigos = ApiClient.getEnemigos();

        for (Enemigo e : enemigos) {
            model.addRow(new Object[]{
                e.getIdEnemigo(),
                e.getNombre(),
                e.getEjemplosHabilidades()
            });
        }
        
        JTable table = new JTable(model);
        
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(table);

        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }
    
    private JPanel createJugadoresView() {

        JPanel panel = new JPanel(new BorderLayout());

        String[] columnas = {"Username", "Nombre Completo", "Correo", "Activo"};

        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Jugador[] jugadores = ApiClient.getJugadores();

        for (Jugador j : jugadores) {
            model.addRow(new Object[]{
                j.getNombreUsuario(),
                j.getNombreCompleto(),
                j.getCorreo(),
                j.isActivo() ? "Activo" : "Inactivo"
            });
        }
        
        JTable table = new JTable(model);
        
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(table);

        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }
    
    private void configurarCrudPanel(String vista) {

        crudPanel.removeAll();

        switch (vista) {
            case "PERSONAJES":
                crudPanel.add(createActionButton("Añadir Personaje"));
                crudPanel.add(createActionButton("Editar Personaje"));
                crudPanel.add(createDangerButton("Eliminar Personaje"));
                crudPanel.add(createActionButton("Filtrar"));
                break;

            case "OBJETOS":
                crudPanel.add(createActionButton("Añadir Objeto"));
                crudPanel.add(createActionButton("Editar Objeto"));
                crudPanel.add(createDangerButton("Eliminar Objeto"));
                crudPanel.add(createActionButton("Filtrar"));
                break;

            case "ENEMIGOS":
                crudPanel.add(createActionButton("Añadir Enemigo"));
                crudPanel.add(createActionButton("Editar Enemigo"));
                crudPanel.add(createDangerButton("Eliminar Enemigo"));
                crudPanel.add(createActionButton("Filtrar"));
                break;

            case "USUARIOS":
                crudPanel.add(createActionButton("Añadir Usuario"));
                crudPanel.add(createActionButton("Editar Usuario"));
                crudPanel.add(createDangerButton("Bloquear Usuario"));
                crudPanel.add(createDangerButton("Elimina Usuario"));
                break;
        }

        crudPanel.revalidate();
        crudPanel.repaint();
    }
}