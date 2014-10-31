package com.geosapiens.questionario;

import java.io.Serializable;

public class Questao implements Serializable {
	private int cod_questionario;
	private int cod_questao;
	private int cod_grupo_questao;
	private String pergunta;
	private int resposta_componente;
	private String resposta;
	
	
	
	
	
	
	
	
	
	public int getCod_questionario() {
		return cod_questionario;
	}
	public void setCod_questionario(int cod_questionario) {
		this.cod_questionario = cod_questionario;
	}
	public int getCod_questao() {
		return cod_questao;
	}
	public void setCod_questao(int cod_questao) {
		this.cod_questao = cod_questao;
	}
	public int getCod_grupo_questao() {
		return cod_grupo_questao;
	}
	public void setCod_grupo_questao(int cod_grupo_questao) {
		this.cod_grupo_questao = cod_grupo_questao;
	}
	public String getPergunta() {
		return pergunta;
	}
	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}
	public int getResposta_componente() {
		return resposta_componente;
	}
	public void setResposta_componente(int resposta_componente) {
		this.resposta_componente = resposta_componente;
	}
	public String getResposta() {
		return resposta;
	}
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	
	
}
