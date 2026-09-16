package com.shiponstore.customermanagement.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shiponstore.customermanagement.databinding.ActivityMainBinding
import com.shiponstore.customermanagement.ui.test.OfflineTestActivity

/**
 * V1 scope note: this screen intentionally does NOT reproduce the existing
 * dashboard / customer / due / supplier UI yet (PART 5 says not to touch or
 * redesign those features in this step). It only proves the Android app
 * boots and the local Room database is reachable, via the Offline Test
 * screen. Porting the real screens is planned for V2 onward.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOpenOfflineTest.setOnClickListener {
            startActivity(Intent(this, OfflineTestActivity::class.java))
        }
    }
}
