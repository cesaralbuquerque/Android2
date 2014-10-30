package br.com.cherobin.aula.listacontatos;

import br.com.cherobin.aula.listacontatos.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ListView lista;
	
	String[] percentual = {"Ricardo", "Joao", "Pedro","Tiago","Lucas","Predo 2"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lista = (ListView) findViewById(R.id.listView1);
		
	
		ArrayAdapter<String> nomes = 
				new ArrayAdapter<String>(this, 
						android.R.layout.simple_list_item_checked, 
						percentual);
		
		lista.setAdapter(nomes);
		
		
		lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2,
					long arg3) { 
					
				CheckedTextView textView = (CheckedTextView)arg0.getChildAt(arg2);
				textView.setChecked(!textView.isChecked());
			 
				//	final String item = (String) arg0.getItemAtPosition(arg2);
//				
//				Toast.makeText(MainActivity.this, "item "+item			 ,
// 						Toast.LENGTH_LONG).show();
 
				
 			
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
