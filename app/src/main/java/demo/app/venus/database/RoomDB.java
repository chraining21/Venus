package demo.app.venus.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import demo.app.venus.models.Products;

@Database(entities = Products.class, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase{
    private static RoomDB database;
    private static String dbName = "Products";

    public synchronized  static  RoomDB getInstance(Context c){
        if(database == null){
            database = Room.databaseBuilder(c.getApplicationContext(),
                            RoomDB.class,dbName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return  database;
    }
    public abstract MainDao mainDao();
}
