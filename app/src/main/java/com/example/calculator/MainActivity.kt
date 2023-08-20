package com.example.calculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculator.databinding.ActivityMainBinding
import com.example.calculator.ui.theme.CalculatorTheme
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    var lastNumeric=false
    var stateError=false
    var lastDot=false

    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    fun onClearClick(view: View) {
        binding.dataTv.text=""
        lastNumeric=false
    }


    fun onBackClick(view: View) {
        binding.dataTv.text=binding.dataTv.text.toString().dropLast(1)
        try {
            val lastChar=binding.dataTv.text.toString().last()
            if(lastChar.isDigit()){
                onEqual()
            }
        }catch (e: Exception){
            binding.resultText.text=""
            binding.resultText.visibility=View.GONE
            Log.e("Last Character Error",e.toString())
        }
    }


    fun onAllClearClick(view: View) {
         binding.dataTv.text=""
        binding.resultText.text=""

         lastNumeric=false
         stateError=false
         lastDot=false
        binding.resultText.visibility=View.GONE

    }


    fun onDigitClick(view: View) {
        if(stateError){
            binding.dataTv.text=(view as Button).text
            stateError=false
        }else{
            binding.dataTv.append((view as Button).text)

        }
        lastNumeric=true
        onEqual()
    }


    fun onEqualClick(view: View) {
      onEqual()
     binding.dataTv.text=binding.resultText.text.toString().drop(1)

    }


    fun onOperatorClick(view: View) {
        if(!stateError&&lastNumeric){
            binding.dataTv.append((view as Button).text)
            lastDot=false
            lastNumeric=false
            onEqual()
        }
    }


    fun onEqual(){
        if(lastNumeric&&!stateError){
            val txt=binding.dataTv.text.toString()
            expression=ExpressionBuilder(txt).build()
            try {
                val result= expression.evaluate()
                binding.resultText.visibility=View.VISIBLE
                binding.resultText.text="="+result.toString()
            } catch (ex:ArithmeticException){
                Log.e("evalute error",ex.toString())
                binding.resultText.text="Error"
                stateError=true
                lastNumeric=false

            }
        }

    }
}
