package id.indrasudirman.movieappusingroomandcoroutines.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import id.indrasudirman.movieappusingroomandcoroutines.R
import id.indrasudirman.movieappusingroomandcoroutines.database.Director
import id.indrasudirman.movieappusingroomandcoroutines.database.Movie
import id.indrasudirman.movieappusingroomandcoroutines.database.MovieDatabase
import id.indrasudirman.movieappusingroomandcoroutines.databinding.FragmentMoviesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class MoviesListFragment : Fragment() {

    private lateinit var moviesListAdapter: MoviesListAdapter
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var moviesList: List<Movie>
    private var _moviesBinding: FragmentMoviesBinding? = null
    private val moviesBinding get() = _moviesBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _moviesBinding = FragmentMoviesBinding.inflate(inflater, container, false)
        val view = moviesBinding.root
        initView()

        return view
    }

    private fun initData() {
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        moviesViewModel.moviesList.observe(this,
        Observer { movies : List<Movie> ->
            moviesList = movies
            moviesListAdapter.setMovieList(movies)
        })
        moviesViewModel.directorList.observe(this,
        Observer { _ ->
            //We need to refresh the movies list in case when director's name change
            moviesViewModel.moviesList.value?.let {
                moviesList = it
                moviesListAdapter.setMovieList(it)
            }
        })
    }

    private suspend fun getDirectionFullName(movie: Movie): String? {
        return MovieDatabase.getDatabase(requireContext()).directorDao().findDirectorById(movie.directorId)?.fullName
    }

    private fun initView() {
        val recyclerView: RecyclerView = moviesBinding.recyclerviewMovies
        moviesListAdapter = MoviesListAdapter(this)
        recyclerView.adapter = moviesListAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    fun removeData() {
        GlobalScope.launch (Dispatchers.IO) { moviesViewModel.deleteAll() }
    }

    fun exportMoviesWithDirectorsToCSVFile(csVFile: File) {
        csvWriter().open(csVFile, append = false) {
            //Header
            writeRow(listOf("[id]", "[${Movie.TABLE_NAME}]", "[${Director.TABLE_NAME}]"))
            moviesList.forEachIndexed { index, movie ->
                val directorName: String = moviesViewModel.directorList.value?.find { it.id == movie.directorId }?.fullName ?: ""
                writeRow(listOf(index, movie.title, directorName))
            }
        }
    }

    companion object {
        fun newInstance(): MoviesListFragment = MoviesListFragment()
    }
}