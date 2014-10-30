package com.example.androidavancadopostcesar_vo;

public class Telefone {
	//tem de ser os mesmo nomes de atributos vindos do servidor
	private long id;
	private String nome;
	private String telefone;
	
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

	public String getNumero() {
		return telefone;
	}

	public void setNumero(String numero) {
		this.telefone = numero;
	}


	
	public Telefone(long id, String nome, String numero){
		this.id = id;
		this.nome = nome;
		this.telefone = numero;
	}
	public Telefone(){};
}
