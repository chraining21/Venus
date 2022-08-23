package demo.app.venus.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MainDAO {
    @Insert(onConflict = REPLACE)
    void insert(Products p);

    @Query("SELECT * FROM Products ORDER BY id DESC")
    List<Products> getAll();

    @Delete
    void delete(Products p);

    @Query("SELECT COUNT(*) FROM Products")
    int getNum();
}
