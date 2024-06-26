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

    protected fun isPhoneNumberValid(phoneNumber: String): Boolean {
        if (ValidationUtils.isPhoneNumberNotValid(phoneNumber)) {
            Toast.makeText(requireContext(), R.string.phone_not_valid, Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

}