package com.example.androidavancadopostcesar;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidavancadopostcesar_vo.Telefone;
import com.google.gson.Gson;

public class ListarTelefonesActivity extends Activity {
	ListView viewTelefonesList;
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listar_telefones);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = 
					new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		viewTelefonesList = (ListView) findViewById(R.id.listViewTelefones);
		getTelefones();

	}

	private void getTelefones() {
		Gson gson = new Gson();
		HttpResponse response = null;
		HttpGet getMethod = new HttpGet("http://www.cherobin.com.br/Android/rest/listarTelefones.php");

		try{
			HttpClient httpClient = new DefaultHttpClient();
			response = httpClient.execute(getMethod);
			String result = EntityUtils.toString(response.getEntity());
			
			Log.e("response", result);
			Telefone[] telefones = gson.fromJson(result, Telefone[].class);
			setTelefone(telefones);
		} catch (Exception e) {	
			//pegar do DB
//			DispositivoDAO dao = new DispositivoDAO(getApplicationContext());
//			List<Dispositivo> dispositivosList = dao.getAll();
//			setDispositivo(dispositivosList);
			//Toast.makeText(getBaseContext(), "Nenhum Telefone Encontrado!", Toast.LENGTH_LONG).show();
		}
		
		
		
	}

	private void setTelefone(List<Telefone> telefones) {
		viewTelefonesList.setAdapter(new TelefoneAdapter(ListarTelefonesActivity.this, telefones));
		
	}
	
	private void setTelefone(Telefone[] telefones){
		List<Telefone> listTelefones = Arrays.asList(telefones);
		viewTelefonesList.setAdapter(new TelefoneAdapter(ListarTelefonesActivity.this, listTelefones));
	}
	

	
	public class TelefoneAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private List<Telefone> viewTelefonesList;
		Activity activity;

		public TelefoneAdapter(Activity activity, List<Telefone> tel){
			super();
			inflater = LayoutInflater.from(activity);
			viewTelefonesList = tel;
			this.activity = activity;
		}
		
		@Override
		public int getCount() {
			return viewTelefonesList.size();
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
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			view = inflater.inflate(R.layout.activity_cadastrar_telefone, parent, false);
			TextView nome = (TextView) view.findViewById(R.id.editTextNome);
			TextView numero = (TextView) view.findViewById(R.id.editTextNumero);
			Button button = (Button) view.findViewById(R.id.buttonCadastrarTelefone);
			
			nome.setText(viewTelefonesList.get(position).getNome());
			numero.setText(viewTelefonesList.get(position).getNumero());
			
			nome.setEnabled(false);
			numero.setEnabled(false);
			button.setVisibility(View.GONE);
			
			return view;
		}
	}
}
