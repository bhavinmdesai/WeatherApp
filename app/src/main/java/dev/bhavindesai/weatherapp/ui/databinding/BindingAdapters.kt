package dev.bhavindesai.weatherapp.ui.databinding

import com.facebook.drawee.view.SimpleDraweeView

@androidx.databinding.BindingAdapter("app:imageUri")
fun setImageURI(view: SimpleDraweeView, imageURI: String) {
    view.setImageURI(imageURI)
}
