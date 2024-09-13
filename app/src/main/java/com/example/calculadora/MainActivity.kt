package com.example.calculadora

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculadora.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var entrada: String = ""
    private var ultimoOperador: Char? = null
    private var resultado: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        configurarListeners()
    }

    private fun configurarListeners() {
        val botoesNumeros = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7,
            binding.btn8, binding.btn9
        )

        for (botao in botoesNumeros) {
            botao.setOnClickListener {
                onNumeroClicado((it as Button).text.toString())
            }
        }

        binding.btnSoma.setOnClickListener { onOperadorClicado('+') }
        binding.btnSub.setOnClickListener { onOperadorClicado('-') }
        binding.btnMult.setOnClickListener { onOperadorClicado('x') }
        binding.btnDiv.setOnClickListener { onOperadorClicado('/') }
        binding.btnPonto.setOnClickListener { onDecimalClicado() }

        binding.btnIgual.setOnClickListener { onIgualClicado() }
        binding.btnLimpar.setOnClickListener { onLimparClicado() }
        binding.btnApagar.setOnClickListener { onApagarClicado() }
    }

    private fun onNumeroClicado(numero: String) {
        entrada += numero
        atualizarTextoPrimario()
    }

    private fun onOperadorClicado(operador: Char) {
        if (entrada.isNotEmpty()) {
            ultimoOperador = operador
            resultado = entrada.toDouble()
            entrada = ""
            atualizarTextoSecundario("$resultado $operador")
        }
    }

    private fun onDecimalClicado() {
        if (!entrada.contains(".")) {
            entrada += "."
            atualizarTextoPrimario()
        }
    }

    private fun onIgualClicado() {
        if (ultimoOperador != null && entrada.isNotEmpty()) {
            val entradaAtual = entrada.toDouble()
            when (ultimoOperador) {
                '+' -> resultado += entradaAtual
                '-' -> resultado -= entradaAtual
                'x' -> resultado *= entradaAtual
                '/' -> if (entradaAtual != 0.0) resultado /= entradaAtual
            }
            atualizarTextoPrimario(resultado.toString())
            atualizarTextoSecundario("")
            entrada = resultado.toString()
            ultimoOperador = null
        }
    }

    private fun onLimparClicado() {
        entrada = ""
        resultado = 0.0
        ultimoOperador = null
        atualizarTextoPrimario("")
        atualizarTextoSecundario("")
    }

    private fun onApagarClicado() {
        if (entrada.isNotEmpty()) {
            entrada = entrada.dropLast(1)
            atualizarTextoPrimario()
        }
    }

    private fun atualizarTextoPrimario(texto: String = entrada) {
        binding.txtPrimario.text = texto
    }

    private fun atualizarTextoSecundario(texto: String) {
        binding.txtSecundario.text = texto
    }
}
