package com.temperoni.gymroutine.view.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.temperoni.gymroutine.R
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import com.firebase.ui.auth.IdpResponse
import kotlin.concurrent.thread


class SplashActivity : AppCompatActivity() {

    private val providers = listOf(AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        thread(true) {
            Thread.sleep(1500L)
            if (FirebaseAuth.getInstance().currentUser == null)
                launchSignIn()
            else launchMainActivityAndClose()
        }
    }

    private fun launchSignIn() {
        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.mipmap.ic_launcher_round)
                .setTheme(R.style.SplashScreen)
                .build(),
                RC_SIGN_IN)
    }

    private fun launchMainActivityAndClose() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                launchMainActivityAndClose()
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                when (response) {
                    null -> finish()
                    else -> when (response.error?.errorCode) {
                        12501 -> finish()
                        else -> makeText(
                                this,
                                response.error?.localizedMessage,
                                LENGTH_SHORT)
                                .show()
                    }
                }
            }
        }
    }

    companion object {
        const val RC_SIGN_IN = 123
    }
}
