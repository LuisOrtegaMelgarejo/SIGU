package com.luisortega.myapplication

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.luisortega.myapplication.models.User
import com.luisortega.myapplication.apiconfig.APIUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.preference.PreferenceManager

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val myPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val name = myPreferences.getString("codadmin", "")
        if (name!=""){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnLogin = findViewById<Button>(R.id.login)
        val user = findViewById<Button>(R.id.user) as EditText
        val password= findViewById<Button>(R.id.password) as EditText

        btnLogin.setOnClickListener {
            val user = user.text.toString().trim()
            val password = password.text.toString().trim()
            if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password))
              logIn(user,password,this)
            else
                Toast.makeText(this,"Ingrese Usuario y Contraseña",Toast.LENGTH_SHORT).show()
        }
    }

    private fun logIn(user : String ,password : String,scope: LoginActivity) {
        val obj = object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful) {
                    if(response.body().error){
                        Toast.makeText(scope,"Usuario y/o clave incorrectos",Toast.LENGTH_SHORT).show()
                    }else{
                        val myPreferences = PreferenceManager.getDefaultSharedPreferences(scope)
                        val myEditor = myPreferences.edit()
                        myEditor.putString("codadmin", response.body().codadmin)
                        myEditor.putString("clave", response.body().clave)
                        myEditor.putString("prefijo", response.body().prefijo)
                        myEditor.putString("nombre", response.body().nombre)
                        myEditor.putString("apellidos", response.body().apellidos)
                        myEditor.commit()
                        val intent = Intent(scope,MainActivity::class.java)
                        startActivity(intent)
                        scope.finish()
                    }
                }else{
                    Toast.makeText(scope,"Error recibir datos",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call : Call<User> , t: Throwable) {
                Toast.makeText(scope,"Error al conectarse a la base de datos",Toast.LENGTH_SHORT).show()
            }
        }
        APIUtils.getApiService().loginPost(user,password).enqueue(obj)
    }
}

