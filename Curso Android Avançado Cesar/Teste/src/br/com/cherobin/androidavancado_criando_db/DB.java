package br.com.cherobin.androidavancado_criando_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB extends SQLiteOpenHelper {

	private static String DATABASE_NAME = "aula.db";
	private static int DATABASE_VERSION = 5;
	public static final String COLUMN_ID = "_id";
	public static final String TABLE_USUARIOS = "usuarios";
	public static final String TABLE_LOCAIS = "locais";
	public static final String COLUMN_USUARIO = "nome";
	public static final String COLUMN_FONE = "fone";
	public static final String COLUMN_SEXO = "sexo";
	public static final String COLUMN_IDLOCAIS = "idLocais";
	public static final String COLUMN_AVALIACAO = "avaliacao";
	public static final String COLUMN_LAT = "lat";
	public static final String COLUMN_LONGI = "longi";

	private static final String DATABASE_CREATE = "create table "
			+ TABLE_USUARIOS + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_USUARIO
			+ " text not null);";

	// ou
	 private static String usuario = "CREATE TABLE [usuarios] ( " +
	 "[id] INTEGER PRIMARY KEY AUTOINCREMENT,			 " +
	 "[nome] TEXT, [fone] TEXT, [sexo] TEXT, [idLocais] TEXT);";
	 
	 private static String local = "CREATE TABLE [locais] ( " +
	 "[id] INTEGER PRIMARY KEY AUTOINCREMENT,			 " +
	 "[nome] TEXT, [avaliacao] float, [lat] TEXT, [long] TEXT);";

	// reescrevi o create.
//	 public DB(Context context, String name, CursorFactory factory, int
//	 version) {
//	 super(context, name, factory, version);
//	 }

	public DB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		db.execSQL(DATABASE_CREATE);
		db.execSQL(usuario);
		db.execSQL(local);
//		db.execSQL(pessoa);
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DB.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCAIS);
		onCreate(db);
	}

}
