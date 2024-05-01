package com.educacionit.webservicesoap.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.educacionit.webservicesoap.R
import com.educacionit.webservicesoap.contract.SoapContract
import com.educacionit.webservicesoap.model.Language
import com.educacionit.webservicesoap.presenter.SoapPresenter

class MainActivity : AppCompatActivity(), SoapContract.View {
    private lateinit var languagesListView: ListView
    private lateinit var presenter: SoapContract.Presenter
    private lateinit var languageAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        languagesListView = findViewById(R.id.languagesListView)
        presenter = SoapPresenter(this)

        presenter.getListOfLanguages()
    }

    override fun showResult(result: List<Language>) {
        languageAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            result.map { "${it.name}, ${it.code}" })
        languagesListView.adapter = languageAdapter
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
