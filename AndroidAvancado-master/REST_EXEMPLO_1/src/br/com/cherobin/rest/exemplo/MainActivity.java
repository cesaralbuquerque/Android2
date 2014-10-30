package br.com.cherobin.rest.exemplo;

import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


public class MainActivity extends Activity {
	ListView viewUsuarioList;
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = 
					new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		viewUsuarioList = (ListView) findViewById(R.id.listViewUsuario);
		
		getTelefones();
	}
	
	private void getTelefones() {
 
		Gson gson = new Gson();
		HttpResponse response = null;
		HttpGet getMethod = new HttpGet(
				"http://www.cherobin.com.br/android/rest/listarGatinhas.php");
		try {
			HttpClient httpClient = new DefaultHttpClient();
			response = httpClient.execute(getMethod);
			String result = EntityUtils.toString(response.getEntity());
  
			Log.e("response", result);
			Usuario[] us = gson.fromJson(result, Usuario[].class);
			setUsuario(us);
			
			
		} catch (Exception e) {			 
			Toast.makeText(getBaseContext(), "Nenhum Telefone Encontrado!",
					Toast.LENGTH_LONG).show();
		}

	}

	private void setUsuario(Usuario[] us) {		
		
		List<Usuario>listUsuarios = Arrays.asList(us);	
		Log.e("lista telefone", us.toString());
		
		
		viewUsuarioList.setAdapter(new telefoneAdapter(MainActivity.this, listUsuarios ));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	public class telefoneAdapter extends BaseAdapter {


		private LayoutInflater inflater;
		private List<Usuario> viewUsuarioList;
		Activity activity;
		public telefoneAdapter(Activity activity,
				List<Usuario> us) {		 
			super();
			inflater = LayoutInflater.from(activity);
			viewUsuarioList = us;
			this.activity = activity;
		}
 
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
 			return viewUsuarioList.size();
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
			view = inflater.inflate(R.layout.usuario_telefone, parent, false);
 
			final TextView nome = (TextView) view.findViewById(R.id.textViewNome);
		 
			final Button btnLigar = (Button) view.findViewById(R.id.buttonTelefone);
			
			btnLigar.setText(viewUsuarioList.get(position).getNome());
			
			
			nome.setText(viewUsuarioList.get(position).getTelefone());		 
			 		
			
			return view;
		}

		 

	}
}
