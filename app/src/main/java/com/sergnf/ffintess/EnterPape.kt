package com.sergnf.ffintess

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_enter_pape.*

class EnterPape : AppCompatActivity() {

    lateinit var colorXml: ColorDrawable
    lateinit var butButton: Button
    lateinit var butPrevios: Button
    lateinit var objectDrawable: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_pape)

        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")
        val fullNameUs = intent.getStringExtra("full_name_us")
        val drawble = resources.getDrawable(R.drawable.ic_vector1,theme)

        tv_user_id.text = "User ID :: $userId"
        tv_email_id.text = "Email ID :: $emailId"
        tv_full_name_us.text = "Name :: $fullNameUs"

        drawble.setTint(456)

        butButton = findViewById(R.id.button_next)

        butButton.setOnClickListener{
            changeTint()
        }
        you_logined.text = "Вы вошли!"
        butPrevios = findViewById(R.id.button_previos)

        butPrevios.setOnClickListener{
            butPrevios.backgroundTintList = getColorStateList(android.R.color.holo_blue_bright)
        }

        button_loggout.setOnClickListener{
            // Выход из приложения
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@EnterPape, LoginActivity::class.java))
            finish()
        }

    }



    private fun changeTint() {

        butButton.text = "FFFFFFFF"

    }



}