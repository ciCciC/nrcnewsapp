package com.koray.nrcnewsapp.core.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class LiveLoginModel : ViewModel() {

    private val accountLiveData: MutableLiveData<GoogleSignInAccount> by lazy { MutableLiveData<GoogleSignInAccount>() }

    fun getAccount(): LiveData<GoogleSignInAccount> {
        return this.accountLiveData
    }

    fun setAccount(account: GoogleSignInAccount) {
        this.accountLiveData.value = account
    }

    fun signOut() {
        this.accountLiveData.value = null
    }

}