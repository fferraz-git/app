package ipvc.estg.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")

/*
* Database
* id - auto increment
* title - string
* content - string
* */

class Note (
    // Int? = null so when creating instance id has not to be passed as argument
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String
)