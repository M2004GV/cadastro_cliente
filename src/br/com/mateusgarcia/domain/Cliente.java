package br.com.mateusgarcia.domain;

import java.util.Objects;

public class Cliente {
    
    private String nome;
    private Long cpf;
    private Long tel;
    private String endereco;
    private Integer numero;
    private String cidade;
    private String estado;

    public Cliente(String nome, String cpf, String tel, String endereco, String num, String cidade, String estado) {
        this.nome = nome.trim();
        this.cpf = Long.valueOf(cpf.trim());
        this.tel = Long.valueOf(tel.trim());
        this.endereco = endereco.trim();
        this.numero = Integer.valueOf(num.trim());
        this.cidade = cidade.trim();
        this.estado = estado.trim();
    }

    // Getters and Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome.trim();
    }

    public Long getCpf() {
        return cpf;
    }
    
    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Long getTel() {
        return tel;
    }
    
    public void setTel(Long tel) {
        this.tel = tel;
    }

    public String getEndereco() {
        return endereco; 
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco.trim();
    }

    public Integer getNumero() {
        return numero;
    }
    
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade.trim();
    }

    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado.trim();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente cliente = (Cliente) obj;
        return Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", cpf=" + cpf +
                '}';
    }
}
