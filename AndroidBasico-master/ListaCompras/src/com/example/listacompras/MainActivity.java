package com.example.listacompras;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends Activity {

	CheckBox arroz;
	CheckBox carne;
	CheckBox leite;
	CheckBox feijao;
	Button calcular;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		arroz = (CheckBox) findViewById(R.id.checkBoxArroz);
//	 	carne = (CheckBox) findViewById(R.id.checkBoxCarne);  
//	 	leite = (CheckBox) findViewById(R.id.checkBoxLeite);
//    	feijao= (CheckBox) findViewById(R.id.checkBoxFeijao);	
//    	calcular = (Button) findViewById(R.id.buttonCalcular);
//    	
    	calcular.setOnClickListener(new OnClickListener() {
			
 	
			@Override
			public void onClick(View arg0) {
				float total = 0;
				if(arroz.isChecked()){
					total+=2.69;
				}
				
				if(carne.isChecked()){
					total+=9.70;
				}
				
				if(leite.isChecked()){
					total+=5.00;
				}
				
				if(feijao.isChecked()){
					total+=2.30;
				}
				
				
				AlertDialog.Builder msgTotal = new AlertDialog.Builder(MainActivity.this);
				
				msgTotal.setTitle("Total das compras");
				msgTotal.setMessage("Você gastou total de "+total);
				msgTotal.setNeutralButton("Ok", null);
				msgTotal.show();
				
				
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
