# Desafio Backend - Consumindo dados de uma fila RabbitMQ

O desafio consiste em criar um micro serviço que consuma dados de uma fila RabbitMQ
e grave os dados para conseguir listar as seguintes informações: valor total do pedido, quantidade de pedidos por cliente
e lista de pedidos realizados por cliente.

Para mais detalhes acesse o diretório do Desafio em: [BuildRun - Desafios Backend](https://github.com/buildrun-tech/buildrun-desafio-backend-btg-pactual/blob/main/problem.md).


Exemplo da mensagem que deve ser consumida:

```
{
  "codigoPedido": 1001,
  "codigoCliente":1,
  "itens": [
     {
       "produto": "lápis",
       "quantidade": 100,
       "preco": 1.10
     },
     {
       "produto": "caderno",
       "quantidade": 10,
       "preco": 1.00
     }
  ]
}
```

Exemplo da mensagem de resposta da API:

```
{
  "summary": {
    "totalOnOrders": 920.00
  },
  "data": [
	{
	  "orderId": 1002,
	  "customerId": 2,
      "total": 460.00
    },
	{
	  "orderId": 1003,
	  "customerId": 2,
	  "total": 460.00
    }
  ],
  "pagination": {
	"page": 0,
	"pageSize": 10,
	"totalElements": 2,
	"totalPages": 1
  }
}

```
# Tecnologias

- Java 17
- Spring Boot
- MongoDB
- MongoDB Compass
- IntelliJ
- Insomnia
- Docker