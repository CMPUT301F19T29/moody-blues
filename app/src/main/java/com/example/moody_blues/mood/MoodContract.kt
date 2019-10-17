package com.example.moody_blues.mood
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

interface MoodContract {
    interface View : BaseView<Presenter> {}

    interface Presenter : BasePresenter {}
}
