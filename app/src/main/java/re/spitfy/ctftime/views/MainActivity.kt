package re.spitfy.ctftime.views

import android.os.Bundle
import com.crashlytics.android.Crashlytics
import com.google.firebase.firestore.CollectionReference
import dagger.android.AndroidInjection
import io.fabric.sdk.android.Fabric
import re.spitfy.ctftime.R
import re.spitfy.ctftime.viewmodel.MainActivityViewModel
import re.spitfy.ctftime.views.BaseActivity
import javax.inject.Inject
import javax.inject.Named

class MainActivity : BaseActivity() {

    @Inject lateinit var mainActivityViewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun setupToolbar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}