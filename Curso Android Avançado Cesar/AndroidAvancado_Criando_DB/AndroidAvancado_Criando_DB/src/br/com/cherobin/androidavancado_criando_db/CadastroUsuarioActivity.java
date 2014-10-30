package br.com.cherobin.androidavancado_criando_db;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import br.com.cherobin.androidavancado_criando_dao.LocalDAO;
import br.com.cherobin.androidavancado_criando_dao.UsuarioDAO;
import br.com.cherobin.androidavancado_criando_db.MostrarLocaisActivity.localAdapter;
import br.com.cherobin.androidavancado_criando_db.MostrarUsuariosActivity.usuarioAdapter;
import br.com.cherobin.androidavancado_criando_vo.Local;
import br.com.cherobin.androidavancado_criando_vo.Usuario;

public class CadastroUsuarioActivity extends Activity {

	EditText txtNome;
	EditText txtFone;
	EditText txtSexo;

	/**
	 * tentativa de copular o listview com locais dentro da activity cadastrousuario*/
	LocalDAO localDao;
	List<Local> localList;
	ListView viewLocalList;
	
	private Usuario usuario = new Usuario();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_usuario);
		txtNome = (EditText) findViewById(R.id.editTextCadastrarUsuario);
		txtFone = (EditText) findViewById(R.id.editTextFone);
		txtSexo = (EditText) findViewById(R.id.editTextSexo);

		findViewById(R.id.buttonCadastrarUsuario).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (validaEntradas()) {
					gravaCadastro();
					Toast.makeText(getBaseContext(), "Cadastrado", Toast.LENGTH_LONG).show();
					finish();
				}
			}
		});
		
		
		localDao = new LocalDAO(getApplicationContext());
		localList = localDao.getAll();
		viewLocalList = (ListView) findViewById(R.id.listViewCadastroUsuario);
		
		if(localList.size()!=0){
			viewLocalList.setAdapter(new localAdapter(CadastroUsuarioActivity.this, localList ));
		}else{
			Toast.makeText(getApplicationContext(), "0 Locais cadastrados", Toast.LENGTH_SHORT).show();
		}
		
		
		
		/**editar o usuário
		 * */
		usuario = (Usuario)getIntent().getSerializableExtra("usuarioSelecionado");
		if(usuario != null){
			txtNome.setText(usuario.getNome());
			txtFone.setText(usuario.getFone());
			txtSexo.setText(usuario.getSexo());
		}
		
		
	}

	private boolean validaEntradas() {
		if (!txtNome.getText().equals("")) {
			return true;
		}
		return false;
	}

	private void gravaCadastro() {
		UsuarioDAO dao = new UsuarioDAO(CadastroUsuarioActivity.this);
		Usuario usuario = new Usuario();
		usuario.setNome(txtNome.getText().toString());
		usuario.setFone(txtFone.getText().toString());
		usuario.setSexo(txtSexo.getText().toString());
		if(getIntent().getSerializableExtra("usuarioSelecionado") == null){
			dao.insert(usuario);
		} else{
			dao.update(usuario);
		}
		
		

	}
	
	
	public class localAdapter extends BaseAdapter{

		private LayoutInflater inflater;
		private List<Local> localList;
		Activity activity;
		
		public localAdapter(Activity activity, List<Local> localList){
			super();
			inflater = LayoutInflater.from(activity);
			this.localList = localList;
			this.activity = activity;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return localList.size();
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
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			view = inflater.inflate(R.layout.adapter_local, parent, false);
			
			final TextView local = (TextView) view.findViewById(R.id.textViewLocal);
			final RatingBar floatAvaliacao = (RatingBar) view.findViewById(R.id.ratingAvalaliacao);
			final TextView lat = (TextView) view.findViewById(R.id.textLat);
			final TextView longi = (TextView) view.findViewById(R.id.textLongi);
			
			local.setText(localList.get(position).getNome());
			floatAvaliacao.setNumStars(localList.get(position).getAvaliacao().intValue());
			lat.setText(localList.get(position).getLat());
			longi.setText(localList.get(position).getLongi());
			
			
			return null;
		}
		
	}

}
