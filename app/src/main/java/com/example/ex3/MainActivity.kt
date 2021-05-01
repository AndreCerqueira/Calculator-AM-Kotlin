package com.example.ex3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

enum class Operator
{
    SOMA, DIV, MULT, SUB
}

class MainActivity : AppCompatActivity()
{
    lateinit var textView: TextView
    var operand: Double = 0.0
    var operator: Operator? = null

    var userIsInTheMiddleOfIntroduction = false

    private var onClickNum: (view: View)->Unit = {
        val buttonPressed = it as Button

        if (userIsInTheMiddleOfIntroduction)
        {
            if (buttonPressed.text == "."){
                if (!textView.text.contains("."))
                    textView.text = textView.text.toString().plus(buttonPressed.text)
            }
            else
            {
                if(textView.text == "0")
                    textView.text = buttonPressed.text
                else
                    textView.text = textView.text.toString().plus(buttonPressed.text)
            }
        } else {
           textView.text = buttonPressed.text
            userIsInTheMiddleOfIntroduction = true
        }
    }

    private var onClickOperation: (view: View)->Unit = {
        val buttonPressed = it as Button

        operand = textView.text.toString().toDouble()
        operator = when (buttonPressed.text.toString()) {
            "+" -> Operator.SOMA
            "-" -> Operator.SUB
            "/" -> Operator.DIV
            "X" -> Operator.MULT
            else -> null
        }

        userIsInTheMiddleOfIntroduction = false
    }

    fun doOperation()
    {
        operator?.let {
            when (it)
            {
                Operator.SOMA -> operand += textView.text.toString().toDouble()
                Operator.SUB -> operand -= textView.text.toString().toDouble()
                Operator.MULT -> operand *= textView.text.toString().toDouble()
                Operator.DIV -> operand /= textView.text.toString().toDouble()
            }
        }

        textView.text = operand.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonNumbersIds = arrayListOf(R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.buttonVirgula)
        val buttonOperatorsIds = arrayListOf(R.id.buttonDiv, R.id.buttonSub, R.id.buttonMult, R.id.buttonSoma)

        // Create and add all number buttons
        for (i in 0..buttonNumbersIds.size-1)
            findViewById<Button>(buttonNumbersIds[i]).setOnClickListener(onClickNum)
        for (i in 0..buttonOperatorsIds.size-1)
            findViewById<Button>(buttonOperatorsIds[i]).setOnClickListener(onClickOperation)

        findViewById<Button>(R.id.buttonAC).setOnClickListener()
        {
            textView.text = "0"
            userIsInTheMiddleOfIntroduction = false
            operand = 0.0
            operator = null
        }

        findViewById<Button>(R.id.buttonIgual).setOnClickListener()
        {
            doOperation()
            userIsInTheMiddleOfIntroduction = false
        }
    }

}