package com.example.moody_blues.feed
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

interface FeedContract {
    interface View : BaseView<Presenter> {}

    interface Presenter : BasePresenter {}
}
