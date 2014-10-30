package com.example.login;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class LogadoActivity extends Activity { 
	
	TextView txt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logado);
		
		
		txt = (TextView) findViewById(R.id.textView1);
		String newString = "nao logou";	
		 
			if(getIntent().getExtras() != null) {
				  newString = getIntent().
			        		getExtras()
			        		.getString("nomeDaPessoaLogada");
			}  
			
			
			txt.setText(newString);
		}
		
 	
		
	}
	
 
