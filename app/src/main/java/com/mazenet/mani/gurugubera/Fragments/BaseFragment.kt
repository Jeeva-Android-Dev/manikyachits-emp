package com.mazenet.mani.gurugubera.Fragments


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import com.mazenet.mani.gurugubera.Utilities.MvpView


open class BaseFragment : Fragment(), MvpView {
    //    protected var activity: BaseActivity? = null
    var progressView: View? = null

    override fun showDialogWithError(errorCode: Int, error: String?) {

    }

    fun checkForInternet(): Boolean {
        val cm = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    override fun showNetWorkError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return TextView(activity).apply {
            setText(com.mazenet.mani.gurugubera.R.string.hello_blank_fragment)
        }
    }

    open fun onBackPressed():Boolean {
        return false
    }

    fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun doFragmentTransaction(
        fragment: BaseFragment,
        Tag: String,
        addToBack: Boolean,
        message: String,
        intentKey: String
    ) {
        hideProgressDialog()
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        if (!message.equals("")) {
            val arguments: Bundle = Bundle()
            arguments.putString(intentKey, message);
            fragment.setArguments(arguments);
        }
        if (addToBack) {
            transaction.addToBackStack(Tag)
        }
        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        transaction.replace(R.id.frame_container, fragment, Tag);
        transaction.commit()
    }

    fun doFragmentTransactionWithBundle(
        fragment: BaseFragment,
        Tag: String,
        addToBack: Boolean,
        arguments: Bundle
    ) {
        hideProgressDialog()
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        fragment.arguments = arguments
        if (addToBack) {
            transaction.addToBackStack(Tag)
        }
        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        transaction.replace(R.id.frame_container, fragment, Tag);
        transaction.commit()
    }

    override fun showProgressDialog() {
        if (activity != null) {
            if (progressView == null && checkForInternet()) {
                val rootLayout = activity!!.findViewById<View>(android.R.id.content) as FrameLayout
                val inflater = LayoutInflater.from(activity)
                progressView = inflater.inflate(R.layout.progress_layout, null, true)
                progressView!!.setEnabled(false)
                progressView!!.setOnClickListener({ v ->

                })
                rootLayout.addView(progressView)
//                val imageView = activity!!.findViewById<ImageView>(R.id.progressImage)
//                val startRotateAnimation = AnimationUtils
//                    .loadAnimation(context, R.anim.rotate_image)
//                imageView.startAnimation(startRotateAnimation)
            }
        }

    }

    override fun hideProgressDialog() {
        if (activity != null) {
            if (progressView != null) {
                progressView!!.setVisibility(View.GONE)
                val vg = progressView!!.getParent() as ViewGroup
                vg.removeView(progressView)
                progressView = null
            }
        }
    }

    protected fun setPrefsInt(key: String, `val`: Int) {
        BaseUtils.setSharedPrefs(activity, key, `val`)
    }

    protected fun setPrefsBoolean(key: String, `val`: Boolean) {
        BaseUtils.setSharedPrefs(activity, key, `val`)
    }

    protected fun setPrefsString(key: String, `val`: String) {
        activity?.let { BaseUtils.setSharedPrefs(it, key, `val`) }
    }

    protected fun getPrefsString(key: String, default: String): String {
        return BaseUtils.getSharedPrefs(activity, key, default)!!
    }

    protected fun getPrefsInt(key: String, default: Int): Int {
        return BaseUtils.getSharedPrefs(activity, key, default)
    }

    protected fun getPrefsBoolean(key: String, default: Boolean): Boolean {
        return BaseUtils.getSharedPrefs(activity, key, default)
    }

    fun ShowKeyBoard() {
        BaseUtils.showKeyboard(context!!)
    }

    fun CloseKeyBoard() {
        BaseUtils.closeKeyboard(context!!)
    }

}
