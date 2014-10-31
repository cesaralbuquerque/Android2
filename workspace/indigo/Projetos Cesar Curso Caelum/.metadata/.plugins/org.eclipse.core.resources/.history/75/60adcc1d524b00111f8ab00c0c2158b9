package br.com.caelum;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestaComponentes extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final TextView texto = (TextView) findViewById(R.id.textView1);
        final EditText campo = (EditText) findViewById(R.id.editText1);
        final Button botao = (Button) findViewById(R.id.Button01);
        
        botao.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				texto.setText("Bem Vindo" + campo.getText().toString());
				campo.setText("");
			}
		});
        
    }
}