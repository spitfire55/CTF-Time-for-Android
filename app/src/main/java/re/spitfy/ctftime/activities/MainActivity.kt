package re.spitfy.ctftime.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.crashlytics.android.Crashlytics
import com.google.firebase.firestore.CollectionReference
import dagger.android.AndroidInjection
import io.fabric.sdk.android.Fabric
import re.spitfy.ctftime.R
import javax.inject.Inject
import javax.inject.Named

class MainActivity : BaseActivity() {

    @Inject
    @Named("Teams")
    private lateinit var teamsDb: CollectionReference


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_main)
    }
}