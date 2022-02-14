package com.sergnf.ffintess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.android.synthetic.main.activity_main.*


import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sergnf.ffintess.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.Email
import kotlinx.android.synthetic.main.activity_main.password
import kotlinx.android.synthetic.main.activity_main.registerBtn
import androidx.core.view.ViewCompat as ViewCompat


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var database : DatabaseReference

    lateinit var colorXml: ColorDrawable
    lateinit var butButton: Button
    lateinit var butPrevios: Button
    lateinit var objectDrawable: Drawable


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        to_login.setOnClickListener{
//            val intent =  Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
            onBackPressed()
        }

        binding.registerBtn.setOnClickListener{
            when {
                TextUtils.isEmpty(Email.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(
                        this@MainActivity,
                        "Пожалуйста введите Email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(password.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(
                        this@MainActivity,
                        "Пожалуйста введите Пароль",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(fullName.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(
                        this@MainActivity,
                        "Как к Вам обращаться? Имя.",
                        Toast.LENGTH_SHORT
                    ).show()
                }


                else -> {
                    val emailFB:String = binding.Email.text.toString().trim() { it <= ' '}
                    val passwordFB:String = binding.password.text.toString().trim() { it <= ' '}
                    val fullName= binding.fullName.text.toString().trim() { it <= ' '}
                    textCreateAcc.text = fullName


                    // Create an instanse and create a register a user with email and password.
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailFB, passwordFB).addOnCompleteListener  {task ->
                        //If the registration is successfully done
                        if (task.isSuccessful) {
                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            Toast.makeText(
                                this@MainActivity,
                                "Вы успешно зарегистрированы !",
                                Toast.LENGTH_SHORT
                            ).show()
                            /**
                             * Здесь пользователь авторизован и добавлен в Аутентификацию
                             * добавим данные в Realtime Database
                             */
//                            val fullName = binding.fullName.text.toString()
//                            val lastName = binding.lastName.text.toString()
//                            val passwordF = binding.password.text.toString()
                            val email = binding.Email.text.toString()

                            database = FirebaseDatabase.getInstance().getReference("UserData")
                            val UserDat = UsersData(fullName, email, passwordFB, email, email)
                            database.child(fullName).setValue(UserDat).addOnSuccessListener {

                                binding.fullName.text.clear()
//                                binding.lastName.text.clear()
                                binding.password.text.clear()
                                binding.Email.text.clear()

                                Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()

                            }.addOnFailureListener {

                                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                            }

                            /** здесь новый пользователь зарегистрирован и автоматически залогинен,
                             * мы должны перебросить его на Главный экран
                             */
                            val intent =
                                Intent(this@MainActivity, EnterPape::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("user_id", firebaseUser.uid)
                            intent.putExtra("email_id", email)
                            intent.putExtra("full_name_us", fullName)
                            startActivity(intent)
                            finish()
                        } else{
                            // Если регистрация не прошла, покажем ошибку
                            Toast.makeText(
                                this@MainActivity,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            }
        }



    }
}