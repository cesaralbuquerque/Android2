package br.com.caelum.cadastro;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.modelo.Aluno;

public class Formulario extends Activity{

	EditText nome;
	EditText telefone;
	EditText site;
	RatingBar nota;
	EditText endereco;
	ImageButton button;
	
	private Aluno aluno = new Aluno();
	/**
	 * TIRA_FOTO é um inteiro que serve somente para nomear o arquivo(foto)*/
	private static final int TIRA_FOTO = 101;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		
		nome = (EditText) findViewById(R.id.nome);
		telefone = (EditText) findViewById(R.id.telefone);
		site = (EditText) findViewById(R.id.site);
		nota = (RatingBar) findViewById(R.id.nota);
		endereco = (EditText) findViewById(R.id.endereco);
		button = (ImageButton) findViewById(R.id.imagem);
		button.setImageResource(R.drawable.smile);
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String arquivo = Environment.getExternalStorageDirectory() + "/"
						+ System.currentTimeMillis() + ".jpg";
				File file = new File(arquivo);
				Uri outputFileUri = Uri.fromFile(file);
				
				aluno.setFoto(arquivo);
				
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
				
				startActivityForResult(intent, TIRA_FOTO);
			}
		});
		
		
		aluno = (Aluno)getIntent().getSerializableExtra("alunoSelecionado");
		
		/**
		 * verifica se o aluno é novo ou não*/
		if(aluno == null){
			aluno = new Aluno();
		}else{
			Button b = (Button) findViewById(R.id.botao);
			b.setText("Alterar");
			
			nome.setText(aluno.getNome());
			telefone.setText(aluno.getTelefone());
			site.setText(aluno.getSite());
			nota.setRating((float) aluno.getNota());
			endereco.setText(aluno.getEndereco());
		}
		
		/**
		 * Verifica se tem imagem */
		carregaImagem();
		
		final Button botao = (Button) findViewById(R.id.botao);
		botao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				aluno.setNome(nome.getEditableText().toString());
				aluno.setTelefone(telefone.getEditableText().toString());
				aluno.setSite(site.getEditableText().toString());
				aluno.setNota(nota.getRating());
				aluno.setEndereco(endereco.getEditableText().toString());
				
				AlunoDAO dao = new AlunoDAO(Formulario.this);
				if(aluno.getId() == null){
					dao.inserir(aluno);
				}else{
					dao.alterar(aluno);
				}
				dao.close();
				
				//Toast.makeText(Formulario.this, "Você clicou no botão", Toast.LENGTH_LONG).show();
				
				finish(); //volta para o onStart da ListaAlunos
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == TIRA_FOTO){
			if(resultCode != RESULT_OK){
				aluno.setFoto(null);
			}
			carregaImagem();
		}
	}
	
	private void carregaImagem(){
		if(aluno.getFoto() != null){
			Bitmap bm = BitmapFactory.decodeFile(aluno.getFoto());
			bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
			
			ImageButton button = (ImageButton) findViewById(R.id.imagem);
			button.setImageBitmap(bm);
		}
	}

}
