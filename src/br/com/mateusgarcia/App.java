package br.com.mateusgarcia;

import javax.swing.*;
import br.com.mateusgarcia.dao.ClienteMapDAO;
import br.com.mateusgarcia.dao.IClienteDAO;
import br.com.mateusgarcia.domain.Cliente;

import java.awt.*;

public class App {

    private static IClienteDAO iClienteDAO;

    public static void main(String args[]) {
        iClienteDAO = new ClienteMapDAO();

        JFrame frame = new JFrame("Cadastro de Clientes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Ajuste o tamanho conforme necessário
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 20, 20)); // 3 linhas, 2 colunas, com espaçamento de 20 pixels
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // Margens internas

        // Usando um único ícone por botão
        OvalButton registerButton = new OvalButton("images/register.png");
        OvalButton searchButton = new OvalButton("images/search.png");
        OvalButton deleteButton = new OvalButton("images/delete.png");
        OvalButton editButton = new OvalButton("images/edit.png");
        OvalButton listButton = new OvalButton("images/list.png");
        OvalButton exitButton = new OvalButton("images/exit.png");

        // Adicionando listeners para os botões
        registerButton.addActionListener(e -> cadastrar());
        searchButton.addActionListener(e -> consultar());
        deleteButton.addActionListener(e -> excluir());
        editButton.addActionListener(e -> alterar());
        listButton.addActionListener(e -> listarClientes());
        exitButton.addActionListener(e -> sair());

        // Adicionando os botões ao painel
        panel.add(registerButton);
        panel.add(searchButton);
        panel.add(deleteButton);
        panel.add(editButton);
        panel.add(listButton);
        panel.add(exitButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void cadastrar() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 5, 5)); // Colunas com espaçamento reduzido

        JTextField nomeField = new JTextField();
        JTextField cpfField = new JTextField();
        JTextField telefoneField = new JTextField();
        JTextField enderecoField = new JTextField();
        JTextField numeroField = new JTextField();
        JTextField cidadeField = new JTextField();
        JTextField estadoField = new JTextField();

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("CPF:"));
        panel.add(cpfField);
        panel.add(new JLabel("Telefone:"));
        panel.add(telefoneField);
        panel.add(new JLabel("Endereço:"));
        panel.add(enderecoField);
        panel.add(new JLabel("Número:"));
        panel.add(numeroField);
        panel.add(new JLabel("Cidade:"));
        panel.add(cidadeField);
        panel.add(new JLabel("Estado:"));
        panel.add(estadoField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Cadastrar Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String[] dados = {
                nomeField.getText(),
                cpfField.getText(),
                telefoneField.getText(),
                enderecoField.getText(),
                numeroField.getText(),
                cidadeField.getText(),
                estadoField.getText()
            };
            if (validarDados(dados)) {
                // Remove pontos e hífen antes de criar o cliente
                String cpf = dados[1].trim().replace(".", "").replace("-", "");
                Cliente cliente = new Cliente(
                    dados[0],
                    cpf,
                    dados[2],
                    dados[3],
                    dados[4],
                    dados[5],
                    dados[6]
                );
                try {
                    iClienteDAO.adicionar(cliente);
                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Dados inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void consultar() {
        String cpfStr = JOptionPane.showInputDialog(null, "Digite o CPF do cliente:", "Consultar", JOptionPane.INFORMATION_MESSAGE);

        if (cpfStr != null && !cpfStr.trim().isEmpty()) {
            // Remove pontos e hífen para padronizar
            String cpf = cpfStr.trim().replace(".", "").replace("-", "");
            Cliente cliente = iClienteDAO.consultar(cpf);
            if (cliente != null) {
                JOptionPane.showMessageDialog(null, "Cliente encontrado:\n" + cliente.toString(), "Consulta", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não encontrado.", "Consulta", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "CPF não pode ser vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void alterar() {
        String dados = JOptionPane.showInputDialog(null,
            "Digite os dados do cliente separados por vírgula, nesta ordem: Nome, CPF, Telefone, Endereço, Número, Cidade, Estado",
            "Alteração", JOptionPane.INFORMATION_MESSAGE);

        if (dados != null && validarDados(dados.split(","))) {
            String[] campos = dados.split(",");
            // Remove pontos e hífen antes de criar o cliente
            String cpf = campos[1].trim().replace(".", "").replace("-", "");
            Cliente cliente = new Cliente(
                campos[0].trim(),
                cpf,
                campos[2].trim(),
                campos[3].trim(),
                campos[4].trim(),
                campos[5].trim(),
                campos[6].trim()
            );
            boolean sucesso = iClienteDAO.alterar(cliente);
            if (sucesso) {
                JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao alterar cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Dados inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void excluir() {
        String cpfStr = JOptionPane.showInputDialog(null, "Digite o CPF do cliente a ser excluído:", "Excluir", JOptionPane.INFORMATION_MESSAGE);

        if (cpfStr != null && !cpfStr.trim().isEmpty()) {
            // Remove pontos e hífen para padronizar
            String cpf = cpfStr.trim().replace(".", "").replace("-", "");
            boolean sucesso = iClienteDAO.excluir(cpf);
            if (sucesso) {
                JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "CPF não pode ser vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void listarClientes() {
        String[] columnNames = {"Nome", "CPF", "Telefone", "Endereço", "Número", "Cidade", "Estado"};
        Object[][] data = iClienteDAO.listar();

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, "Lista de Clientes", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void sair() {
        JOptionPane.showMessageDialog(null, "Até Logo", "Sair", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private static boolean validarDados(String[] dados) {
        if (dados.length != 7) return false;
        try {
            // Remove caracteres de formatação e verifica se são números
            Long.parseLong(dados[1].trim().replace(".", "").replace("-", ""));
            Long.parseLong(dados[2].trim().replace("(", "").replace(")", "").replace("-", ""));
            Integer.parseInt(dados[4].trim());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
