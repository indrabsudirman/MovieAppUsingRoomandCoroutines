package id.indrasudirman.movieappusingroomandcoroutines.director

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import id.indrasudirman.movieappusingroomandcoroutines.R

class DirectorSaveDialogFragment : DialogFragment() {

    private var directorFullNameExtra: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        directorFullNameExtra = arguments!!.getString(EXTRA_DIRECTOR_FULL_NAME)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_director, null)
        val directorEditText = view.findViewById<EditText>(R.id.et_director_full_name)
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