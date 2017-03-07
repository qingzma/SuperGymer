package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qingzhi on 06/03/2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DBManager";

    // table name
    private static final String TABLE_USER = "users";
    private static final String TABLE_HISTORY = "histories";
    private static final String TABLE_EQUIPMENT = "equipments";

    // USER Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_HEIGHT= "height";
    private static final String KEY_GENDER="gender";
    private static final String KEY_PASSWORD="password";
    // histories Table columns names
    private static final String KEY_HISTORY_ID="historyID";
    private static final String KEY_DATE="date";
    private static final String KEY_CALORY="calory";
    // equipments Table column names
    private static final String KEY_EQUIPMENT_ID="equipmentID";
    private static final String KEY_PART="part";
    private static final String KEY_EQUIPMENT_NAME="equipmentName";
    private static final String KEY_HYPERLINK="hyperlink";
    private static final String KEY_INTRO="introduction";




    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE="CREATE TABLE " + TABLE_USER  + "("
                +KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_NAME + " TEXT,"
                +KEY_WEIGHT+ " TEXT,"+KEY_HEIGHT +" TEXT, "
                +KEY_GENDER+" INTEGER, "
                +KEY_PASSWORD+" TEXT " + ")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_HISTORY_TABLE="CREATE TABLE " + TABLE_HISTORY  + "("
                +KEY_HISTORY_ID+ " INTEGER PRIMARY KEY, "
                +KEY_ID + " INTEGER,"+ KEY_DATE + " TEXT,"
                +KEY_CALORY+ " REAL"+")";
        db.execSQL(CREATE_HISTORY_TABLE);

        String CREATE_EQUIPMENT_TABLE="CREATE TABLE " + TABLE_EQUIPMENT  + "("
                +KEY_EQUIPMENT_ID + " INTEGER PRIMARY KEY,"+ KEY_EQUIPMENT_NAME + " TEXT,"
                +KEY_PART+ " TEXT,"+KEY_HYPERLINK +" TEXT,"
                +KEY_INTRO+" TEXT"+")";
        db.execSQL(CREATE_EQUIPMENT_TABLE);








    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
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
        values.put(KEY_PASSWORD,user.get_password());

        //insert row
        db.insert(TABLE_USER,null,values);
        db.close();
    }

    //Adding new Equipment
    public void addEquipment(Equipment eq){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_EQUIPMENT_NAME,eq.get_equipment_name());
        values.put(KEY_PART,eq.get_part());
        values.put(KEY_HYPERLINK,eq.get_video_url());
        values.put(KEY_INTRO,eq.get_introduction());

        //insert row
        db.insert(TABLE_EQUIPMENT,null,values);
        db.close();
    }

    //Adding new history
    public void addHistory(History history){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_ID,history.get_userId());
        values.put(KEY_DATE,history.get_date().toString());
        values.put(KEY_CALORY,history.get_calories());

        //insert row
        db.insert(TABLE_HISTORY,null,values);
        db.close();
    }

    //get one user
    public User getUser(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor=db.query(TABLE_USER,new String[]{KEY_ID, KEY_NAME, KEY_WEIGHT,
                KEY_HEIGHT, KEY_GENDER,KEY_PASSWORD},KEY_ID+"=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor!=null) {
            cursor.moveToFirst();

        }
        User user=new User(Integer.parseInt(cursor.getString(0)),cursor.getString(1),Float.parseFloat(cursor.getString(2)),
                Float.parseFloat(cursor.getString(3)),Integer.parseInt(cursor.getString(4)),cursor.getString(5));

        return user;
    }

    //get user(s) by name
    public List<User> getUser(String name){
        List<User> userList=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String querySelect="SELECT  *  FROM  " +TABLE_USER+
                "    WHERE "+KEY_NAME +" = "+ "\""+name+"\"";
        //Cursor cursor=db.query(TABLE_USER,new String[]{KEY_ID, KEY_NAME, KEY_WEIGHT,
        //                KEY_HEIGHT, KEY_GENDER,KEY_PASSWORD},KEY_NAME+"=?",
        //        new String[]{name}, null, null, null, null);
        Cursor cursor=db.rawQuery(querySelect,null);
        if(cursor!=null) {
            cursor.moveToFirst();
            do{
                User user=new User(Integer.parseInt(cursor.getString(0)),cursor.getString(1),Float.parseFloat(cursor.getString(2)),
                        Float.parseFloat(cursor.getString(3)),Integer.parseInt(cursor.getString(4)),cursor.getString(5));
                userList.add(user);
            }while(cursor.moveToNext());
        }

        return userList;

    }




    //get one equipment
    public Equipment getEquipment(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor=db.query(TABLE_EQUIPMENT,new String[]{KEY_EQUIPMENT_ID,KEY_EQUIPMENT_NAME,KEY_PART,
                        KEY_HYPERLINK,KEY_INTRO},KEY_EQUIPMENT_ID+"=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor!=null)
            cursor.moveToFirst();

        Equipment equipment=new Equipment(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),
                cursor.getString(3),cursor.getString(4));
        return equipment;
    }

    //get user(s) by name
    public List<Equipment> getEquipmentByName(String name){
        List<Equipment> equipmentList=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String querySelect="SELECT  *  FROM  " +TABLE_EQUIPMENT+
                "    WHERE "+KEY_EQUIPMENT_NAME +" = "+ "\""+name+"\"";
        Cursor cursor=db.rawQuery(querySelect,null);
        if(cursor!=null) {
            cursor.moveToFirst();
            do{
                Equipment equipment=new Equipment(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4));
                equipmentList.add(equipment);
            }while(cursor.moveToNext());
        }

        return equipmentList;

    }


    //get all users.
    public List<User> getAllUsers(){
        List<User> userList =new ArrayList<>();

        //select all users.
        String selectQuery="SELECT  * FROM "+TABLE_USER;

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        //looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                User user=new User();
                user.set_userId(Integer.parseInt(cursor.getString(0)));
                user.set_name(cursor.getString(1));
                user.set_weight(Float.parseFloat(cursor.getString(2)));
                user.set_height(Float.parseFloat(cursor.getString(3)));
                user.set_gender(Integer.parseInt(cursor.getString(4)));
                user.set_password(cursor.getString(5));

                userList.add(user);
            } while(cursor.moveToNext());
        }
        //cursor.close();

        return userList;
    }

    //get all equipment.
    public List<Equipment> getAllEquipment(){
        List<Equipment> equipmentList=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String querySelect="SELECT  *  FROM  " +TABLE_EQUIPMENT;
        Cursor cursor=db.rawQuery(querySelect,null);
        if(cursor!=null) {
            cursor.moveToFirst();
            do{
                Equipment equipment=new Equipment(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4));
                equipmentList.add(equipment);
            }while(cursor.moveToNext());
        }

        return equipmentList;

    }




    //get users count
    public int getUsersCount(){
        String countQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    //get equipments count
    public int getEquipmentsCount(){
        String countQuery = "SELECT  * FROM " + TABLE_EQUIPMENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }



    // Updating single user
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME,user.get_name());
        values.put(KEY_WEIGHT,user.get_weight());
        values.put(KEY_HEIGHT,user.get_height());
        values.put(KEY_GENDER,user.get_gender());
        values.put(KEY_PASSWORD,user.get_password());

        // updating row
        return db.update(TABLE_USER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(user.get_userId()) });
    }

    // Updating single equipment
    public int updateEquipment(Equipment equipment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_EQUIPMENT_NAME,equipment.get_equipment_name());
        values.put(KEY_PART,equipment.get_part());
        values.put(KEY_HYPERLINK,equipment.get_video_url());
        values.put(KEY_INTRO,equipment.get_introduction());

        // updating row
        return db.update(TABLE_EQUIPMENT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(equipment.get_equipment_id()) });
    }

    // Deleting single user
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?",
                new String[] { String.valueOf(user.get_userId()) });
        db.close();
    }

    // Deleting single equipment
    public void deleteEquipment(Equipment equipment) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?",
                new String[] { String.valueOf(equipment.get_equipment_id()) });
        db.close();
    }









}
