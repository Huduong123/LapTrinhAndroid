package com.example.projectandroidcuoiki.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseUser extends SQLiteOpenHelper {

    public static final String databaseName = "Signup.db";

    public DatabaseUser(@Nullable Context context) {

        super(context, "Signup.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create Table user(id INTEGER PRIMARY KEY AUTOINCREMENT ,username TEXT NOT NULL, email TEXT, password TEXT NOT NULL, hovaten TEXT, sodienthoai TEXT, diachi TEXT)");


        sqLiteDatabase.execSQL("CREATE TABLE user_orders(" +
                "id_order INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER NOT NULL, " +
                "order_date TEXT DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE)");


        sqLiteDatabase.execSQL("CREATE TABLE orders(" +
                "id_order INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "product_name TEXT NOT NULL, " +
                "price REAL NOT NULL, " +
                "quantity INTEGER NOT NULL, " +
                "size TEXT, " +
                "order_date TEXT DEFAULT CURRENT_TIMESTAMP, " +
                "order_id INTEGER NOT NULL, " +
                "user_id INTEGER NOT NULL, " +
                "FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                "FOREIGN KEY (order_id) REFERENCES user_orders(id_order) ON DELETE CASCADE ON UPDATE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists user");
        sqLiteDatabase.execSQL("drop Table if exists orders");
        sqLiteDatabase.execSQL("drop Table if exists user_orders");
    }


    public Boolean insertUser(String username, String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("user", null, contentValues);

        if(result == -1){
            return false;
        }else {
            return  true;
        }
    }

    public Boolean checkUsername(String username){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from user where username =?", new String[]{username});
      if(cursor.getCount() > 0){
          return  true;
      }else {
          return  false;
      }
    }
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email = ?", new String[]{email});

        if(cursor.getCount() > 0){
            return  true;
        }else {
            return  false;
        }
    }

    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from user where username = ? and password = ?", new String[]{username, password});
        if(cursor.getCount() > 0){
            return  true;
        }else {
            return  false;
        }
    }


    public Boolean updateUser(String username, String email, String hovaten, String sodienthoai, String diachi) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("hovaten", hovaten);
        contentValues.put("sodienthoai", sodienthoai);
        contentValues.put("diachi", diachi);

        // Update the user's info where the username matches
        int result = MyDatabase.update("user", contentValues, "username = ?", new String[]{username});

        if(result > 0){
            return  true;
        }else {
            return  false;
        }
    }

    public Cursor getUserInfo(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM user WHERE username = ?", new String[]{username});
    }


    public boolean insertOrder(String productName, double price, int quantity, String size, int orderId, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("product_name", productName);
        contentValues.put("price", price);
        contentValues.put("quantity", quantity);
        contentValues.put("size", size);
        contentValues.put("order_id", orderId);
        contentValues.put("user_id", userId);

        // Insert into orders table
        long result = db.insert("orders", null, contentValues);
        return result != -1;
    }



    public boolean isEmailTakenByOthers(String email, String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM user WHERE email = ? AND username != ?",
                new String[]{email, username}
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public int getUserId(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM user WHERE username = ?", new String[]{username});

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex("id");
                    if (columnIndex != -1) { // Kiểm tra cột tồn tại
                        return cursor.getInt(columnIndex);
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return -1;
    }

    public boolean changePassword(String username, String currentPassword, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM user WHERE username = ? AND password = ?",
                new String[]{username, currentPassword}
        );

        if (cursor.getCount() > 0) {

            if (currentPassword.equals(newPassword)) {
                cursor.close();
                return false;
            }

            ContentValues contentValues = new ContentValues();
            contentValues.put("password", newPassword);

            int result = db.update("user", contentValues, "username = ?", new String[]{username});
            cursor.close();
            return result > 0;
        }

        cursor.close();
        return false;
    }


}
