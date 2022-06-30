package com.example.customsearchview

import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter

object BindingAdapters {
    interface OnQueryTextSubmit {
        fun onQueryTextSubmit(query: String)
    }

    @JvmStatic
    @BindingAdapter(value = ["bgColor", "iconColor"], requireAll = false)
    fun setToolBarColors(view: Toolbar, bgColor: Int, iconColor: Int) {
        view.let { toolbar ->
            toolbar.setBackgroundColor(bgColor)
            toolbar.navigationIcon?.setTint(iconColor)
        }
    }

    @JvmStatic
    @BindingAdapter(value = [
        "selectedTextColor",
        "unSelectedTextColor",
        "overlayColor",
        "alpha",
        "clearColor",
        "hintText",
        "boxBgDrawable",
        "searchIconDrawable",
        "onQueryTextSubmit"
    ], requireAll = false)
    fun setSearchViewColors(
        view: SearchView,
        textColor: Int,
        hintTextColor: Int,
        searchBgColor: Int,
        searchBgAlpha: Int,
        clearColor:Int,
        hintText: String,
        boxBgDrawable: Drawable,
        searchIconDrawable: Drawable,
        submit:OnQueryTextSubmit,
    ) {
        view.let { searchView ->
            searchView.isIconified = false
            searchView.setOnCloseListener { true }

            // 検索文字のサイズ及び色設定
            searchView.findViewById<SearchView.SearchAutoComplete>(androidx.appcompat.R.id.search_src_text)?.apply {
                setTextColor(textColor)
                textSize = 13.toFloat()
            }
            // 検索ボックス内背景色設定
            searchView.findViewById<ViewGroup>(androidx.appcompat.R.id.search_edit_frame)?.apply {
                val wrapDrawable = DrawableCompat.wrap(boxBgDrawable)
                wrapDrawable.setTint(searchBgColor)
                wrapDrawable.alpha = searchBgAlpha
                background = wrapDrawable
            }
            // 検索ボックス内XボタンPadding及びTint色設定
            searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)?.apply {
                val removeIconPadding = resources.getDimensionPixelSize(R.dimen.padding_s)
                setPadding(removeIconPadding, removeIconPadding, removeIconPadding, removeIconPadding)
                setColorFilter(hintTextColor)
            }
            // 虫眼鏡アイコンTint色及びヒントテキストの文字と色設定
            searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)?.apply {
                val hintTextSize = resources.getDimensionPixelSize(R.dimen.text_s)
                val hintIconSize = resources.getDimensionPixelSize(R.dimen.text_l)
                val wrapDrawable = DrawableCompat.wrap(searchIconDrawable)
                wrapDrawable.setTint(hintTextColor)
                wrapDrawable.setBounds(0, 0, hintIconSize, hintIconSize)
                val sb = SpannableStringBuilder(" ").append(hintText)
                sb.setSpan(ImageSpan(wrapDrawable),0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                sb.setSpan(AbsoluteSizeSpan(hintTextSize), 0, sb.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                sb.setSpan(ForegroundColorSpan(hintTextColor), 0, sb.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                searchView.queryHint = sb
            }
            // 検索下線削除
            searchView.findViewById<View>(androidx.appcompat.R.id.search_plate)?.apply {
                setBackgroundColor(clearColor)
            }

            // 検索実行
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()

                    query?.trim()?.let {
                        if (it.isNotEmpty()) submit.onQueryTextSubmit(it)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }
}
