package com.koray.nrcnewsapp.core.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.koray.nrcnewsapp.R


class LoginFragment : Fragment(), View.OnClickListener {

    private val RC_SIGN_IN = 200

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var signInButton: SignInButton

    private val liveAccountModel: LiveAccountModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setGoogleSignInButton(view)
        setGoogleSignInCheck()
    }

    private fun setGoogleSignInButton(view: View) {
        signInButton = view.findViewById(R.id.google_sign_in_button)
        signInButton.setOnClickListener(this)
        signInButton.setColorScheme(SignInButton.COLOR_AUTO)
        signInButton.setSize(SignInButton.SIZE_ICON_ONLY)
    }

    private fun setGoogleSignInCheck() {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        val account = GoogleSignIn.getLastSignedInAccount(requireContext())

        updateUI(account)

    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            this.liveAccountModel.setAccount(AccountModel(account, true))
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.google_sign_in_button -> signIn()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            updateUI(account)
        } catch (e: ApiException) {
            println("signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()

        fun getTagName(): String {
            return LoginFragment::class.java.name
        }
    }

}