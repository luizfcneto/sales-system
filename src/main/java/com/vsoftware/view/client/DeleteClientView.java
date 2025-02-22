package com.vsoftware.view.client;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.vsoftware.controller.ClientController;

public class DeleteClientView extends JDialog {

    private static final long serialVersionUID = 1L;
	private ClientController controller;
    private int clientCode;

    public DeleteClientView(JFrame parent) {
        super(parent, "Remover Cliente", true);
        setSize(400, 100);
        setLayout(new FlowLayout());

        JLabel messageLabel = new JLabel("Tem certeza que deseja remover este Cliente?");
        add(messageLabel);

        JButton confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.deleteClient(clientCode);
                if (controller != null) {
                    controller.updateClientList();
                }
                dispose();
            }
        });
        add(confirmButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });
        add(cancelButton);

        setLocationRelativeTo(parent);
    }

    public void setController(ClientController controller) {
        this.controller = controller;
    }

    public void setClientCode(int clientCode) {
        this.clientCode = clientCode;
    }
}
