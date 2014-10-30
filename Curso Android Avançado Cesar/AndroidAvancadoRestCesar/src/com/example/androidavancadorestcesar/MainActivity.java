package com.example.androidavancadorestcesar;

import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.example.androidavancadorestcesar.dao.DispositivoDAO;
import com.example.androidavancadorestcesar.vo.Dispositivo;
import com.google.gson.Gson;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ListView viewDispositivosList;
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(android.os.Build.VERSION.SDK_INT > 9){
			StrictMode.ThreadPolicy policy = new 
					StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		viewDispositivosList = (ListView) findViewById(R.id.listDispositivos);
		
		getDispositivos();
	}
	

	private void getDispositivos(){
	 
		Gson gson = new Gson();
		HttpResponse response = null;
		HttpGet getMethod = new HttpGet("http://www.cherobin.com.br/android/rest/listarNote.php");
		
		try{
			HttpClient httpClient = new DefaultHttpClient();
			response = httpClient.execute(getMethod);
			String result = EntityUtils.toString(response.getEntity());
			
			Log.e("response", result);
			Dispositivo[] dispositivos = gson.fromJson(result, Dispositivo[].class);
			setDispositivo(dispositivos);
		} catch (Exception e) {	
			//pegar do DB
			DispositivoDAO dao = new DispositivoDAO(getApplicationContext());
			List<Dispositivo> dispositivosList = dao.getAll();
			setDispositivo(dispositivosList);
			//Toast.makeText(getBaseContext(), "Nenhum Telefone Encontrado!", Toast.LENGTH_LONG).show();
		}
	}
	
	private void setDispositivo(List<Dispositivo> dispositivos){
 
		
		viewDispositivosList.setAdapter(new DispositivosAdapter(MainActivity.this, dispositivos));
	}
	
	private void setDispositivo(Dispositivo[] dispositivos){
		List<Dispositivo>listDispositivos = Arrays.asList(dispositivos);
//		Log.e("Lista de dipositivos", dispositivos.toString());
		
		viewDispositivosList.setAdapter(new DispositivosAdapter(MainActivity.this, listDispositivos));
	}

	public class DispositivosAdapter extends BaseAdapter{

		private LayoutInflater inflater;
		private List<Dispositivo> viewDispositivosList;
		Activity activity;
		public DispositivosAdapter(Activity activity, 
				List<Dispositivo> dis){
			super();
			inflater = LayoutInflater.from(activity);
			viewDispositivosList = dis;
			this.activity = activity;
		}
		
		
		@Override
		public int getCount() {
			return viewDispositivosList.size();
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
		
			Resources res = getResources();
			Log.e("draw", viewDispositivosList.get(position).getModelo());
			String mDrawableName =  viewDispositivosList.get(position).getModelo().toString();
			int resourceId = res.getIdentifier(mDrawableName , "drawable", getPackageName());
			
			View view = convertView;
			view = inflater.inflate(R.layout.dispositivo_formulario, parent, false);
			final TextView id = (TextView) view.findViewById(R.id.textViewId);
			final TextView modelo = (TextView) view.findViewById(R.id.textViewModelo);
			final TextView memoria = (TextView) view.findViewById(R.id.textViewMemoria);
			final TextView processador = (TextView) view.findViewById(R.id.textViewProcessador);
			final TextView peso = (TextView) view.findViewById(R.id.textViewPeso);
			final TextView tela = (TextView) view.findViewById(R.id.textViewTela);
			final ImageView image = (ImageView) view.findViewById(R.id.imageView1);
			
			id.setText(""+viewDispositivosList.get(position).getId());
			modelo.setText(viewDispositivosList.get(position).getModelo());
			memoria.setText(viewDispositivosList.get(position).getMemoria());
			processador.setText(viewDispositivosList.get(position).getProcessador());
			peso.setText(viewDispositivosList.get(position).getPeso());
			tela.setText(viewDispositivosList.get(position).getTela());
			
//			if(viewDispositivosList.get(position).getId()==1)
//			image.setImageDrawable(getResources().getDrawable(R.drawable.asus));
//			
		
			Drawable drawable = res.getDrawable(resourceId);
			image.setImageDrawable(drawable );
			
			
			return view;
		}
		

	}


}
