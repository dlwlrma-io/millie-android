package io.dlwlrma.millie.ui.binding

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.dlwlrma.millie.R
import io.dlwlrma.millie.domain.model.News
import io.dlwlrma.millie.ui.main.adapter.MainAdapter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


@BindingAdapter("android:items")
fun bindItems(view: RecyclerView, items: List<News>?) {
    if (items.isNullOrEmpty()) {
        return
    }

    val configuration = view.context.resources.configuration
    val screenWidthDp = configuration.screenWidthDp
    val spanCount = if (screenWidthDp >= 600) 3 else 1

    val adapter = view.adapter as MainAdapter
    view.layoutManager = GridLayoutManager(view.context, spanCount)

    adapter.calDiff(items)
}

@BindingAdapter("android:image")
fun bindImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .fallback(R.drawable.ic_no_image_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL) // 온라인 상태 일때도 저장된 데이터가 있는 경우 사용
        .into(view)
}

@BindingAdapter("android:date")
fun bindDate(view: TextView, isoDate: String) {
    try {
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        isoFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = isoFormat.parse(isoDate)
        date?.let {
            val format = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm", Locale.KOREAN)
            format.timeZone = TimeZone.getTimeZone("Asia/Seoul")

            view.text = format.format(date)
        }
    } catch (e: ParseException) {
        view.text = ""
    }
}

@SuppressLint("SetJavaScriptEnabled")
@BindingAdapter("android:url")
fun bindDate(view: WebView, url: String) {
    view.settings.apply {
        javaScriptEnabled = true
        javaScriptCanOpenWindowsAutomatically = true
        setSupportMultipleWindows(true)
    }

    view.webViewClient = WebViewClient()
    view.webChromeClient = WebChromeClient()
    view.loadUrl(url)
}
