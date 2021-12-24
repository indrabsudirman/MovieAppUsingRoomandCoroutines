package id.indrasudirman.movieappusingroomandcoroutines.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


@Database(entities = [Movie::class, Director::class], version = 1)
abstract class MovieDatabase : RoomDatabase(){

    abstract fun movieDao(): MovieDao
    abstract fun directorDao(): DirectorDao

    companion object {
        private var INSTANCE : MovieDatabase? = null
        private const val DB_NAME = "movie.db"

                fun getDatabase (context: Context): MovieDatabase {
                    if (INSTANCE == null) {
                        synchronized(MovieDatabase::class.java) {
                            if (INSTANCE == null) {
                                INSTANCE = Room.databaseBuilder(context.applicationContext, MovieDatabase::class.java, DB_NAME)
                                    .allowMainThreadQueries() //Uncomment this line, if you don't want to use RxJava or Coroutines just yet (block UI thread)
                                    .addCallback(object : Callback() {
                                        override fun onCreate(db: SupportSQLiteDatabase) {
                                            super.onCreate(db)
                                            Log.d("MovieDatabase.class", "Populating with data...")
                                            GlobalScope.launch(Dispatchers.IO) {
                                                rePopulateDb (
                                                INSTANCE)}
                                        }
                                    }).build()
                            }
                        }
                    }
                    return INSTANCE!!
                }
    }
}