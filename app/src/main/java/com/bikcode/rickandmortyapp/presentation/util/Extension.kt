package com.bikcode.rickandmortyapp.presentation.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.squareup.picasso.Picasso


fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun <T : ViewDataBinding> ViewGroup.bindingInflate(
    @LayoutRes layoutRes: Int,
    attachToRoot: Boolean = true
): T = DataBindingUtil.inflate(LayoutInflater.from(context), layoutRes, this, attachToRoot)

fun ImageView.bindImageUrl(
    url: String?,
    @DrawableRes placeholder: Int,
    @DrawableRes errorPlaceholder: Int
) {
    if(url.isNullOrEmpty()){
        setImageResource(placeholder)
        return
    }
    Picasso.get()
        .load(url)
        .placeholder(placeholder)
        .error(errorPlaceholder)
        .into(this)
}