package com.example.barcodescanningapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
	
	// ======  Константы для конструктора ====== 
	private static final String DATABASE_NAME = "card_database.db";    // Имя базы данных
	private static final int DATABASE_VERSION = 1;                     // Версия базы данных
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	// ======  Константы для таблицы card_table ======
	public static final String TABLE_NAME = "card_table";
	public static final String ID = "_id";
	public static final String BARCODE = "barcode";
	public static final String CARD_NAME = "card_name";
	
	// ====== Запрос создания таблицы card_table ======
	private static final String SQL_CREATE_CARD_TABLE = "CREATE TABLE " 
													+ TABLE_NAME + " (" + ID + " integer primary key autoincrement,"
													+ BARCODE + " text," + CARD_NAME + " varchar(60));";
	
	// ====== Запрос удаления таблицы card_table ======
	private static final String SQL_DELETE_CARD_TABLE = "drop table if exist " + TABLE_NAME;

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_CARD_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("LOG_TAG", "Обновление базы данных с версии " + oldVersion
				+ " до версии " + newVersion + ", которое удалит все старые данные");
		// ====== Удаление существующих таблиц ======
		db.execSQL(SQL_DELETE_CARD_TABLE);
		// ====== Создание новых экземпляров таблиц ======
		onCreate(db);

	}

}
