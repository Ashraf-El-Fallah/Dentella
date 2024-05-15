package com.af.dentalla.ui.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.af.dentalla.R
import com.af.dentalla.utils.ValidationUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    protected open fun isUserNameValid(userName: String): Boolean {
        if (ValidationUtils.isUserNameNotValid(userName)) {
            Toast.makeText(requireContext(), R.string.user_name_not_valid, Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    protected open fun isEmailValid(email: String): Boolean {
        if (ValidationUtils.isEmailNotValid(email)) {
            Toast.makeText(requireContext(), R.string.email_not_valid, Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    protected open fun isPasswordValid(password: String): Boolean {
        if (ValidationUtils.isPasswordNotValid(password)) {
            Toast.makeText(requireContext(), R.string.password_not_valid, Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

//    abstract val layoutIdFragment: Int
//    abstract val viewModel: ViewModel

//    private lateinit var _binding: VDB
//    protected val binding: VDB
//        get() = _binding

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
////        _binding = DataBindingUtil.inflate(inflater, layoutIdFragment, container, false)
////        _binding.apply {
////            lifecycleOwner = viewLifecycleOwner
////            setVariable(BR.viewModel, viewModel)
////            return root
////        }
//    }


//    protected fun setTitle(visibility: Boolean, title: String? = null) {
//        if (visibility) {
//            (activity as AppCompatActivity).supportActionBar?.show()
//            title?.let {
//                (activity as AppCompatActivity).supportActionBar?.title = it
//            }
//        } else {
//            (activity as AppCompatActivity).supportActionBar?.hide()
//        }
//    }

}