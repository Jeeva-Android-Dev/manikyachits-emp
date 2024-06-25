package com.mazenet.mani.gurugubera.Adapters

import android.view.View

interface AdapterClickListener {

    fun onPositionClicked(view:View,position:Int)
    fun onLongClicked(position:Int)
//    fun onSwitchClicked(position:Int)
}