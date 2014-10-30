package br.com.cherobin.androidavancado_criando_db;

import br.com.cherobin.androidavancado_criando_dao.LocalDAO;
import br.com.cherobin.androidavancado_criando_vo.Local;
import android.app.Activity;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class CadastroLocalActivity extends Activity{
	
	EditText txtNome;
	RatingBar floatAvaliacao;
	EditText txtLat;
	EditText txtLongi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_local);
		
		txtNome = (EditText) findViewById(R.id.editTextLocal);
		floatAvaliacao = (RatingBar) findViewById(R.id.ratingAvalaliacao);
		txtLat = (EditText) findViewById(R.id.textLat);
		txtLongi = (EditText) findViewById(R.id.textLongi);
		
		
		
		findViewById(R.id.buttonCadastrarLocal2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gravaCadastro();
				Toast.makeText(getBaseContext(), "Cadastrado", Toast.LENGTH_LONG).show();
				finish();	
			}
		});
		
	}
	
	private void valida(){
		if(txtLat.toString().equals("")  || txtLongi.toString().equals("")){
			txtLat.setText("0");
			txtLongi.setText("0");
		}
	}
	
	private void gravaCadastro(){
		valida();
		LocalDAO dao = new LocalDAO(CadastroLocalActivity.this);
		Local local = new Local();
		local.setNome(txtNome.getText().toString());
		local.setAvaliacao(floatAvaliacao.getRating());
		local.setLat(txtLat.getText().toString());
		local.setLongi(txtLongi.getText().toString());
		dao.insert(local);
		
				
	}

}
