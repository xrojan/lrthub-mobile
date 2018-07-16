package com.xrojan.lrthubkotlin.ui.profile

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.xrojan.lrthubkotlin.App
import com.xrojan.lrthubkotlin.R
import com.xrojan.lrthubkotlin.repository.entities.Gender
import com.xrojan.lrthubkotlin.repository.entities.UserProfile
import com.xrojan.lrthubkotlin.viewmodel.UserViewModel
import com.xrojan.lrthubkotlin.viewmodel.data.UIDataArray
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_update_profile_dialog.*
import kotlinx.android.synthetic.main.profile_fragment.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class UpdateProfileDialogFragment : DialogFragment() {
    private lateinit var userProfile: UIDataArray<List<UserProfile>>
    private val userViewModel: UserViewModel = App.injectUserViewModel()
    private val subscriptions = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_profile_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun subscribe(disposable: Disposable): Disposable {
        subscriptions.add(disposable)
        return disposable
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }

    override fun onStart() {
        super.onStart()
        setStyle(android.app.DialogFragment.STYLE_NO_FRAME, theme)
        dialog.window!!.setWindowAnimations(R.style.DialogAnimationRtlLtr)
    }

    fun show(manager: FragmentManager?, tag: String?, userProfile: UIDataArray<List<UserProfile>>) {
        super.show(manager, tag)
        this.userProfile = userProfile
        Log.e(tag, userProfile.toString())
    }

    private fun getSpinners() {
        subscribe(userViewModel.getUserLocalData()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe {
                    if (it.isNotEmpty()) {
                        //
                    }
                })
    }

    private fun getGender(token: String) {
        subscribe(userViewModel.getGenders(token)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe {
                    //
                })
    }

    fun setSpinnerGender(data: UIDataArray<List<Gender>>) {
        //
    }

}
