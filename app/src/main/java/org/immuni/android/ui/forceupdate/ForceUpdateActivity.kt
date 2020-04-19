package org.immuni.android.ui.forceupdate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.immuni.android.R
import com.bendingspoons.base.extensions.setDarkStatusBar

class ForceUpdateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.force_update_activity)
        setDarkStatusBar(resources.getColor(R.color.colorPrimary))
    }
}