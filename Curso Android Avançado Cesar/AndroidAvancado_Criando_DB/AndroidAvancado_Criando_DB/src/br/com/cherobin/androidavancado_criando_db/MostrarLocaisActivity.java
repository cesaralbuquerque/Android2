package br.com.cherobin.androidavancado_criando_db;

 
import java.util.List;

import br.com.cherobin.androidavancado_criando_dao.LocalDAO;
import br.com.cherobin.androidavancado_criando_dao.UsuarioDAO;
import br.com.cherobin.androidavancado_criando_vo.Local;
import br.com.cherobin.androidavancado_criando_vo.Usuario;
import android.app.Activity;
import android.media.Rating;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MostrarLocaisActivity extends Activity {

	 LocalDAO dao;
	 List<Local> localList;
	 
	 ListView viewLocalList;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__listar_local);
		dao = new LocalDAO(getApplicationContext());
		localList = dao.getAll();
		
		viewLocalList = (ListView) findViewById(R.id.listViewLocal);
 
		if(localList.size()!=0){
			viewLocalList.setAdapter(new localAdapter(MostrarLocaisActivity.this, localList ));
		}else{
			Toast.makeText(getApplicationContext(), "0 Locais cadastrados", Toast.LENGTH_SHORT).show();
		}
	}

	public class localAdapter extends BaseAdapter {


		private LayoutInflater inflater;
		private List<Local> localList;
		Activity activity;
		public localAdapter(Activity activity,
				List<Local> localList) {		 
			super();
			inflater = LayoutInflater.from(activity);
			this.localList = localList;
			this.activity = activity;
		}
 
		@Override
		public int getCount() { 
 			return localList.size();
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
			view = inflater.inflate(R.layout.adapter_local, parent, false);
 
			final TextView local = (TextView) view.findViewById(R.id.textViewLocal);
			final RatingBar floatAvaliacao = (RatingBar) view.findViewById(R.id.ratingAvalaliacao);
			final TextView lat = (TextView) view.findViewById(R.id.textLat);
			final TextView longi = (TextView) view.findViewById(R.id.textLongi);
			
			local.setText(localList.get(position).getNome());
			floatAvaliacao.setNumStars(localList.get(position).getAvaliacao().intValue());
			lat.setText(localList.get(position).getLat());
			longi.setText(localList.get(position).getLongi());
			
			return view;
		}

	}

}
