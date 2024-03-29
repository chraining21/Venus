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

    @Query("SELECT * FROM Products WHERE kind = :kind AND brandName = :brand ORDER BY id DESC")
    List<Products> getQuery(int kind,String brand);

    @Query("SELECT * FROM Products WHERE Kind = 0 ORDER BY id DESC")
    List<Products> getNormal();

    @Query("SELECT * FROM Products WHERE Kind = 1 ORDER BY id DESC")
    List<Products> getHoney();

    @Query("SELECT * FROM Products WHERE Kind = 2 ORDER BY id DESC")
    List<Products> getToxic();

    @Query("SELECT * FROM Products WHERE Kind = 3 ORDER BY id DESC")
    List<Products> getBuy();

    @Delete
    void delete(Products p);

    @Query("SELECT COUNT(*) FROM Products")
    int getNum();
}