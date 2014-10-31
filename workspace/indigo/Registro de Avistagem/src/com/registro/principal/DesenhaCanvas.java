package com.registro.principal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class DesenhaCanvas extends View {
	private static final ImageView imagem = null;

	private static final Bitmap bitmap = null;

	private Canvas _myCanvas;
	private Paint paint;
	private float XXX;
	private float YYY;
	private int larguraView;
	private int alturaView;
	private float meioLarguraView;
	private float meioAlturaView;
	private Bitmap imagemDesenha;
	private Drawable testeDrawable;
	private Canvas testeCanvas;
	private Canvas meioView;
	private Paint paintMeioView;
	private float scaleLine;

	public DesenhaCanvas(Context context) {
		super(context);
		paint = new Paint();
		_myCanvas = new Canvas();
	}

	public DesenhaCanvas(Context context, AttributeSet attr) {
		super(context, attr);
		paint = new Paint();
		_myCanvas = new Canvas();
		paintMeioView = new Paint();

		this.setBackgroundResource(R.drawable.radar_cesar);

		// testeDrawable.draw(imagemDesenha);
		// testeDrawable.setBitmap(imagemDesenha);
		// testeCanvas.draw(testeDrawable);
		// this.setBackgroundDrawable(kkk);
		// _myCanvas.setBitmap(imagemDesenha);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		// canvas.drawBitmap(this.imagemDesenha, XXX, YYY, null);
		if (tamanhoLinha() <= meioLarguraView) {
			paint.setColor(Color.BLUE);
			canvas.drawCircle(XXX, YYY, 10, paint);
			paintMeioView.setColor(Color.WHITE);

			canvas.drawCircle(meioLarguraView, meioAlturaView, 5, paintMeioView);
			canvas.drawLine(meioLarguraView, meioAlturaView, XXX, YYY, paint);
		}
		// System.out.println("tamanho da linha: " + tamanhoLinha());
		// System.out.println("angulo: " + (anguloLinha() * 180/ Math.PI) );

		invalidate();
	}

	// Touch-input handler
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		alturaView = this.getMeasuredHeight();
		larguraView = this.getMeasuredWidth();
		meioAlturaView = (alturaView / 2);
		meioLarguraView = (larguraView / 2);

		XXX = event.getX();
		YYY = event.getY();
		// if(alturaView >= YYY && larguraView >= XXX){
		// onDraw(_myCanvas);
		// }else{
		// onDraw(_myCanvas, larguraView-10, alturaView-10);
		// }

		return true; // Event handled
	}

	public float tamanhoLinha() {
		float regua, xQuadrado, yQuadrado, xMenosX, yMenosY;
		xMenosX = (meioLarguraView - XXX);
		yMenosY = (meioAlturaView - YYY);
		xQuadrado = (float) Math.pow(xMenosX, 2);
		yQuadrado = (float) Math.pow(yMenosY, 2);

		regua = (float) Math.sqrt((xQuadrado + yQuadrado));
		return regua;
	}

	public float anguloLinha() {
		float angulo;
		float y = meioAlturaView - YYY;
		float x = meioLarguraView - XXX;
		angulo = (float) Math.atan2(y, x);
		return angulo;
	}

	// protected void onDraw(Canvas canvas, int X, int Y) {
	//
	// //canvas.drawBitmap(this.imagemDesenha, XXX, YYY, null);
	// paint.setColor(Color.BLUE);
	// canvas.drawCircle(X, Y, 10, paint);
	//
	// paintMeioView.setColor(Color.WHITE);
	// canvas.drawCircle(meioLarguraView, meioAlturaView, 5, paintMeioView);
	// canvas.drawLine(meioLarguraView, meioAlturaView, X, Y, paint);
	//
	// // System.out.println("tamanho da linha: " + tamanhoLinha());
	// // System.out.println("angulo: " + (anguloLinha() * 180/ Math.PI) );
	//
	//
	// invalidate();
	// }

}
