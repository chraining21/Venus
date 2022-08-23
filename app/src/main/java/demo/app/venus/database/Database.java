package demo.app.venus.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = Products.class,version = 1,exportSchema = false)
public abstract class Database extends RoomDatabase {
    private static Database database;
    private static String DATABASE_NAME = "App_DB";

    public synchronized  static Database getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return  database;
    }

    public abstract MainDAO mainDAO();
}