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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import br.com.cherobin.androidavancado_criando_dao.UsuarioDAO;
import br.com.cherobin.androidavancado_criando_vo.Local;
import br.com.cherobin.androidavancado_criando_vo.Usuario;

public class CadastroUsuarioActivity extends Activity {

	EditText txtNome;
	EditText txtFone;
	EditText txtSexo;

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
		dao.insert(usuario);

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
			// TODO Auto-generated method stub
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
