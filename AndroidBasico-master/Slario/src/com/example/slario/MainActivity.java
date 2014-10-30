package com.example.slario;

import android.app.Activity;
import android.app.AlertDialog;
 
 
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends Activity {

	Button calular;
	RadioGroup group;
	EditText valor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		calular = (Button) findViewById(R.id.button1);
		group = (RadioGroup) findViewById(R.id.radioGroup1);
		valor = (EditText) findViewById(R.id.editText1);
		calular.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				Double salario = Double.valueOf((valor.getText().toString()));
				int op = group.getCheckedRadioButtonId();

				if (op == R.id.radio0) {
					salario += salario*0.4;
				}

				if (op == R.id.radio1) {
					salario += salario*0.45;
				}

				if (op == R.id.radio2) {
					salario += salario*0.5;
				}
				
				AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this); 
				
				alert.setTitle("Salário");
				alert.setMessage("Novo Salario "+salario);
				alert.setNegativeButton("ok", null);
				alert.show();
				
				
				
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
