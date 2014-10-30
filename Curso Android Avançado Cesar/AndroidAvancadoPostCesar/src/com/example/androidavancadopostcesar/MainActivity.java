package com.example.androidavancadopostcesar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.buttonCadastrarTelefone).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(MainActivity.this, CadastrarTelefoneActivity.class);
				 startActivity(intent);
			}
		});
		
		findViewById(R.id.buttonListarTelefone).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(MainActivity.this, ListarTelefonesActivity.class);
				 startActivity(intent);
			}
		});
		
		findViewById(R.id.buttonSincronizarTelefone).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(MainActivity.this, SincronizarTelefonesActivity.class);
				 startActivity(intent);
			}
		});
		
		
	}
}
