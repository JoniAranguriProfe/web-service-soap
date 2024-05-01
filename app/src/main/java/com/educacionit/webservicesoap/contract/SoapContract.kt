package com.educacionit.webservicesoap.contract

import com.educacionit.webservicesoap.model.Language

interface SoapContract {
    interface View {
        fun showResult(result: List<Language>)
        fun showError(message: String)
    }

    interface Presenter {
        fun getListOfLanguages()
        fun detachView()
    }

    interface Model{
        suspend fun getListOfLanguages(): List<Language>
    }
}
