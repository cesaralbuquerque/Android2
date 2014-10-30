package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

 
public class MainActivity extends Activity {
	
	EditText login;
	EditText senha; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		login = (EditText) findViewById(R.id.editTextLogin);
		senha = (EditText) findViewById(R.id.editTextSenha);
		
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			 
//				if(login.getText().toString().equals("admin")
//						&&
//				 senha.getText().toString().equals("admin")){
				 
					Intent i = new Intent(MainActivity.this, 
							LogadoActivity.class);   
				 
					i.putExtra("nomeDaPessoaLogada",login.getText().toString());
										
					startActivity(i);
					
					finish();
			
				 
			//					Toast.makeText(
//							getApplicationContext(), 
//							"Logou!", 
//							Toast.LENGTH_LONG).show();
//				}else{
//					Toast.makeText(getApplicationContext(), 
//							"Login ou Senha Errado!", 
//							Toast.LENGTH_LONG).show();
//				}
				
				
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
