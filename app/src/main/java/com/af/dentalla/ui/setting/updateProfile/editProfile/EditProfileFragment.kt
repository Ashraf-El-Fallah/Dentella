package com.af.dentalla.ui.setting.updateProfile.editProfile

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
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
import com.af.dentalla.R
import com.af.dentalla.data.remote.requests.UserProfileInformation
import com.af.dentalla.databinding.FragmentEditProfileBinding
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.safeNavigate
import com.af.dentalla.utils.visible
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by viewModels()
    private var isEditMode = false
    private lateinit var galleryLauncher: ActivityResultLauncher<String>
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

        returnUserProfileInformationObserver()
        updateUserProfileInformationObserver()

        setTheEditTextsNotEditable()
        changeBetweenSaveAndEdit()

        navigateToHomeScreen()
        navigateToUpdatePasswordScreen()

        choosePictureFromGalleryListener()
    }

    private fun choosePictureFromGalleryListener() {
        pickPicFromGallery()
        binding.imgViewProfile.setOnClickListener {
            openGalleryForImage()
            if (!isEditMode) {
                handleEditOrSaveButtonClickListener()
            }
        }
    }

    private fun pickPicFromGallery() {
        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                imageUri = it
                binding.imgViewProfile.setImageURI(it)
            }
        }
    }

    private fun openGalleryForImage() {
        galleryLauncher.launch("image/*")
    }

    private fun uriToFile(uri: Uri): File? {
        return try {
            val contentResolver: ContentResolver = requireContext().contentResolver
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val tempFile = File.createTempFile("image", ".jpg", requireContext().cacheDir)
            FileOutputStream(tempFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun fileToMultipartBody(file: File): MultipartBody.Part {
        val requestFile: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("photo", file.name, requestFile)
    }


    private fun sendUpdatedUserDataToViewModel(updatedUserProfileInformation: UserProfileInformation) {
        updatedUserProfileInformation.let {
            editProfileViewModel.updateUserProfileInformation(
                it
            )
        }
    }

    private fun updateUserProfileInformationObserver() {
        editProfileViewModel.updateUserProfileInFormation.observe(viewLifecycleOwner) { updateProfileState ->
            when (updateProfileState) {
                is ScreenState.Loading -> {
                    binding.progressBar.progress.visible()
                    binding.textViewEditOrSave.gone()
                }

                is ScreenState.Success -> {
                    binding.progressBar.progress.gone()
                    binding.textViewEditOrSave.visible()
                    Toast.makeText(
                        requireContext(),
                        R.string.response_return_successfully,
                        Toast.LENGTH_LONG
                    ).show()
                }

                is ScreenState.Error -> {
                    if (updateProfileState.message == "HTTP 401 Unauthorized") {
                        Toast.makeText(
                            requireContext(),
                            R.string.want_to_login_again,
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            R.string.cannot_send_data,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    binding.progressBar.progress.gone()
                    binding.textViewEditOrSave.visible()
                }
            }
        }
    }


    private fun getTextOrHint(editText: EditText): String {
        val text = editText.text.toString()
        return text.ifBlank { editText.hint.toString() }
    }

    private fun handleUpdatedUserInformation() {
        val photoPart = imageUri?.let { uri ->
            val file = uriToFile(uri)
            file?.let { fileToMultipartBody(it) }
        }
        val updatedUserProfileInformation = UserProfileInformation(
            userName = getTextOrHint(binding.editTextName),
            email = getTextOrHint(binding.editTextEmail),
            phoneNumber = getTextOrHint(binding.editTextMobileNumber),
            bio = getTextOrHint(binding.editTextBio),
            currentUniversity = getTextOrHint(binding.editTextCurrentUniversity),
            currentLevel = "intermediate",
            photo = photoPart
        )
        sendUpdatedUserDataToViewModel(updatedUserProfileInformation)
    }

    private fun returnUserProfileInformationObserver() {
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
                    if (profileInformationState.message == "HTTP 401 Unauthorized") {
                        Toast.makeText(
                            requireContext(),
                            R.string.want_to_login_again,
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            R.string.error_when_returning_response,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is ScreenState.Success -> {
                    binding.apply {
                        progressBar.progress.gone()
                        textViewEditOrSave.visible()

                        val profileInformation = profileInformationState.uiData

                        editTextName.hint = profileInformation.userName
                        editTextEmail.hint = profileInformation.email
                        editTextMobileNumber.hint = profileInformation.phoneNumber
                        editTextBio.hint = profileInformation.bio
                        editTextCurrentUniversity.hint = profileInformation.currentUniversity
                        val imgViewProfile = binding.imgViewProfile
                        val photoUrl = profileInformation.photo
                        if (!photoUrl.isNullOrEmpty()) {
                            Glide.with(requireContext())
                                .load(photoUrl)
                                .into(imgViewProfile)
                        } else {
                            imgViewProfile.setImageResource(R.drawable.img)
                        }
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
        binding.apply {
            editTextName.isEnabled = false
            editTextBio.isEnabled = false
            editTextEmail.isEnabled = false
            editTextMobileNumber.isEnabled = false
            editTextCurrentUniversity.isEnabled = false
        }
    }

    private fun changeBetweenSaveAndEdit() {
        binding.textViewEditOrSave.setOnClickListener {
            handleEditOrSaveButtonClickListener()
        }
    }

    private fun handleEditOrSaveButtonClickListener() {
        toggleEditMode(binding.editTextName, isEditMode)
        toggleEditMode(binding.editTextEmail, isEditMode)
        toggleEditMode(binding.editTextMobileNumber, isEditMode)
        toggleEditMode(binding.editTextCurrentUniversity, isEditMode)
        toggleEditMode(binding.editTextBio, isEditMode)

        if (isEditMode) {
            handleUpdatedUserInformation()
        }
        returnUserProfileInformationObserver()
        isEditMode = !isEditMode
    }

    private fun toggleEditMode(editText: EditText, isEditeMode: Boolean) {
        if (isEditeMode) {
            val newText = editText.text.toString()
            editText.isEnabled = false
            editText.hint = newText
            binding.textViewEditOrSave.text = getString(R.string.edit)
        } else {
            editText.isEnabled = true
            editText.requestFocus()

            if (editText.text.isEmpty()) {
                editText.setText(editText.hint)
                editText.setSelection(editText.text.length)
            }
            binding.textViewEditOrSave.text = getString(R.string.save)
        }
    }

    private fun navigateToHomeScreen() {
        binding.back.root.setOnClickListener {
            findNavController().safeNavigate(EditProfileFragmentDirections.actionEditProfileFragmentToPatientProfileFragment())
        }
    }

//    private fun updateEditTextHint(editText: EditText) {
//        editText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//            override fun afterTextChanged(s: Editable?) {
//                editText.hint = s.toString()
//            }
//        })
//    }

//    private fun saveAllHintsForAllEditTexts() {
//        updateEditTextHint(binding.editTextName)
//        updateEditTextHint(binding.editTextEmail)
//        updateEditTextHint(binding.editTextMobileNumber)
//        updateEditTextHint(binding.editTextCurrentUniversity)
//        updateEditTextHint(binding.editTextBio)
//    }

    //    private fun uriToMultipart(uri: Uri): MultipartBody.Part? {
//        try {
//            val contentResolver: ContentResolver = context?.contentResolver ?: return null
//            val mimeType = contentResolver.getType(uri) ?: return null
//
//            val inputStream = contentResolver.openInputStream(uri) ?: return null
//            val file = File(requireContext().cacheDir, "temp_file")
//
//            FileOutputStream(file).use { outputStream ->
//                inputStream.copyTo(outputStream)
//            }
//            val requestFile: RequestBody = file.asRequestBody(mimeType.toMediaTypeOrNull())
//            return MultipartBody.Part.createFormData("file", file.name, requestFile)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return null
//        }
//    }
}