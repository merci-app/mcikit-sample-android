# Configurações Merci-Kit Android

## Pré-Requisitos
````groovy
    - Android Version: 4.3 ou superior
    - Kotlin: 1.3.31 ou superior
    - Androidx
````

## Dependências:

Para configurar Merci-Kit em seu projeto primeiro adicione no root do seu `build.gradle` o 
repositório que irá permitir o download do mci-kit:
```groovy
maven { url 'https://jitpack.io' } //Permite o funcionamento de algumas dependências do mci-kit

maven { url '<nossa-url-repo-privada>' }
```

Em seguida adicione adicione a dependência mci-kit no gradle do seu aplicativo:
```groovy
dependencies {
    implementation 'kit.merci:mci-kit:1.0.0-alpha16'
}
```

## Inicialização
A framework deverá ser iniciada no método `onCreate` do seu `Application` :

```kotlin
Merci.instantiate(
                application = this,
                clientId = "<seu-client-id>",
                clientSecret = "<seu-client-secret>",
                environment = Environment.SANDBOX ou Environment.PRODUCTION,
                clientProvider = SuaClasseClientProvider() 
                )
```

O parâmetro clientProvider pode ser uma classe sua que herda de `ClienteProvider`, útil para que
sua app seja notificada sempre que o `mci-kit` necessite solicitar algo, por exemplo:
````kotlin
 class SuaClasseClientProvider : ClientProvider() {
    
     /**
     * Sempre que for identificado que o usuáio não esta autenticado iremos
     * notificar o seu app a partir dessa função.
     **/
     override fun authenticate() {
     }
 
     /**
     * Sempre que o usuário clicar nas ações de Solicitar Suporte dentro do mci-kit iremos  * notificar seu app a partir dessa função
     **/
     override fun supportRequested(context: Context) {
     }
 } 
````

## Autenticação

Para utilizar os recursos da framework é necessário autenticar o usuário como exibido a seguir:
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

## Iniciar uma venda

Para iniciar uma venda direta, é necessário chamar o método abaixo, informando o identifcador do estabelecimento como mostra a seguir:
````kotlin
try {
    Merci.launch(this, Merchant("<merchant-id>"))
} catch (e: MerchantNotFound) {
    // Será enviado uma Exception caso o Merchant não seja encontrato em nossa plataforma
}
````

---

[Merci @ 2019](https://merci.com.br)
