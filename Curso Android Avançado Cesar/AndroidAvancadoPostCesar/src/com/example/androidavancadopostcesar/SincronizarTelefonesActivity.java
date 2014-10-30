package com.example.androidavancadopostcesar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import com.example.androidavancadopostcesar_dao.TelefoneDAO;
import com.example.androidavancadopostcesar_vo.Telefone;

import android.R.bool;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;
import android.provider.MediaStore;

public class SincronizarTelefonesActivity extends ActionBarActivity {

	EditText txtLogin;
	EditText txtSenha;

	TelefoneDAO dao;
	List<Telefone> telefoneList = null;
	Button sincronizar;
	Boolean autenticado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = 
					new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		setContentView(R.layout.activity_sincronizar_telefones);

		txtLogin = (EditText) findViewById(R.id.editTextLogin);
		txtSenha = (EditText) findViewById(R.id.editTextSenha);

		dao = new TelefoneDAO(getApplicationContext());
		telefoneList = dao.getAll();

		autenticado = false;

		findViewById(R.id.buttonAutenticar).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (autenticaUsuario(txtLogin.getText().toString(),
								txtSenha.getText().toString())) {
							autenticado = true;
							sincronizar();
						}
					}
				});

		sincronizar = (Button) findViewById(R.id.buttonSincronizar);

		sincronizar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sincronizaDoBanco();
				// try {
				// Uri notification =
				// RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				// Ringtone r =
				// RingtoneManager.getRingtone(getApplicationContext(),
				// notification);
				// r.play();
				// } catch (Exception e) {
				// e.printStackTrace();
				// try {
				//
				// FileOutputStream fos = openFileOutput("01 Faixa 1.mp3",
				// Context.MODE_PRIVATE);
				// Ringtone r =
				// RingtoneManager.getRingtone(getResources().getInteger(R.drawable.faixa_1));
				// } catch (FileNotFoundException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				//
				//
				//
				// }

				Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
				r.play();
				finish();
			}

		});

		sincronizar();

		findViewById(R.id.buttonCadastrarUsuario).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(
								SincronizarTelefonesActivity.this,
								CadastrarUsuarioActivity.class);
						startActivity(intent);
					}
				});

		Toast.makeText(
				getApplicationContext(),
				"Existe "
						+ telefoneList.size()
						+ " telefone(s) à ser(em) sincronizado(s). \nSeu status é "
						+ autenticado, Toast.LENGTH_LONG).show();
	}

	private void sincronizar() {
		if (telefoneList.size() == 0 || !autenticado) {
			sincronizar.setEnabled(false);
			Log.e("funcionou", "aaa");
		} else {

			sincronizar.setEnabled(true);
		}
	}

	// validacao login : http://www.cherobin.com.br/Android/rest/login.php
	// espera values: nome - password;
	private boolean autenticaUsuario(String login, String senha) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				"http://www.cherobin.com.br/Android/rest/login.php");

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("nome", login));
			nameValuePairs.add(new BasicNameValuePair("password", senha));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);

			if (response.getStatusLine().getStatusCode() == 200) {
				Toast.makeText(getApplicationContext(), "Validado!",
						Toast.LENGTH_LONG).show();
				return true;
			}

			Log.e("response", response.getStatusLine().getReasonPhrase());

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return false;

	}

	// inserir novo telefone :
	// http://www.cherobin.com.br/Android/rest/inserirNovoTelefone.php.php
	// espera values: nome - telefone;
	private void sincronizaDoBanco() {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				"http://www.cherobin.com.br/Android/rest/inserirNovoTelefone.php");
		Telefone telefoneAux;
		for (int i = 0; i < telefoneList.size(); i++) {
			telefoneAux = new Telefone();
			telefoneAux = telefoneList.get(i);

			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs.add(new BasicNameValuePair("nome", telefoneAux
						.getNome()));
				nameValuePairs.add(new BasicNameValuePair("telefone",
						telefoneAux.getNumero()));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				HttpResponse response = httpclient.execute(httppost);

				if (response.getStatusLine().getStatusCode() == 200) {
					Toast.makeText(getApplicationContext(), "Validado!",
							Toast.LENGTH_LONG).show();
					dao.delete(telefoneAux);
				} else {
					Toast.makeText(
							getApplicationContext(),
							"O telefone "
									+ telefoneAux.getNome()
									+ "não pode ser sincronizado/n Compre um Iphone!",
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

}
