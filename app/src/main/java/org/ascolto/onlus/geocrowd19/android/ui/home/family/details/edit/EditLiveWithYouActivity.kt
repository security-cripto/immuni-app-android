package org.ascolto.onlus.geocrowd19.android.ui.home.family.details.edit

import android.os.Bundle
import androidx.lifecycle.Observer
import com.bendingspoons.base.extensions.setLightStatusBarFullscreen
import kotlinx.android.synthetic.main.user_edit_livewithyou_activity.*
import kotlinx.android.synthetic.main.user_edit_livewithyou_activity.back
import org.ascolto.onlus.geocrowd19.android.AscoltoActivity
import org.ascolto.onlus.geocrowd19.android.R
import org.ascolto.onlus.geocrowd19.android.loading
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class EditLiveWithYouActivity : AscoltoActivity() {
    private lateinit var viewModel: EditDetailsViewModel
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_edit_livewithyou_activity)
        setLightStatusBarFullscreen(resources.getColor(android.R.color.transparent))
        userId = intent?.extras?.getString("userId")!!
        viewModel = getViewModel { parametersOf(userId)}

        viewModel.navigateBack.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                finish()
            }
        })

        viewModel.user.observe(this, Observer {
            when(it.isInSameHouse) {
                true -> {
                    yes.isChecked = true
                    no.isChecked = false
                }
                false -> {
                    no.isChecked = true
                    yes.isChecked = false
                }
            }

            pageTitle.text = String.format(applicationContext.getString(R.string.user_edit_live_with_you_title),
                it.nickname!!.humanReadable(applicationContext, it.gender))
        })

        viewModel.loading.observe(this, Observer {
            loading(it)
        })

        back.setOnClickListener { finish() }

        update.setOnClickListener {
            val sameHouse = when {
                yes.isChecked -> true
                else -> false
            }

            val user = viewModel.user()
            user?.let {
                viewModel.updateUser(user.copy(isInSameHouse = sameHouse))
            }
        }
    }
}
