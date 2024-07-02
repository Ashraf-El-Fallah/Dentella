package com.af.dentalla.ui.patient.homescreen

import com.af.dentalla.data.remote.requests.Post

interface AddPostDialogListener {
    fun onPostAdded(post: Post)
}