package id.indrasudirman.movieappusingroomandcoroutines.director

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import id.indrasudirman.movieappusingroomandcoroutines.R
import id.indrasudirman.movieappusingroomandcoroutines.database.Director
import id.indrasudirman.movieappusingroomandcoroutines.database.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DirectorSaveDialogFragment : DialogFragment() {

    private var directorFullNameExtra: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        directorFullNameExtra = requireArguments().getString(EXTRA_DIRECTOR_FULL_NAME)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_director, null)
        val directorEditText = view.findViewById<EditText>(R.id.et_director_full_name)
        directorEditText.setText(directorFullNameExtra)
        directorEditText.setSelection(directorFullNameExtra?.length ?: 0)
        alertDialogBuilder.setView(view)
            .setTitle(getString(R.string.dialog_director_title))
            .setPositiveButton(R.string.save) {_, _ ->
                GlobalScope.launch(Dispatchers.IO) {
                    saveDirector (directorEditText.text.toString())
                }
            }
            .setNegativeButton(R.string.cancel) {
                dialog, _ -> dialog.cancel()
            }
        return alertDialogBuilder.create()
    }

    private suspend fun saveDirector (fullName: String) {
        if (TextUtils.isEmpty(fullName)) {
            return
        }
        val directorDao = MovieDatabase.getDatabase(requireContext()).directorDao()
        if (directorFullNameExtra != null) {
            //click on item row -> update
            val directorToUpdate = directorDao.findDirectorByName(directorFullNameExtra)
            if (directorToUpdate != null) {
                if (directorToUpdate.fullName != fullName) {
                    directorToUpdate.fullName = fullName
                    directorDao.update(directorToUpdate)
                }
            }
        } else {
            directorDao.insert(Director(fullName = fullName))
        }
    }

    companion object {
        private const val EXTRA_DIRECTOR_FULL_NAME = "director_full_name"
        const val TAG_DIALOG_DIRECTOR_SAVE = "dialog_director_save"

        fun newInstance(directorFullName: String?): DirectorSaveDialogFragment {
            val fragment = DirectorSaveDialogFragment()
            val args = Bundle()
            args.putString(EXTRA_DIRECTOR_FULL_NAME, directorFullName)
            fragment.arguments = args
            return fragment
        }
    }
}