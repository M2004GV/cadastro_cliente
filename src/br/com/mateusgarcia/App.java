package br.com.mateusgarcia;

import java.awt.*;
import javax.swing.*;
import br.com.mateusgarcia.dao.ClienteMapDAO;
import br.com.mateusgarcia.dao.IClienteDAO;
import br.com.mateusgarcia.domain.Cliente;

public class App {

    private static IClienteDAO iClienteDAO;

    public static void main(String args[]) {
        iClienteDAO = new ClienteMapDAO();

        JFrame frame = new JFrame("Cadastro de Clientes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JButton cadastrarButton = new JButton("Cadastrar Cliente");
        JButton consultarButton = new JButton("Consultar Cliente");
        JButton excluirButton = new JButton("Excluir Cliente");
        JButton alterarButton = new JButton("Alterar Cliente");
        JButton listarButton = new JButton("Listar Clientes");
        JButton sairButton = new JButton("Sair");

        cadastrarButton.addActionListener(e -> cadastrar());
        consultarButton.addActionListener(e -> consultar());
        excluirButton.addActionListener(e -> excluir());
        alterarButton.addActionListener(e -> alterar());
        listarButton.addActionListener(e -> listarClientes());
        sairButton.addActionListener(e -> sair());

        // Personalizando botões com um layout centralizado
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(cadastrarButton, gbc);

        gbc.gridy = 1;
        frame.add(consultarButton, gbc);

        gbc.gridy = 2;
        frame.add(excluirButton, gbc);

        gbc.gridy = 3;
        frame.add(alterarButton, gbc);

        gbc.gridy = 4;
        frame.add(listarButton, gbc);

        gbc.gridy = 5;
        frame.add(sairButton, gbc);

        frame.setVisible(true);
    }

    private static void cadastrar() {
        JPanel panel = new JPanel(new GridLayout(0, 2));

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
                Cliente cliente = new Cliente(
                    dados[0],
                    dados[1],
                    dados[2],
                    dados[3],
                    dados[4],
                    dados[5],
                    dados[6]
                );
                iClienteDAO.cadastrar(cliente);
                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Dados inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void consultar() {
        String cpfStr = JOptionPane.showInputDialog(null, "Digite o CPF do cliente:", "Consultar", JOptionPane.INFORMATION_MESSAGE);

        if (cpfStr != null && !cpfStr.trim().isEmpty()) {
            try {
                long cpf = Long.parseLong(cpfStr.trim());
                Cliente cliente = iClienteDAO.consultar(cpf);
                if (cliente != null) {
                    JOptionPane.showMessageDialog(null, "Cliente encontrado:\n" + cliente.toString(), "Consulta", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Cliente não encontrado.", "Consulta", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CPF inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
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
            Cliente cliente = new Cliente(
                campos[0],
                campos[1],
                campos[2],
                campos[3],
                campos[4],
                campos[5],
                campos[6]
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
            try {
                long cpf = Long.parseLong(cpfStr.trim());
                boolean sucesso = iClienteDAO.excluir(cpf);
                if (sucesso) {
                    JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CPF inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "CPF não pode ser vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void listarClientes() {
        String[] columnNames = {"Nome", "CPF", "Telefone", "Endereço", "Número", "Cidade", "Estado"};
        Object[][] data = iClienteDAO.listar();

        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
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
            Long.parseLong(dados[1].trim());
            Long.parseLong(dados[2].trim());
            Integer.parseInt(dados[4].trim());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
