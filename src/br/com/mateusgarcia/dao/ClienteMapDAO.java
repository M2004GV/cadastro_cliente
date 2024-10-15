package br.com.mateusgarcia.dao;

import br.com.mateusgarcia.domain.Cliente;

import java.util.HashMap;
import java.util.Map;

public class ClienteMapDAO implements IClienteDAO {
    private Map<String, Cliente> clientes = new HashMap<>();

    @Override
    public void adicionar(Cliente cliente) {
        if (clientes.containsKey(cliente.getCpf())) {
            throw new IllegalArgumentException("Cliente já cadastrado.");
        }
        clientes.put(cliente.getCpf(), cliente);
    }

    @Override
    public Cliente consultar(String cpf) {
        return clientes.get(cpf);
    }

    @Override
    public boolean alterar(Cliente cliente) {
        if (clientes.containsKey(cliente.getCpf())) {
            clientes.put(cliente.getCpf(), cliente);
            return true;
        }
        return false;
    }

    @Override
    public boolean excluir(String cpf) {
        return clientes.remove(cpf) != null;
    }

    @Override
    public Object[][] listar() {
        Object[][] data = new Object[clientes.size()][7];
        int i = 0;
        for (Cliente cliente : clientes.values()) {
            data[i][0] = cliente.getNome();
            data[i][1] = cliente.getCpf();
            data[i][2] = cliente.getTelefone();
            data[i][3] = cliente.getEndereco();
            data[i][4] = cliente.getNumero();
            data[i][5] = cliente.getCidade();
            data[i][6] = cliente.getEstado();
            i++;
        }
        return data;
    }
}
