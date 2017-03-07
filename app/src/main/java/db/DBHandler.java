package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qingzhi on 06/03/2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DBManager";

    // Contacts table name
    private static final String TABLE_USER = "users";

    // USER Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_HEIGHT= "height";
    private static final String KEY_GENDER="gender";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE="CREATE TABLE " + TABLE_USER  + "("
                +KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_NAME + " TEXT,"
                +KEY_WEIGHT+ " TEXT,"+KEY_HEIGHT +" TEXT,"
                +KEY_GENDER+" INTEGER"+")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    // Adding new user
    public void addUser(User user){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,user.get_name());
        values.put(KEY_WEIGHT,user.get_weight());
        values.put(KEY_HEIGHT,user.get_height());
        values.put(KEY_GENDER,user.get_gender());

        //insert row
        db.insert(TABLE_USER,null,values);
        db.close();
    }

    public User getUser(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor=db.query(TABLE_USER,new String[]{KEY_ID, KEY_NAME, KEY_WEIGHT,
                KEY_HEIGHT, KEY_GENDER},KEY_ID+"=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor!=null)
            cursor.moveToFirst();

        User user=new User(Integer.parseInt(cursor.getString(0)),cursor.getString(1),Float.parseFloat(cursor.getString(0)),
                Float.parseFloat(cursor.getString(0)),Integer.parseInt(cursor.getString(0)));
        return user;
    }

}
