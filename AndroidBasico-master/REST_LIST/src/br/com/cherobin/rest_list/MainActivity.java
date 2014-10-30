package br.com.cherobin.rest_list;

import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


public class MainActivity extends Activity {
	ListView viewTelefoneList;
	
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
		
		viewTelefoneList = (ListView) findViewById(R.id.listViewTelefones);
		
		getTelefones();
	}
	
	private void getTelefones() {
 
		Gson gson = new Gson();
		HttpResponse response = null;
		HttpGet getMethod = new HttpGet(
				"http://www.cherobin.com.br/android/rest/listarTelefone.php");
		try {
			HttpClient httpClient = new DefaultHttpClient();
			response = httpClient.execute(getMethod);
			String result = EntityUtils.toString(response.getEntity());
  
			Log.e("response", result);
			Telefone[] tel = gson.fromJson(result, Telefone[].class);
			setTelefone(tel);
			
			
		} catch (Exception e) {			 
			Toast.makeText(getBaseContext(), "Nenhum Telefone Encontrado!",
					Toast.LENGTH_LONG).show();
		}

	}

	private void setTelefone(Telefone[] tel) {		
		
		List<Telefone>listTelefone = Arrays.asList(tel);	
		Log.e("lista telefone", tel.toString());
		
		
		viewTelefoneList.setAdapter(new telefoneAdapter(MainActivity.this, listTelefone ));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	public class telefoneAdapter extends BaseAdapter {


		private LayoutInflater inflater;
		private List<Telefone> viewTelefoneList;
		Activity activity;
		public telefoneAdapter(Activity activity,
				List<Telefone> telefones) {		 
			super();
			inflater = LayoutInflater.from(activity);
			viewTelefoneList = telefones;
			this.activity = activity;
		}
 
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
 			return viewTelefoneList.size();
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
			view = inflater.inflate(R.layout.adapter_telefone, parent, false);
 
			final TextView nome = (TextView) view.findViewById(R.id.textViewNome);
		 
			final Button btnLigar = (Button) view.findViewById(R.id.buttonTelefone);
			
			btnLigar.setText(viewTelefoneList.get(position).numero);
			Log.e("numero", viewTelefoneList.get(position).numero);
			
			nome.setText(viewTelefoneList.get(position).nome);		 
			Log.e("nome", viewTelefoneList.get(position).nome);
			
			btnLigar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					 Toast.makeText(activity.getBaseContext(), "Ligando para "+nome.getText(), Toast.LENGTH_SHORT).show();					
				
					 String url = "tel:"+btnLigar.getText().toString();				
					    Intent intent =
					    	new Intent(Intent.ACTION_CALL, Uri.parse(url));
					    startActivity(intent);
				}
			});
			
		
			
			return view;
		}

		 

	}
}
