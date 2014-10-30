package com.example.androidavancadorestcesar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB extends SQLiteOpenHelper {

	private static String DATABASE_NAME = "dispositivos.db";
	private static int DATABASE_VERSION = 0;
	 
	public static final String TABLE_DISPOSITIVOS = "dispositivos";
	
 
	private static String dispositivo = "CREATE TABLE [dispositivos] ( " +
	 "[id] INTEGER PRIMARY KEY AUTOINCREMENT,			 " +
	 "[modelo] TEXT,"+
	 "[memoria] TEXT,"+
	 "[processador] TEXT,"+
	 "[peso] TEXT,"+
	 "[tela] TEXT);";
	  

	public DB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
 
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
 		db.execSQL(dispositivo);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DB.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISPOSITIVOS);
		onCreate(db);
	}

}
