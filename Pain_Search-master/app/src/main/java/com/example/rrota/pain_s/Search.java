package com.example.rrota.pain_s;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by KamillaKhairullina
 * Поиск
 */
public class Search {
    private DatabaseHelper dbHelper;

    //Конструктор
    public Search(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Cursor getDrugsListByKeyword(String search) {
        //Открытие бд для чтения
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Выбор данных по искомому слову
        String selectQuery = "SELECT  rowid as " +
                DatabaseHelper.COLUMN_ID + "," +
                DatabaseHelper.COLUMN_NAME + "," +
                DatabaseHelper.COLUMN_PRICE + "," +
                DatabaseHelper.COLUMN_IMG +
                " FROM " + DatabaseHelper.TABLE +
                " WHERE " + DatabaseHelper.COLUMN_NAME + "  LIKE  '%" + search + "%' ";


        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }




}