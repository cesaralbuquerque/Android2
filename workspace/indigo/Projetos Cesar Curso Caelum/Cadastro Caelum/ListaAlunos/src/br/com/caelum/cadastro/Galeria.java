package br.com.caelum.cadastro;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Gallery;
import br.com.caelum.cadastro.adapter.GaleriaAlunosAdapter;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.modelo.Aluno;

public class Galeria extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.galeria);
	
	AlunoDAO dao = new AlunoDAO(this);
	List<Aluno> alunos = dao.getLista();
	dao.close();
	
	Gallery galeria = (Gallery) findViewById(R.id.gallery);
	GaleriaAlunosAdapter adapter = new GaleriaAlunosAdapter(alunos, this);
	galeria.setAdapter(adapter);
	
	}
}
