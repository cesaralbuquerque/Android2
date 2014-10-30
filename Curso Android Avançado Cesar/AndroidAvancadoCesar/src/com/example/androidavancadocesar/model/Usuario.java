package com.example.androidavancadocesar.model;

import java.io.Serializable;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.R.string;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class Usuario implements Serializable{
	
	public Usuario() {}
	
	public Usuario(long id, String nome, String telefone, String sexo) {
		this.id = id;
		this.nome = nome;
		this.telefone= telefone;
		this.sexo = sexo;
	}

	private Long id;
	private String nome;
	private String telefone;
	private String sexo;
	public Long getId() {
		return id;
	}
	public void setId(int i) {
		this.id = (long) i;
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
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

}
