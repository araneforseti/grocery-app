package com.semblanceoffunctionality.grocery.ui

import android.content.Context
import android.util.AttributeSet
import com.semblanceoffunctionality.grocery.R

class StatusSwitcher(
    context: Context,
    attrs: AttributeSet
) : androidx.appcompat.widget.AppCompatImageButton(context, attrs) {
    enum class StatusEmun {
        STOCKED,
        UNKNOWN,
        NOT_STOCKED;
    }

    interface StatusListener {
        fun onStocked()
        fun onUnknown()
        fun onNotStocked()
    }

    private var mState: StatusEmun = StatusEmun.UNKNOWN
    private var mStatusListener: StatusListener? = null

    override fun performClick(): Boolean {
        super.performClick()
        val next = ((mState.ordinal + 1) % StatusEmun.values().size)
        setState(StatusEmun.values()[next])
        performSwitchClick()
        return true
    }

    private fun performSwitchClick() {
        when(mState) {
            StatusEmun.STOCKED -> mStatusListener?.onStocked()
            StatusEmun.UNKNOWN -> mStatusListener?.onUnknown()
            StatusEmun.NOT_STOCKED -> mStatusListener?.onNotStocked()
        }
    }

    private fun createDrawableState() {
        when(mState) {
            StatusEmun.STOCKED -> setImageResource(R.drawable.ic_status_stocked)
            StatusEmun.UNKNOWN -> setImageResource(R.drawable.ic_status_unknown)
            StatusEmun.NOT_STOCKED -> setImageResource(R.drawable.ic_status_not_stocked)
        }
    }

    fun getState(): StatusEmun {
        return mState
    }

    fun setState(state: StatusEmun) {
        this.mState = state
        createDrawableState()
    }

    fun getStatusListener(): StatusListener? {
        return mStatusListener
    }

    fun setStatusListener(statuslistener: StatusListener) {
        this.mStatusListener = statuslistener
    }
}