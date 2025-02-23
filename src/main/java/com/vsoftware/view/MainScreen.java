package com.vsoftware.view;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.vsoftware.controller.ClientController;
import com.vsoftware.controller.ControllerFactory;
import com.vsoftware.view.manager.ClientOperationListener;
import com.vsoftware.view.manager.ClientViewManager;

	public class MainScreen extends JFrame implements ClientOperationListener {

	    private static final long serialVersionUID = 1L;
	    private JDesktopPane desktopPane;
	    private WindowManager windowManager;
	    private ClientViewManager clientViewManager;

	    public MainScreen() {
	        setTitle("Sistema de Gerenciamento de Vendas");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(800, 600);

	        JMenuBar menuBar = new JMenuBar();
	        setJMenuBar(menuBar);

	        JMenu menuClientes = new JMenu("Clientes");
	        menuBar.add(menuClientes);

	        desktopPane = new JDesktopPane();
	        setContentPane(desktopPane);

	        windowManager = new WindowManager(desktopPane);

	        ClientController clientController = ControllerFactory.criarClientController();
	        clientViewManager = new ClientViewManager(clientController, windowManager);

	        clientViewManager.addClientOperationListener(this);

	        JMenuItem menuItemListarClientes = new JMenuItem("Listar Clientes");
	        menuClientes.add(menuItemListarClientes);
	        menuItemListarClientes.addActionListener(e -> clientViewManager.showList());

	        JMenuItem menuItemCadastrarCliente = new JMenuItem("Cadastrar Cliente");
	        menuClientes.add(menuItemCadastrarCliente);
	        menuItemCadastrarCliente.addActionListener(e -> clientViewManager.showCreate());

	        JMenuItem menuItemAtualizarCliente = new JMenuItem("Atualizar Cliente");
	        menuClientes.add(menuItemAtualizarCliente);
	        menuItemAtualizarCliente.addActionListener(e -> {
	            JInternalFrame janelaLista = obterJanelaListaClientes();

	            if (janelaLista != null) {
	                JScrollPane scrollPane = (JScrollPane) janelaLista.getContentPane().getComponent(0);
	                if (scrollPane != null) {
	                    JTable tabela = (JTable) scrollPane.getViewport().getView();
	                    if (tabela != null) {
	                        int linhaSelecionada = tabela.getSelectedRow();

	                        if (linhaSelecionada != -1) {
	                            int codigo = (int) tabela.getValueAt(linhaSelecionada, 0);
	                            clientViewManager.showUpdate(codigo);
	                        } else {
	                            mostrarMensagem("Nenhum cliente selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
	                        }
	                    } else {
	                        mostrarMensagem("Tabela não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
	                    }
	                } else {
	                    mostrarMensagem("Scrollpane não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
	                }
	            } else {
	                mostrarMensagem("Janela de listagem não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
	            }
	        });

	        JMenuItem menuItemExcluirCliente = new JMenuItem("Excluir Cliente");
	        menuClientes.add(menuItemExcluirCliente);
	        menuItemExcluirCliente.addActionListener(e -> {
	            JInternalFrame janelaLista = obterJanelaListaClientes();

	            if (janelaLista != null) {
	                JScrollPane scrollPane = (JScrollPane) janelaLista.getContentPane().getComponent(0);
	                if (scrollPane != null) {
	                    JTable tabela = (JTable) scrollPane.getViewport().getView();
	                    if (tabela != null) {
	                        int linhaSelecionada = tabela.getSelectedRow();

	                        if (linhaSelecionada != -1) {
	                            int codigo = (int) tabela.getValueAt(linhaSelecionada, 0);
	                            clientViewManager.showDelete(codigo);
	                        } else {
	                            mostrarMensagem("Nenhum cliente selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
	                        }
	                    } else {
	                        mostrarMensagem("Tabela não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
	                    }
	                } else {
	                    mostrarMensagem("Scrollpane não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
	                }
	            } else {
	                mostrarMensagem("Janela de listagem não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
	            }
	        });

	        setVisible(true);
	    }

	    private JInternalFrame obterJanelaListaClientes() {
	        for (JInternalFrame janela : desktopPane.getAllFrames()) {
	            if (janela.getTitle().equals("Lista de Clientes")) {
	                return janela;
	            }
	        }
	        return null;
	    }

	    private void mostrarMensagem(String mensagem, String titulo, int tipo) {
	        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
	    }

	    @Override
	    public void onClientOperationCompleted() {
	        JOptionPane.showMessageDialog(this, "Operacao concluida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

	        JInternalFrame janelaLista = obterJanelaListaClientes();
	        if (janelaLista != null) {
	            JScrollPane scrollPane = (JScrollPane) janelaLista.getContentPane().getComponent(0);
	            if (scrollPane != null) {
	                JTable tabela = (JTable) scrollPane.getViewport().getView();
	                if (tabela != null) {
	                    clientViewManager.updateTable(tabela);
	                }
	            }
	        }
	    }

}
