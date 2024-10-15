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
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Adicionando uma imagem de fundo
        JLabel background = new JLabel(new ImageIcon("path/to/background.jpg"));
        background.setLayout(new GridBagLayout());

        // Usando GridBagLayout para melhor posicionamento
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento

        JButton cadastrarButton = new JButton("Cadastrar Cliente", new ImageIcon("path/to/cadastrar-icon.png"));
        JButton consultarButton = new JButton("Consultar Cliente", new ImageIcon("path/to/consultar-icon.png"));
        JButton excluirButton = new JButton("Excluir Cliente", new ImageIcon("path/to/excluir-icon.png"));
        JButton alterarButton = new JButton("Alterar Cliente", new ImageIcon("path/to/alterar-icon.png"));
        JButton listarButton = new JButton("Listar Clientes", new ImageIcon("path/to/listar-icon.png"));
        JButton sairButton = new JButton("Sair", new ImageIcon("path/to/sair-icon.png"));

        // Ações dos botões
        cadastrarButton.addActionListener(e -> cadastrar());
        consultarButton.addActionListener(e -> consultar());
        excluirButton.addActionListener(e -> excluir());
        alterarButton.addActionListener(e -> alterar());
        listarButton.addActionListener(e -> listarClientes());
        sairButton.addActionListener(e -> sair());

        // Adicionando botões ao painel com GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(cadastrarButton, gbc);
        gbc.gridy++;
        panel.add(consultarButton, gbc);
        gbc.gridy++;
        panel.add(excluirButton, gbc);
        gbc.gridy++;
        panel.add(alterarButton, gbc);
        gbc.gridy++;
        panel.add(listarButton, gbc);
        gbc.gridy++;
        panel.add(sairButton, gbc);

        // Adicionando o painel ao JLabel de fundo
        background.add(panel);

        frame.setContentPane(background);
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
