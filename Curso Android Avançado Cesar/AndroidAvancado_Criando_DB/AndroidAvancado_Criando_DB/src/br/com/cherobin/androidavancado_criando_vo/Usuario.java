package br.com.cherobin.androidavancado_criando_vo;

import java.io.Serializable;

public class Usuario implements Serializable{
	private long id;
	private String nome;
	private String fone;
	private String sexo;
	private String idLocais;

	public Usuario() {}
	
	public Usuario(long id, String nome, String fone, String sexo, String idLocais) {
		this.id = id;
		this.nome = nome;
		this.fone = fone;
		this.sexo = sexo;
		this.idLocais = idLocais;
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

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getIdLocais() {
		return idLocais;
	}

	public void setIdLocais(String idLocais) {
		this.idLocais = idLocais;
	}

}
