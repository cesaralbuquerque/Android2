package com.example.androidavancadopostcesar;

import com.example.androidavancadopostcesar_dao.TelefoneDAO;
import com.example.androidavancadopostcesar_vo.Telefone;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class CadastrarTelefoneActivity extends ActionBarActivity {

	EditText txtNome;
	EditText txtNumero;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_telefone);

		txtNome = (EditText) findViewById(R.id.editTextNome);
		txtNumero= (EditText) findViewById(R.id.editTextNumero);

		findViewById(R.id.buttonCadastrarTelefone).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gravaCadastro();
				Toast.makeText(getBaseContext(), "Cadastrado", Toast.LENGTH_LONG).show();
				finish();	
			}
		});
	}

	private void gravaCadastro(){
		TelefoneDAO dao = new TelefoneDAO(CadastrarTelefoneActivity.this);
		Telefone telefone = new Telefone();
		telefone.setNome(txtNome.getText().toString());
		telefone.setNumero(txtNumero.getText().toString());
		dao.insert(telefone);
		
				
	}




}
