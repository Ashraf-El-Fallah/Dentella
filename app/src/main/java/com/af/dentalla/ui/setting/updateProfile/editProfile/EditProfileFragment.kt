package com.af.dentalla.ui.setting.updateProfile.editProfile

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.af.dentalla.data.remote.requests.DoctorProfileInformation
import com.af.dentalla.databinding.FragmentEditProfileBinding
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.safeNavigate
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by viewModels()
    private var isEditMode = false
    private lateinit var galleryLauncher: ActivityResultLauncher<String>

    //    private var updatedDoctorProfileInformation: DoctorProfileInformation? = null
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        returnDoctorProfileInformationObserver()
        updateDoctorProfileInformationObserver()

        setTheEditTextsNotEditable()
        saveAllHintsForAllEditTexts()
        changeBetweenSaveAndEdit()

        navigateToHomeScreen()
        navigateToUpdatePasswordScreen()

        choosePictureFromGalleryListener()
//        binding.textViewEditOrSave.setOnClickListener {
//            if (binding.textViewEditOrSave.text == "Save") {
//                handleUpdatedDoctorInformation()
//                updateDoctorProfileInformationObserver()
//            }
//        }
    }

    private fun choosePictureFromGalleryListener() {
        pickPicFromGallery()
        binding.imgViewProfile.setOnClickListener {
            openGalleryForImage()
        }
    }

    private fun pickPicFromGallery() {
        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                imageUri = it
            }
        }
    }

    private fun openGalleryForImage() {
        galleryLauncher.launch("image/*")
    }


    private fun sendUpdatedDoctorDateToViewModel(updatedDoctorProfileInformation: DoctorProfileInformation) {
        updatedDoctorProfileInformation.let {
            editProfileViewModel.updateDoctorProfileInformation(
                it
            )
        }
    }

    private fun updateDoctorProfileInformationObserver() {
        editProfileViewModel.updateDoctorProfileInFormation.observe(viewLifecycleOwner) { updateProfileState ->
            when (updateProfileState) {
                is ScreenState.Loading -> {
                    binding.progressBar.progress.visible()
                    binding.textViewEditOrSave.gone()
                }

                is ScreenState.Success -> {
                    binding.progressBar.progress.gone()
                    Toast.makeText(
                        requireContext(),
                        "Response return successfully",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is ScreenState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Error when returning response",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.progressBar.progress.gone()
                }
            }
        }
    }


    private fun uriToMultipart(uri: Uri): MultipartBody.Part? {
        try {
            val contentResolver: ContentResolver = context?.contentResolver ?: return null
            val mimeType = contentResolver.getType(uri) ?: return null

            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val file = File(requireContext().cacheDir, "temp_file")

            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            val requestFile: RequestBody = RequestBody.create(mimeType.toMediaTypeOrNull(), file)
            return MultipartBody.Part.createFormData("file", file.name, requestFile)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }


    private fun handleUpdatedDoctorInformation() {
        binding.imgViewProfile.setImageURI(imageUri)
        val photoPart = imageUri?.let { uriToMultipart(it) }
        val updatedDoctorProfileInformation = DoctorProfileInformation(
            userName = binding.editTextName.hint.toString(),
            email = binding.editTextEmail.hint.toString(),
            phoneNumber = binding.editTextMobileNumber.hint.toString(),
            bio = binding.editTextBio.hint.toString(),
            currentLevel = "intermediate",
            currentUniversity = binding.editTextCurrentUniversity.hint.toString(),
            photo = photoPart
        )
        sendUpdatedDoctorDateToViewModel(updatedDoctorProfileInformation)
    }

    private fun returnDoctorProfileInformationObserver() {
        editProfileViewModel.profileInformation.observe(viewLifecycleOwner) { profileInformationState ->
            when (profileInformationState) {
                is ScreenState.Loading -> {
                    binding.apply {
                        progressBar.progress.visible()
                        textViewEditOrSave.gone()
                    }
                }

                is ScreenState.Error -> {
                    binding.apply {
                        progressBar.progress.gone()
                        textViewEditOrSave.visible()
                    }
                    Toast.makeText(requireContext(), "Error when getting data", Toast.LENGTH_SHORT)
                        .show()
                }

                is ScreenState.Success -> {
                    binding.apply {
                        progressBar.progress.gone()
                        textViewEditOrSave.visible()
                    }
                    val doctorProfileInformation = profileInformationState.uiData
                    binding.apply {
                        editTextName.hint = doctorProfileInformation.userName
                        editTextEmail.hint = doctorProfileInformation.email
                        editTextMobileNumber.hint = doctorProfileInformation.phoneNumber
                        editTextBio.hint = doctorProfileInformation.bio
                        editTextCurrentUniversity.hint = doctorProfileInformation.currentUniversity
//                        Glide.with(requireContext())
//                            .load(doctorProfileInformation.photo.toString())
//                            .into(imgViewProfile)
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

//            returnDoctorProfileInformationObserver()
        }
    }

    private fun toggleEditMode(editText: EditText, isEditeMode: Boolean) {
        if (isEditeMode) {
            val newText = editText.text.toString()
            editText.isEnabled = false
            editText.hint = newText
            binding.textViewEditOrSave.text = "Edit"
            handleUpdatedDoctorInformation()
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