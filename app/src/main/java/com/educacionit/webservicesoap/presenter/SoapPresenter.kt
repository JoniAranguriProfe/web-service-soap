package com.educacionit.webservicesoap.presenter

import com.educacionit.webservicesoap.contract.SoapContract
import com.educacionit.webservicesoap.model.SoapModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SoapPresenter(private var view: SoapContract.View?) : SoapContract.Presenter {
    private var soapModel: SoapContract.Model = SoapModel()
    override fun getListOfLanguages() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = soapModel.getListOfLanguages()
            withContext(Dispatchers.Main) {
                    view?.showResult(result)
            }
        }
    }

    override fun detachView() {
        view = null
    }
}

