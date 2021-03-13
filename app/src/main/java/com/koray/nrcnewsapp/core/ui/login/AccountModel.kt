package com.koray.nrcnewsapp.core.ui.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

data class AccountModel(
    var googleSignInAccount: GoogleSignInAccount? = null,
    var isLoggedIn: Boolean
)
