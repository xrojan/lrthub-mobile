package com.xrojan.lrthubkotlin.fragments

import android.support.v4.app.Fragment
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

open class BaseFragment : Fragment() {
    private val subscriptions = CompositeDisposable()

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
    fun showError(tag: String, message: String) {
        Log.e(tag, message)
    }
}