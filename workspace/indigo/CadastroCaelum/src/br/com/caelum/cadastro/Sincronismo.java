package br.com.caelum.cadastro;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.modelo.Aluno;

public class Sincronismo {

	private String endereco = "http://www.caelum.com.br/mobile?dado=";
	private Context context;
	private ProgressDialog progress;
	private Toast aviso;
	
	public Sincronismo(Context context){
		this.context = context;
	}
	
	public void sincronizar(){
		progress = ProgressDialog.show(context, "Aguarde...", "Enviando dados para a web!!!", true);
		aviso = Toast.makeText(context, "Dados enviados com sucesso!!!", Toast.LENGTH_LONG);
		new Thread(new Runnable(){
			public void run(){
				try{
					Thread.sleep(2000);
				}catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				AlunoDAO dao = new AlunoDAO(context);
				List<Aluno> lista = dao.getLista();
				dao.close();
				
				HttpClient httpclient = new DefaultHttpClient();
				
				String listaJSON = new AlunoConverter().toJSON(lista);
				
				Log.i("envio", listaJSON);
				
				HttpPost httpPost = new HttpPost(endereco);
				try {
					httpPost.setEntity(new StringEntity(listaJSON));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				httpPost.setHeader("Accept", "application/json");
				httpPost.setHeader("Content-type", "application/json");
				
				HttpResponse response = null;
				try {
					response = httpclient.execute(httpPost);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					aviso.setText(EntityUtils.toString(response.getEntity()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				progress.dismiss();
				aviso.show();
			}
		}).start();
	}
}
