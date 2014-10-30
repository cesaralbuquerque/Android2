package com.example.androidavacadolistadecompras;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidavacadolistadecompras_vo.Produto;
import com.google.gson.Gson;

public class ListarComprasActivity extends Activity {
	ListView viewProdutoList;
	Button btnExcluir;
	Button btnAtualizar;

//
//	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listar_produtos);

//		if (android.os.Build.VERSION.SDK_INT > 9) {
//			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//					.permitAll().build();
//			StrictMode.setThreadPolicy(policy);
//		}

		viewProdutoList = (ListView) findViewById(R.id.listViewProdutos);

		getProdutos();
	}

	private void getProdutos() {

		Gson gson = new Gson();
		HttpResponse response = null;
		HttpGet getMethod = new HttpGet(
				"http://cesaralbuquerque.hol.es/PHP_hostinger_lista_de_compras/listarProdutos.php");
		try {
			HttpClient httpClient = new DefaultHttpClient();
			response = httpClient.execute(getMethod);
			String result = EntityUtils.toString(response.getEntity());

			Log.e("response", result);
			Produto[] us = gson.fromJson(result, Produto[].class);
		 
			setProduto(us);

		} catch (Exception e) {
			Toast.makeText(getBaseContext(), "Nenhum Produto Encontrado!",
					Toast.LENGTH_LONG).show();
		}

	}

	private void setProduto(Produto[] us) {

		List<Produto> listProdutos = Arrays.asList(us);
		Log.e("lista produto", ""+listProdutos.size());

		viewProdutoList.setAdapter(new produtoAdapter(
				ListarComprasActivity.this, listProdutos));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class produtoAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		private List<Produto> viewProdutoList;
		Activity activity;

		public produtoAdapter(Activity activity, List<Produto> produtos) {
		 
			inflater = LayoutInflater.from(activity);
			viewProdutoList = produtos;
			this.activity = activity;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return viewProdutoList.size();
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
		public View getView(final int position, View convertView, ViewGroup parent) {

 			View view = convertView;
 			view = inflater.inflate(R.layout.adapter_produto, parent, false);

			final TextView produto = (TextView) view
					.findViewById(R.id.textViewAdapterCompraProduto);
			final TextView quantidade = (TextView) view
					.findViewById(R.id.APQTtextView);
			final CheckBox comprado = (CheckBox) view
					.findViewById(R.id.checkBoxAdapterCompraComprado);

			produto.setText(viewProdutoList.get(position).getDescricao());
			quantidade.setText(""+viewProdutoList.get(position).getQuantidade());
		 
			comprado.setChecked(viewProdutoList.get(position).getComprado()>0?true:false);
			
			btnExcluir = (Button)view.findViewById(R.id.buttonAdapterExcluir);
			btnExcluir.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					excluirCompra(viewProdutoList.get(position).getId());
					getProdutos();
				}
			});
			
			btnAtualizar = (Button)view.findViewById(R.id.buttonAdapterAtualizar);
			btnAtualizar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					long id = viewProdutoList.get(position).getId();
					String des = produto.getText().toString();
					String qua = quantidade.getText().toString();
					
					updateCompras(id, des, qua , comprado.isChecked());
					
					Toast.makeText(getApplicationContext(), "Atualizado!",
							Toast.LENGTH_LONG).show();
				}
			});
			
			comprado.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					long id = viewProdutoList.get(position).getId();
					String des = produto.getText().toString();
					String qua = quantidade.getText().toString();
					
					updateCompras(id, des, qua , comprado.isChecked());
					
					Toast.makeText(getApplicationContext(), "Atualizado!",
							Toast.LENGTH_LONG).show();
					
				}
			});

			return view;
		}
	}
	
	private void excluirCompra(long id) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				"http://cesaralbuquerque.hol.es/PHP_hostinger_lista_de_compras/DeletarProduto.php");

		try {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("id", ""+id));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			HttpResponse response = httpclient.execute(httppost);

			if (response.getStatusLine().getStatusCode() == 200) {
				Toast.makeText(getApplicationContext(), "Excluido!",
						Toast.LENGTH_LONG).show();
			}

			Log.e("response", response.getStatusLine().getReasonPhrase());

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

	}
	
	private void updateCompras(long id, String descricao, String quantidade, Boolean comprado){
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				"http://cesaralbuquerque.hol.es/PHP_hostinger_lista_de_compras/AlterarProduto.php");

		try {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
			nameValuePairs.add(new BasicNameValuePair("id", ""+id));
			nameValuePairs.add(new BasicNameValuePair("descricao", descricao));
			nameValuePairs.add(new BasicNameValuePair("quantidade", ""+quantidade));
			nameValuePairs.add(new BasicNameValuePair("comprado", comprado?"1":"0"));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			HttpResponse response = httpclient.execute(httppost);

			if (response.getStatusLine().getStatusCode() == 200) {
//				Toast.makeText(getApplicationContext(), "Atualizado!",
//						Toast.LENGTH_LONG).show();
			}

			Log.e("response", response.getStatusLine().getReasonPhrase());

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
	}
	
	
}
