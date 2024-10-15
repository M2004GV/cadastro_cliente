package br.com.mateusgarcia.dao;

import br.com.mateusgarcia.domain.Cliente;

import java.util.Collection;

public interface IClienteDAO {
    
    /**
     * Cadastra um novo cliente no sistema.
     * 
     * @param cliente O objeto Cliente a ser cadastrado.
     */
    public void cadastrar(Cliente cliente);
    
    /**
     * Exclui um cliente do sistema com base no CPF.
     * 
     * @param cpf O CPF do cliente a ser excluído.
     * @return true se a exclusão foi bem-sucedida, false caso contrário.
     */
    public Boolean excluir(Long cpf);
    
    /**
     * Altera os dados de um cliente existente.
     * 
     * @param cliente O objeto Cliente com os dados a serem alterados.
     * @return true se a alteração foi bem-sucedida, false caso contrário.
     */
    public Boolean alterar(Cliente cliente);
    
    /**
     * Consulta um cliente pelo CPF.
     * 
     * @param cpf O CPF do cliente a ser consultado.
     * @return O objeto Cliente correspondente, ou null se não encontrado.
     */
    public Cliente consultar(Long cpf);
    
    /**
     * Busca todos os clientes cadastrados.
     * 
     * @return Uma coleção de objetos Cliente.
     */
    public Collection<Cliente> buscarTodos();

    /**
     * Lista os clientes em formato de matriz.
     * 
     * @return Uma matriz de objetos representando os clientes.
     */
    public Object[][] listar();
}


