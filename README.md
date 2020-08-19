# Configurações Merci-Kit Android

## Pré-Requisitos
````groovy
    - Android Version: 4.3 ou superior
    - Kotlin: 1.3.31 ou superior
    - Androidx
````

## Whitelist
Para a utilização do SDK é necessário o envio de algumas informações do aplicativo cliente para que o 
mesmo entre na nossa lista de Whitelist. Para isso é necessário enviar as seguintes informações:

- AppId: O mesmo usado para a PlayStore do. Ex.: `com.android.app.cliente`
- SHA-1 Keystore: Esse hash deve ser extraído do arquivo de certificado que assina o APK de Release:

````bash
keytool -list -v -keystore <KEYSTORE_PATH> -alias <KEYSTORE_ALIAS> -storepass <KEYSTORE_PASS> -keypass <KEYSTORE_PASS>
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
    implementation 'kit.merci:mci-kit:1.4.0'
    implementation 'kit.merci:mci-market-place:1.4.0'
    implementation 'kit.merci:mci-pay:1.4.0'
    implementation 'kit.merci:mci-withdraw:1.4.0'
}
```

A partir da versão 1.4.0 foi introduzida a modularização do SDK, por conta disso é possível adicionar
módulo por necessidade de uso. Por exemplo, caso precise integrar apenas o módulo de Pagar, basta fazer
o seguinde: 

```groovy
dependencies {
    implementation 'kit.merci:mci-kit:1.4.0'
    implementation 'kit.merci:mci-pay:1.4.0'
}
```

Observação: É necessário sempre ter a dependência do mci-kit para que funcione corretamente a base
do SDK

## Inicialização
A framework deverá ser iniciada no método `onCreate` do seu `Application` :

```kotlin
Merci.instantiate(
                application = this,
                clientId = "<seu-client-id>",
                clientSecret = "<seu-client-secret>",
                environment = Environment.SANDBOX ou Environment.PRODUCTION,
                clientSecurityProvider = SuaClasseClientSecurityProvider(),
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
     
     /**
      Eventos de Analytics trackeados dentro do SDK serão notificados nessa função de callback
     **/
     override fun onEvent(name: String, params: Map<String, Any>) {
     }
 } 
````

O parâmetro clientSecurityProvider pode ser uma classe sua que herda de `ClientSecurityProvider`, 
utilizada para que o aplicativo cliente envie o Token de Sessão para validações de segurança:

````kotlin
 class SuaClasseClientProvider : ClientSecurityProvider {
                             
     /**
        Nessa função deverá ser retornado o token de sessão
     **/     
     override fun getSessionToken(): String {
       return "<TOKEN_SESSION>"
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

O proceso de autenticação deve ocorrer uma única vez.
Sugerimos efetuar a autenticação da SDK Merci logo após efetuarem o processo de login do seu aplicativo.
Sempre que o usuário efetuar logout em seu aplicativo, é obrigatório chamar o método Revoke em nossa SDK.

## Iniciar Markey Pay

Para iniciar Market Pay é necessário chamar o método de launch da seguinte forma:
````kotlin
Merci.launch(this, MarketPay)
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

## Iniciar Pagar

Para iniciar o módulo de pagar, é necessário chamar o método de launch da seguinte forma:
````kotlin
Merci.launch(this, Payment)
````

## Iniciar Sacar:
Para iniciar o módulo de Sacar, é necessário chamar o método de launch da seguinte forma:

````kotlin
Merci.launch(this, Withdraw(enableSupport = 'true OU false'))
````

Caso enableSupport for true, será exibido um ícone de help (?) e quando o usuário clicar será executado
o seguinte callback dentro da classe ClientProvider:

````kotlin
override fun onWithdrawSupportRequested(activity: FragmentActivity) {

}
````

## Alterar identidade visual default

O mci-kit permite que o cliente altere a identidade visual dos principais pontos do framework. Para fazer isso
é necessário definir os seguintes parametros:
````xml
<item name="homeBackgroundColor" type="color"><!-- Cor de fundo da home --></item>
<item name="homeTitleColor" type="color"><!-- Cor de texto dos botões de categoria da home --></item>
<item name="actionTintColor" type="color"><!-- Cor dos botões de ação --></item>
<item name="actionTextTintColor" type="color"><!-- Cor do texto dos botões de ação --></item>
<item name="actionBarTintColor" type="color"><!-- Cor dos botões da ActionBar --></item>
<item name="loadingTintColor" type="color"><!-- Cor do loading --></item>
<item name="homeImage" type="drawable"><!-- Imagem de logo que será exibida na ToolBar --></item>
<item name="brandImage" type="drawable"><!-- Imagem que será exibida na transação --></item>
<item name="clientName" type="string"><!-- Identificação do cliente que irá ser exibido nas UIs onde necessitar exibir o nome do cliente --></item>
````
## Analytics
Eventos trackeados dentro do SDK que serão notificados na função `onEvent` no `ClientProvider`. Os objetos expostos nas notificações serão todos no padrão JSON, permitindo a fácil leitura por qualquer plataforma.

```
------------------------------------------------------------------------

MerciSDK_UserAuthenticationNotification:
{
    "timestamp": "1573728019",
    "status": "authenticaded|revoked"
}
------------------------------------------------------------------------

MerciSDK_ModulePresentationNotification:
{
    "timestamp": "1573728019",
    "module": "marketplace|payment|wallet",
    "status": "presented|dismissed"
}
------------------------------------------------------------------------
    
MerciSDK_MerchantPresentationNotification:
{
    "timestamp": "1573728019",
    "merchant": {
        "id": "66a42b85-6c02-455b-a3e2-f4ebea478e63",
        "name": "Uber Pré-Pago"
    },
    "status": "presented|dismissed"
}
------------------------------------------------------------------------

MerciSDK_TransactionNotification:   
{
    "timestamp": "1573728019",
    "merchant": {
        "id": "66a42b85-6c02-455b-a3e2-f4ebea478e63",
        "name": "Uber Pré-Pago"
    },
    "amount": 123.45,
    "status": "started|canceled|failed|completed"
}   
------------------------------------------------------------------------
```

## Proguard & R8
Caso seu projeto utilize uma dessas ferramentas de obfuscação será necessário adicionar as seguintes
regras no seu proguard-rules.pro:

```proguard
# mci-kit rules
-keep class kit.merci.data.model.** { *; }
-keep class kit.merci.data.network.requests.** { *; }
-keep class kit.merci.data.network.response.** { *; }
-keep class app.merci.merchant.taxis99.data.model.** { *; }
-keep class app.merci.merchant.taxis99.data.network.request.** { *; }
-keep class app.merci.merchant.taxis99.data.network.response.** { *; }
-keep class foundation.merci.external.** { *; }
```

---

[Merci @ 2020](https://merci.com.br)
