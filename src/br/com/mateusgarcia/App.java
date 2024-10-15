package br.com.mateusgarcia;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import br.com.mateusgarcia.dao.ClienteMapDAO;
import br.com.mateusgarcia.dao.IClienteDAO;
import br.com.mateusgarcia.domain.Cliente;

import java.awt.*;
import java.text.ParseException;

public class App {
    private static IClienteDAO iClienteDAO;

    public static void main(String[] args) {
        iClienteDAO = new ClienteMapDAO();

        JFrame frame = new JFrame("Cadastro de Clientes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        String[] buttonLabels = {"Cadastrar Cliente", "Consultar Cliente", "Excluir Cliente", "Alterar Cliente", "Listar Clientes", "Sair"};
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.addActionListener(e -> {
                switch (button.getText()) {
                    case "Cadastrar Cliente":
                        cadastrar();
                        break;
                    case "Consultar Cliente":
                        consultar();
                        break;
                    case "Excluir Cliente":
                        excluir();
                        break;
                    case "Alterar Cliente":
                        alterar();
                        break;
                    case "Listar Clientes":
                        listarClientes();
                        break;
                    case "Sair":
                        sair();
                        break;
                }
            });

            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 1.0;
            gbc.weighty = 0.1;
            frame.add(button, gbc);
        }

        frame.setVisible(true);
    }

    private static void cadastrar() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField nomeField = new JTextField(15);
        JTextField cpfField = new JFormattedTextField(createFormatter("###.###.###-##"));
        JTextField telefoneField = new JFormattedTextField(createFormatter("(##) #####-####"));
        JTextField enderecoField = new JTextField(15);
        JTextField numeroField = new JTextField(5);
        JTextField cidadeField = new JTextField(10);
        JTextField estadoField = new JTextField(2);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; panel.add(nomeField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1; panel.add(cpfField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1; panel.add(telefoneField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Endereço:"), gbc);
        gbc.gridx = 1; panel.add(enderecoField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("Número:"), gbc);
        gbc.gridx = 1; panel.add(numeroField, gbc);
        gbc.gridx = 0; gbc.gridy = 5; panel.add(new JLabel("Cidade:"), gbc);
        gbc.gridx = 1; panel.add(cidadeField, gbc);
        gbc.gridx = 0; gbc.gridy = 6; panel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1; panel.add(estadoField, gbc);

        int result = JOptionPane.showConfirmDialog(null, panel, "Cadastrar Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Implementar a lógica de cadastro
        }
    }

    private static MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter;
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
