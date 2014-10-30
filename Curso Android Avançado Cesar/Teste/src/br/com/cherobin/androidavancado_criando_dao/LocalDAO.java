package br.com.cherobin.androidavancado_criando_dao;

import java.util.ArrayList;
import java.util.List;

import br.com.cherobin.androidavancado_criando_db.DB;
import br.com.cherobin.androidavancado_criando_vo.Local;
import br.com.cherobin.androidavancado_criando_vo.Usuario;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
 

public class LocalDAO {
	private Context ctx;
	private static String table_name = "locais";	
	private static String[] columns = { "id", "nome", "avaliacao", "lat", "longi"};

	public LocalDAO(Context ctx) {
		this.ctx = ctx;
	}

	public boolean insert(Local vo) {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();

		ContentValues ctv = new ContentValues();
		ctv.put("nome", vo.getNome());
		ctv.put("avaliacao", vo.getAvaliacao());
		ctv.put("lat", vo.getLat());
		ctv.put("longi", vo.getLongi());
		return db.insert(table_name, null, ctv) > 0;

	}

	public void deleteAll() {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		db.delete(table_name, null, null);
		db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='" + table_name
				+ "';");
		db.close();
	}

	
	public boolean delete(Local vo) {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		return (db.delete(table_name, "id=?", new String[] { ""+vo.getId()}) > 0);
	}

	public boolean update(Local vo) {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();

		ContentValues ctv = new ContentValues();
		ctv.put("nome", vo.getNome());	 
		ctv.put("avaliacao", vo.getAvaliacao());	
		ctv.put("lat", vo.getLat());
		ctv.put("longi", vo.getLongi());

		if (db.update(table_name, ctv, "id=?", new String[] { ""+vo.getId()}) > 0) {
			db.close();
			return true;
		} else {
			db.close();
			return false;
		}
	}

	public Local getById(Integer ID) {
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();

		Cursor rs = db.query(table_name, columns, "id=?",
				new String[] { ID.toString() }, null, null, null);

		Local vo = null;

		if (rs.moveToFirst()) {
			vo = new Local();
			vo.setId(rs.getInt(rs.getColumnIndex("id")));
			vo.setNome(rs.getString(rs.getColumnIndex("nome"))); 
			vo.setAvaliacao(rs.getFloat(rs.getColumnIndex("avaliacao")));
			vo.setLat(rs.getString(rs.getColumnIndex("lat")));
			vo.setLongi(rs.getString(rs.getColumnIndex("longi")));
		}
		db.close();
		return vo;
	}

	public List<Local> getAll() {

		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		Cursor rs = db.rawQuery("SELECT * FROM " + table_name, null);
		List<Local> lista = new ArrayList<Local>();
		while (rs.moveToNext()) {
			Local vo = new Local(rs.getLong(0), rs.getString(1), rs.getFloat(2), rs.getString(3), rs.getString(3));
			lista.add(vo);
		}
		db.close();
		return lista;
	}

	public boolean insertList(List<Local> vo) {
		int auxCount = 0;
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		db.execSQL("BEGIN IMMEDIATE TRANSACTION");
		ContentValues ctv = new ContentValues();
		for (int i = 0; i < vo.size(); i++) {
			Local auxVo = vo.get(i);
			ctv.put("nome", auxVo.getNome()); 
			ctv.put("avaliacao", auxVo.getAvaliacao());
			ctv.put("lat", auxVo.getLat());
			ctv.put("longi", auxVo.getLongi());
			db.insert(table_name, null, ctv);
			auxCount++;
		}
		db.execSQL("COMMIT TRANSACTION");
		db.close();
		return auxCount == vo.size();
	}
}
