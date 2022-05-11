package br.edu.ifsp.scl.ads.pdm.dados

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Configuracao(val numeroDados: Int, val numeroFaces:Int): Parcelable