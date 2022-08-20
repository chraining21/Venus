package demo.app.venus.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import demo.app.venus.models.Products;

@Dao
public interface MainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Products p);

    @Query("SELECT * FROM products")
    List<Products> getAll();

    @Delete
    void delete(Products product);
}
