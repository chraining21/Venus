package demo.app.venus.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Products p);

    @Query("SELECT productName FROM Products WHERE productName= :name")
    List<String> getName(String name);
    @Query("SELECT * FROM Products")
    List<Products> getAll();

    @Delete
    void delete(Products product);
}
