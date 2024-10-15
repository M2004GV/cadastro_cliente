package br.com.mateusgarcia.dao;

import br.com.mateusgarcia.domain.Cliente;

public interface IClienteDAO {
    void adicionar(Cliente cliente);
    Cliente consultar(String cpf); // Alterado de Long para String
    boolean alterar(Cliente cliente);
    boolean excluir(String cpf);   // Alterado de Long para String
    Object[][] listar();
}
