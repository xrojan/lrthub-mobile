package com.xrojan.lrthubkotlin.activities

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Joshua de Guzman on 09/07/2018.
 */

open class BaseActivity : AppCompatActivity() {
    private val tag = BaseActivity::class.java.simpleName
    private val subscriptions = CompositeDisposable()

    /**
     * Loads request fragment in a specific layout resource
     * @param layoutId
     * @param fragment
     */
    fun loadFragment(layoutId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(layoutId, fragment)
                .commitNow()
    }

    /**
     * Create disposable subscriptions
     * @param disposable
     */
    fun subscribe(disposable: Disposable): Disposable {
        subscriptions.add(disposable)
        return disposable
    }

    /**
     * Base error logging
     * @param message
     */
    fun showError(message: String) {
        Log.e(tag, message)
    }

    /**
     * Override activity life cycles
     */
    override fun onStop() {
        super.onStop()
        subscriptions.clear()
    }
}