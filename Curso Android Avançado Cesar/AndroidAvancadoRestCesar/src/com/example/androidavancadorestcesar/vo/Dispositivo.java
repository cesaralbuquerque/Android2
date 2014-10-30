package com.example.androidavancadorestcesar.vo;

public class Dispositivo {

	private long id;
	private String modelo;
	private String memoria;
	private String processador;
	private String peso;
	private String tela;
	
	
	public Dispositivo(long id, String modelo, String memoria, String processador, String peso, String tela){
		this.id = id;
		this.modelo = modelo;
		this.memoria = memoria;
		this.processador = processador;
		this.peso = peso;
		this.tela = tela;
	}
	
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getModelo() {
		return modelo;
	}


	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	public String getMemoria() {
		return memoria;
	}


	public void setMemoria(String memoria) {
		this.memoria = memoria;
	}


	public String getProcessador() {
		return processador;
	}


	public void setProcessador(String processador) {
		this.processador = processador;
	}


	public String getPeso() {
		return peso;
	}


	public void setPeso(String peso) {
		this.peso = peso;
	}


	public String getTela() {
		return tela;
	}


	public void setTela(String tela) {
		this.tela = tela;
	}



	
	
}
