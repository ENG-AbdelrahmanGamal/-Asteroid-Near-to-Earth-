package com.udacity.asteroidradar

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
       // imageView.contentDescription="@string/potentially_hazardous_asteroid_image"
        //SUGGESTION by Reviewer
        imageView.contentDescription=imageView.context.getString(R.string.potentially_hazardous_asteroid_image)

    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
      //  imageView.contentDescription="@string/not_hazardous_asteroid_image"
        //SUGGESTION by Reviewer
        imageView.contentDescription=imageView.context.getString(R.string.not_hazardous_asteroid_image)

    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
       // imageView.contentDescription="@string/potentially_hazardous_asteroid_image"
        //SUGGESTION by Reviewer
        imageView.contentDescription=imageView.context.getString(R.string.potentially_hazardous_asteroid_image)

    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
       // imageView.contentDescription="@string/not_hazardous_asteroid_image"
        // SUGGESTION by Reviewer
        imageView.contentDescription=imageView.context.getString(R.string.not_hazardous_asteroid_image)


    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
    fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}
@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
@BindingAdapter("picture_of_day")
fun bindImageToDisplayPictureOfDay(imageView: ImageView,pictureOfDay: String?)
{
        Picasso.get()
            .load(pictureOfDay)
            .placeholder(R.drawable.placeholder_picture_of_day)
            .error(R.drawable.ic_launcher_background)
            .into(imageView)
    Log.d("TAG", "bindImageToDisplayPictureOfDay: $pictureOfDay")

}
