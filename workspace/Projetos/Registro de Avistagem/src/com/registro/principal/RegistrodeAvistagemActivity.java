package com.registro.principal;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery.LayoutParams;
import android.widget.ViewSwitcher.ViewFactory;
import android.database.Cursor; //manupula os dados
import android.database.sqlite.SQLiteDatabase; //banco de dados

public class RegistrodeAvistagemActivity extends Activity implements ViewFactory{

	private LocationManager locationManager;
	private Avistagem avistagem;
	Location location;
	
	/*
	 * Switcher
	 * */
	private ImageSwitcher mISwitcher;  
	private Integer[] imagesIDs = {R.drawable.pic2, R.drawable.pic1, R.drawable.pic2, R.drawable.pic2};
	
	String nomeBanco = "bd_avistagem";
	SQLiteDatabase BancoDados = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		/*
		 * Switcher
		 * */
		mISwitcher = (ImageSwitcher)findViewById(R.id.ImageSwitcher01);
		mISwitcher.setFactory(this);
		// some animation when image changes
		mISwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.slide_in_left));
		mISwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.slide_out_right));         
		
		
		Gallery gallery = (Gallery) findViewById(R.id.gallery1);
		gallery.setAdapter(new ImageAdapter(this));
		gallery.setOnItemClickListener(new OnItemClickListener(){

			public void	onItemClick(AdapterView<?> parent, View v, int position, long id){
				mISwitcher.setImageResource(imagesIDs[position]);
				System.out.println(mISwitcher.getChildAt(position));
			}			
		});
		
		
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

		final Spinner spnSolicitado  = (Spinner) findViewById(R.id.solicitado);
		ArrayAdapter<CharSequence> adapterSolicitado = ArrayAdapter.createFromResource(
				this, R.array.solicitado, android.R.layout.simple_spinner_item);
		adapterSolicitado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnSolicitado.setAdapter(adapterSolicitado);

		final Spinner spnRealizado = (Spinner) findViewById(R.id.realizado);
		ArrayAdapter<CharSequence> adapterRealizado = ArrayAdapter.createFromResource(
				this, R.array.realizado, android.R.layout.simple_spinner_item);
		adapterRealizado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnRealizado.setAdapter(adapterRealizado);
		
		
		final TextView dataPorra = (TextView) findViewById(R.id.textViewDate);
		Calendar c =  Calendar.getInstance();
		int dia = c.get(Calendar.DAY_OF_MONTH);
		int mes = c.get(Calendar.MONTH);
		int ano = c.get(Calendar.YEAR);
		String data = dia+"/"+(mes+1)+"/"+ano;
		dataPorra.setText(data);

		final EditText profundidade = (EditText) findViewById(R.id.txtProfundidade);
		final EditText numeroFilhotes = (EditText) findViewById(R.id.txtFilhote);
		final EditText numeroAdultos = (EditText) findViewById(R.id.txtAdultos);
		final EditText horaSolicitado = (EditText) findViewById(R.id.txtSolicitado);
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
				avistagem.setSolicitado(spnSolicitado.getSelectedItem().toString());
				avistagem.setHoraSolicitado(horaSolicitado.getText().toString());
				avistagem.setRealizado(spnRealizado.getSelectedItem().toString());
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
	
/*
 * Switcher
 * */	
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}
	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.FILL_PARENT,ImageSwitcher.LayoutParams.FILL_PARENT));
		return i; 
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

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return imagesIDs.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView galleryview = new ImageView(mContext);
			galleryview.setImageResource(imagesIDs[position]);
			galleryview.setAdjustViewBounds(true);
			galleryview.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			galleryview.setPadding(5, 0, 5, 0);
			galleryview.setBackgroundResource(android.R.drawable.picture_frame);            
			return galleryview; 
		}
	}
	
	
	
	
	
}