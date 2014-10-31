package com.geosapiens.questionario;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class QuestionarioActivity extends Activity {
	
	private ViewGroup viewGroup;
	private Button botao;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		viewGroup = (ViewGroup) findViewById(R.id.viewgroup);
        
       
        
        botao = (Button) findViewById(R.id.button1);
        botao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				getLayoutInflater().inflate(R.layout.questao, viewGroup);
				
			}
		});
        
    }
}

