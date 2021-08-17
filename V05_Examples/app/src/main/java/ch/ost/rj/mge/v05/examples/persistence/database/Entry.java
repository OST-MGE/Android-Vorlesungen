package ch.ost.rj.mge.v05.examples.persistence.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Entry {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String content;
}
