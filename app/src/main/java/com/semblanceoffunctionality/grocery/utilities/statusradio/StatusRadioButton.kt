package com.semblanceoffunctionality.grocery.utilities.statusradio

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.core.widget.ImageViewCompat
import com.semblanceoffunctionality.grocery.R

class StatusRadioButton: RelativeLayout, StatusRadioCheckable {

    companion object {
        private const val DEFAULT_PRESSED_BACKGROUND_PRESSED_ID = R.drawable.background_pressed
        private const val DEFAULT_UNPRESSED_IMAGE_TINT = Color.TRANSPARENT
        private const val DEFAULT_PRESSED_IMAGE_TINT = Color.TRANSPARENT
    }

    private var imageI: ImageView? = null
    private var image: Drawable? = null

    private var unpressedImageTint = Color.TRANSPARENT
    private var pressedImageTint = Color.TRANSPARENT

    private var pressedBackgroundDrawable: Drawable? = null
    private var unpressedBackgroundDrawable: Drawable? = null

    private var myOnClickListener: OnClickListener? = null
    private var myOnTouchListener: OnTouchListener? = null

    private var myChecked = false
    private var myCheckable = true

    private val onCheckedChangeListeners = ArrayList<StatusRadioCheckable.OnCheckedChangeListener>()

    constructor(context: Context) : super(context) {
        setupView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        parseAttrs(attrs)
        setupView()
    }

    constructor(context: Context, attrs: AttributeSet, defaultStyleAttrs: Int) : super(context, attrs, defaultStyleAttrs) {
        parseAttrs(attrs)
        setupView()
    }

    private fun parseAttrs(attrs: AttributeSet) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.StatusImageRadioButton)
        try {
            image = array.getDrawable(R.styleable.StatusImageRadioButton_statusImage)
            unpressedImageTint = array.getColor(R.styleable.StatusImageRadioButton_statusUnpressedImageTint, DEFAULT_UNPRESSED_IMAGE_TINT)
            pressedImageTint = array.getColor(R.styleable.StatusImageRadioButton_statusPressedImageTint, DEFAULT_PRESSED_IMAGE_TINT)
            pressedBackgroundDrawable = array.getDrawable(R.styleable.StatusImageRadioButton_statusPressedBackgroundDrawable)
        } finally {
            array.recycle()
        }
    }

    private fun setupView() {
        inflateView()
        bindView()
        setCustomTouchListener()
    }

    private fun inflateView() {
        LayoutInflater.from(context).inflate(R.layout.image_radio_button, this, true)
        imageI = findViewById(R.id.image_i)
        unpressedBackgroundDrawable = background
    }

    private fun bindView() {
        imageI?.setImageDrawable(image)
        bindUnpressedImageTint()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        myOnClickListener = l
    }

    private fun setCustomTouchListener() {
        super.setOnTouchListener(TouchListener())
    }

    override fun setOnTouchListener(l: OnTouchListener?) {
        myOnTouchListener = l
    }

    // TODO: handle user defined click listeners
    fun onTouchListener() = myOnTouchListener

    fun onTouchDown(motionEvent: MotionEvent) {
        // TODO: handle user defined click listeners
        isChecked = true
    }

    fun onTouchUp(motionEvent: MotionEvent) {
        // TODO: handle user defined click listeners
        myOnClickListener?.onClick(this)
    }

    private fun setPressedState() {
        if (pressedBackgroundDrawable == null) setBackgroundResource(DEFAULT_PRESSED_BACKGROUND_PRESSED_ID)
        else background = pressedBackgroundDrawable

        bindPressedImageTint()
    }

    private fun setUnpressedState() {
        background = unpressedBackgroundDrawable

        bindUnpressedImageTint()
    }

    private fun bindPressedImageTint() {
        if (pressedImageTint != DEFAULT_PRESSED_IMAGE_TINT) {
            imageI?.let { ImageViewCompat.setImageTintList(it, ColorStateList.valueOf(pressedImageTint)) }
        }
    }

    private fun bindUnpressedImageTint() {
        if (unpressedImageTint != DEFAULT_UNPRESSED_IMAGE_TINT) {
            imageI?.let { ImageViewCompat.setImageTintList(it, ColorStateList.valueOf(unpressedImageTint)) }
        }
    }

    fun setImageResource(@DrawableRes imageResId: Int) {
        imageI?.setImageResource(imageResId)
    }

    override fun addOnCheckedChangeListener(listener: StatusRadioCheckable.OnCheckedChangeListener) {
        onCheckedChangeListeners.add(listener)
    }

    override fun removeOnCheckedChangeListener(listener: StatusRadioCheckable.OnCheckedChangeListener) {
        onCheckedChangeListeners.remove(listener)
    }

    override fun setChecked(checked: Boolean) {
        if (myCheckable) {
            if (myChecked != checked) {
                myChecked = checked
                if (onCheckedChangeListeners.isNotEmpty()) {
                    for (i in onCheckedChangeListeners.indices) {
                        onCheckedChangeListeners[i].onCheckedChange(this, myChecked)
                    }
                }

                if (myChecked) setPressedState() else setUnpressedState()
            }
        }
    }

    override fun isChecked(): Boolean {
        return myChecked
    }

    override fun toggle() {
        isChecked = !myChecked
    }

    // TODO: handle user defined click listeners
    fun setCheckable(checkable: Boolean) {
        myCheckable = checkable
    }

    private inner class TouchListener : OnTouchListener {

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> onTouchDown(event)
                MotionEvent.ACTION_UP -> onTouchUp(event)
            }

            myOnTouchListener?.onTouch(v, event)

            return true
        }
    }
}