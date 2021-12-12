package com.example.testforskb_lab

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso

fun picassoHelper(url: String, imageView: ImageView) {
    Picasso.get().load(url).into(imageView);
}

fun parserForDate(string: String): String {
    val doubleString = string.split("T")
    val dateStringTripple = doubleString[0].split("-")
    val timeStringTripple = doubleString[1].split(":")
    return dateStringTripple[0] + "." + dateStringTripple[1] + "." + dateStringTripple[2] +
            " " + timeStringTripple[0] + ":" + timeStringTripple[1]
}

fun createClient(
    context: Context
): GoogleSignInClient {
    val gso: GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("379999383597-30nutsiarfi94kili4sp91iseqoe78do.apps.googleusercontent.com")
            .build()
    return GoogleSignIn.getClient(context, gso)
}