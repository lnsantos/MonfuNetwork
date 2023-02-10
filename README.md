
Monfu Network
==================  
**Um simples projeto open-source para ajudar desenvolvedores com callAdapter personalizado.**

### Última versão
Essa é uma versão experimental, novas atualizações viram em breve.

`io.github.lnsantos:monfu-network:0.0.3-alpha  `

### Como utilizar
Adicione **MonfuNetworkCallAdapterFactory** na sua configuração do retrofit.
```kotlin  
 private val retrofitInstance = Retrofit.Builder()
           .addCallAdapterFactory(MonfuNetworkCallAdapterFactory.create())
           .build()  
```
## Recursos

**MonfuResult** da suporte a coroutines, então a sua UI thread não vai travar quando precisa fazer uma requisição.
```kotlin
@GET("/example")  
suspend fun getExample(): MonfuResult<ExampleResponse>
```
Caso não seja utilizado **suspend fun**, vai ser necessário aplicar um tratamento com thread para não travar a UI.

Depois que a requisição é efetuada seja com sucesso ou erro podemos está manipulando o resultado via class.

**Exemplo:**
```kotlin
when (val resultApi = api.getExample()) {  
    is MonfuSuccess -> {  
        // Seu feedback de sucesso
        println(resultApi.data) 
    }  
  
    is MonfuFailed -> {  
        // Qualquer status code 
        if(resultApi.code == 427) {
           val error : MeuBackenError = Gson().fromJson(
               resultApi.errorBody, 
               MeuBackenError::class.java
            )  
        reference.value = error.mensagemPersonalizada  
        }
  }  
  
    is MonfuUnknown -> {  
        // Qualquer erro inesperado, onde não conseguimos descobrir oque ocorrer exemplo falhas com a internet.
        println(resultApi.exception.message) 
    }  
}
``` 

**MonfuFlow** é um typealias de Flow, então caso você queira utilizar o flow normal você vai conseguir normalmente, diferente do MonfuResult ele encapsula todos os problemas por exception.
```kotlin
@GET("/example")  
suspend fun getExample(): MonfuFlow<ExampleResponse>

// ou

@GET("/example")  
suspend fun getExample(): Flow<ExampleResponse>
```

:heavy_exclamation_mark: Essa biblioteca ainda está em desenvolvimento, a versão alpha foi liberada para que o público externo consiga efetuar os testes e retornar com feedback. 
