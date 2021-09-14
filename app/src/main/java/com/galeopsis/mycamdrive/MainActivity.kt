package com.galeopsis.mycamdrive

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.galeopsis.mycamdrive.view.AuthorizationFragment
import com.galeopsis.mycamdrive.view.CameraDetailsFragment
import com.galeopsis.mycamdrive.view.Communicator

class MainActivity : AppCompatActivity(), Communicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AuthorizationFragment.newInstance())
                .commitNow()
        }
    }

    override fun passData(position: Int) {
        val bundle = Bundle()
        bundle.putInt("POS", position)

        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentB = CameraDetailsFragment()
        fragmentB.arguments = bundle
        transaction.replace(R.id.container, fragmentB)
        transaction.commit()
    }
}