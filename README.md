# Microsserviço Wishlist

## Resumo

1. Apresentação
2. Tecnologias
3. Executar projeto
3. Executar testes
3. Funcionalidades

## Apresentação

Uma das funcionalidades mais interessantes em um e-commerce
é a Wishlist, ou a lista de desejos. No e-commerce o cliente pode
realizar a busca de produtos, ou pode acessar a tela de detalhes
do produto. Em ambas as telas ele pode selecionar os produtos
de sua preferência e armazená-los na sua Wishlist. A qualquer
momento o cliente pode visualizar sua Wishlist completa, com
todos os produtos que ele selecionou em uma única tela.

## Tecnologias

- Java 17
- Spring Boot 3
- MongoDB
- Gradle
- Docker
- Docker compose
- Arquitetura limpa
- JUnit 5
- Rest Assured
- Mockito
- Instancio

## Executar projeto

### Docker compose:
```docker compose up -d ```

### Environment variables:
```dockerfile
    # Define o ambiente em que o projeto está sendo executado
    LUIZALABS_WISHLIST_ENVIRONMENT = prod | local
    
    #Define a URL de conxexão com o MongoDB
    LUIZALABS_WISHLIST_MONGODB_URL = mongodb://mongodb:27017/wishlist
    
    # Define o tamanho máximo de produtos por wishlist
    LUIZALABS_WISHLIST_MAX_LIMIT = 20
```
 


## Executar testes

### Gradle:
```gradle clean test```

## Funcionalidades

### Criar wishlist

- URI: `/wishlist`
- Método: `POST`
- Descrição: Cria nova wishlist se o cliente ainda não tiver uma.

#### Requisiçao:
```json
{
  "customerId": "string"
}
```

#### Resposta:
**Código HTTP**: 201 Created
```json
{
  "id": "string",
  "customerId": "string",
  "maxLimit": 20,
  "createdAt": "yyyy-MM-dd HH:mm:ss"
}
```

### Adicionar produtos

- URI: `/wishlist/{wishlistId}/customer/{customerId}/product`
- Método: `POST`
- Descrição: Adiciona um novo produto

#### Requisiçao:
```json
{
  "productId": "string"
}
```

#### Resposta:
**Código HTTP**: 204 No Content

```No content```


### Remover produtos

- URI: `/wishlist/{wishlistId}/customer/{customerId}/product/{productId}`
- Método: `DELETE`
- Descrição: Remove produto


#### Resposta:
**Código HTTP**: 204 No content

```No content```

### Buscar produto

- URI: `/wishlist/{wishlistId}/customer/{customerId}/product/{productId}`
- Método: `GET`
- Descrição: Busca produto por id

#### Resposta:
**Código HTTP**: 200 Ok

```json
{
  "wishlistId": "string",
  "customerId": "string",
  "productId": "string"
}
```

### Buscar todos os produtos

- URI: `/wishlist/{wishlistId}/customer/{customerId}/product`
- Método: `GET`
- Descrição: Busca todos produtos da wishlist

#### Resposta:
**Código HTTP**: 200 Ok
```json
[
  "string",
  "string"
]
```




















