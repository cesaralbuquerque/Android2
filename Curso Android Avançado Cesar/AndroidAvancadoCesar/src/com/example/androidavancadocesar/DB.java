package com.example.androidavancadocesar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB extends SQLiteOpenHelper {

	private static String DATABASE_NAME = "aula.db";
	private static int DATABASE_VERSION = 3;
	 
	public static final String TABLE_USUARIOS = "usuarios";
	public static final String TABLE_LUGARES = "lugares"; 
 
	private static String usuario = "CREATE TABLE [usuarios] ( " +
	 "[id] INTEGER PRIMARY KEY AUTOINCREMENT,			 " +
	 "[nome] TEXT,"+
	 "[telefone] TEXT,"+
	 "[sexo] TEXT);";
	 
	 
	 private static String lugar = "CREATE TABLE [lugares] ( " +
	 "[id] INTEGER PRIMARY KEY AUTOINCREMENT,			 " +
	 "[nome] TEXT,"+
	 "[pontuacao] TEXT,"+
	 "[latitude] TEXT," +
	 "[longitude] TEXT);";

	public DB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
 
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
 		db.execSQL(usuario);
 		db.execSQL(lugar);
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DB.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LUGARES);
		onCreate(db);
	}

	
	
}
