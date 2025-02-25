package com.vsoftware.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultDesktopManager;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.vsoftware.controller.ControllerFactory;
import com.vsoftware.utils.WindowUtils;
import com.vsoftware.view.manager.ClientOperationListener;
import com.vsoftware.view.manager.ClientViewManager;
import com.vsoftware.view.manager.ProductOperationListener;
import com.vsoftware.view.manager.ProductViewManager;

public class MainScreen extends JFrame implements ClientOperationListener, ProductOperationListener {
	
	private static final long serialVersionUID = 1L;
	private final JDesktopPane desktopPane = new JDesktopPane();
    private final WindowManager windowManager;
    private final ClientViewManager clientViewManager;
    private final ProductViewManager productViewManager;

    public MainScreen() {
        configureMainFrame();
        this.windowManager = new WindowManager(desktopPane);
        this.clientViewManager = createClientViewManager();
        this.productViewManager = createProductViewManager();
        setupMenuBar();
        setupDesktopPane();
    }
 
    private void configureMainFrame() {
        setTitle("Sistema de Gerenciamento de Vendas - VR Software");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);        
        setLocationRelativeTo(null);
        setContentPane(desktopPane);
    }

    private ClientViewManager createClientViewManager() {
        ClientViewManager manager = new ClientViewManager(
            ControllerFactory.createClientController(),
            windowManager
        );
        manager.addClientOperationListener(this);
        return manager;
    }
    
    private ProductViewManager createProductViewManager() {
    	ProductViewManager manager = new ProductViewManager(
			ControllerFactory.createProductController(), 
			windowManager
		);
    	manager.addProductOperationListener(this);
    	return manager;
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createClientMenu());
        menuBar.add(createProductMenu());
        setJMenuBar(menuBar);
    }

    private JMenu createClientMenu() {
        JMenu menu = new JMenu("Clientes");
        menu.add(createMenuItem("Listar", this::showClientList));
        menu.add(createMenuItem("Cadastrar", e -> clientViewManager.showCreate()));
        menu.addSeparator();
        menu.add(createMenuItem("Atualizar", this::handleUpdateClient));
        menu.add(createMenuItem("Excluir", this::handleDeleteClient));
        return menu;
    }
    
    private JMenu createProductMenu() {
        JMenu menu = new JMenu("Produtos");
        menu.add(createMenuItem("Listar", this::showProductList)); 
        menu.add(createMenuItem("Cadastrar", e -> productViewManager.showCreate()));
        menu.addSeparator();
        menu.add(createMenuItem("Atualizar", this::handleUpdateProduct));
        menu.add(createMenuItem("Excluir", this::handleDeleteProduct));
        return menu;
    }

    private JMenuItem createMenuItem(String text, ActionListener listener) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(listener);
        return item;
    }

    private void setupDesktopPane() {
        desktopPane.setDesktopManager(new DefaultDesktopManager() {
            private static final long serialVersionUID = 1L;

			@Override
            public void activateFrame(JInternalFrame f) {
                WindowUtils.centerWindow(f, desktopPane);
            }
        });
    }

    private void showClientList(ActionEvent e) {
        JInternalFrame existingWindow = windowManager.findWindowByTitle("Lista de Clientes");
        if (existingWindow == null) {
            clientViewManager.showList();
        } else {
            existingWindow.toFront();
        }
    }

    private void handleUpdateClient(ActionEvent e) {
        JInternalFrame listWindow = windowManager.findWindowByTitle("Lista de Clientes");
        if (listWindow == null || !listWindow.isVisible()) {
            showErrorMessage("Abra a lista de clientes");
            return;
        }

        int clientCode = getSelectedClientCode(listWindow);
        if (clientCode == -1) {
            showErrorMessage("Nenhum cliente selecionado");
            return;
        }

        clientViewManager.showUpdate(clientCode);
    }

    private void handleDeleteClient(ActionEvent e) {
        JInternalFrame listWindow = windowManager.findWindowByTitle("Lista de Clientes");
        if (listWindow == null || !listWindow.isVisible()) {
            showErrorMessage("Abra a lista de clientes");
            return;
        }

        int clientCode = getSelectedClientCode(listWindow);
        if (clientCode == -1) {
            showErrorMessage("Nenhum cliente selecionado");
            return;
        }

        clientViewManager.showDelete(clientCode);
    }

    private int getSelectedClientCode(JInternalFrame listWindow) {
        try {
            JScrollPane scrollPane = (JScrollPane) listWindow.getContentPane().getComponent(0);
            JTable table = (JTable) scrollPane.getViewport().getView();
            int selectedRow = table.getSelectedRow();
            return selectedRow != -1 ? (Integer) table.getValueAt(selectedRow, 0) : -1;
        } catch (Exception e) {
            showErrorMessage("Erro ao obter cliente selecionado");
            return -1;
        }
    }
    
    private void showProductList(ActionEvent e) {
    	JInternalFrame existingWindow = windowManager.findWindowByTitle("Lista de Produtos");
        if (existingWindow == null) {
            productViewManager.showList();
        } else {
            existingWindow.toFront();
        }
    }

    private void handleUpdateProduct(ActionEvent e) {
        JInternalFrame listWindow = windowManager.findWindowByTitle("Lista de Produtos");
        if (listWindow == null || !listWindow.isVisible()) {
            showErrorMessage("Abra a lista de produtos");
            return;
        }

        int productCode = getSelectedProductCode(listWindow);
        if (productCode == -1) {
            showErrorMessage("Nenhum produto selecionado");
            return;
        }

        productViewManager.showUpdate(productCode);
    }

    private void handleDeleteProduct(ActionEvent e) {
        JInternalFrame listWindow = windowManager.findWindowByTitle("Lista de Produtos");
        if (listWindow == null || !listWindow.isVisible()) {
            showErrorMessage("Abra a lista de produtos");
            return;
        }

        int productCode = getSelectedProductCode(listWindow);
        if (productCode == -1) {
            showErrorMessage("Nenhum produto selecionado");
            return;
        }

        productViewManager.showDelete(productCode);
    }
    
    private int getSelectedProductCode(JInternalFrame listWindow) {
        try {
            JScrollPane scrollPane = (JScrollPane) listWindow.getContentPane().getComponent(0);
            JTable table = (JTable) scrollPane.getViewport().getView();
            int selectedRow = table.getSelectedRow();
            return selectedRow != -1 ? (Integer) table.getValueAt(selectedRow, 0) : -1;
        } catch (Exception e) {
            showErrorMessage("Erro ao obter produto selecionado");
            return -1;
        }
    }

    @Override
    public void onClientOperationCompleted() {
        refreshClientList();
    }

    private void refreshClientList() {
        JInternalFrame listWindow = windowManager.findWindowByTitle("Lista de Clientes");
        if (listWindow != null) {
            clientViewManager.updateExistingList(listWindow);
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
    }

    @Override
    public void onProductOperationCompleted() {
        refreshProductList();
    }

    private void refreshProductList() {
        JInternalFrame listWindow = windowManager.findWindowByTitle("Lista de Produtos");
        if (listWindow != null) {
            productViewManager.updateExistingList(listWindow);
        }
    }

}
