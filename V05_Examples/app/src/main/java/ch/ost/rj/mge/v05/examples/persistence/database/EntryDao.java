package ch.ost.rj.mge.v05.examples.persistence.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EntryDao {
    @Query("SELECT * FROM entry")
    List<Entry> getEntries();

    @Insert
    void insert(Entry entry);

    @Delete
    void delete(Entry entry);
}
