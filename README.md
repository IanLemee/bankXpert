# BankXpert. Neste projeto simplificado de conta bancária, temos algumas funcionalidades interessantes que gostaria de compartilhar:

## Funcionalidade do PIX: Você pode procurar uma conta pelo email ou CPF e realizar transferências de forma fácil e rápida.

## Compra de Ações: A partir do momento em que a API da BRAPI é consumida utilizando OpenFeign, você pode comprar ações diretamente pelo BankXpert.

## Arquitetura de Microservices: O BankXpert não é um monolito, mas sim um microservice. Utilizamos um sistema de mensageria através das filas do RabbitMQ, com comunicação assíncrona e seguindo o princípio de FIFO (First In, First Out). Isso nos permite gerenciar os eventos de forma eficiente.

## Sistema de Envio de Emails: Cada microservice tem seu próprio banco de dados. Quando um usuário se cadastra no BankXpert, faz uma transferência bancária ou um PIX, um evento é disparado para um broker ou fila, que fica aguardando até que o serviço de emails esteja pronto para consumir essa fila e enviar o email.

## Tecnologias Utilizadas:
### Docker: Para criar as imagens dos nossos bancos de dados.
### Spring Boot: Utilizado com diversas dependências, como MailSender, Spring Security, RabbitMQ, OpenFeign, entre outras.
### RabbitMQ: Para o sistema de mensageria.

## Funcionalidades do Software:
### Cadastro de usuário
### Login de usuário
### Transferência bancária
### Compra e venda de ações
### Disparo de emails

## Endpoints Disponíveis:

## Criar Usuário: POST http://localhost:8081/user
Exemplo:
{
 "name": "ian",
 "email": "ian@email.com",
 "password": "1234567890",
 "document": "111111111"
}

## Login de Usuário: POST http://localhost:8081/login/
Exemplo:
{
 "email": "ian@email.com",
 "password": "1234567890"
}

## Transferência Entre Contas: POST http://localhost:8081/transfer/
Exemplo:
{
 "emailOrDocument": "ana@email.com",
 "amount": "300.00"
}

## Venda de Ações: POST http://localhost:8081/transfer/stocksSell
Exemplo:
{
 "stockName": "CSMG3",
 "quantity": 6
}

## Compra de Ações: POST http://localhost:8081/transfer/stocksBuy
Exemplo:
{
 "stockName": "CSMG3",
 "quantity": 6
}

Próximos Passos:
Adicionar uma camada de testes unitários.
Tornar a segurança mais avançada com OAuth2.
Realizar o deploy na AWS.
