package com.lnsergioantonio.restapp.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.lnsergioantonio.restapp.R
import com.lnsergioantonio.restapp.ui.fragment.dialog.HeadersAdapter
import com.lnsergioantonio.restapp.ui.adapter.HeadersItem
import kotlinx.android.synthetic.main.activity_add_headers.*

const val HEADERS_LIST = "HEADERS_LIST"
const val RESULT_HEADERS_LIST = 654

class AddHeadersActivity : AppCompatActivity() {
    private val adapter = HeadersAdapter()
    private var headersItems : List<HeadersItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_headers)

        initActionBar()
        initIntentExtras()
        initAdapter()
        initListeners()

    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_close)
        }
    }

    private fun initAdapter() {
        listHeaders.adapter = adapter
    }

    private fun initIntentExtras() {
        headersItems = intent.getParcelableArrayListExtra(HEADERS_LIST) ?: emptyList()
        adapter.setHeaderList(headersItems)
    }

    private fun initListeners() {
        buttonAddHeader.setOnClickListener {
            val header = HeadersItem(
                    key = inputKey.text.toString(),
                    value = inputValue.text.toString(),
            )
            adapter.addHeader(header)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.transition.push_up_in, R.transition.push_up_out)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            val result = intent
            intent.putParcelableArrayListExtra(HEADERS_LIST, adapter.getItems())
            setResult(RESULT_HEADERS_LIST, result)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}