package com.example.spinner;

 
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText valorDigitado;
	Button btnCalcular;
	Spinner sp;	

	String[] percentual = {"De 40%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%","De 45%","De 50%"};
	ArrayAdapter<String> aPercentual;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		valorDigitado = (EditText) findViewById(R.id.editText1MainSalario);
		btnCalcular = (Button) findViewById(R.id.buttonMainCalculaSalario);
		sp = (Spinner) findViewById(R.id.spinnerMainSalario);
		
		aPercentual = 
new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,percentual);
		
		sp.setAdapter(aPercentual);
		
	
		
		btnCalcular.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if(!valorDigitado.getText().toString().equals("")){
				double valorSalario = 
							Double.valueOf(valorDigitado.getText().toString());
					switch (sp.getSelectedItemPosition()) {
					case 0:
						valorSalario+=valorSalario*0.4;
						break;
						
					case 1:
						valorSalario+=valorSalario*0.45;
						break;
					case 2:
						valorSalario+=valorSalario*0.5;
						break;
					default:
						break;
					}
					Toast.makeText(MainActivity.this, "Novo Salario "+valorSalario,
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
