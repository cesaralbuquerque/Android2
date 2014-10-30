package com.example.androidavacadolistadecompras;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	Button btnCadastrarProduto;
	Button btnListarProdutos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnCadastrarProduto = (Button) findViewById(R.id.ButtonMainCadastrarProduto);
		btnListarProdutos = (Button) findViewById(R.id.ButtonMainListarProdutos);

		btnCadastrarProduto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						CadastrarCompraActivity.class);
				startActivity(intent);
			}
		});

		btnListarProdutos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						ListarComprasActivity.class);
				startActivity(intent);
			}
		});

	}

}
