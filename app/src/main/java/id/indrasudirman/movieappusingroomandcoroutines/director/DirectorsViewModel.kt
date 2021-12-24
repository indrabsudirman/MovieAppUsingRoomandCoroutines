package id.indrasudirman.movieappusingroomandcoroutines.director

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.indrasudirman.movieappusingroomandcoroutines.database.Director
import id.indrasudirman.movieappusingroomandcoroutines.database.DirectorDao
import id.indrasudirman.movieappusingroomandcoroutines.database.MovieDatabase

class DirectorsViewModel(application: Application) : AndroidViewModel(application) {

    private val directorDao: DirectorDao = MovieDatabase.getDatabase(application).directorDao()
    val directorList: LiveData<List<Director>>

    init {
        directorList = directorDao.allDirectors
    }

    suspend fun insert(vararg directors: Director) {
        directorDao.insert(*directors)
    }

    suspend fun update(director: Director) {
        directorDao.update(director)
    }

    suspend fun deleteAll() {
        directorDao.deleteAll()
    }
}