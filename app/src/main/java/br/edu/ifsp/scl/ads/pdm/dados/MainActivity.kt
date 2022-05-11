 package br.edu.ifsp.scl.ads.pdm.dados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.scl.ads.pdm.dados.databinding.ActivityMainBinding
import kotlin.random.Random
import kotlin.random.nextInt

 class MainActivity : AppCompatActivity() {

     companion object{
         val PARAMETRO = "PARAMETRO"
     }


    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var geradorRandomico: Random
    private lateinit var configuracaoSetada: Configuracao

    // Cria objeto activity result launcher
    private lateinit var settingsActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        geradorRandomico = Random(System.currentTimeMillis())

        configuracaoSetada = Configuracao()

        activityMainBinding.jogarDadoBt.setOnClickListener{

            var resultadoPrimeiroDado = 0
            var resultadoSegundoDado = 0

            if(configuracaoSetada.numeroFaces <= 6){
                resultadoPrimeiroDado = jogarDado(activityMainBinding.primeiroResultadoIv)

                if(configuracaoSetada.numeroDados == 2) {
                    resultadoSegundoDado = jogarDado(activityMainBinding.segundoResultadoIv)
                }else{
                    activityMainBinding.segundoResultadoIv.visibility = View.GONE
                }

            }else{
                activityMainBinding.primeiroResultadoIv.visibility = View.GONE
                activityMainBinding.segundoResultadoIv.visibility = View.GONE
            }

            val textoComplemento =  if (resultadoSegundoDado > 0) "${resultadoPrimeiroDado} e ${resultadoSegundoDado}" else "${resultadoPrimeiroDado}"

            "A(s) face(s) sorteada(s) foi(ram) ${textoComplemento}".also{activityMainBinding.resultadoTv.text =it}

        }

        settingsActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
            if(result.resultCode == RESULT_OK){
                // Modificações na view quando chegar alterações (configurações)
                if(result.data !=null) {

                    val configuracao: Configuracao? =
                        result.data?.getParcelableExtra<Configuracao>(PARAMETRO)

                    if (configuracao != null) {
                        configuracaoSetada = configuracao
                    }

                }
            }
        }

    }

     private fun gerarNumeroAleatorio(): Int {
         val resultado: Int = geradorRandomico.nextInt(1..6)
         return resultado
     }

     private fun jogarDado(imageView: ImageView): Int {
         val resultadoNumeroGerado = gerarNumeroAleatorio()

         val nomeImagem = "dice_${resultadoNumeroGerado}"

         imageView.setImageResource(
             resources.getIdentifier(nomeImagem, "mipmap", packageName)
         )

         imageView.visibility = View.VISIBLE

         return resultadoNumeroGerado
     }

     override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
         return true
     }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         if(item.itemId == R.id.settingsMi){
             val settingsIntent = Intent(this, SettingsActivity::class.java)
             settingsActivityLauncher.launch(settingsIntent)
             return true
         }
         return false
     }
}