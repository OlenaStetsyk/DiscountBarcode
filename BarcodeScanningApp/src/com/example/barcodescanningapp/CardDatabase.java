package com.example.barcodescanningapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CardDatabase {
	
	private DBHelper db_helper;
	private SQLiteDatabase db;
	private ContentValues cv;
	Cursor cursor;
	private static String  SQL_QUERY = null;
	
	public CardDatabase(Context context) {
		db_helper = new DBHelper(context);
		db = db_helper.getWritableDatabase();
		//db.execSQL("drop table if exist " + db_helper.TABLE_NAME);
	}
	// ====== Функция добавления новой карты в таблицу card_table ======
	public void addCard(String BARCODE, String CARD_NAME) {
	  	cv = new ContentValues();
	  	cv.put("barcode", BARCODE);
	  	cv.put("card_name", CARD_NAME);
	  	// ====== Метод вставки данных ======
	  	db.insert(db_helper.TABLE_NAME, null, cv); 		
	  	}
	
	// ====== Функция удаления карты из таблицы card_table ======
	public void deleteCard(int ID){
		SQL_QUERY = "delete from " + db_helper.TABLE_NAME + " where _id = " + ID + ";";
	  	db.execSQL(SQL_QUERY);
	}
	
	// ====== Функция чтения данных из таблицы card_table ======
	public List<String> readDataFromCardTable(){
		SQL_QUERY = "SELECT " + db_helper.BARCODE + " FROM " + db_helper.TABLE_NAME;
		List<String> list = new ArrayList<String>();
		cursor = db.rawQuery(SQL_QUERY, null);
		while (cursor.moveToNext()) {
			String barcode = cursor.getString(cursor.getColumnIndex(db_helper.BARCODE));
			list.add(barcode);
			//String card_name = cursor.getString(cursor.getColumnIndex(db_helper.CARD_NAME));
			//System.out.println("ROW HAS BARCODE " + barcode + " AND NAME " + card_name);
		}
		if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
	}
	
}
