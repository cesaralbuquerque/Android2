package com.example.androidavancadorestcesar.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.androidavancadorestcesar.vo.Dispositivo;

import com.example.androidavancadorestcesar.DB;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DispositivoDAO {
	private Context ctx;
	private static String table_name = "dispositivos";	
	private static String[] columns = { "id", "modelo","memoria","processador","peso","tela"};
	
	
	public DispositivoDAO (Context ctx){
		this.ctx = ctx;
	}
	
	public List<Dispositivo> getAll() {

		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		Cursor rs = db.rawQuery("SELECT * FROM " + table_name, null);
		List<Dispositivo> lista = new ArrayList<Dispositivo>();
		while (rs.moveToNext()) {
			Dispositivo vo = new Dispositivo(rs.getLong(0), 
					rs.getString(1),rs.getString(2),
					rs.getString(3),rs.getString(4),
					rs.getString(5));
			lista.add(vo);
		}
		db.close();
		return lista;
	}
}
