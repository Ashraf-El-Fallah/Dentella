package com.af.dentalla.ui.articles

import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.af.dentalla.data.remote.requests.Article
import com.af.dentalla.databinding.DialogAddArticleBinding

class AddArticleDialog(context: Context, private val addArticleDialogListener: AddArticleDialogListener) :
    AppCompatDialog(context) {
    private lateinit var binding: DialogAddArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogAddArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addDialog.buttonPost.setOnClickListener {
            val input = binding.addDialog.editTextToWrite.text.toString()
            if (input.isNullOrEmpty()) {
                Toast.makeText(context, "Please enter article to post", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val article = Article(content = input, imageData = null, title = null)
            addArticleDialogListener.onArticleAdded(article)
            dismiss()
        }
        binding.addDialog.imgViewCancel.setOnClickListener {
            cancel()
        }
    }
}