package br.com.caelum.cadastro.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Indicator extends View implements OnTouchListener{

	
	private static final float MAXIMOTELA = 200;
	private static final float MAXIMOALTURA = 30;
	private float x;
	
	public Indicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setOnTouchListener(this);
		setMinimumHeight((int)MAXIMOALTURA);
		setMinimumWidth((int)MAXIMOTELA);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			this.x = event.getX();
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			this.x = event.getX();
			invalidate();
			break;
			
		default:
			break;
		}
		return false;
	}
	
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getSuggestedMinimumWidth(), 
				getSuggestedMinimumHeight());
	}

	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		canvas.drawColor(Color.CYAN);
		Paint p =new Paint();
		p.setColor(Color.RED);
		RectF r = new RectF(0, 0, x, MAXIMOALTURA);
		canvas.drawRect(r, p);
	}

	public double getValor() {
		return x * 10 / MAXIMOTELA;
	}

	public void setValor(double valor) {
		this.x = (float) (MAXIMOTELA * valor / 10);
	}
	
}
