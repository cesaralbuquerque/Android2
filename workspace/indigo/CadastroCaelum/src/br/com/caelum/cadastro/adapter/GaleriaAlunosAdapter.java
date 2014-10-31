package br.com.caelum.cadastro.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import br.com.caelum.cadastro.R;
import br.com.caelum.modelo.Aluno;

public class GaleriaAlunosAdapter extends BaseAdapter{

	private List<Aluno> alunos;
	private Context context;
	
	public GaleriaAlunosAdapter(List<Aluno> alunos, Context context){
		this.alunos = alunos;
		this.context = context;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		ImageView foto = new ImageView(context);
		Aluno aluno = alunos.get(position);
		
		if (aluno.getFoto() != null){
			Bitmap bm = BitmapFactory.decodeFile(aluno.getFoto());
			foto.setImageBitmap(Bitmap.createScaledBitmap(bm, 200, 200, true));
		}else{
			foto.setImageResource(R.drawable.smile);
		}
		
		return foto;
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return alunos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}


	
}
