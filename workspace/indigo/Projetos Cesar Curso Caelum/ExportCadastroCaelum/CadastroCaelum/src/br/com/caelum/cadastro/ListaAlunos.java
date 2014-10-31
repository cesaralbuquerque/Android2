package br.com.caelum.cadastro;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.modelo.Aluno;

public class ListaAlunos extends Activity {
    /** Called when the activity is first created. */
	
	private List<Aluno> alunos;
	private ListView listaAlunos;
	private Aluno alunoSelecionado;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista);   
        
        AlunoDAO dao = new AlunoDAO(this);
        alunos = dao.getLista();
        dao.close();
        
        //List<String> alunos = Arrays.asList("Anderson", "Filipe", "Filipe", "Filipe", "Filipe", "Filipe", "Filipe", "Filipe");
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this,android.R.layout.simple_list_item_1, alunos);
        
        
       listaAlunos = (ListView)findViewById(R.id.listaAlunos);
       listaAlunos.setAdapter(adapter);
       listaAlunos.setClickable(true);
       
       //click simples
       listaAlunos.setOnItemClickListener(new OnItemClickListener() {    	   
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int posicao,	long id) {
				//Toast.makeText(ListaAlunos.this, "Posição Selecionada: "+posicao, 4).show();
				Intent edicao = new Intent(ListaAlunos.this, Formulario.class);
				edicao.putExtra("alunoSelecionado", (Aluno) listaAlunos.getItemAtPosition(posicao) );
				startActivity(edicao);
			}
       });

       //click longo
       listaAlunos.setOnItemLongClickListener(new OnItemLongClickListener() {
    	   @Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id){
	    		//Toast.makeText(ListaAlunos.this, "Posição selecionada com toque mas LONGO: "+ posicao, 4).show();
				alunoSelecionado = (Aluno) adapter.getItemAtPosition(posicao);
				registerForContextMenu(listaAlunos);
				return false;
			}
       });
       
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem novo = menu.add(0, 0, 0, "Novo");
		novo.setIcon(R.drawable.novo);
		novo.setOnMenuItemClickListener(new OnMenuItemClickListener() {			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				//Toast.makeText(ListaAlunos.this, "Você clicou no novoaluno", 2).show();
				Intent intent = new Intent(ListaAlunos.this, Formulario.class); //"guarda" os estados
				startActivity(intent);
				return false;
			}
		});
		
		
//		MenuItem sincronizar = menu.add(0, 1 , 0, "Sincronizar");
//		sincronizar.setIcon(R.drawable.dial);
		
		MenuItem sincronizar = menu.add(0, 1, 0, "Sincronizar");
		sincronizar.setIcon(R.drawable.dial);
		sincronizar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Sincronismo sincronismo = new Sincronismo(ListaAlunos.this);
				sincronismo.sincronizar();
				return false;
			}
		});
		
		MenuItem galeria = menu.add(0, 2, 0, "Galeria");
		galeria.setIcon(R.drawable.foto);
		galeria.setIntent(new Intent(this, Galeria.class));
		
		MenuItem mapa = menu.add(0, 3, 0, "Mapa");
		mapa.setIcon(R.drawable.mapa);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
//		menu.add(0, 0, 0, "Ligar");
//		menu.add(0, 1, 0, "Enviar SMS");
//		menu.add(0, 2, 0, "Achar no Mapa");
		menu.add(0, 3, 0, "Navegar no Site");
//		menu.add(0, 4, 0, "Deletar");
//		menu.add(0, 5, 0, "Enviar Email");
		/***
		 * declara itens do menu
		 */
		MenuItem deletar = menu.add(0, 4, 0, "Deletar");
		final MenuItem email = menu.add(0, 5, 0, "Enviar E-mail");
		final MenuItem acharMapa = menu.add(0, 2, 0, "Achar no mapa");
		final MenuItem enviarSMS = menu.add(0, 1, 0, "Enviar SMS");
		final MenuItem ligar = menu.add(0, 0, 0, "Ligar");
		
		deletar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				AlunoDAO dao = new AlunoDAO(ListaAlunos.this);
				dao.deletar(alunoSelecionado);
				dao.close();
				
				carregaLista();
				return false;
			}
		});
		email.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			//O emulador não funciona para email
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				/**
				 * configura email
				 * */
				Intent intentEmail = new Intent(Intent.ACTION_SEND);
				intentEmail.setType("menssage/rfc822");
				intentEmail.putExtra(Intent.EXTRA_EMAIL, new String[] {"caelum@caelum.com.br"});
				intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Elogios do curso de android");
				intentEmail.putExtra(Intent.EXTRA_TEXT, "Este curos é ótimo");
				email.setIntent(intentEmail);
				startActivity(Intent.createChooser(intentEmail, "Selecione a sua aplicação de email!"));
				return false;
			}
		});
		acharMapa.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intentMapa = new Intent(Intent.ACTION_VIEW);
				intentMapa.setData(Uri.parse("geo:0,0?z=14&q="+alunoSelecionado.getEndereco()));
				acharMapa.setIntent(intentMapa);
				return false;
			}
		});
		enviarSMS.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intentSMS = new Intent(Intent.ACTION_VIEW);
				intentSMS.setData(Uri.parse("sms:"+alunoSelecionado.getTelefone()));
				intentSMS.putExtra("sms_body", "Mensagem");
				enviarSMS.setIntent(intentSMS);
				return false;
			}
		});
		ligar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intentLigar = new Intent(Intent.ACTION_CALL);
				intentLigar.setData(Uri.parse("tel:"+alunoSelecionado.getTelefone()));
				ligar.setIntent(intentLigar);
				return false;
			}
		});

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		carregaLista();
	}

	private void carregaLista() {
		AlunoDAO dao = new AlunoDAO(this);
		alunos = dao.getLista();
		dao.close();
		ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this,
				android.R.layout.simple_list_item_1, alunos);
		listaAlunos.setAdapter(adapter);
	}
	

	
    
   
    
}