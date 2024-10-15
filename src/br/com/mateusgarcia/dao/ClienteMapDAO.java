package br.com.mateusgarcia.dao;

import br.com.mateusgarcia.domain.Cliente;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteMapDAO implements IClienteDAO {

    private Map<Long, Cliente> clientes;

    public ClienteMapDAO() {
        clientes = new HashMap<>(); // Inicializa o mapa para armazenar clientes
    }

    @Override
    public void cadastrar(Cliente cliente) {
        clientes.put(cliente.getCpf(), cliente); // Usa CPF como chave para o mapa
    }

    @Override
    public Cliente consultar(Long cpf) {
        return clientes.get(cpf); // Retorna o cliente com o CPF fornecido
    }

    @Override
    public Boolean alterar(Cliente cliente) {
        if (clientes.containsKey(cliente.getCpf())) {
            clientes.put(cliente.getCpf(), cliente); // Atualiza o cliente
            return true;
        }
        return false; // Retorna falso se o cliente não for encontrado
    }

    @Override
    public Boolean excluir(Long cpf) {
        return clientes.remove(cpf) != null; // Remove o cliente e retorna se a operação foi bem-sucedida
    }

    @Override
    public Object[][] listar() {
        List<Cliente> listaClientes = new ArrayList<>(clientes.values()); // Converte o mapa em uma lista
        Object[][] dados = new Object[listaClientes.size()][7]; // Cria a matriz para os dados

        for (int i = 0; i < listaClientes.size(); i++) {
            Cliente cliente = listaClientes.get(i);
            dados[i][0] = cliente.getNome();
            dados[i][1] = cliente.getCpf();
            dados[i][2] = cliente.getTel();
            dados[i][3] = cliente.getEndereco(); // Use getEnd() para obter o endereço
            dados[i][4] = cliente.getNumero();
            dados[i][5] = cliente.getCidade();
            dados[i][6] = cliente.getEstado();
        }

        return dados; // Retorna a matriz com os dados dos clientes
    }

    @Override
    public Collection<Cliente> buscarTodos() {
        return new ArrayList<>(clientes.values()); // Retorna todos os clientes como uma coleção
    }
}
