package br.com.cherobin.androidavancado_criando_vo;

public class Local {
	private long id;
	private String nome;
	private Float avaliacao;
	private String lat;
	private String longi;

	
	public Local(){}
	
	public Local(long id, String nome, float avaliacao, String lat, String longi){
		this.id = id;
		this.nome = nome;
		this.avaliacao = avaliacao;
		this.lat = lat;
		this.longi = longi;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Float getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Float avaliacao) {
		this.avaliacao = avaliacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLongi() {
		return longi;
	}

	public void setLongi(String longi) {
		this.longi = longi;
	}

}
