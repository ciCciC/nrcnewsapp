package com.koray.nrcnewsapp.core.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.SignInButton
import com.koray.nrcnewsapp.R


class LoginFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var modelLive: LiveLoginModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signInButton: SignInButton = view.findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener(this)
        signInButton.setSize(SignInButton.SIZE_STANDARD)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        modelLive = ViewModelProvider(this).get(LiveLoginModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.sign_in_button -> signIn()
        }
    }

    private fun signIn(){
//        val signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}