package com.example.hele.jizhangben;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hele on 2017/3/19.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String COST_MONEY = "cost_money";
    public static final String COST_DATE = "cost_date";
    public static final String COST_TITLE = "cost_title";

    public DatabaseHelper(Context context) {
        super(context, "immoc_daily", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists imooc_cost("+
                "id integer primary key, "+
                "cost_title varchar, "+
                "cost_date varchar, "+
                "cost_money varchar)");
    }

    public void insertCost(CostBean costBean){
        SQLiteDatabase database=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COST_TITLE,costBean.costTitle);
        cv.put(COST_DATE,costBean.costDate);
        cv.put(COST_MONEY,costBean.costMoney);
        database.insert("imooc_cost",null,cv);
    }

    public Cursor getAllCostData(){
        SQLiteDatabase database=getWritableDatabase();
        return database.query("imooc_cost",null,null,null,null,null,"cost_date "+"ASC");
    }

    public void deleteAllData(){
        SQLiteDatabase database=getWritableDatabase();
        database.delete("imooc_cost",null,null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
