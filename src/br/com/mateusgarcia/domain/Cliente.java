package br.com.mateusgarcia.domain;

public class Cliente {
    private String nome;
    private String cpf; // Certifique-se de que é String
    private String telefone;
    private String endereco;
    private String numero;
    private String cidade;
    private String estado;

    public Cliente(String nome, String cpf, String telefone, String endereco, String numero, String cidade, String estado) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
    }

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Método toString para exibir informações do cliente
    @Override
    public String toString() {
        return "Nome: " + nome +
               "\nCPF: " + cpf +
               "\nTelefone: " + telefone +
               "\nEndereço: " + endereco +
               "\nNúmero: " + numero +
               "\nCidade: " + cidade +
               "\nEstado: " + estado;
    }
}
