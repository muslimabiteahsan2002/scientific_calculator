package com.example.mycalculator

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.mycalculator.databinding.ActivityMainBinding
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var input: TextView
    private lateinit var output: TextView
    private lateinit var back: AppCompatButton
    private lateinit var clear: AppCompatButton
    private lateinit var fist: AppCompatButton
    private lateinit var second: AppCompatButton
    private lateinit var sin: AppCompatButton
    private lateinit var cos: AppCompatButton
    private lateinit var tan: AppCompatButton
    private lateinit var log: AppCompatButton
    private lateinit var ln: AppCompatButton
    private lateinit var fac: AppCompatButton
    private lateinit var square: AppCompatButton
    private lateinit var squareRoot: AppCompatButton
    private lateinit var inv: AppCompatButton
    private lateinit var division: AppCompatButton
    private lateinit var b7: AppCompatButton
    private lateinit var b8: AppCompatButton
    private lateinit var b9: AppCompatButton
    private lateinit var mul: AppCompatButton
    private lateinit var b4: AppCompatButton
    private lateinit var b5: AppCompatButton
    private lateinit var b6: AppCompatButton
    private lateinit var add: AppCompatButton
    private lateinit var b1: AppCompatButton
    private lateinit var b2: AppCompatButton
    private lateinit var b3: AppCompatButton
    private lateinit var sub: AppCompatButton
    private lateinit var bPi: AppCompatButton
    private lateinit var b0: AppCompatButton
    private lateinit var dot: AppCompatButton
    private lateinit var equal: AppCompatButton
    private val pi = "3.14159265359"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFind()
        setListener()
    }

    private fun setListener() {
        b0.setOnClickListener {
            output.text ="${output.text}0"
        }
        b1.setOnClickListener {
            output.text ="${output.text}1"
        }
        b2.setOnClickListener {
            output.text ="${output.text}2"
        }
        b3.setOnClickListener {
            output.text ="${output.text}3"
        }
        b4.setOnClickListener {
            output.text ="${output.text}4"
        }
        b5.setOnClickListener {
            output.text ="${output.text}5"
        }
        b6.setOnClickListener {
            output.text ="${output.text}6"
        }
        b7.setOnClickListener {
            output.text ="${output.text}7"
        }
        b8.setOnClickListener {
            output.text ="${output.text}8"
        }
        b9.setOnClickListener {
            output.text ="${output.text}9"
        }
        dot.setOnClickListener {
            output.text ="${output.text}."
        }
        back.setOnClickListener {
            output.text =""
            input.text =""
        }
        clear.setOnClickListener {
            var value = output.text.toString()
            if (value.isEmpty()){
                Toast.makeText(this, "No value", Toast.LENGTH_SHORT).show()
            } else {
                value = value.substring(0, value.length - 1)
                output.text = value
            }
        }
        add.setOnClickListener {
            output.text = "${output.text}+"
        }
        sub.setOnClickListener {
            output.text = "${output.text}-"
        }
        mul.setOnClickListener {
            output.text = "${output.text}×"
        }
        division.setOnClickListener {
            output.text = "${output.text}÷"
        }
        fist.setOnClickListener {
            output.text = "${output.text}("
        }
        second.setOnClickListener {
            output.text = "${output.text})"
        }
        squareRoot.setOnClickListener {
            val value = output.text.toString()
            val r = sqrt(value.toDouble())
            output.text = "$r"
        }
        bPi.setOnClickListener {
            input.text = bPi.text
            output.text = "${output.text}$pi"
        }
        sin.setOnClickListener {
            output.text = "${output.text}sin"
        }
        cos.setOnClickListener {
            output.text = "${output.text}cos"
        }
        tan.setOnClickListener {
            output.text = "${output.text}tan"
        }
        inv.setOnClickListener {
            output.text = "${output.text}^(-1)"
        }
        ln.setOnClickListener {
            output.text = "${output.text}ln"
        }
        log.setOnClickListener {
            output.text = "${output.text}log"
        }
        fac.setOnClickListener {
            val value = output.text.toString().toInt()
            val factorial = factorial(value)
            output.text = "$factorial"
            input.text = "$value!"
        }
        square.setOnClickListener {
            val d = output.text.toString().toDouble()
            val squareB = d*d
            output.text = "$squareB"
            input.text = "$d²"
        }
        equal.setOnClickListener {
            val value = output.text.toString()
            val replace = value.replace("÷", "/").replace("×", "*")
            val result = eval(replace)
            output.text = result.toString()
            input.text = value
        }
    }

    private fun setFind() {
        input = binding.input
        output = binding.output
        //number
        b0 = binding.b0
        b1 = binding.b1
        b2 = binding.b2
        b3 = binding.b3
        b4 = binding.b4
        b5 = binding.b5
        b6 = binding.b6
        b7 = binding.b7
        b8 = binding.b8
        b9 = binding.b9
        dot = binding.bDot
        //symbol
        sin = binding.sin
        cos = binding.cos
        tan = binding.tan
        back = binding.back
        clear = binding.clear
        fist = binding.first
        second = binding.second
        log = binding.log
        ln = binding.ln
        square = binding.square
        squareRoot = binding.squareRoot
        fac = binding.fac
        bPi = binding.pi
        inv = binding.inv
        division = binding.division
        add = binding.add
        mul = binding.mul
        sub = binding.minus
        equal = binding.equal
    }

    private fun factorial(n:Int): Int {
        return if (n == 1 || n==0) 1 else n*factorial(n-1)
    }

    //eval function
    private fun eval(str: String): Double {
        return object : Any() {
            var pos = -1
            var ch = 0
            fun nextChar() {
                ch = if (++pos < str.length) str[pos].code else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.code) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < str.length) throw RuntimeException("Unexpected: " + ch.toChar())
                return x
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor
            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    if (eat('+'.code)) x += parseTerm() // addition
                    else if (eat('-'.code)) x -= parseTerm() // subtraction
                    else return x
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    if (eat('*'.code)) x *= parseFactor() // multiplication
                    else if (eat('/'.code)) x /= parseFactor() // division
                    else return x
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.code)) return parseFactor() // unary plus
                if (eat('-'.code)) return -parseFactor() // unary minus
                var x: Double
                val startPos = pos
                if (eat('('.code)) { // parentheses
                    x = parseExpression()
                    eat(')'.code)
                } else if (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) { // numbers
                    while (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) nextChar()
                    x = str.substring(startPos, pos).toDouble()
                } else if (ch >= 'a'.code && ch <= 'z'.code) { // functions
                    while (ch >= 'a'.code && ch <= 'z'.code) nextChar()
                    val func = str.substring(startPos, pos)
                    x = parseFactor()
                    x =
                        when (func) {
                            "sqrt" -> sqrt(x)
                            "sin" -> sin(
                                Math.toRadians(
                                    x
                                )
                            )
                            "cos" -> cos(
                                Math.toRadians(x)
                            )
                            "tan" -> tan(Math.toRadians(x))
                            "log" -> log10(
                                x
                            )
                            "ln" -> ln(x)
                            else -> throw RuntimeException(
                                "Unknown function: $func"
                            )
                        }
                } else {
                    throw RuntimeException("Unexpected: " + ch.toChar())
                }
                if (eat('^'.code)) x = x.pow(parseFactor()) // exponentiation
                return x
            }
        }.parse()
    }


}