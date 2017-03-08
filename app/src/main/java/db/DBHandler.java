package db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.learn.qingzhi.supergymer.MainActivity;
import com.learn.qingzhi.supergymer.R;
import java.nio.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private static final String TABLE_BTDEVICE="btdevices";

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
    private static final String KEY_DURATION="duration";
    // equipments Table column names
    private static final String KEY_EQUIPMENT_ID="equipmentID";
    private static final String KEY_PART="part";
    private static final String KEY_EQUIPMENT_NAME="equipmentName";
    private static final String KEY_HYPERLINK="hyperlink";
    private static final String KEY_INTRO="introduction";
    // btdevices Table column names
    private static final String KEY_BTDEVICE_ID="deviceID";
    private static final String KEY_BTDEVICE_NAME="deviceName";
    private static final String KEY_UUID="uuid";
    private static final String KEY_IMAGE_PATH="imagePath";





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
                +KEY_CALORY+ " REAL, "
                +KEY_DURATION+" REAL "+")";
        db.execSQL(CREATE_HISTORY_TABLE);

        String CREATE_EQUIPMENT_TABLE="CREATE TABLE " + TABLE_EQUIPMENT  + "("
                +KEY_EQUIPMENT_ID + " INTEGER PRIMARY KEY,"+ KEY_EQUIPMENT_NAME + " TEXT,"
                +KEY_PART+ " TEXT,"+KEY_HYPERLINK +" TEXT,"
                +KEY_INTRO+" TEXT, "
                +KEY_IMAGE_PATH+" TEXT "+")";
        db.execSQL(CREATE_EQUIPMENT_TABLE);

        String CREATE_BTDEVICE_TABLE="CREATE TABLE " + TABLE_BTDEVICE  + "("
                +KEY_BTDEVICE_ID + " INTEGER PRIMARY KEY, "+ KEY_BTDEVICE_NAME + " TEXT, "
                +KEY_UUID+ " TEXT, "+KEY_IMAGE_PATH +" TEXT "+")";
        db.execSQL(CREATE_BTDEVICE_TABLE);








    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BTDEVICE);
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
        values.put(KEY_IMAGE_PATH,eq.get_image_path());

        //insert row
        db.insert(TABLE_EQUIPMENT,null,values);
        db.close();
    }

    //Adding new history
    public long addHistory(History history){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_ID,history.get_userId());
        values.put(KEY_DATE,MyDateFormat.Date2String.format(history.get_date()));
        values.put(KEY_CALORY,history.get_calories());
        values.put(KEY_DURATION,history.get_duration());

        //insert row
        long index=db.insert(TABLE_HISTORY,null,values);
        db.close();
        return index;
    }

    //Addint new bluetooth device
    public long addBTDevice(BTDevice btDevice){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_BTDEVICE_NAME,btDevice.get_btdevice_name());
        values.put(KEY_UUID,btDevice.get_uuid().toString());
        values.put(KEY_IMAGE_PATH,btDevice.get_image_path());


        //insert row
        long index=db.insert(TABLE_BTDEVICE,null,values);
        db.close();
        return index;
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
        List<User> userList=null;
        SQLiteDatabase db = this.getReadableDatabase();
        String querySelect="SELECT  *  FROM  " +TABLE_USER+
                "    WHERE "+KEY_NAME +" = "+ "\""+name+"\"";
        //Cursor cursor=db.query(TABLE_USER,new String[]{KEY_ID, KEY_NAME, KEY_WEIGHT,
        //                KEY_HEIGHT, KEY_GENDER,KEY_PASSWORD},KEY_NAME+"=?",
        //        new String[]{name}, null, null, null, null);
        Cursor cursor=db.rawQuery(querySelect,null);
        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            do{
                User user=new User(Integer.parseInt(cursor.getString(0)),cursor.getString(1),Float.parseFloat(cursor.getString(2)),
                        Float.parseFloat(cursor.getString(3)),Integer.parseInt(cursor.getString(4)),cursor.getString(5));
                userList.add(user);
            }while(cursor.moveToNext());
        }
        else
            return null;

        return userList;

    }




    //get one equipment
    public Equipment getEquipment(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor=db.query(TABLE_EQUIPMENT,new String[]{KEY_EQUIPMENT_ID,KEY_EQUIPMENT_NAME,KEY_PART,
                        KEY_HYPERLINK,KEY_INTRO,KEY_IMAGE_PATH},KEY_EQUIPMENT_ID+"=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor!=null)
            cursor.moveToFirst();

        Equipment equipment=new Equipment(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),
                cursor.getString(3),cursor.getString(4),cursor.getString(5));
        return equipment;
    }

    //get Equipments(s) by name
    public List<Equipment> getEquipmentByName(String name){
        List<Equipment> equipmentList=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String querySelect="SELECT  *  FROM  " +TABLE_EQUIPMENT+
                "    WHERE "+KEY_EQUIPMENT_NAME +" = "+ "\""+name+"\"";
        Cursor cursor=db.rawQuery(querySelect,null);
        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            do{
                Equipment equipment=new Equipment(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),
                        cursor.getString(5));
                equipmentList.add(equipment);
            }while(cursor.moveToNext());
        }

        return equipmentList;

    }
    //get BT device(s) by name
    public List<BTDevice> getBTDeviceByName(String name){
        List<BTDevice> btDeviceList=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String querySelect="SELECT  *  FROM  " +TABLE_BTDEVICE+
                "    WHERE "+KEY_BTDEVICE_NAME +" = "+ "\""+name+"\"";
        Cursor cursor=db.rawQuery(querySelect,null);
        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            do{
                //File file=new File(cursor.getString(3));
                BTDevice btDevice=new BTDevice(cursor.getInt(0),
                        cursor.getString(1),
                        UUID.fromString(cursor.getString(2)),
                        cursor.getString(3));
                btDeviceList.add(btDevice);
            }while(cursor.moveToNext());
        }

        return btDeviceList;

    }

    //get one day's calory history
    public float getHistoryCaloryOfDay(int uid ,Date date){
        //Date dt= Calendar.getInstance().getTime();
        //Log.d("hah",MyDateFormat.Date2String.format(dt));
        SQLiteDatabase db = this.getReadableDatabase();
        String querySelect="SELECT  *  FROM  " +TABLE_HISTORY+
                "    WHERE "+KEY_ID +" = " +uid +
                " AND "+KEY_DATE+" = "+"\""+MyDateFormat.Date2String.format(date)+"\"";
        Cursor cursor=db.rawQuery(querySelect,null);
        if(cursor.moveToFirst()){
            return cursor.getFloat(3);
        }
        return -1;
    }
    //get one day's duration history
    public float getHistoryDurationOfDay(int uid ,Date date){
        //Date dt= Calendar.getInstance().getTime();
        //Log.d("hah",MyDateFormat.Date2String.format(dt));
        SQLiteDatabase db = this.getReadableDatabase();
        String querySelect="SELECT  *  FROM  " +TABLE_HISTORY+
                "    WHERE "+KEY_ID +" = " +uid +
                " AND "+KEY_DATE+" = "+"\""+MyDateFormat.Date2String.format(date)+"\"";
        Cursor cursor=db.rawQuery(querySelect,null);
        if(cursor.moveToFirst()){
            return cursor.getFloat(4);
        }
        return -1;
    }


    //get all users.
    public List<User> getAllUsers(){
        List<User> userList =new ArrayList<>();

        //select all users.
        String selectQuery="SELECT  * FROM "+TABLE_USER;

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        //looping through all rows and adding to list
        if(cursor!=null){
            cursor.moveToFirst();
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
        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            do{
                Equipment equipment=new Equipment(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),
                        cursor.getString(5));
                equipmentList.add(equipment);
            }while(cursor.moveToNext());
        }

        return equipmentList;

    }

    //get all history of a user
    public List<History> getAllHistory(int uid){
        List<History> historyList=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String querySelect="SELECT  *  FROM  " +TABLE_HISTORY+
                "    WHERE "+KEY_ID +" = " +uid;
        Cursor cursor=db.rawQuery(querySelect,null);
        if(cursor!=null) {
            cursor.moveToFirst();
            do{
                History history=new History(cursor.getInt(0),
                        cursor.getInt(1),MyDateFormat.toDate(cursor.getString(2)),
                        cursor.getFloat(3),cursor.getFloat(4));
                historyList.add(history);
            }while(cursor.moveToNext());
        }else
            return  null;

        return historyList;
    }



    //get all BT device(s)
    public List<BTDevice> getAllBTDevices(){
        List<BTDevice> btDeviceList=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String querySelect="SELECT  *  FROM  " +TABLE_BTDEVICE;
        Cursor cursor=db.rawQuery(querySelect,null);
        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            do{
                //File file=new File(cursor.getString(3));
                BTDevice btDevice=new BTDevice(cursor.getInt(0),
                        cursor.getString(1),
                        UUID.fromString(cursor.getString(2)),
                        cursor.getString(3));
                btDeviceList.add(btDevice);
            }while(cursor.moveToNext());
        }

        return btDeviceList;

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
    public int getAllEquipmentsCount(){
        String countQuery = "SELECT  * FROM " + TABLE_EQUIPMENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    //get bt device count
    public int getAllBTDeviceCount(){
        String countQuery = "SELECT  * FROM " + TABLE_BTDEVICE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    //get equipments types and names
    public List<String> getEquipmentNames(){
        List<String> names=new ArrayList<>();
        List<Equipment> equipments=getAllEquipment();
        for( Equipment equipment: equipments){
            if(!names.contains(equipment.get_equipment_name())){
                names.add(equipment.get_equipment_name());
            }
        }
        return names;
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


    //get equipments count
    public int getHistoryCount(int uid){
        String countQuery = "SELECT  * FROM " + TABLE_HISTORY+
                "    WHERE "+KEY_ID +" = " +uid;;
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
        values.put(KEY_IMAGE_PATH,equipment.get_image_path());

        // updating row
        return db.update(TABLE_EQUIPMENT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(equipment.get_equipment_id()) });
    }

    // Updating single history
    public int updateHistory(Long histID,History history) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_ID,history.get_userId());
        values.put(KEY_DATE,MyDateFormat.Date2String.format(history.get_date()));
        values.put(KEY_CALORY,history.get_calories());
        values.put(KEY_DURATION,history.get_duration());

        // updating row
        return db.update(TABLE_HISTORY, values, KEY_EQUIPMENT_ID + " = ?",
                new String[] { String.valueOf(histID) });
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


    private boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }


    public void initDb(Context context){
        if(!doesDatabaseExist(context,DATABASE_NAME)){
            DBHandler db = new DBHandler(context);



            //Inserting Users
            Log.d("Insert: ", "Inserting ..");
            db.addUser(new User("Tom1", 70, 180, 1,"skjfkdsf"));
            db.addUser(new User("John1", 60,190,1,"a34sdfsd"));
            db.addUser(new User("Lily0", 50,165,0,"sdfas234fdgas"));
            db.addUser(new User("Sue0", 65,170,0,"ahfi893r"));

            List<User> contacts = db.getAllUsers();

            for (User cn : contacts) {
                String log = cn.toString();
                // Writing Contacts to log
                Log.d("Name: ", log);
            }

            String imageUriArm = "drawable://" + R.drawable.arm1;
            String imageUriBack = "drawable://" + R.drawable.back;
            String imageUrileg = "drawable://" + R.drawable.leg1;
            String imageUriReduceFat = "drawable://" + R.drawable.btdevice_yoga_mat;
            String imageUriChest = "drawable://" + R.drawable.chest1;
            String imageUriAbdomen = "drawable://" + R.drawable.abs1;


            Log.d("Inserting: ", "inserting equipments");
            db.addEquipment(new Equipment(context.getResources().getString(R.string.dumbbell_name),
                    context.getResources().getString(R.string.part_arm),
                    "Qskz8c1C0Ds",
                    context.getResources().getString(R.string.dumbbell_arm_introduction),
                    imageUriArm));
            db.addEquipment(new Equipment(context.getResources().getString(R.string.dumbbell_name),
                    context.getResources().getString(R.string.part_chest),
                    "mQMA88jJrFc",
                    context.getResources().getString(R.string.dumbbell_chest_introduction),
                    imageUriChest));
            db.addEquipment(new Equipment(context.getResources().getString(R.string.dumbbell_name),
                    context.getResources().getString(R.string.part_abdomen),
                    "7Nu7eSe9KHQ",
                    context.getResources().getString(R.string.dumbbell_abdomen_introduction),
                    imageUriAbdomen));
            db.addEquipment(new Equipment(context.getResources().getString(R.string.yoga_name),
                    context.getResources().getString(R.string.part_abdomen),
                    "VkBxPdqczzo",
                    context.getResources().getString(R.string.yoga_abdomen_introduction),
                    imageUriAbdomen));
            db.addEquipment(new Equipment(context.getResources().getString(R.string.yoga_name),
                    context.getResources().getString(R.string.part_leg),
                    "4mqPJmzXW0A",
                    context.getResources().getString(R.string.yoga_leg_introduction),
                    imageUrileg));
            db.addEquipment(new Equipment(context.getResources().getString(R.string.treadmill_name),
                    context.getResources().getString(R.string.part_reduce_fate),
                    "ya_WI8TuFBY",
                    context.getResources().getString(R.string.treadmill_reduce_fat_introduction),
                    imageUriReduceFat));

            List<Equipment> equipmentList=new ArrayList<>();
            equipmentList=db.getAllEquipment();
            for (Equipment item:equipmentList){
                Log.d("Equipment: ",item.toString());
            }


            equipmentList=db.getEquipmentByName("Dumbbell");
            for (Equipment item:equipmentList){
                Log.d("Equipment: ",item.toString());
            }



            User user=db.getUser(0);
            int uid=user.get_userId();
            //uid=2;
            Date dt1=Calendar.getInstance().getTime();
            Date dt2=new Date(new Date().getTime()- 1*24*3600*1000);
            Date dt3=new Date(new Date().getTime()- 2*24*3600*1000);
            Date dt4=new Date(new Date().getTime()- 3*24*3600*1000);

            History history1=new History(uid,dt1,200,40);
            History history2=new History(uid,dt2,300,350);
            History history3=new History(uid,dt3,100,20);
            History history4=new History(uid,dt4,150,50);

            db.addHistory(history1);
            db.addHistory(history2);
            db.addHistory(history3);
            db.addHistory(history4);



            //String path = Environment.getExternalStorageDirectory()+" ";

            Log.d("Inserting: ", "inserting bluetooth devices");
            String imageUribtdevice_dumbbell = "drawable://" + R.drawable.btdevice_dumbbell;
            String imageUribtdevice_treadmill = "drawable://" + R.drawable.btdevice_treadmill;
            String imageUribtdevice_yoga_mat = "drawable://" + R.drawable.btdevice_yoga_mat;
            Log.d("Inserting: ", imageUribtdevice_dumbbell);

            BTDevice btDevice1=new BTDevice(context.getResources().getString(R.string.dumbbell_name),
                    UUID.fromString("f7826da6-4fa2-4e98-8024-bc5b71e0893e"),imageUribtdevice_dumbbell);
            BTDevice btDevice2=new BTDevice(context.getResources().getString(R.string.yoga_name),
                    UUID.fromString("ad3429e0-81ce-4cfa-ad81-93b8f2d601e5"),imageUribtdevice_dumbbell);
            BTDevice btDevice3=new BTDevice(context.getResources().getString(R.string.treadmill_name),
                    UUID.fromString("e242d6b0-01c2-4491-8d71-a52386a6f0c8"),imageUribtdevice_dumbbell);

            db.addBTDevice(btDevice1);
            db.addBTDevice(btDevice2);
            db.addBTDevice(btDevice3);

            List<BTDevice> btDeviceList=db.getAllBTDevices();
            for (BTDevice btDevice:btDeviceList){
                Log.d("Inserting: ",btDevice.toString());
            }




        }


    }









}
