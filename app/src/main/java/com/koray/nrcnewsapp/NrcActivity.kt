package com.koray.nrcnewsapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.koray.nrcnewsapp.core.domain.ArticleItemModel
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.domain.NewsPageItemModel
import com.koray.nrcnewsapp.core.network.viewmodel.LiveArticleSelectionModel
import com.koray.nrcnewsapp.core.network.viewmodel.LiveCategorySelectionModel
import com.koray.nrcnewsapp.core.network.viewmodel.LiveToolbarArrow
import com.koray.nrcnewsapp.core.ui.articlepage.ArticlePageFragment
import com.koray.nrcnewsapp.core.ui.category.CategoryOnListInteractionListener
import com.koray.nrcnewsapp.core.ui.info.MenuItemInfoFragment
import com.koray.nrcnewsapp.core.ui.login.LiveLoginModel
import com.koray.nrcnewsapp.core.ui.newspage.NewsPageFragment
import com.koray.nrcnewsapp.core.ui.newspage.NewsPageOnListFragmentInteractionListener
import com.koray.nrcnewsapp.core.util.AnimationEffect
import com.koray.nrcnewsapp.core.util.ChangeBackgroundOnTouch
import com.koray.nrcnewsapp.core.util.FragmentAnimation
import javax.inject.Singleton


@Singleton
class NrcActivity : AppCompatActivity(),
    CategoryOnListInteractionListener,
    NewsPageOnListFragmentInteractionListener, View.OnClickListener {

    private val RC_SIGN_IN = 200

    private val liveCategorySelectionModel: LiveCategorySelectionModel by viewModels()
    private val liveArticleItemSelectionModel: LiveArticleSelectionModel by viewModels()
    private val liveAccountModel: LiveLoginModel by viewModels()
    private val liveToolbarArrow: LiveToolbarArrow by viewModels()
    private lateinit var toolbarText: TextView
    private lateinit var toolbarArrow: ImageView

    private val menuItemInfoFragment: MenuItemInfoFragment = MenuItemInfoFragment.newInstance()
    private val newsPageFragment: NewsPageFragment = NewsPageFragment.newInstance()

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var signInButton: SignInButton

    private var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedInstanceState = savedInstanceState

        setContentView(R.layout.activity_main)
        setCustomToolbar()
        setGoogleSignInButton()
        setGoogleSignInCheck()
        setAccount()
    }

    private fun setAccount() {
        this.liveAccountModel
            .getAccount()
            .observe(this, Observer {
                if (it != null) {
                    if (this.savedInstanceState == null) {
                        initNewsPageFragment()
                    }
                } else {
                    supportFragmentManager.fragments.forEach { fragmentToRemove ->
                        if (fragmentToRemove != null) {
                            supportFragmentManager
                                .beginTransaction()
                                .remove(fragmentToRemove)
                                .commit()
                        }
                    }
                    this.signInButton.visibility = View.VISIBLE
                }
            })
    }

    private fun setGoogleSignInButton() {
        signInButton = findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener(this)
        signInButton.setSize(SignInButton.SIZE_STANDARD)
    }

    private fun setGoogleSignInCheck() {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val account = GoogleSignIn.getLastSignedInAccount(this)

        updateUI(account)

    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account == null) {
            this.signInButton.visibility = View.VISIBLE
        } else {
            this.signInButton.visibility = View.GONE
            this.liveAccountModel.setAccount(account)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setCustomToolbar() {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_app)

        toolbarText = findViewById(R.id.toolbar_title)
        toolbarArrow = findViewById(R.id.toolbar_arrow)
        toolbarArrow.visibility = View.INVISIBLE

        val rotate = AnimationEffect.rotate(duration = 2 * 200)

        toolbarArrow.setOnClickListener {
            it.startAnimation(rotate)
            onBackPress()
        }

        toolbarArrow.setOnTouchListener(ChangeBackgroundOnTouch(resources))

        liveToolbarArrow.getStatus()
            .observe(this, Observer { status -> toolbarArrow.visibility = status })

        liveCategorySelectionModel.getCategory()
            .observe(this, Observer { categoryItem ->
                run {
                    val categoryDisplay = categoryItem.display!!
                    toolbarText.text =
                        "* ${categoryDisplay[0].toUpperCase() + categoryDisplay.substring(1)} *"
                }
            }
            )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            R.id.menu_info -> {
                if (!menuItemInfoFragment.isVisible) {
                    initInfoFragment()
                    liveToolbarArrow.showArrow()
                }
                return true
            }
            R.id.menu_logout -> {
                this.handleSingOutResult()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initInfoFragment() {
        val slide = FragmentAnimation.slide(supportFragmentManager)
        commitFragment(slide, menuItemInfoFragment, MenuItemInfoFragment.getTagName())
    }

    private fun initNewsPageFragment() {
        val rightBottomToLeftTop = FragmentAnimation.rightBottomToLeftTop(supportFragmentManager)
        commitFragment(rightBottomToLeftTop, newsPageFragment, ArticlePageFragment.getTagName())
    }

    private fun initArticlePageFragment() {
        val articlePageFragment: ArticlePageFragment = ArticlePageFragment.newInstance()
        val rightToLeftAnim = FragmentAnimation.rightToLeftAnim(supportFragmentManager)
        commitFragment(rightToLeftAnim, articlePageFragment, ArticlePageFragment.getTagName())
    }

    private fun commitFragment(
        fragmentTransaction: FragmentTransaction,
        fragment: Fragment,
        fragmentTag: String,
        containerId: Int = R.id.news_page_container
    ) {
        fragmentTransaction.add(
            containerId,
            fragment,
            fragmentTag
        )
            .addToBackStack(null)
            .commit()
    }

    override fun onListFragmentInteraction(category: CategoryItemModel?) {
        liveCategorySelectionModel.setCategory(category!!)
    }

    override fun onListFragmentInteraction(newsPageItem: NewsPageItemModel?) {
        if (newsPageItem is ArticleItemModel) {
            liveArticleItemSelectionModel.setArticleItemModel(newsPageItem)
            initArticlePageFragment()
            liveToolbarArrow.showArrow()
        }
    }

    override fun onBackPressed() {
        onBackPress()
    }

    private fun onBackPress() {
        val backStackCount = supportFragmentManager.backStackEntryCount

        if (backStackCount > 1) {
            supportFragmentManager.popBackStack()
        }

        if (backStackCount == 2) {
            liveToolbarArrow.hideArrow()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.sign_in_button -> signIn()
        }
    }

    private fun signIn() {
        println("Clicked to sign")
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
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

    private fun handleSingOutResult() {
        val completedTask = this.mGoogleSignInClient.signOut()
//        completedTask.addOnCompleteListener()

        this.liveAccountModel.signOut()
    }
}