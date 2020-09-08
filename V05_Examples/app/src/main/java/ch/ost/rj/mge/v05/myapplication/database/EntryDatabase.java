package ch.ost.rj.mge.v05.myapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Entry.class}, version = 1)
public abstract class EntryDatabase extends RoomDatabase {
    public abstract EntryDao entryDao();
}
