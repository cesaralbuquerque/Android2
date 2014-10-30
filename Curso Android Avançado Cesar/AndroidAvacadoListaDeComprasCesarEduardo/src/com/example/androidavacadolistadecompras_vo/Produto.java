package com.example.androidavacadolistadecompras_vo;

public class Produto {
	private long id;
	private String descricao;
	private int quantidade;
	private int comprado;
	
	
	
			
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getComprado() {
		return comprado;
	}

	public void setComprado(int comprado) {
		this.comprado = comprado;
	}

	public Produto(long id, String descricao, int quantidade, int comprado) {
		this.id = id;
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.comprado = comprado;
	}
	public Produto(){};

}
