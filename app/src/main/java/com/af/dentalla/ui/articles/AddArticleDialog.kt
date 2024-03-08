package com.af.dentalla.ui.articles

import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatDialog
import com.af.dentalla.databinding.DialogAddArticleBinding
import com.af.dentalla.databinding.DialogAddBinding

class AddArticleDialog(context: Context) : AppCompatDialog(context) {
    private lateinit var binding: DialogAddArticleBinding
    private lateinit var addBaseBinding: DialogAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        addBaseBinding = DialogAddBinding.inflate(layoutInflater)
        binding = DialogAddArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addBaseBinding.imgViewCancel.setOnClickListener {
            cancel()
        }
    }
}