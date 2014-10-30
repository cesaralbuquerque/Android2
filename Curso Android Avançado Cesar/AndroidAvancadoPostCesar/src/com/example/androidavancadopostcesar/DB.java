package com.example.androidavancadopostcesar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB extends SQLiteOpenHelper {

	private static String DATABASE_NAME = "telefone.db";
	private static int DATABASE_VERSION = 1;
	 
	public static final String TABLE_TELEFONE = "telefone";
	
 
	private static String telefone = "CREATE TABLE [telefone] ( " +
	 "[id] INTEGER PRIMARY KEY AUTOINCREMENT,			 " +
	 "[nome] TEXT,"+
	 "[numero] TEXT);";
	  

	public DB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
 
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
 		db.execSQL(telefone);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DB.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TELEFONE);
		onCreate(db);
	}

}
