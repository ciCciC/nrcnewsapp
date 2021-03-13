package com.koray.nrcnewsapp.core.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveAccountModel : ViewModel() {

    private val accountLiveData: MutableLiveData<AccountModel> by lazy { MutableLiveData<AccountModel>() }

    fun getAccount(): LiveData<AccountModel> {
        return this.accountLiveData
    }

    fun setAccount(account: AccountModel) {
        this.accountLiveData.value = account
    }

    fun signOut() {
        this.accountLiveData.value = AccountModel(isLoggedIn = false)
    }

}