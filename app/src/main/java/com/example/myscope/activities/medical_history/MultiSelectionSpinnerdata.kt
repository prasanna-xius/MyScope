package com.example.myscope.activities.medical_history

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
import android.view.LayoutInflater
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

import android.widget.TextView


//@SuppressLint("AppCompatCustomView")
/*<<<<<<< HEAD:app/src/main/java/com/example/myscope/activities/medical_history/MultiSelectionSpinnerMedication.kt
class MultiSelectionSpinnerMedication : Spinner, DialogInterface.OnMultiChoiceClickListener {
=======
class MultiSelectionSpinnerdata : Spinner, DialogInterface.OnMultiChoiceClickListener {
>>>>>>> b6af64ace4d8441f802b4fb4ccf5a90e4b34602b:app/src/main/java/com/example/myscope/activities/medical_history/MultiSelectionSpinnerdata.kt
    internal var _items: Array<String>? = null
    internal var mSelection: BooleanArray? = null
    var allTitle: TextView ?= null


    internal var simple_adapter: ArrayAdapter<String>

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

        simple_adapter = ArrayAdapter(context,
                R.layout.spinner_dropdown_item,R.style.MyDialogTheme)
        super.setAdapter(simple_adapter)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        simple_adapter = ArrayAdapter(context,
               R.layout.spinner_dropdown_item)
        super.setAdapter(simple_adapter)
    }

    override fun onClick(dialog: DialogInterface, which: Int, isChecked: Boolean) {
        if (mSelection != null && which < mSelection!!.size && which>=1) {
            mSelection!![which] = isChecked

            simple_adapter.clear()
            simple_adapter.add(buildSelectedItemString())
        } else {
            if(which.equals(0)) {
            mSelection!![0] = false
            }

        }
    }

    override fun performClick(): Boolean {
        val builder = AlertDialog.Builder(context,R.style.MyDialogTheme)
        val titleView = LayoutInflater.from(context).inflate(R.layout.spinner_dropdown_item, null)
        allTitle = titleView.findViewById(R.id.text1) as TextView

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
        simple_adapter.clear()
        simple_adapter.add(_items!![0])
        Arrays.fill(mSelection!!, false)
    }

    fun setItems(items: List<String>) {
        _items = items.toTypedArray()
        mSelection = BooleanArray(_items!!.size)
        simple_adapter.clear()
        simple_adapter.add(_items!![0])
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
        simple_adapter.clear()
        simple_adapter.add(buildSelectedItemString())
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
        simple_adapter.clear()
        simple_adapter.add(buildSelectedItemString())
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
        simple_adapter.clear()
        simple_adapter.add(buildSelectedItemString())
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

}*/
