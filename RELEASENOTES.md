# Release notes

## 1.3.2
Otimização na comunicação entre o SDK e APIs

## 1.3.1
BugFix


## 1.3.0
Feature: Tageamento de eventos.

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

## 1.2.7
Improvements: Otimização nas chamadas dos serviços

## 1.2.6
Feature: Inclusão de um novo campos para a customização de cor do texto dos botões de ação:

````xml
<item name="actionTextTintColor" type="color"><!-- Cor do texto dos botões de ação --></item>
````

## 1.2.5
BugFix: Correção no tint color da image de close da Home

## 1.2.4
BugFix: Correção de issue no ambiente de produção

## 1.2.3
BugFix: Correção de UI nas imagens de merchant

## 1.2.2

BugFix: Fix no ambiente de produção do SDK

## 1.2.1

Feature: Melhoria e inclusão de novos campos para customização de cores nas UIs do SDK:
````xml
<item name="homeBackgroundColor" type="color"><!-- Cor de fundo da home --></item>
<item name="homeTitleColor" type="color"><!-- Cor de texto dos botões de categoria da home --></item>
<item name="actionTintColor" type="color"><!-- Cor dos botões de ação --></item>
<item name="actionBarTintColor" type="color"><!-- Cor dos botões da ActionBar --></item>
<item name="loadingTintColor" type="color"><!-- Cor do loading --></item>
````

## 1.2.0

BugFix: Agent name

BugFix: Inclusão do `merciClientName` para a identificação do cliente nas UIs do SDK 

## 1.1.1

BugFix: Correção de conflito de layouts

## 1.1.0

Feature: Marketplace home

## 1.0.0

Versão de lançamento

[Merci @ 2019](https://merci.com.br)
