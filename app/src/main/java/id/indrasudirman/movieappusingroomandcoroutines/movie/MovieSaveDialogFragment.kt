package id.indrasudirman.movieappusingroomandcoroutines.movie

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import id.indrasudirman.movieappusingroomandcoroutines.R

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