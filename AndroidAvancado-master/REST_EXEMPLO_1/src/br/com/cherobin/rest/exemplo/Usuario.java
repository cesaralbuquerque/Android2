package br.com.cherobin.rest.exemplo;

import java.io.Serializable;

public class Usuario  {
	private long id;
	private String nome;
	private String telefone;
	private String sexo;

	public Usuario() {}
	
	public Usuario(long id, String nome, String telefone, String sexo) {
		this.id = id;
		this.nome = nome;
		this.telefone= telefone;
		this.sexo = sexo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telfone) {
		this.telefone = telfone;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

}
