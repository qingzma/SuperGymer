package db;

import android.content.Context;
import android.util.Log;

import com.learn.qingzhi.supergymer.R;

import java.util.ArrayList;
import java.util.List;

import db.*;

/**
 * Created by qingzhi on 07/03/2017.
 */

public class DbSetup {
    public void setupDB(Context context){
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



        Log.d("Inserting: ", "inserting equipments");
        db.addEquipment(new Equipment(context.getResources().getString(R.string.dumbbell_name),
                context.getResources().getString(R.string.part_arm),
                "www.baidu.com",
                context.getResources().getString(R.string.dumbbell_arm_introduction)));
        db.addEquipment(new Equipment(context.getResources().getString(R.string.dumbbell_name),
                context.getResources().getString(R.string.part_chest),
                "www.baidu.com",
                context.getResources().getString(R.string.dumbbell_chest_introduction)));
        db.addEquipment(new Equipment(context.getResources().getString(R.string.dumbbell_name),
                context.getResources().getString(R.string.part_abdomen),
                "www.baidu.com",
                context.getResources().getString(R.string.dumbbell_abdomen_introduction)));
        db.addEquipment(new Equipment(context.getResources().getString(R.string.yoga_name),
                context.getResources().getString(R.string.part_abdomen),
                "www.baidu.com",
                context.getResources().getString(R.string.yoga_abdomen_introduction)));
        db.addEquipment(new Equipment(context.getResources().getString(R.string.yoga_name),
                context.getResources().getString(R.string.part_leg),
                "www.baidu.com",
                context.getResources().getString(R.string.yoga_leg_introduction)));
        db.addEquipment(new Equipment(context.getResources().getString(R.string.treadmill_name),
                context.getResources().getString(R.string.part_reduce_fate),
                "www.baidu.com",
                context.getResources().getString(R.string.treadmill_reduce_fat_introduction)));

        List<Equipment> equipmentList=new ArrayList<>();
        equipmentList=db.getAllEquipment();
        for (Equipment item:equipmentList){
            Log.d("Equipment: ",item.toString());
        }


        equipmentList=db.getEquipmentByName("Dumbbell");
        for (Equipment item:equipmentList){
            Log.d("Equipment: ",item.toString());
        }
    }
}
