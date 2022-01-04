package id.indrasudirman.movieappusingroomandcoroutines.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.indrasudirman.movieappusingroomandcoroutines.database.*

class  MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val movieDao: MovieDao = MovieDatabase.getDatabase(application).movieDao()
    private val directorDao: DirectorDao = MovieDatabase.getDatabase(application).directorDao()

    val moviesList: LiveData<List<Movie>>
    val directorList: LiveData<List<Director>>

    init {
        moviesList = movieDao.allMovies
        directorList = directorDao.allDirectors
    }

    suspend fun insert(vararg movies: Movie) {
        movieDao.insert(*movies)
    }

    suspend fun update(movie: Movie) {
        movieDao.update(movie)
    }

    suspend fun deleteAll() {
        movieDao.deleteAll()
    }

}