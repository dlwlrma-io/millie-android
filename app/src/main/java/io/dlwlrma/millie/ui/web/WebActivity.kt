package io.dlwlrma.millie.ui.web

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.dlwlrma.millie.BR
import io.dlwlrma.millie.R
import io.dlwlrma.millie.databinding.ActivityWebBinding
import io.dlwlrma.millie.ui.base.BaseActivity

@AndroidEntryPoint
class WebActivity : BaseActivity<ActivityWebBinding, WebViewModel>() {
    override val layoutRes: Int = R.layout.activity_web
    override val viewModel: WebViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback {
            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.run {
            setVariable(BR.activity, this@WebActivity)
            setVariable(BR.title, intent.getStringExtra("title"))
            setVariable(BR.url, intent.getStringExtra("url"))
            executePendingBindings()
        }
    }

    fun onBackButtonClick(view: View) {
        setResult(Activity.RESULT_OK)
        finish()
    }
}
