package com.vsoftware.view;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MainScreen extends JFrame {
	private static final long serialVersionUID = 1L;
	private JDesktopPane desktopPane;

    public MainScreen() {
        setTitle("Sistema de Gerenciamento de Vendas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuCadastro = new JMenu("Cadastro");
        menuBar.add(menuCadastro);

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        setVisible(true);
    }

}
