package com.example.androidavacadolistadecompras;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastrarCompraActivity extends Activity {

	EditText txtProduto;
	EditText txtQuantidade;
	Button btnCadastrar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_produto);
		txtProduto = (EditText) findViewById(R.id.editTextCadCompraProduto);
		txtQuantidade = (EditText) findViewById(R.id.editTextCadCompraQuantidade);
		btnCadastrar = (Button) findViewById(R.id.buttonCadCompraCadastrar);

		findViewById(R.id.buttonCadCompraCadastrar).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if (validaEntradas()) {
							cadastraCompra(txtProduto.getText().toString(),
									txtQuantidade.getText().toString());
							Toast.makeText(getBaseContext(),
									"Compra Cadastrada", Toast.LENGTH_LONG)
									.show();
							finish();
						}
					}
				});
	}

	private boolean validaEntradas() {
		if (!txtProduto.getText().equals("")
				&& !txtQuantidade.getText().equals("")) {
			return true;
		}
		return false;
	}

	private void cadastraCompra(String descricao, String quantidade) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				"http://cesaralbuquerque.hol.es/PHP_hostinger_lista_de_compras/inserirProduto.php");

		try {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("descricao", descricao));
			nameValuePairs
					.add(new BasicNameValuePair("quantidade", quantidade));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);

			if (response.getStatusLine().getStatusCode() == 200) {
				Toast.makeText(getApplicationContext(), "Cadastrado!",
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
