package br.com.lucas.githubrepositories.ui

import android.view.View

enum class State { EMPTY, LOADING, ERROR, SUCCESS }

class ViewStateManager(
    private val mainView: View,
    private val errorView: View,
    private val loadingView: View,
    private val emptyView: View
) {
    fun success() {
        mainView.visibility = View.VISIBLE
        errorView.visibility = View.GONE
        loadingView.visibility = View.GONE
        emptyView.visibility = View.GONE
    }

    fun loading() {
        mainView.visibility = View.GONE
        errorView.visibility = View.GONE
        loadingView.visibility = View.VISIBLE
        emptyView.visibility = View.GONE
    }

    fun error() {
        mainView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        emptyView.visibility = View.GONE
    }

    fun empty(){
        mainView.visibility = View.GONE
        errorView.visibility = View.GONE
        loadingView.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
    }
}