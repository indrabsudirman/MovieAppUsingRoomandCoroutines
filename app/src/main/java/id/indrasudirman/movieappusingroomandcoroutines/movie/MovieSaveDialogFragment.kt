package id.indrasudirman.movieappusingroomandcoroutines.movie

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.DialogFragment
import id.indrasudirman.movieappusingroomandcoroutines.R
import id.indrasudirman.movieappusingroomandcoroutines.database.Director
import id.indrasudirman.movieappusingroomandcoroutines.database.Movie
import id.indrasudirman.movieappusingroomandcoroutines.database.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieSaveDialogFragment : DialogFragment() {

    private var movieTitleExtra : String? = null
    private var movieDirectorFullNameExtra : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieTitleExtra = requireArguments().getString(EXTRA_MOVIE_TITLE)
        movieDirectorFullNameExtra = requireArguments().getString(EXTRA_MOVIE_DIRECTOR_FULL_NAME)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_movie, null)
        val movieEditText = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.et_movie_title)
        val movieDirectorEditText = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.et_movie_director_full_name)

        if (movieTitleExtra != null) {
            movieEditText.setText(movieTitleExtra)
            movieEditText.setSelection(movieTitleExtra!!.length)
        }
        alertDialogBuilder.setView(view)
            .setTitle(getString(R.string.dialog_movie_title))
            .setPositiveButton(R.string.save) { _,_ ->
                GlobalScope.launch (Dispatchers.IO) {saveMovie(movieEditText.text.toString(), movieDirectorEditText.text.toString())  }
            }
            .setNegativeButton(R.string.cancel) {dialog, _, -> dialog.cancel()}

        return alertDialogBuilder.create()
    }

    private suspend fun saveMovie(movieTitle: String, movieDirectionFullName: String) {
        if (TextUtils.isEmpty(movieTitle) || TextUtils.isEmpty(movieDirectionFullName)) {
            return
        }
        val directorDao = MovieDatabase.getDatabase(requireContext()).directorDao()
        val movieDao = MovieDatabase.getDatabase(requireContext()).movieDao()
        var directorId: Long = -1L
        if (movieDirectorFullNameExtra != null) {
            //Clicked on item row -> update
            val directorToUpdate = directorDao.findDirectorByName(movieDirectorFullNameExtra)
            if (directorToUpdate != null) {
                directorId = directorToUpdate.id
                if (directorToUpdate.fullName != movieDirectionFullName) {
                    directorToUpdate.fullName = movieDirectionFullName
                    directorDao.update(directorToUpdate)
                }
            }
        } else {
            // we need director id for movie object; in case director is already in DB
            //insert() would return -1, so we manually check is it exists and get
            //the id of already save director
            val newDirector = directorDao.findDirectorByName(movieDirectionFullName)
            directorId = newDirector?.id ?: directorDao.insert(Director(fullName = movieDirectionFullName))
        }
        if (movieTitleExtra != null) {
            //clicked on item row -> update
            val movieToUpdate = movieDao.findMovieByTitle(movieTitleExtra!!)
            if (movieToUpdate != null) {
                if (movieToUpdate.title != movieTitle) {
                    movieToUpdate.title = movieTitle
                    if (directorId != -1L) {
                        movieToUpdate.directorId = directorId
                    }
                    movieDao.update(movieToUpdate)
                }
            }
        } else {
            //we can many movies with same title but different director
            movieDao.insert(Movie(title = movieTitle, directorId = directorId))
        }

    }

    companion object {
        private const val EXTRA_MOVIE_TITLE = "movie_title"
        private const val EXTRA_MOVIE_DIRECTOR_FULL_NAME = "movie_director"
        const val TAG_DIALOG_MOVIE_SAVE = "dialog_movie_save"

        fun newInstance(movieTitle: String?, movieDirectionFullName: String?) : MovieSaveDialogFragment {
            val fragment = MovieSaveDialogFragment()
            val args = Bundle().apply {
                putString(EXTRA_MOVIE_TITLE, movieTitle)
                putString(EXTRA_MOVIE_DIRECTOR_FULL_NAME, movieDirectionFullName)
            }
            fragment.arguments = args
            return fragment
        }
    }
}