package com.example.androidavancadocesar;

import java.util.List;


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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class ListaUsuariosActivity extends Activity {

	UsuarioDAO dao;
	List<Usuario> usuarioList;
	ListView viewUsuarioList;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_usuarios);

		dao = new UsuarioDAO(getApplicationContext());
		usuarioList = dao.getAll();
		
		viewUsuarioList = (ListView) findViewById(R.id.listViewListaUsuarios);
		
		if(usuarioList.size() != 0){
			viewUsuarioList.setAdapter(new usuarioAdapter(ListaUsuariosActivity.this, usuarioList));
		}else {
			Toast.makeText(getApplicationContext(), "0 Usuarios cadastrados",
					Toast.LENGTH_SHORT).show();

		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		refresh();

	}
	
	public void refresh() {
		dao = new UsuarioDAO(getApplicationContext());
		usuarioList = dao.getAll();
	}
		
	public class usuarioAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private List<Usuario> usuarioList;
		Activity activity;
		
		public usuarioAdapter(Activity activity, List<Usuario> usuarioList){
			super();
			inflater = LayoutInflater.from(activity);
			this.usuarioList = usuarioList;
			this.activity = activity;
		}
		
		@Override
		public int getCount() {
			return usuarioList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			view = inflater.inflate(R.layout.activity_formulario_usuario, parent, false);
			
			TextView nome = (TextView) view.findViewById(R.id.editTextNomeUsuario);
			TextView  telefone = (TextView) view.findViewById(R.id.editTextTelefone);
			TextView  sexo = (TextView) view.findViewById(R.id.editTextSexo);
			
			nome.setEnabled(false);
			telefone.setEnabled(false);
			sexo.setEnabled(false);

			
			nome.setText(usuarioList.get(position).getNome());
			telefone.setText(usuarioList.get(position).getTelefone());
			sexo.setText(usuarioList.get(position).getSexo());
			
			Button editar = (Button) view.findViewById(R.id.buttonCadastrarUsuario);
			editar.setText("Editar");
			editar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(ListaUsuariosActivity.this, FormularioUsuarioActivity.class);
					intent.putExtra("edit", (Usuario)usuarioList.get(position));
					startActivity(intent);
				}
			});
			
			Button excluir = (Button) view.findViewById(R.id.buttonExcluirUsuario);
			excluir.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dao.delete((Usuario)usuarioList.get(position));
					refresh();
				}
			});
			
			return view;
			
			
		}
		
		
		
	}
	
	
}
	
	



