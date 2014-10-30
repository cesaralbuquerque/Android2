package br.com.cherobin.androidavancado_criando_db;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.buttonCadastrar).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// se quiser depois usar o put e get extra
						Intent intent = new Intent(MainActivity.this,
								CadastroUsuarioActivity.class);
						startActivity(intent);

					}
				});

		findViewById(R.id.buttonMostrar).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// se quiser depois usar o put e get extra
						Intent intent = new Intent(MainActivity.this,
								MostrarUsuariosActivity.class);
						startActivity(intent);

					}
				});
		findViewById(R.id.buttonCadastrarLocal).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, CadastroLocalActivity.class);
				startActivity(intent);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
