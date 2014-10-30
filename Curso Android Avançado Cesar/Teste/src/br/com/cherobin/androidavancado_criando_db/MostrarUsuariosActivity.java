package br.com.cherobin.androidavancado_criando_db;

 
import java.util.List;

import br.com.cherobin.androidavancado_criando_dao.UsuarioDAO;
import br.com.cherobin.androidavancado_criando_vo.Usuario;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MostrarUsuariosActivity extends Activity {

	 UsuarioDAO dao;
	 List<Usuario> usuarioList;
	 
	 ListView viewUsuarioList;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__listar_usuario);
		dao = new UsuarioDAO(getApplicationContext());
		usuarioList = dao.getAll();
		
		viewUsuarioList = (ListView) findViewById(R.id.listViewUsuario);
 
		if(usuarioList.size()!=0){
			viewUsuarioList.setAdapter(new usuarioAdapter(MostrarUsuariosActivity.this, usuarioList ));
		}else{
			Toast.makeText(getApplicationContext(), "0 Usuarios cadastrados", Toast.LENGTH_SHORT).show();
			
		}
	}

	public class usuarioAdapter extends BaseAdapter {


		private LayoutInflater inflater;
		private List<Usuario> usuarioList;
		Activity activity;
		public usuarioAdapter(Activity activity,
				List<Usuario> usuarioList) {		 
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
			return position;
		}

		@Override
		public long getItemId(int position) { 
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			View view = convertView;
			view = inflater.inflate(R.layout.adapter_usuario, parent, false);
 
			final TextView nome = (TextView) view.findViewById(R.id.textViewAdapterUsuarioNome);
			final TextView fone = (TextView) view.findViewById(R.id.textViewAdapterUsuarioFone);
			final TextView sexo = (TextView) view.findViewById(R.id.textViewAdapterUsuarioSexo);
			final Button ligar	= (Button) view.findViewById(R.id.buttonLigar);

			nome.setText(usuarioList.get(position).getNome());
			fone.setText(usuarioList.get(position).getFone());
			sexo.setText(usuarioList.get(position).getSexo());
			ligar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(null, "ligar para ela", Toast.LENGTH_LONG);
					
				}
			});
			return view;
		}



	}

}
