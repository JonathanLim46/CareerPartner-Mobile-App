package com.example.careerpartner.finishup.screen

import android.app.Activity
import android.app.AlertDialog
import com.example.careerpartner.R

class LoadingDialog {

    private lateinit var activity: Activity
    private lateinit var dialog: AlertDialog

    fun startLoadingDialog(activity: Activity) {
        this.activity = activity
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.item_loading_finishup, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    fun dismissDialog(){
        if(::dialog.isInitialized && dialog.isShowing) {
            dialog.dismiss()
        }
    }
}