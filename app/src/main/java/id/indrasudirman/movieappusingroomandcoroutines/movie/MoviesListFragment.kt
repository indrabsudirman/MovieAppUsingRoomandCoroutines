package id.indrasudirman.movieappusingroomandcoroutines.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.indrasudirman.movieappusingroomandcoroutines.R
import id.indrasudirman.movieappusingroomandcoroutines.database.Movie
import id.indrasudirman.movieappusingroomandcoroutines.database.MovieDatabase
import id.indrasudirman.movieappusingroomandcoroutines.databinding.FragmentMoviesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
//        GlobalScope.launch (Dispatchers.IO) { moviesViewModel.de }
    }
}