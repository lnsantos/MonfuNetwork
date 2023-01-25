package com.lnsantos.monfunetwork

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.lnsantos.library.monfunetwork.MonfuNetworkCallAdapterFactory
import com.lnsantos.library.monfunetwork.model.*
import com.lnsantos.library.monfunetwork.model.result.MonfuFailed
import com.lnsantos.library.monfunetwork.model.result.MonfuResult
import com.lnsantos.library.monfunetwork.model.result.MonfuSuccess
import com.lnsantos.library.monfunetwork.model.result.MonfuUnknown
import com.lnsantos.monfunetwork.ui.theme.MonfuNetworkTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : ComponentActivity() {

    data class UserDTO(
        @SerializedName("login") val login: String
    )

    data class ErrorDTO(
      val message: String
    )

    interface CoinmapService {

        @GET("/users/pedro")
        suspend fun getVenues(): MonfuFlow<UserDTO>
    }

    private val retrofitInstance = Retrofit
        .Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(MonfuNetworkCallAdapterFactory.create())
        .build()

    private val api: CoinmapService = retrofitInstance.create(CoinmapService::class.java)

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonfuNetworkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val reference = remember { mutableStateOf("Start") }
                    MainScope().launch(Dispatchers.IO) {
                        delay(1000)
                        api.getVenues().collect {
                            reference.value = it.login
                        }
                    }

//                    MainScope().launch(Dispatchers.Default) {
//                        when (val resultApi = api.getVenues()) {
//
//                            is MonfuSuccess -> {
//                                reference.value = resultApi.
//                            }
//
//                            is MonfuFailed -> {
//                                val error : ErrorDTO = Gson().fromJson(resultApi.errorBody, ErrorDTO::class.java)
//                                reference.value = "Falha com comunicação ${error.message}"
//                            }
//
//                            is MonfuUnknown -> {
//                                reference.value = resultApi.exception.toString()
//                            }
//                        }
//                    }
                    Greeting(reference.value)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MonfuNetworkTheme {
        Greeting("Android")
    }
}