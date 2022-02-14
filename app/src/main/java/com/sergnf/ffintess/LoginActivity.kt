package com.sergnf.ffintess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.Email
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_login.login_Btn
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        no_account_Text.setOnClickListener{
            val intent =  Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        login_Btn.setOnClickListener{
            when {
                TextUtils.isEmpty(Email.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Пожалуйста введите Email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(password.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Пожалуйста введите Пароль",
                        Toast.LENGTH_SHORT
                    ).show()
                }

//                TextUtils.isEmpty(fullName.text.toString().trim{ it <= ' '}) -> {
//                    Toast.makeText(
//                        this@MainActivity,
//                        "Как к Вам обращаться? Имя.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }


                else -> {
                    val email: String = Email.text.toString().trim() { it <= ' '}
                    val password: String = password.text.toString().trim() { it <= ' '}
//                    val fullNameUs: String = fullName.text.toString().trim() { it <= ' '}
//                    textCreateAcc.text = fullNameUs


                    // Create an instanse and create a register a user with email and password.
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {task ->
                            //If the registration is successfully done
                            if (task.isSuccessful) {
                                // Firebase registered user
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this@LoginActivity,
                                    "Вы успешно зарегистрированы !",
                                    Toast.LENGTH_SHORT
                                ).show()
                                /** здесь новый пользователь зарегистрирован и автоматически залогинен,
                                 * мы должны перебросить его на Главный экран
                                 */
                                val intent =
                                    Intent(this@LoginActivity, EnterPape::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                val mess: String = FirebaseAuth.getInstance().currentUser!!.uid
                                Log.i("My_Firebas", mess.toString())
                                Log.i("My_HHH_Firebas", mess.toString())
                                intent.putExtra("user_id",
                                    mess
                                )
                                intent.putExtra("email_id", email)
//                                intent.putExtra("full_name_us", fullNameUs)
                                startActivity(intent)
                                finish()
                            } else{
                                // Если регистрация не прошла, покажем ошибку
                                Toast.makeText(
                                    this@LoginActivity,
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
