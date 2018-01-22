package re.spitfy.ctftime.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar


class AutoCompleteLoadingTextView : android.support.v7.widget.AppCompatAutoCompleteTextView {
    constructor(ctx : Context) : super(ctx)
    constructor(ctx : Context, attrSet : AttributeSet) : super(ctx, attrSet)
    constructor(ctx : Context, attrSet : AttributeSet, defStyle : Int) : super(ctx, attrSet, defStyle)

    private lateinit var loadingIndicator : ProgressBar

    fun setLoadingIndicator(progressBar : ProgressBar) {
        loadingIndicator = progressBar
    }

    override fun performFiltering(text: CharSequence?, keyCode: Int) {
        loadingIndicator.visibility = View.VISIBLE
        super.performFiltering(text, keyCode)
    }

    override fun onFilterComplete(count: Int) {
        loadingIndicator.visibility = View.INVISIBLE
        super.onFilterComplete(count)
    }
}