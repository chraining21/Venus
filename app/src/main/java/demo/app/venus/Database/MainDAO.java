package demo.app.venus.Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import demo.app.venus.Models.Items;


@Dao
public interface MainDAO {
    @Insert(onConflict = REPLACE)
    void insert(Items items);

    @Query("SELECT * FROM items ORDER BY id DESC")
    List<Items> getAll();

    @Query("UPDATE items SET title = :title, notes = :notes WHERE ID = :id")
    void update(int id, String title, String notes);

    @Delete
    void delete(Items items);
}
