package com.af.dentalla.ui.patient.homeScreen

import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.af.dentalla.R
import com.af.dentalla.data.remote.requests.Post
import com.af.dentalla.databinding.DialogAddPostBinding

class AddPostDialog(context: Context, private val addPostDialogListener: AddPostDialogListener) :
    AppCompatDialog(context,R.style.CustomDialogTheme) {
    private lateinit var binding: DialogAddPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addDialog.buttonPost.setOnClickListener {
            val content = binding.addDialog.editTextToWrite.text.toString()
            if (content.isNullOrEmpty()) {
                Toast.makeText(context, R.string.please_add_content, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val post = Post(content = content)
            addPostDialogListener.onPostAdded(post)
            dismiss()
        }

        binding.addDialog.imgViewCancel.setOnClickListener {
            cancel()
        }
    }
}