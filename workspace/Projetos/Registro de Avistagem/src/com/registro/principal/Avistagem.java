package com.registro.principal;

import java.util.Date;





public class Avistagem {
	private int codAvistagem;
	private Date data;
	private String hora = "horaAparece";
	private Number latitude;
	private Number longitude;
	private int profundidade;
	private String estadoMar;
	private String visibilidade;
	private String ondulacao;
	private int codAnimalAvistado;
	private String grupo;
	private int nFilhotes;
	private int nAdultos;
	private String comportamento;
	private String confianca;
	private String canhao;
	private String solicitado;
	private String realizado;
	private String tempoInterrupcao;
	private String horaSolicitado;
	private String horaRealizado;
	
	public void setCodAvistagem(int codAvistagem) {
		this.codAvistagem = codAvistagem;
	}
	public int getCodAvistagem() {
		return codAvistagem;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Date getData() {
		return data;
	}
	public void setLatitude(Number latitude) {
		this.latitude = latitude;
	}
	public Number getLatitude() {
		return latitude;
	}
	public void setProfundidade(int profundidade) {
		this.profundidade = profundidade;
	}
	public int getProfundidade() {
		return profundidade;
	}
	public void setEstadoMar(String estadoMar) {
		this.estadoMar = estadoMar;
	}
	public String getEstadoMar() {
		return estadoMar;
	}
	public void setVisibilidade(String visibilidade) {
		this.visibilidade = visibilidade;
	}
	public String getVisibilidade() {
		return visibilidade;
	}
	public void setLongitude(Number longitude) {
		this.longitude = longitude;
	}
	public Number getLongitude() {
		return longitude;
	}
	public void setOndulacao(String ondulacao) {
		this.ondulacao = ondulacao;
	}
	public String getOndulacao() {
		return ondulacao;
	}
	public void setCodAnimalAvistado(int codAnimalAvistado) {
		this.codAnimalAvistado = codAnimalAvistado;
	}
	public int getCodAnimalAvistado() {
		return codAnimalAvistado;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setnFilhotes(int nFilhotes) {
		this.nFilhotes = nFilhotes;
	}
	public int getnFilhotes() {
		return nFilhotes;
	}
	public void setnAdultos(int nAdultos) {
		this.nAdultos = nAdultos;
	}
	public int getnAdultos() {
		return nAdultos;
	}
	public void setComportamento(String comportamento) {
		this.comportamento = comportamento;
	}
	public String getComportamento() {
		return comportamento;
	}
	public void setConfianca(String confianca) {
		this.confianca = confianca;
	}
	public String getConfianca() {
		return confianca;
	}
	public void setCanhao(String canhao) {
		this.canhao = canhao;
	}
	public String getCanhao() {
		return canhao;
	}
	public void setSolicitado(String solicitado) {
		this.solicitado = solicitado;
	}
	public String getSolicitado() {
		return solicitado;
	}
	public void setRealizado(String realizado) {
		this.realizado = realizado;
	}
	public String getRealizado() {
		return realizado;
	}
	public void setHoraSolicitado(String horaSolicitado) {
		this.horaSolicitado = horaSolicitado;
	}
	public String getHoraSolicitado() {
		return horaSolicitado;
	}
	public void setHoraRealizado(String horaRealizado) {
		this.horaRealizado = horaRealizado;
	}
	public String getHoraRealizado() {
		return horaRealizado;
	}
	public void setTempoInterrupcao(String string) {
		this.tempoInterrupcao = string;
	}
	public String getTempoInterrupcao() {
		return tempoInterrupcao;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	
	public String getHora() {
		return hora;
	}
//	public execSQL{
//		String insereSQL = "INSERT INTO avistagem (hora, latitude, longitude, profundidade, estado_mar, visibilidade, ondulacao, " +
//		"cod_animal, grupo, numero_filhotes, numero_adultos, comportamento, confianca, canhao, solicitado, realizado, " +
//		"tempo_interrupcao, hora_solicitado, hora_realizado) VALUES ( " + objeto.getHora() + "," + objeto.getLatitude()+ ","
//		+ this.getLongitude() objeto.getLongitude() + "," + objeto.getProfundidade()+ "," + objeto.getEstadoMar()+ "," + objeto.getVisibilidade()+ "," 
//		+ objeto.getOndulacao()+ "," + objeto.getCodAnimalAvistado() + "," + objeto.getGrupo() + "," + objeto.getnFilhotes() + "," 
//		+ objeto.getnAdultos()+ "," + objeto.getComportamento()+ "," + objeto.getConfianca()+ "," + objeto.getCanhao()+ "," 
//		+ objeto.getSolicitado()+ "," + objeto.getRealizado()+")";
//		
//	}
}
