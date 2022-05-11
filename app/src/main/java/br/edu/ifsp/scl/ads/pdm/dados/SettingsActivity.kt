package br.edu.ifsp.scl.ads.pdm.dados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import br.edu.ifsp.scl.ads.pdm.dados.MainActivity.Companion.PARAMETRO
import br.edu.ifsp.scl.ads.pdm.dados.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.pdm.dados.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var activitySettingsBinding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitySettingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(activitySettingsBinding.root)

        activitySettingsBinding.salvarBt.setOnClickListener{
            val numeroDados: Int = (activitySettingsBinding.numeroDadosSp.selectedView as TextView).text.toString().toInt()

            val textoNumeroFaces = activitySettingsBinding.numeroFacesEt.text.toString()
            val numeroFaces: Int = if(textoNumeroFaces.isNotEmpty()) textoNumeroFaces.toInt() else 1

            val configuracao = Configuracao(numeroDados, numeroFaces)

            val  retornoIntent = Intent()
            retornoIntent.putExtra(PARAMETRO, configuracao)

            setResult(RESULT_OK,retornoIntent)
            finish()
        }

    }
}