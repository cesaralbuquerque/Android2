package br.com.geosapiens.questionariodinamico;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class QuestionarioDinamicoActivity extends Activity {
	private ViewGroup lista;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
  
        lista = (ViewGroup)findViewById(R.id.lista);
        
//        Button botaoQuestao = (Button) findViewById(R.id.criaQuestao);
//        botaoQuestao.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				
//			//v = getLayoutInflater().inflate(R.layout.questao, list);	
//			//v = QuestionarioDinamicoActivity.this.getLayoutInflater().inflate(R.layout.questao, list);
//				
//			}
//		});
        
    }
    
   
    
    
}