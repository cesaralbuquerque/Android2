package com.example.androidavancadopostcesar;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class CadastrarUsuarioActivity extends Activity {

	EditText txtLogin;
	EditText txtSenha;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_usuario);
		
		txtLogin = (EditText) findViewById(R.id.editTextCadastrarLogin);
		txtSenha = (EditText) findViewById(R.id.editTextCadastrarSenha);
		
		findViewById(R.id.buttonConfirmaCadastroUsuario).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cadastraUsuario(txtLogin.getText().toString(), txtSenha.getText().toString());
				finish();
			}
		});
		

	}

	public void cadastraUsuario(String login, String senha){
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				"http://www.cherobin.com.br/android/rest/inserirNovoUsuario.php");

		try {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("nome", login));
			nameValuePairs.add(new BasicNameValuePair("password", senha));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);

			if (response.getStatusLine().getStatusCode() == 200) {
				Toast.makeText(getApplicationContext(), "O usuaário" + login +" Cadastrado!",
						Toast.LENGTH_LONG).show();
			}

			Log.e("response", response.getStatusLine().getReasonPhrase());

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
}
