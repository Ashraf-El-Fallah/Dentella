package com.af.dentalla.ui.base

import androidx.fragment.app.Fragment
import com.af.dentalla.utilities.ValidationUtils
import com.af.dentalla.utilities.showToastShort
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    protected open fun isUserNameValid(userName: String) {
        if (ValidationUtils.isUserNameNotValid(userName)) {
            requireView().showToastShort("This user name is not valid")
            return
        }
    }

    protected open fun isEmailValid(email: String) {
        if (ValidationUtils.isEmailNotValid(email)) {
            requireView().showToastShort("This email is not valid")
            return
        }
    }

    protected open fun isPasswordValid(password: String) {
        if (ValidationUtils.isPasswordNotValid(password)) {
            requireView().showToastShort("This password is not valid")
            return
        }
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