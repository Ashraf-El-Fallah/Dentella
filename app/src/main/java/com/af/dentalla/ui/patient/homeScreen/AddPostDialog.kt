package com.af.dentalla.ui.patient.homeScreen

import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatDialog
import com.af.dentalla.databinding.DialogAddArticleBinding
import com.af.dentalla.databinding.DialogAddBinding
import com.af.dentalla.databinding.DialogAddPostBinding

class AddPostDialog(context: Context) : AppCompatDialog(context) {
    private lateinit var binding: DialogAddPostBinding
    private lateinit var addBaseBinding: DialogAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        addBaseBinding = DialogAddBinding.inflate(layoutInflater)
        binding = DialogAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addBaseBinding.imgViewCancel.setOnClickListener {
            cancel()
        }
    }
}