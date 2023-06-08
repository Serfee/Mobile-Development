package com.amb.SerFee.ui.dropdownmenu

import android.content.Context
import android.database.DataSetObserver
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

import android.widget.Spinner
import android.widget.SpinnerAdapter


class CustomSpinner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatSpinner(context, attrs, defStyleAttr) {

    override fun performClick(): Boolean {
        var handled = super.performClick()

        // Open the dropdown manually
        if (!handled) {
            handled = true
            onItemClickListener?.onItemClick(null, this, 0, 0)
        }

        return handled
    }

    override fun setAdapter(adapter: SpinnerAdapter?) {
        // Wrap the adapter with an empty view to prevent the dropdown from showing initially
        super.setAdapter(EmptySpinnerAdapter(adapter))
    }

    private inner class EmptySpinnerAdapter(private val wrappedAdapter: SpinnerAdapter?) :
        SpinnerAdapter {
        override fun registerDataSetObserver(observer: DataSetObserver?) {
            TODO("Not yet implemented")
        }

        override fun unregisterDataSetObserver(observer: DataSetObserver?) {
            TODO("Not yet implemented")
        }

        override fun getCount(): Int {
            TODO("Not yet implemented")
        }

        override fun getItem(position: Int): Any {
            TODO("Not yet implemented")
        }

        override fun getItemId(position: Int): Long {
            TODO("Not yet implemented")
        }

        override fun hasStableIds(): Boolean {
            TODO("Not yet implemented")
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            TODO("Not yet implemented")
        }

        override fun getItemViewType(position: Int): Int {
            TODO("Not yet implemented")
        }

        override fun getViewTypeCount(): Int {
            TODO("Not yet implemented")
        }

        override fun isEmpty(): Boolean {
            TODO("Not yet implemented")
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            return wrappedAdapter?.getDropDownView(position, convertView, parent)
        }

        // Implement other methods from the SpinnerAdapter interface as needed

        // ...

    }
}
