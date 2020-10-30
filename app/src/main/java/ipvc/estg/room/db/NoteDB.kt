package ipvc.estg.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ipvc.estg.room.dao.CityDao
import ipvc.estg.room.dao.NoteDao
import ipvc.estg.room.entities.City
import ipvc.estg.room.entities.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the City class

// Note: When you modify the database schema, you'll need to update the version number and define a migration strategy
//For a sample, a destroy and re-create strategy can be sufficient. But, for a real app, you must implement a migration strategy.

@Database(entities = arrayOf(Note::class), version = 8, exportSchema = false)
public abstract class NoteDB : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    private class WordDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var noteDao = database.noteDao()

                    // Delete all content here.
                    noteDao.deleteAll()

                    // Add sample cities.
                    var note = Note(1, "Viana do Castelo", "Portugal")
                    noteDao.insert(note)
                    note = Note(2, "Porto", "Portugal")
                    noteDao.insert(note)
                    note = Note(3, "Aveiro", "Portugal")
                    noteDao.insert(note)

                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NoteDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NoteDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDB::class.java,
                    "notes"
                )
                    //estratégia de destruição
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}