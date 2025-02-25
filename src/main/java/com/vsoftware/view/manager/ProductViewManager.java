package com.vsoftware.view.manager;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.vsoftware.controller.ProductController;
import com.vsoftware.domain.Product;
import com.vsoftware.view.WindowManager;

public class ProductViewManager {
	private final ProductController controller;
    private final WindowManager windowManager;
    private final List<ProductOperationListener> listeners = new ArrayList<>();

    public ProductViewManager(ProductController controller, WindowManager windowManager) {
        this.controller = controller;
        this.windowManager = windowManager;
    }

    public void addProductOperationListener(ProductOperationListener listener) {
        listeners.add(listener);
    }

    private void fireProductOperationCompleted() {
        listeners.forEach(ProductOperationListener::onProductOperationCompleted);
    }

    public void showList() {
        JInternalFrame window = windowManager.createWindow("Lista de Produtos");
        ProductListUI listUI = new ProductListUI(controller.getAllProducts());
        window.add(listUI.getScrollPane());
        window.pack();
        windowManager.showWindow(window);
    }

    public void updateExistingList(JInternalFrame listWindow) {
        JScrollPane scrollPane = (JScrollPane) listWindow.getContentPane().getComponent(0);
        JTable table = (JTable) scrollPane.getViewport().getView();
        ProductListUI listUI = new ProductListUI(controller.getAllProducts());
        table.setModel(listUI.getTableModel());
    }

    public void showCreate() {
        JInternalFrame window = windowManager.createWindow("Cadastrar Produto");
        ProductFormUI formUI = new ProductFormUI("Cadastrar", null);
        formUI.addActionListener(e -> handleFormSubmission(window, formUI));
        window.add(formUI.getFormPanel());
        window.pack();
        windowManager.showWindow(window);
    }

    public void showUpdate(int code) {
        Product product = controller.getProductByCode(code);
        JInternalFrame window = windowManager.createWindow("Editar Produto - " + code);
        ProductFormUI formUI = new ProductFormUI("Atualizar", product);
        formUI.addActionListener(e -> handleUpdateSubmission(window, formUI, product.getCode()));
        window.add(formUI.getFormPanel());
        window.pack();
        windowManager.showWindow(window);
    }

    public void showDelete(int code) {
        JInternalFrame window = windowManager.createWindow("Excluir Produto");
        int option = JOptionPane.showConfirmDialog(
                window,
                "Deseja realmente excluir o produto " + code + "?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION) {
            try {
                controller.deleteProduct(code);
                fireProductOperationCompleted();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        window,
                        ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
        window.dispose();
    }

    private void handleFormSubmission(JInternalFrame window, ProductFormUI formUI) {
        try {
            controller.createProduct(
                    formUI.getDescription(),
                    formUI.getPrice()
            );
            fireProductOperationCompleted();
            window.dispose();
        } catch (Exception ex) {
            showErrorMessage(window, ex);
        }
    }

    private void handleUpdateSubmission(JInternalFrame window, ProductFormUI formUI, int code) {
        try {
            controller.updateProduct(
                    code,
                    formUI.getDescription(),
                    formUI.getPrice()
            );
            fireProductOperationCompleted();
            window.dispose();
        } catch (Exception ex) {
            showErrorMessage(window, ex);
        }
    }

    private void showErrorMessage(JInternalFrame window, Exception ex) {
        JOptionPane.showMessageDialog(
                window,
                ex.getMessage(),
                "Erro na Operação",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
