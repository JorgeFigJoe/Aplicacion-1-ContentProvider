package com.example.aplicacion1

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        return true
    }

    fun onClickAddDetails(view: View?) {


        val values = ContentValues()


        values.put(ContentProviderAplicacion1.name, (findViewById<View>(R.id.textName) as EditText).text.toString())


        contentResolver.insert(ContentProviderAplicacion1.CONTENT_URI, values)


        Toast.makeText(baseContext, "Nuevo dato guardado en SQLITE", Toast.LENGTH_LONG).show()
    }

    fun onClickShowDetails(view: View?) {

        val resultView = findViewById<View>(R.id.res) as TextView


        val cursor = contentResolver.query(Uri.parse("content://com.demo1.user.provider/users"), null, null, null, null)


        if (cursor!!.moveToFirst()) {
            val strBuild = StringBuilder()
            while (!cursor.isAfterLast) {
                strBuild.append("""
      
    ${cursor.getString(cursor.getColumnIndex("id"))}-${cursor.getString(cursor.getColumnIndex("name"))}
    """.trimIndent())
                cursor.moveToNext()
            }
            resultView.text = strBuild
        } else {
            resultView.text = "Sin datos"
        }
    }
}