package com.example.radiobutton;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button btnCalcula;
	RadioGroup radioGroup;
	EditText valorSalario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		valorSalario = (EditText) findViewById(R.id.editTextMainSalario);
		btnCalcula = (Button) findViewById(R.id.buttonMainBtnCalcular);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroupAumentaSalario);

		btnCalcula.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (!valorSalario.getText().toString().equals("")) {
					double valorDigitado = Double.valueOf(valorSalario
							.getText().toString());

					int op = radioGroup.getCheckedRadioButtonId();

					if (op == R.id.radioAumentoSalario40) {
						// Toast.makeText(MainActivity.this, "salario 40",
						// Toast.LENGTH_LONG).show();
						
						valorDigitado = valorDigitado *1.4;
					}

					if (op == R.id.radioAumentaSalario45) {
						// Toast.makeText(MainActivity.this, "salario 45",
						// Toast.LENGTH_LONG).show();
						valorDigitado = valorDigitado *1.45;
					}

					if (op == R.id.radioAumentaSalario50) {
						// Toast.makeText(MainActivity.this, "salario 50",
						// Toast.LENGTH_LONG).show();
						valorDigitado = valorDigitado *1.5;
					}

					AlertDialog.Builder msg = new AlertDialog.Builder(
							MainActivity.this);
					msg.setTitle("Novo Salário " );
					msg.setMessage("Valor do Salário " + valorDigitado);
					msg.setNegativeButton("ok", null);
					msg.show();

				}else{
					 Toast.makeText(MainActivity.this, "Digite o valor",
					 Toast.LENGTH_LONG).show();
				}
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
