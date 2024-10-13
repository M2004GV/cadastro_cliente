package br.com.mateusgarcia.dao;

import br.com.mateusgarcia.domain.Cliente;


import java.util.Collection;

public interface IClienteDAO {
	
	public Boolean cadastrar(Cliente cliente);
	
	void excluir(Long cpf);
	
	public void alterar(Cliente cliente);
	
	public Cliente consultar(Long cpf);
	
	public Collection<Cliente> buscarTodos();

}


