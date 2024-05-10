package com.af.dentalla.ui.setting.updateProfile.editProfile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.af.dentalla.databinding.FragmentEditProfileBinding
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.loadImage
import com.af.dentalla.utils.safeNavigate
import com.af.dentalla.utils.visible
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by viewModels()
    private var isEditMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        returnProfileInformationObserver()
        setTheEditTextsNotEditable()
        saveAllHintsForAllEditTexts()
        changeBetweenSaveAndEdit()
        navigateToHomeScreen()
        navigateToUpdatePasswordScreen()
    }

    private fun returnProfileInformationObserver() {
        editProfileViewModel.profileInformation.observe(viewLifecycleOwner) { profileInformationState ->
            when (profileInformationState) {
                is ScreenState.Loading -> binding.progressBar.progress.visible()
                is ScreenState.Error -> {
                    binding.progressBar.progress.gone()
//                    Toast.makeText(requireContext(), "Error when getting data", Toast.LENGTH_SHORT)
//                        .show()
                }

                is ScreenState.Success -> {
                    binding.progressBar.progress.gone()
                    val doctorProfileInformation = profileInformationState.uiData
                    binding.apply {
                        editTextName.hint = doctorProfileInformation.userName
                        editTextEmail.hint = doctorProfileInformation.email
                        editTextMobileNumber.hint = doctorProfileInformation.phoneNumber
                        editTextBio.hint = doctorProfileInformation.bio
                        editTextCurrentUniversity.hint = doctorProfileInformation.currentUniversity
                        Glide.with(requireContext())
                            .load(doctorProfileInformation.photo.toString())
                            .into(imgViewProfile)
//                        imgViewProfile.loadImage(doctorProfileInformation.photo.toString())
                    }
                }
            }
        }
    }

    private fun navigateToUpdatePasswordScreen() {
        binding.editTextUpdatePassword.setOnClickListener {
            val action =
                EditProfileFragmentDirections.actionEditProfileFragmentToUpdatePasswordFragment()
            findNavController().navigate(action)
        }
    }

    private fun setTheEditTextsNotEditable() {
        binding.editTextName.isEnabled = false
        binding.editTextBio.isEnabled = false
        binding.editTextEmail.isEnabled = false
        binding.editTextMobileNumber.isEnabled = false
        binding.editTextCurrentUniversity.isEnabled = false
    }

    private fun updateEditTextHint(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                editText.hint = s.toString()
            }
        })
    }

    private fun saveAllHintsForAllEditTexts() {
        updateEditTextHint(binding.editTextName)
        updateEditTextHint(binding.editTextEmail)
        updateEditTextHint(binding.editTextMobileNumber)
        updateEditTextHint(binding.editTextCurrentUniversity)
        updateEditTextHint(binding.editTextBio)
    }

    private fun changeBetweenSaveAndEdit() {
        binding.textViewEditOrSave.setOnClickListener {
            toggleEditMode(binding.editTextName, isEditMode)
            toggleEditMode(binding.editTextEmail, isEditMode)
            toggleEditMode(binding.editTextMobileNumber, isEditMode)
            toggleEditMode(binding.editTextCurrentUniversity, isEditMode)
            toggleEditMode(binding.editTextBio, isEditMode)
            isEditMode = !isEditMode
        }
    }

    private fun toggleEditMode(editText: EditText, isEditeMode: Boolean) {
        if (isEditeMode) {
            val newText = editText.text.toString()
            editText.isEnabled = false
            editText.hint = newText
            binding.textViewEditOrSave.text = "Edit"
        } else {
            editText.isEnabled = true
            editText.requestFocus()
            binding.textViewEditOrSave.text = "Save"
        }
    }

    private fun navigateToHomeScreen() {
        binding.back.setOnClickListener {
            findNavController().safeNavigate(EditProfileFragmentDirections.actionEditProfileFragmentToPatientProfileFragment())
        }
    }
}