package com.example.androidavancadocesar;

import com.example.androidavancadocesar.dao.UsuarioDAO;
import com.example.androidavancadocesar.model.Usuario;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;
import android.view.View.OnClickListener;

public class FormularioUsuarioActivity extends Activity {

	EditText nome;
	EditText telefone;
	EditText sexo;

	Usuario usuario;
	
	Boolean isEdit=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formulario_usuario);

		nome = (EditText) findViewById(R.id.editTextNomeUsuario);
		telefone = (EditText) findViewById(R.id.editTextTelefone);
		sexo = (EditText) findViewById(R.id.editTextSexo);

		if (getIntent().getExtras() != null) {
			usuario = (Usuario) getIntent().getExtras().getSerializable("edit");
			nome.setText(usuario.getNome());
			telefone.setText(usuario.getTelefone());
			sexo.setText(usuario.getSexo());
		}

		findViewById(R.id.buttonCadastrarUsuario).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						grava();
						Toast.makeText(getBaseContext(), "Cadastrado",
								Toast.LENGTH_LONG).show();
						finish();
					}
				});
		Button excluir = (Button) findViewById(R.id.buttonExcluirUsuario);
		excluir.setVisibility(View.INVISIBLE);
		
	}

	public void grava() {
		UsuarioDAO dao = new UsuarioDAO(FormularioUsuarioActivity.this);
		Usuario usuario = new Usuario();
		usuario.setNome(nome.getEditableText().toString());
		usuario.setTelefone(telefone.getEditableText().toString());
		usuario.setSexo(sexo.getEditableText().toString());
		
		if (getIntent().getExtras() != null) {
			dao.update(usuario);
		}else{
			dao.insert(usuario);
		}
		

	}

}
