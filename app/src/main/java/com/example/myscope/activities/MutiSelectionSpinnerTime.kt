package com.example.myscope.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.AttributeSet
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import com.example.myscope.R

import java.util.Arrays
import java.util.LinkedList

@SuppressLint("AppCompatCustomView")
class MultiSelectionSpinnerTime : Spinner, DialogInterface.OnMultiChoiceClickListener {
    internal var _items: Array<String>? = null
    internal var mSelection: BooleanArray? = null

    internal var simple_adaptertime: ArrayAdapter<String>

    val selectedStrings: List<String>
        get() {
            val selection = LinkedList<String>()
            for (i in _items!!.indices) {
                if (mSelection!![i]) {
                    selection.add(_items!![i])
                }
            }
            return selection
        }

    val selectedIndicies: List<Int>
        get() {
            val selection = LinkedList<Int>()
            for (i in _items!!.indices) {
                if (mSelection!![i]) {
                    selection.add(i)
                }
            }
            return selection
        }

    val selectedItemsAsString: String
        get() {
            val sb = StringBuilder()
            var foundOne = false

            for (i in _items!!.indices) {
                if (mSelection!![i]) {
                    if (foundOne) {
                        sb.append(", ")
                    }
                    foundOne = true
                    sb.append(_items!![i])
                }
            }
            return sb.toString()
        }

    @SuppressLint("ResourceType")
    constructor(context: Context) : super(context) {

        simple_adaptertime = ArrayAdapter(context,
                R.layout.spinner_item,R.style.MyDialogTheme)
        super.setAdapter(simple_adaptertime)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        simple_adaptertime = ArrayAdapter(context,
                R.layout.spinner_item)
        super.setAdapter(simple_adaptertime)
    }

    override fun onClick(dialog: DialogInterface, which: Int, isChecked: Boolean) {
        if (mSelection != null && which < mSelection!!.size && which>=1) {
            mSelection!![which] = isChecked

            simple_adaptertime.clear()
            simple_adaptertime.add(buildSelectedItemString())
        } else {
            if(which.equals(0)) {
                mSelection!![0] = false
            }

        }
    }

    override fun performClick(): Boolean {
        val builder = AlertDialog.Builder(context,R.style.MyDialogTheme)
        builder.setMultiChoiceItems(_items, mSelection, this)

        builder.setPositiveButton("Ok") { arg0, arg1 -> }


        builder.show()
        return true
    }

    override fun setAdapter(adapter: SpinnerAdapter) {

    }

    fun setItems(items: Array<String>) {
        _items = items
        mSelection = BooleanArray(_items!!.size)
        simple_adaptertime.clear()
        simple_adaptertime.add(_items!![0])
        Arrays.fill(mSelection!!, false)
    }

    fun setItems(items: List<String>) {
        _items = items.toTypedArray()
        mSelection = BooleanArray(_items!!.size)
        simple_adaptertime.clear()
        simple_adaptertime.add(_items!![0])
        Arrays.fill(mSelection!!, false)
    }

    fun setSelection(selection: Array<String>) {
        for (cell in selection) {
            for (j in _items!!.indices) {
                if (_items!![j] == cell) {
                    mSelection?.set(j, true)
                }
            }
        }
    }

    fun setSelection(selection: List<String>) {
        for (i in mSelection!!.indices) {
            mSelection!![i] = false
        }
        for (sel in selection) {
            for (j in _items!!.indices) {
                if (_items!![j] == sel) {
                    mSelection!![j] = true
                }
            }
        }
        simple_adaptertime.clear()
        simple_adaptertime.add(buildSelectedItemString())
    }

    override fun setSelection(index: Int) {
        for (i in mSelection!!.indices) {
            mSelection!![i] = false
        }
        if (index >= 0 && index < mSelection!!.size) {
            mSelection!![index] = true
        } else {
            throw IllegalArgumentException("Index " + index
                    + " is out of bounds.")
        }
        simple_adaptertime.clear()
        simple_adaptertime.add(buildSelectedItemString())
    }

    fun setSelection(selectedIndicies: IntArray) {
        for (i in mSelection!!.indices) {
            mSelection!![i] = false
        }
        for (index in selectedIndicies) {
            if (index >= 0 && index < mSelection!!.size) {
                mSelection!![index] = true
            } else {
                throw IllegalArgumentException("Index " + index
                        + " is out of bounds.")
            }
        }
        simple_adaptertime.clear()
        simple_adaptertime.add(buildSelectedItemString())
    }

    private fun buildSelectedItemString(): String {
        val sb = StringBuilder()
        var foundOne = false

        for (i in _items!!.indices) {
            if (mSelection!![i]) {
                if (foundOne) {
                    sb.append(", ")
                }
                foundOne = true

                sb.append(_items!![i])
            }
        }
        return sb.toString()
    }

}
