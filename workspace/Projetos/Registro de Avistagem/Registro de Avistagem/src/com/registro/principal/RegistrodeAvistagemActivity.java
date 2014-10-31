package com.registro.principal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.database.Cursor; //manupula os dados
import android.database.sqlite.SQLiteDatabase; //banco de dados

public class RegistrodeAvistagemActivity extends Activity {

	private LocationManager locationManager;
	private Avistagem avistagem;
	Location location;

	String nomeBanco = "bd_avistagem";
	SQLiteDatabase BancoDados = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/*
		 * Gera o banco ao iniciar o programa.
		 * */
		criaBanco();

		final Spinner estadoMar = (Spinner) findViewById(R.id.estado_mar);
		ArrayAdapter<CharSequence> adapterEstado_Mar = ArrayAdapter.createFromResource(
				this, R.array.estado_mar, android.R.layout.simple_spinner_item);
		adapterEstado_Mar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		estadoMar.setAdapter(adapterEstado_Mar);

		final Spinner visibilidade = (Spinner) findViewById(R.id.visibilidade);
		ArrayAdapter<CharSequence> adapterVisibilidade = ArrayAdapter.createFromResource(
				this, R.array.visibilidade, android.R.layout.simple_spinner_item);
		adapterVisibilidade .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		visibilidade.setAdapter(adapterVisibilidade);

		final Spinner ondulacao = (Spinner) findViewById(R.id.ondulacao);
		ArrayAdapter<CharSequence> adapterOndulacao = ArrayAdapter.createFromResource(
				this, R.array.ondulacao, android.R.layout.simple_spinner_item);
		adapterOndulacao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ondulacao.setAdapter(adapterOndulacao);

		final Spinner grupo = (Spinner) findViewById(R.id.grupo);
		ArrayAdapter<CharSequence> adapterGrupo = ArrayAdapter.createFromResource(
				this, R.array.grupo, android.R.layout.simple_spinner_item);
		adapterGrupo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		grupo.setAdapter(adapterGrupo);

		final Spinner comportamento  = (Spinner) findViewById(R.id.comportamento);
		ArrayAdapter<CharSequence> adapterComportamento = ArrayAdapter.createFromResource(
				this, R.array.comportamento, android.R.layout.simple_spinner_item);
		adapterComportamento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		comportamento.setAdapter(adapterComportamento);

		final Spinner confianca  = (Spinner) findViewById(R.id.confianca);
		ArrayAdapter<CharSequence> adapterConfianca = ArrayAdapter.createFromResource(
				this, R.array.confianca, android.R.layout.simple_spinner_item);
		adapterConfianca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		confianca.setAdapter(adapterConfianca);

		final Spinner canhao  = (Spinner) findViewById(R.id.canhao);
		ArrayAdapter<CharSequence> adapterCanhao = ArrayAdapter.createFromResource(
				this, R.array.canhao, android.R.layout.simple_spinner_item);
		adapterCanhao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		canhao.setAdapter(adapterCanhao);

		final Spinner solicitado  = (Spinner) findViewById(R.id.solicitado);
		ArrayAdapter<CharSequence> adapterSolicitado = ArrayAdapter.createFromResource(
				this, R.array.solicitado, android.R.layout.simple_spinner_item);
		adapterSolicitado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		solicitado.setAdapter(adapterSolicitado);

		final Spinner realizado = (Spinner) findViewById(R.id.realizado);
		ArrayAdapter<CharSequence> adapterRealizado = ArrayAdapter.createFromResource(
				this, R.array.realizado, android.R.layout.simple_spinner_item);
		adapterRealizado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		realizado.setAdapter(adapterRealizado);

		final EditText profundidade = (EditText) findViewById(R.id.txtProfundidade);
		final EditText numeroFilhotes = (EditText) findViewById(R.id.txtFilhote);
		final EditText numeroAdultos = (EditText) findViewById(R.id.txtAdultos);
		final EditText horaSolicitado = (EditText) findViewById(R.id.txtSolicitacao);
		final EditText horaRealizado = (EditText) findViewById(R.id.txtRealizado);
		final EditText tempoInterrupcao = (EditText) findViewById(R.id.txtTempoInterrupcao);
		//		final EditText latitude = (EditText) findViewById(R.id.txtLatitude);
		//		final EditText longitude = (EditText) findViewById(R.id.txtLongitude);
		final View hora = (View)findViewById(R.id.digitalClock1);
		TextView txtLatitude = (TextView)findViewById(R.id.txtLatitude);
		TextView txtLongitude = (TextView)findViewById(R.id.txtLongitude);


		/**
		 * Gera dados do GPS
		 */
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		String bestProvider = locationManager.getBestProvider(new Criteria(),true);
		location = locationManager.getLastKnownLocation(bestProvider);
		location = new Location(bestProvider);
		if(location != null){
			/*
			 * setLatitude e logitude de forma arcaica somente para teste.
			 * */
			location.setLatitude(50.00);
			location.setLongitude(50.00);
			txtLatitude.setText(String.valueOf(location.getLatitude()));
			txtLongitude.setText(String.valueOf(location.getLongitude()));
		}		

		final Button botao = (Button) findViewById(R.id.btnConfirmar);
		botao.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				avistagem = new Avistagem();				
				//Spinner estado = (Spinner) findViewById(R.id.canhao);
				//System.out.println("teste: " + estado.getSelectedItem());
				try{
				avistagem.setLatitude(location.getLatitude());
				avistagem.setLongitude(location.getLongitude());
				avistagem.setProfundidade(Integer.parseInt(profundidade.getText().toString()));
				avistagem.setEstadoMar(estadoMar.getSelectedItem().toString());
				avistagem.setVisibilidade(visibilidade.getSelectedItem().toString());
				avistagem.setOndulacao(ondulacao.getSelectedItem().toString());
				avistagem.setGrupo(grupo.getSelectedItem().toString());
				avistagem.setnAdultos(Integer.parseInt((numeroAdultos.getText().toString())));
				avistagem.setnFilhotes(Integer.parseInt((numeroFilhotes.getText().toString())));
				avistagem.setComportamento(comportamento.getSelectedItem().toString());
				avistagem.setConfianca(confianca.getSelectedItem().toString());
				avistagem.setCanhao(canhao.getSelectedItem().toString());
				avistagem.setSolicitado(solicitado.getSelectedItem().toString());
				avistagem.setHoraSolicitado(horaSolicitado.getText().toString());
				avistagem.setRealizado(realizado.getSelectedItem().toString());
				avistagem.setHoraRealizado(horaRealizado.getText().toString());
				avistagem.setTempoInterrupcao(tempoInterrupcao.getText().toString());

				//avistagem.setHora(hora.toString());  arrumar hora
				System.out.println("funcionaaaa "+ avistagem.getHora());
				} catch (Exception e) {
					System.out.println(e);
					System.out.println("NAOOOOO funcionaaaa "+ avistagem.getVisibilidade());
				}

				insereBanco(avistagem);
			}
		});
	}


	public void criaBanco(){
		try {
			BancoDados = openOrCreateDatabase(nomeBanco,MODE_WORLD_READABLE, null);
			String criaSQL = "CREATE TABLE avistagem (cod_avistagem INTEGER PRIMARY KEY, data TEXT, hora TEXT, " +
					"latitude NUMERIC, longitude NUMERIC, profundidade NUMERIC, estado_mar TEXT, visibilidade TEXT, ondulacao TEXT," +
					" cod_animal NUMERIC, grupo TEXT, numero_filhotes NUMERIC, numero_adultos NUMERIC, comportamento TEXT, confianca TEXT," +
					" canhao TEXT, solicitado TEXT, realizado TEXT, tempo_interrupcao TEXT, hora_solicitado TEXT, hora_realizado TEXT);";
			BancoDados.execSQL(criaSQL);
			//BancoDados.execSQL("CREATE TABLE lalala (cod_avistagem INTERGER PRIMARY KEY, data TEXT)");
			mensagemErro("Banco de Dados", "Criado com sucesso.");
		} catch (Exception erro) {
			mensagemErro("Banco de Dados", "Não foi possível a implementação do banco." + erro);
		}
	}
	
	public void insereBanco(Avistagem objeto){
		try {
			BancoDados = openOrCreateDatabase(nomeBanco, MODE_WORLD_READABLE, null);
			String insereSQL = "INSERT INTO avistagem (hora, latitude, longitude, profundidade, estado_mar, visibilidade, ondulacao, " +
					"cod_animal, grupo, numero_filhotes, numero_adultos, comportamento, confianca, canhao, solicitado, realizado, " +
					"tempo_interrupcao, hora_solicitado, hora_realizado) VALUES ( \"" + objeto.getHora() + "\",\"" + objeto.getLatitude()+ "\",\""
					+ objeto.getLongitude() + "\",\"" + objeto.getProfundidade()+ "\",\"" + objeto.getEstadoMar()+ "\",\"" + objeto.getVisibilidade()+ "\",\"" 
					+ objeto.getOndulacao()+ "\",\"" + objeto.getCodAnimalAvistado() + "\",\"" + objeto.getGrupo() + "\",\"" + objeto.getnFilhotes() + "\",\"" 
					+ objeto.getnAdultos()+ "\",\"" + objeto.getComportamento()+ "\",\"" + objeto.getConfianca()+ "\",\"" + objeto.getCanhao()+ "\",\"" 
					+ objeto.getSolicitado()+ "\",\"" + objeto.getRealizado() +	"\",\"" + objeto.getTempoInterrupcao() + "\",\""
					+ objeto.getSolicitado() + "\",\"" + objeto.getRealizado() + "\")";
			BancoDados.execSQL(insereSQL);
			mensagemErro("Banco de Dados", "Criado com sucesso.");
		} catch (Exception erro) {
			mensagemErro("Banco de Dados", "Não foi possível a implementação do banco." + erro);
		}
	}

	public void mensagemErro(String tituloMensagem, String Alerta){
		AlertDialog.Builder Mensagem = new AlertDialog.Builder(RegistrodeAvistagemActivity.this);
		Mensagem.setTitle(tituloMensagem);
		Mensagem.setMessage(Alerta);
		Mensagem.setNeutralButton("OK", null);
		Mensagem.show();
	}

}