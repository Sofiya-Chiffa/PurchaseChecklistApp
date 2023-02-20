package com.ephirium.purchasechecklistapplication;

import static android.database.sqlite.SQLiteDatabase.OPEN_READONLY;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

class DatabaseHelper extends SQLiteOpenHelper {

    private static final String ASSET_DB_NAME = "my.db";

    private static final String DB_NAME = "newmy.db";
    private static final int SCHEMA = 1;

    public static final String TABLE_PRODUCTS = "products";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COUNT = "count";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_STATUS = "status";

    private final Context context;
    private SQLiteDatabase database;

    @SuppressLint("StaticFieldLeak")
    private static DatabaseHelper databaseHelper;

    private static final String SELECT_PRODUCTS_TABLE = "SELECT * FROM " + TABLE_PRODUCTS;
    private static final String DROP_PRODUCTS_TABLE = "DROP TABLE " + TABLE_PRODUCTS;
    private static final String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_COUNT + " INT, " +
            COLUMN_COST + " INT," +
            COLUMN_STATUS + " BOOLEAN);";


    public static DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) databaseHelper = new DatabaseHelper(context);
        return databaseHelper;
    }

    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.context = context;
    }

    public void open() {
        database = getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public void insert(String table, ContentValues contentValues) {
        database.insert(table, null, contentValues);
    }

    public void delete(String table, String whereCause, String[] whereArgs) {
        database.delete(table, whereCause, whereArgs);
    }

    public void update(
            String table,
            ContentValues contentValues,
            String whereCause,
            String[] whereArgs
    ) {
        database.update(table, contentValues, whereCause, whereArgs);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRODUCTS_TABLE);
        copyDatabase(db, ASSET_DB_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_PRODUCTS_TABLE);
    }

    private void copyDatabase(SQLiteDatabase db, String assetsDatabaseName) {
        try {
            copyFilesDatabase(db, copyAssetsDatabase(assetsDatabaseName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Cursor qw(){
        return database.rawQuery("select * from " + DatabaseHelper.TABLE_PRODUCTS, null);
    }

    private File copyAssetsDatabase(String assetsDatabaseName) throws IOException {
        File filesDatabaseFile = new File(context.getFilesDir() + "/" + assetsDatabaseName);
        InputStream inputStream = context.getAssets().open(assetsDatabaseName);
        OutputStream outputStream = Files.newOutputStream(filesDatabaseFile.toPath());
        byte[] buffer = new byte[1024];
        while (inputStream.read(buffer) > 0) outputStream.write(buffer);
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return filesDatabaseFile;
    }

    private void copyFilesDatabase(SQLiteDatabase db, File filesDatabaseFile) {
        SQLiteDatabase filesDatabase = SQLiteDatabase.openDatabase(filesDatabaseFile.getPath(), null, OPEN_READONLY);
        ResponseConverter responseConverter = new ResponseConverter();

        Cursor cursor = filesDatabase.rawQuery(SELECT_PRODUCTS_TABLE, null);
        List<ContentValues> contentValuesList = responseConverter.getContentValues(cursor);
        for (ContentValues contentValues : contentValuesList) {
            db.insert(TABLE_PRODUCTS, null, contentValues);
        }

        cursor.close();
        filesDatabase.close();
        filesDatabaseFile.delete();
    }

    private static class ResponseConverter {

        private List<ContentValues> getContentValues(Cursor cursor) {
            List<ContentValues> list = new ArrayList<>();
            if (cursor == null || cursor.getCount() == 0) return list;
            ProductsConverter productsConverter = new ProductsConverter();
            while (cursor.moveToNext()) list.add(productsConverter.convert(cursor));
            return list;
        }

        private static class ProductsConverter {

            @SuppressLint("Range")
            public ContentValues convert(Cursor cursor) {
                ContentValues contentValues = new ContentValues();

                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                int count = cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT));
                int cost = cursor.getInt(cursor.getColumnIndex(COLUMN_COST));
                int status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS));

                contentValues.put(COLUMN_ID, id);
                contentValues.put(COLUMN_NAME, name);
                contentValues.put(COLUMN_COUNT, count);
                contentValues.put(COLUMN_COST, cost);
                contentValues.put(COLUMN_STATUS, status);

                return contentValues;
            }
        }
    }
}
