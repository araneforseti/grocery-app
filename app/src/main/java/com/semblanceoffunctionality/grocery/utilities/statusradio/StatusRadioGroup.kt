package com.semblanceoffunctionality.grocery.utilities.statusradio

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.annotation.IdRes
import com.semblanceoffunctionality.grocery.R
import com.semblanceoffunctionality.grocery.data.StockStatus
import com.semblanceoffunctionality.grocery.data.StockStatusEnum
import com.semblanceoffunctionality.grocery.data.StockStatusEnum.*

class StatusRadioGroup : LinearLayout {

    private var radioButtonCheckedId = View.NO_ID
    var status : StockStatus? = null
        set(newStatus) {
            field = newStatus
            when(status?.stockStatus) {
                STOCKED -> this.check(R.id.status_stocked)
                UNKNOWN -> this.check(R.id.status_unknown)
                NOT_STOCKED -> this.check(R.id.status_not_stocked)
                else -> {this.clearCheck()}
            }
        }

    private var protectFromCheckedChange = false

    private var onCheckedChangeListener: OnCheckedChangeListener? = null
    private var childOnCheckedChangeListener: StatusRadioCheckable.OnCheckedChangeListener? = null
    private var passThroughHierarchyChangeListener: PassThroughHierarchyChangeListener? = null

    private val childViews = HashMap<Int, View>()


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
        val array = context.obtainStyledAttributes(attrs, R.styleable.StatusRadioGroup, 0, 0)
        try {
            radioButtonCheckedId = array.getResourceId(R.styleable.StatusRadioGroup_grgRadioButtonCheckedId, View.NO_ID)
        } finally {
            array.recycle()
        }
    }

    private fun setupView() {
        childOnCheckedChangeListener = CheckedStateTracker()
        passThroughHierarchyChangeListener = PassThroughHierarchyChangeListener()
        super.setOnHierarchyChangeListener(passThroughHierarchyChangeListener)
        when(status?.stockStatus) {
            STOCKED -> this.check(R.id.status_stocked)
            UNKNOWN -> this.check(R.id.status_unknown)
            NOT_STOCKED -> this.check(R.id.status_not_stocked)
            else -> {this.clearCheck()}
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child is StatusRadioCheckable) {
            if (child.isChecked) {
                protectFromCheckedChange = true
                if (radioButtonCheckedId != View.NO_ID) setCheckedStateForView(radioButtonCheckedId, false)
                protectFromCheckedChange = false
                setCheckedId(child.id, true)
            }
        }

        super.addView(child, index, params)
    }

    override fun setOnHierarchyChangeListener(listener: OnHierarchyChangeListener?) {
        // the user listener is delegated to our pass-through listener
        passThroughHierarchyChangeListener?.onHierarchyChangeListener = listener
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        // checks the appropriate radio button as requested in the XML file
        if (radioButtonCheckedId != View.NO_ID) {
            protectFromCheckedChange = true
            setCheckedStateForView(radioButtonCheckedId, true)
            protectFromCheckedChange = false
            setCheckedId(radioButtonCheckedId, true)
        }
    }

    private fun setCheckedId(@IdRes id: Int, checked: Boolean) {
        radioButtonCheckedId = id
        onCheckedChangeListener?.onCheckedChanged(this, childViews[id], checked, radioButtonCheckedId)
    }

    @IdRes
    fun checkedRadioButtonId() = radioButtonCheckedId

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return p is LayoutParams
    }

    private fun clearCheck() = check(View.NO_ID)

    private fun check(@IdRes id: Int) {
        if (id != View.NO_ID && id == radioButtonCheckedId) return // don't even bother
        if (radioButtonCheckedId != View.NO_ID) setCheckedStateForView(radioButtonCheckedId, false)
        if (id != View.NO_ID) setCheckedStateForView(id, true)
        setCheckedId(id, true)
    }

    private fun setCheckedStateForView(viewId: Int, checked: Boolean) {
        var checkedView = childViews[viewId]

        if (checkedView == null) {
            checkedView = findViewById(viewId)

            if (checkedView != null) {
                childViews[viewId] = checkedView
            }
        }

        (checkedView as? StatusRadioCheckable)?.isChecked = checked
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return LayoutParams(context, attrs)
    }

    // TODO: handle user defined click listeners
    fun setOnCheckedChangeListener(onCheckedChangeListener: OnCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener
    }

    // TODO: handle user defined click listeners
    fun onCheckedChangeListener() = onCheckedChangeListener

    fun interface OnCheckedChangeListener {
        fun onCheckedChanged(radioGroup: View, radioButton: View?, checked: Boolean, checkedId: Int)
    }

    private inner class CheckedStateTracker : StatusRadioCheckable.OnCheckedChangeListener {

        override fun onCheckedChange(radioButton: View, checked: Boolean) {
            if (protectFromCheckedChange) return // prevents from infinite recursion
            protectFromCheckedChange = true
            if (radioButtonCheckedId != View.NO_ID) setCheckedStateForView(radioButtonCheckedId, false)
            protectFromCheckedChange = false
            setCheckedId(radioButton.id, true)
        }
    }

    private inner class PassThroughHierarchyChangeListener : OnHierarchyChangeListener {

        var onHierarchyChangeListener: OnHierarchyChangeListener? = null

        override fun onChildViewAdded(parent: View?, child: View?) {
            if (parent == this@StatusRadioGroup && child is StatusRadioCheckable) {
                var id = child.id

                if (id == View.NO_ID) { // generates an id if it's missing
                    id = View.generateViewId()
                    child.id = id
                }

                childOnCheckedChangeListener?.let { (child as StatusRadioCheckable).addOnCheckedChangeListener(it) }
                childViews[id] = child
            }

            onHierarchyChangeListener?.onChildViewAdded(parent, child)
        }

        override fun onChildViewRemoved(parent: View?, child: View?) {
            if (parent == this@StatusRadioGroup && child is StatusRadioCheckable) {
                childOnCheckedChangeListener?.let { (child as StatusRadioCheckable).removeOnCheckedChangeListener(it) }
            }

            childViews.remove(child?.id)

            onHierarchyChangeListener?.onChildViewRemoved(parent, child)
        }
    }
}