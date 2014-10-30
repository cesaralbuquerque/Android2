package br.com.cherobin.rest_exemplo;

 
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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends Activity {

	Button btnMostraTelefone;
	
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
		
		btnMostraTelefone = (Button) findViewById(R.id.btnMostrarTelefone);
		
		btnMostraTelefone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			getTelefone(); 
				
			}
		});
	}
	
	private void getTelefone() {
 
		Gson gson = new Gson();
		HttpResponse response = null;
		HttpGet getMethod = new HttpGet(
				"http://www.cherobin.com.br/android/rest/telefone.php");
		try {
			HttpClient httpClient = new DefaultHttpClient();
			response = httpClient.execute(getMethod);
			String result = EntityUtils.toString(response.getEntity());
  
			Log.e("response", result);
			Telefone tel = gson.fromJson(result, Telefone.class);
			setTelefone(tel);
			
			
		} catch (Exception e) {			 
			Toast.makeText(getBaseContext(), "Nenhum Telefone Encontrado!",
					Toast.LENGTH_LONG).show();
		}

	}

	private void setTelefone(Telefone tel) {		
		Toast.makeText(getBaseContext(), "Nome: "+tel.nome+" Telefone: "+tel.numero,
				Toast.LENGTH_LONG).show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
