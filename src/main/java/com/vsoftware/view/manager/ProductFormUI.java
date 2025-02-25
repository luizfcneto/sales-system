package com.vsoftware.view.manager;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.vsoftware.domain.Product;

public class ProductFormUI {
    private final JPanel panel;
    private final JTextField txtDescription;
    private final JTextField txtPrice;
    private final JButton btnAction;

    public ProductFormUI(String actionButtonLabel, Product existingProduct) {
        panel = new JPanel(new GridLayout(3, 2, 5, 5));

        txtDescription = new JTextField(20);
        txtPrice = new JTextField(10);
        btnAction = new JButton(actionButtonLabel);

        if (existingProduct != null) {
            populateFields(existingProduct);
        }

        panel.add(new JLabel("Descrição:"));
        panel.add(txtDescription);
        panel.add(new JLabel("Preço (R$):"));
        panel.add(txtPrice);
        panel.add(new JLabel(""));
        panel.add(btnAction);
    }

    private void populateFields(Product product) {
        txtDescription.setText(product.getDescription());
        txtPrice.setText(String.valueOf(product.getPrice()));
    }

    public void addActionListener(ActionListener listener) {
        btnAction.addActionListener(listener);
    }

    public JPanel getFormPanel() {
        return panel;
    }

    public String getDescription() {
        return txtDescription.getText().trim();
    }

    public String getPrice() {
        return txtPrice.getText().trim();
    }

}