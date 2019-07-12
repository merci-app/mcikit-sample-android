# Configuração Merci kit

## Pre-Requisito
````groovy
    - Android Version: 4.3 ou superior
    - Kotlin: 1.3.31 ou superior
    - Androidx
````

## Configuração
Para configurar Merci Kit em seu projeto primeiro adicione no root do seu `build.gradle` o 
repositório que irá permitir o download do mci-kit:

```groovy
maven { url 'https://jitpack.io' } //Permite o funcionamento de algumas dependências do mci-kit

maven { url '<nossa-url-repo-priava>' }
```

Em seguida adicione adicione a dependência mci-kit no gradle do seu aplicativo:

```groovy
dependencies {
    implementation 'kit.merci:mci-kit:1.0.0'
}
```

## Inicialização
Após adicionar as dependências é preciso configurar a inicialização do `mci-kit` no `onCreate`
do seu `Application` :

```kotlin
Merci.instantiate(
                application = this, //Referencia do seu Application
                clientId = "<seu-client-id>", //Seu ClientId 
                clientSecret = "<seu-client-secret>", //Seu ClientSecret
                environment = Environment.SANDBOX ou Environment.PRODUCTION, // Configuração de ambiente
                clientProvider = SuaClasseClientProvider() 
                )
```

O parâmetro clientProvider pode ser uma classe sua que herda de `ClienteProvider`, útil para que
sua app seja notificada sempre que o `mci-kit` necessite solicitar algo, por exemplo:
 ```kotlin
 class SuaClasseClientProvider : ClientProvider() {
    
     /**
     * Sempre que for identificado que o usuáio não esta autenticado iremos
     * notificar o seu app a partir dessa função. Mais a frente explicaremos como autenticar
     * no mci-kit.
     **/
     override fun authenticate() {
     }
 
     /**
     * Sempre o usuário clicar nas ações de Solicitar Support dentro do mci-kit iremos notificar
     * sseu app a partir dessa função
     **/
     override fun supportRequested(context: Context) {
     }
 } 
 ```

## Autenticação
Para acessar as funcionalidades disponíveis no `mci-kit` é necessário autenticar o seu usuário
dentro da nossa plataforma da seguinte forma:

````kotlin
Merci.authenticate(vatNumber = "<cpf-do-usuario>", object: MCICallback {
                override fun onSuccess() {
                }

                override fun onError(e: Throwable) {
                }
            })
````

Para realizar o logout:

````kotlin
Merci.revokeAuthentication()
````
Para checar se o usuário esta autenticado na nossa plataforma:

````kotlin
Merci.isAuthenticated()
````

## Iniciar Merchant
Para iniciar um Merchant:

````kotlin
try {
    Merci.launch(this, Merchant("<id-do-merchant>"))
} catch (e: MerchantNotFound) {
    // Será enviado uma Exception caso o Merchant não seja encontrato em nossa plataforma
}
````