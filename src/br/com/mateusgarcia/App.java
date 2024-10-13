package br.com.mateusgarcia;

import javax.swing.JOptionPane;

import br.com.mateusgarcia.dao.ClienteMapDAO;
import br.com.mateusgarcia.dao.IClienteDAO;
import br.com.mateusgarcia.domain.Cliente;

public class App {
	
	//constantes
	private static IClienteDAO iClienteDAO;

	public static void main(String args[]) {		
		//mostrar telinha pedindo informações do usuario
		
		iClienteDAO = new ClienteMapDAO();
		
		String opcao = JOptionPane.showInputDialog(null,
				"Digite 1 para cadastro, 2 para consultar, 3 para exclusao, 4 para alteracao ou 5 para sair",
				"Cadastro", JOptionPane.INFORMATION_MESSAGE);
		
		while(!isOpcaoValida(opcao)) {
			if("".equals(opcao)) {
				sair();
			}
			opcao = JOptionPane.showInputDialog(null,
				"Digite 1 para cadastro, 2 para consultar, 3 para exclusao, 4 para alteracao ou 5 para sair",
				"Cadastro", JOptionPane.INFORMATION_MESSAGE);
			
		}
		
		while(isOpcaoValida(opcao)) {
			if(isOpcaoSair(opcao)) {
				sair();
			}else if(isCadastro(opcao)) {
				String dados = JOptionPane.showInputDialog(null,
						"Digite os dados do cliente separados por virgula, nesta ordem: Nome, CPF, Telefone, Endereço, Número, Cidade, Estado",
						"Cadastro", JOptionPane.INFORMATION_MESSAGE);
				cadastrar(dados);
			}
		}
	}
	
	private static void cadastrar(String dados) {
		String[] dadosSeparados = dados.split(",");
		//tentar validar se todos os campos estão preenchidos
		Cliente cliente =  new Cliente(dadosSeparados[0],dadosSeparados[1], dadosSeparados[2],dadosSeparados[3], dadosSeparados[4],	dadosSeparados[5], dadosSeparados[6]);
		Boolean isCadastrado = iClienteDAO.cadastrar(cliente);
		if(isCadastrado) {
			JOptionPane.showInternalMessageDialog(null, "Cliente cadastrado com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showInternalMessageDialog(null, "Cliente já está cadastrado", "ERRO", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private static boolean isCadastro(String opcao) {
		if("1".equals(opcao)) {
			return true;
		}
		return false;
	}

	private static boolean isOpcaoSair(String opcao) {
		if("5".equals(opcao)) {
			return true;
		}
		return false;
	}

	private static void sair() {
		JOptionPane.showInternalMessageDialog(null, "Até Logo", "Sair", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

	private static boolean isOpcaoValida(String opcao) {
		if("1".equals(opcao) || "2".equals(opcao) || "3".equals(opcao)
				|| "4".equals(opcao) || "5".equals(opcao)) {
			return true;
		}
		return false;
	}
}
