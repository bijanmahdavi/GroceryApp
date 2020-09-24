package com.example.groceryappdemo.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.groceryappdemo.models.Item

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATA_NAME, null, DATABASE_VERSION) {



    companion object{
        const val DATA_NAME = "cartData"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "cartItems"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PRICE = "price"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_IMAGE = "image"

        const val createTable = "create table $TABLE_NAME ($COLUMN_ID CHAR(50), $COLUMN_NAME CHAR(50), $COLUMN_PRICE INTEGER, $COLUMN_AMOUNT INTEGER, $COLUMN_IMAGE CHAR(250))"
        const val dropTable = "drop table $TABLE_NAME"
    }


    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL(createTable)
        Log.d("abc", "OnCreate")

    }

    override fun onUpgrade(database: SQLiteDatabase?, p1: Int, p2: Int) {
        database?.execSQL(dropTable)
        onCreate(database)
    }

    // insert new item
    fun addItem(id: String, name: String, price: Int, amount: Int, image: String){
        var idList = getItemIDs()
        Log.d("list", idList.toString())
        var database = writableDatabase
        var contentValues = ContentValues()
        var update = -1
        if(idList.contains(id)) {
/*        for(i in 0 until idList.size) {
            if(idList[i] == id) {
                update = i
            }*/
            //updateItem(id, name, price, amount+1)
            updateItem(id, name, price, amount)
        } else {
            contentValues.put(COLUMN_ID, id)
            contentValues.put(COLUMN_NAME, name)
            contentValues.put(COLUMN_PRICE, price)
            contentValues.put(COLUMN_AMOUNT, amount)
            contentValues.put(COLUMN_IMAGE, image)
            database.insert(TABLE_NAME, null, contentValues)
        }

    }

    // update
    // update item set name ='', email='' where id = 1
    fun updateItem(id: String, name: String, price: Int, amount: Int,){
        var database = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(id.toString())
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, name)
        contentValues.put(COLUMN_PRICE, price)
        contentValues.put(COLUMN_AMOUNT, amount)
        database.update(TABLE_NAME, contentValues, whereClause, whereArgs)
    }

    // delete
    // delete from table where id = 1
    fun deleteItem(id: String){
        var database = writableDatabase
        var whereClause = "$COLUMN_ID = ?"
        var whereArgs = arrayOf(id.toString())
        database.delete(TABLE_NAME, whereClause, whereArgs)
    }

    // select or read
    // select * from item
    // select id, name from item
    // select * from item where id = 1
    fun getItems(): ArrayList<Item>{
        var itemList: ArrayList<Item> = ArrayList()
        var database = writableDatabase
        var columns = arrayOf(
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_PRICE,
            COLUMN_AMOUNT,
            COLUMN_IMAGE
        )
        var cursor = database.query(TABLE_NAME, columns, null, null, null, null, null)
        if(cursor !=null && cursor.moveToFirst()){
            do{
                var id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                var name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                var price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE))
                var quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT))
                var image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                var productItem = Item(id, name, price, quantity, image)
                itemList.add(productItem)

            }while (cursor.moveToNext())
        }
        cursor.close()
        return itemList
    }

    private fun getItemIDs(): ArrayList<String> {
        var idList: ArrayList<String> = ArrayList()
        var database = readableDatabase
        var idCol = arrayOf(COLUMN_ID)
        var cursor = database.query(TABLE_NAME, idCol, null, null, null, null, null)
        if(cursor !=null && cursor.moveToFirst()) {
            do {
                var id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                idList.add(id)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return idList
    }

}