package com.example.moviedbappwithmap.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    protected abstract fun clear()
}