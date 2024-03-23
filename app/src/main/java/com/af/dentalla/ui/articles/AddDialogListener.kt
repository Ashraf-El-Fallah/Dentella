package com.af.dentalla.ui.articles

import com.af.dentalla.data.remote.requests.Article

interface AddDialogListener {
    fun onArticleAdded(article: Article)
}