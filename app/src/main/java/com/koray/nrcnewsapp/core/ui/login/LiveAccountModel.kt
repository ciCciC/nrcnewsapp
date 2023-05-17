package com.koray.nrcnewsapp.core.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveAccountModel : ViewModel() {

    private val accountLiveData: MutableLiveData<AccountModel> by lazy { MutableLiveData<AccountModel>() }

    fun getAccount(): LiveData<AccountModel> {
        this.accountLiveData.value = AccountModel(isLoggedIn = true) // TODO: remove this line
        return this.accountLiveData
    }

    fun setAccount(account: AccountModel) {
        this.accountLiveData.value = account
    }

    fun signOut() {
        this.accountLiveData.value = AccountModel(isLoggedIn = false)
    }

}