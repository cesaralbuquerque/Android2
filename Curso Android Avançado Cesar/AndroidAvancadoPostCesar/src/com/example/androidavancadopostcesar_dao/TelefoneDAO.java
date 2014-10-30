package com.example.androidavancadopostcesar_dao;

import java.util.ArrayList;
import java.util.List;




import com.example.androidavancadopostcesar.DB;
import com.example.androidavancadopostcesar_vo.Telefone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TelefoneDAO {
private Context ctx;
private static String table_name = "telefone";
private static String[] columns = { "id", "nome", "numero"};

public TelefoneDAO(Context ctx){
	this.ctx = ctx;
}

public boolean insert(Telefone vo) {
	SQLiteDatabase db = new DB(ctx).getWritableDatabase();

	ContentValues ctv = new ContentValues();
	ctv.put("nome", vo.getNome());
	ctv.put("numero", vo.getNumero());
	db.insert(table_name, null, ctv);
	db.close();
	return true;
}

public void deleteAll() {
	SQLiteDatabase db = new DB(ctx).getWritableDatabase();
	db.delete(table_name, null, null);
	db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='" + table_name
			+ "';");
	db.close();
}


public boolean delete(Telefone vo) {
	SQLiteDatabase db = new DB(ctx).getWritableDatabase();
	return (db.delete(table_name, "id=?", new String[] { ""+vo.getId()}) > 0);
}

public boolean update(Telefone vo) {
	SQLiteDatabase db = new DB(ctx).getWritableDatabase();

	ContentValues ctv = new ContentValues();
	ctv.put("nome", vo.getNome());	 
	ctv.put("numero", vo.getNumero());

	if (db.update(table_name, ctv, "id=?", new String[] { ""+vo.getId()}) > 0) {
		db.close();
		return true;
	} else {
		db.close();
		return false;
	}
}

public Telefone getById(Integer ID) {
	SQLiteDatabase db = new DB(ctx).getReadableDatabase();

	Cursor rs = db.query(table_name, columns, "id=?",
			new String[] { ID.toString() }, null, null, null);

	Telefone vo = null;

	if (rs.moveToFirst()) {
		vo = new Telefone();
		vo.setId(rs.getInt(rs.getColumnIndex("id")));
		vo.setNome(rs.getString(rs.getColumnIndex("nome"))); 
		vo.setNumero(rs.getString(rs.getColumnIndex("numero")));
	}
	db.close();
	return vo;
}

public List<Telefone> getAll() {

	SQLiteDatabase db = new DB(ctx).getReadableDatabase();
	Cursor rs = db.rawQuery("SELECT * FROM " + table_name, null);
	List<Telefone> lista = new ArrayList<Telefone>();
	while (rs.moveToNext()) {
		Telefone vo = new Telefone(rs.getLong(0), rs.getString(1), rs.getString(2));
		lista.add(vo);
	}
	db.close();
	return lista;
}

public boolean insertList(List<Telefone> vo) {
	int auxCount = 0;
	SQLiteDatabase db = new DB(ctx).getWritableDatabase();
	db.execSQL("BEGIN IMMEDIATE TRANSACTION");
	ContentValues ctv = new ContentValues();
	for (int i = 0; i < vo.size(); i++) {
		Telefone auxVo = vo.get(i);
		ctv.put("nome", auxVo.getNome()); 
		ctv.put("numero", auxVo.getNumero());
		db.insert(table_name, null, ctv);
		auxCount++;
	}
	db.execSQL("COMMIT TRANSACTION");
	db.close();
	return auxCount == vo.size();
}



}
