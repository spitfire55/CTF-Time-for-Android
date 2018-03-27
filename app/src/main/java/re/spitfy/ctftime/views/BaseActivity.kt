package re.spitfy.ctftime.views

import android.arch.lifecycle.AndroidViewModel
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var viewModel: AndroidViewModel

    abstract fun setupToolbar()

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }


}