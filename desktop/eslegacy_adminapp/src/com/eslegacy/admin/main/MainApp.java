package com.eslegacy.admin.main;

import javax.swing.UIManager;
import com.eslegacy.admin.util.FontManager;
import com.eslegacy.admin.view.LoginView;

public class MainApp {

    public static void main(String[] args) {

        LoginView login = new LoginView();
        login.setVisible(true);

        UIManager.put("Button.font", FontManager.button());
        UIManager.put("Label.font", FontManager.normal());
        UIManager.put("Table.font", FontManager.table());
        UIManager.put("TableHeader.font", FontManager.subtitle());
        UIManager.put("TextField.font", FontManager.normal());
        UIManager.put("PasswordField.font", FontManager.normal());
        UIManager.put("OptionPane.messageFont", FontManager.normal());
    }
}