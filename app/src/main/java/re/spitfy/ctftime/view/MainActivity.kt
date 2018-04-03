package re.spitfy.ctftime.view

import android.os.Bundle
import re.spitfy.ctftime.R
import re.spitfy.ctftime.viewmodel.MainActivityViewModel

class MainActivity : BaseActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityViewModel = getViewModel(MainActivityViewModel::class.java)
    }

    override fun setupToolbar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}