package io.dlwlrma.millie.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.dlwlrma.millie.BR
import io.dlwlrma.millie.R
import io.dlwlrma.millie.databinding.ActivityMainBinding
import io.dlwlrma.millie.domain.model.News
import io.dlwlrma.millie.ui.base.BaseActivity
import io.dlwlrma.millie.ui.main.MainViewModel.UiState
import io.dlwlrma.millie.ui.main.adapter.MainAdapter
import io.dlwlrma.millie.ui.web.WebActivity
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutRes: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() {
        binding.recyclerView.adapter = MainAdapter().apply {
            onClickListener = {
                startWebActivity(it)
            }
        }
    }

    private fun initViewModel() {
        viewModel.uiState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .distinctUntilChanged()
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        binding.setVariable(BR.news, uiState.news)
                        binding.executePendingBindings()
                    }

                    is UiState.Error -> {
                        showToast("뉴스를 가져올 수 없습니다.")
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun startWebActivity(news: News) {
        val intent = Intent(this, WebActivity::class.java)
        intent.putExtra("title", news.title)
        intent.putExtra("url", news.url)
        startActivity(intent)
    }
}