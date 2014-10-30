package com.example.androidavancadocesar.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.androidavancadocesar.DB;
import com.example.androidavancadocesar.model.Usuario;

public class UsuarioDAO {

	private Context ctx;
	private static String table_name = "usuarios";	
	private static String[] columns = { "id", "nome","telefone","sexo"};

	public UsuarioDAO(Context ctx) {
		this.ctx = ctx;
	}

	public boolean insert(Usuario vo) {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();

		ContentValues ctv = new ContentValues();
		ctv.put("nome", vo.getNome());
		ctv.put("telefone", vo.getTelefone());
		ctv.put("sexo", vo.getSexo());
		return db.insert(table_name, null, ctv) > 0;

	}

	public void deleteAll() {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		db.delete(table_name, null, null);
		db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='" + table_name
				+ "';");
		db.close();
	}

	
	public boolean delete(Usuario vo) {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		return (db.delete(table_name, "id=?", new String[] { ""+vo.getId()}) > 0);
	}

	public boolean update(Usuario vo) {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();

		ContentValues ctv = new ContentValues();
		ctv.put("nome", vo.getNome());	 
		ctv.put("telefone", vo.getTelefone());
		ctv.put("sexo", vo.getSexo());
		
		if (db.update(table_name, ctv, "id=?", new String[] { ""+vo.getId()}) > 0) {
			db.close();
			return true;
		} else {
			db.close();
			return false;
		}
	}

	public Usuario getById(Integer ID) {
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();

		Cursor rs = db.query(table_name, columns, "id=?",
				new String[] { ID.toString() }, null, null, null);

		Usuario vo = null;

		if (rs.moveToFirst()) {
			vo = new Usuario();
			vo.setId(rs.getInt(rs.getColumnIndex("id")));
			vo.setNome(rs.getString(rs.getColumnIndex("nome"))); 
			vo.setTelefone(rs.getString(rs.getColumnIndex("telefone")));
			vo.setSexo(rs.getString(rs.getColumnIndex("sexo")));
		}
		db.close();
		return vo;
	}

	public List<Usuario> getAll() {

		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		Cursor rs = db.rawQuery("SELECT * FROM " + table_name, null);
		List<Usuario> lista = new ArrayList<Usuario>();
		while (rs.moveToNext()) {
			Usuario vo = new Usuario(rs.getLong(0), 
					rs.getString(1),rs.getString(2),
					rs.getString(3));
			lista.add(vo);
		}
		db.close();
		return lista;
	}

	public boolean insertList(List<Usuario> vo) {
		int auxCount = 0;
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		db.execSQL("BEGIN IMMEDIATE TRANSACTION");
		ContentValues ctv = new ContentValues();
		for (int i = 0; i < vo.size(); i++) {
			Usuario auxVo = vo.get(i);
			ctv.put("nome", auxVo.getNome());
			ctv.put("telefone", auxVo.getTelefone());
			ctv.put("sexo", auxVo.getSexo());
			db.insert(table_name, null, ctv);
			auxCount++;
		}
		db.execSQL("COMMIT TRANSACTION");
		db.close();
		return auxCount == vo.size();
	}
	
	
}
