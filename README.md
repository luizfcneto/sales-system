# Gerenciamento de Vendas
Este projeto consiste em uma aplicação Java desenvolvida com o objetivo de auxiliar o usuário na gestão de vendas para seus clientes. O sistema oferece funcionalidades para cadastro de clientes, produtos e vendas, priorizando a usabilidade e a facilidade de atendimento ao cliente final.

## Funcionalidades
1. Consulta de Clientes

    Permite consultar, incluir, excluir e alterar cadastros de clientes.

2. Cadastro de Cliente

    Código: Identificador único do cliente.
    Nome: Nome do cliente.
    Limite de compra (valor): Limite de crédito do cliente.
    Dia de fechamento da fatura: Data para o fechamento do ciclo de compras.

3. Consulta de Produtos

    Permite consultar, incluir, excluir e alterar cadastros de produtos.

4. Cadastro de Produto

    Código: Identificador único do produto.
    Descrição: Descrição detalhada do produto.
    Preço: Valor unitário do produto.

5. Consulta de Vendas

    Permite consultar, incluir, excluir e alterar cadastros de vendas.
    Visualizar por Clientes: Exibe os dados agrupados por cliente, incluindo valores associados.
    Visualizar por Produto: Exibe os dados agrupados por produto, incluindo valores associados.
    Realizar filtros: Permite filtrar por cliente, produto e período.

6. Cadastro de Venda

    Cliente: Cliente previamente cadastrado.
    Produtos: Produtos previamente cadastrados.

## Validações
    Limite de Crédito do Cliente:
        Verificar as compras realizadas após o dia de fechamento informado no cadastro do cliente.
        Caso o limite de crédito seja excedido, o sistema deve:
            Informar o valor disponível.
            Informar a data do próximo fechamento.
        Considerar meses com 28, 29, 30 e 31 dias.
    Bloqueio de Produtos Iguais:
        Não permitir a inclusão de produtos repetidos em uma venda.
        Contudo, deve ser possível:
            Alterar a quantidade de um produto já adicionado.
            Excluir produtos da venda.

## Requisitos Obrigatórios
    Persistência de Dados: Banco de dados PostgreSQL.
    Versão do Java: Java 8 ou superior.
    Interface Gráfica: Pacote SWING.
    Versionamento de Código: Repositório público no GitLab, GitHub ou BitBucket.
    Consultas SQL: Utilização de linguagem SQL diretamente no código.

## Opcionais (Desejáveis)
    Cobertura de Testes Unitários: Testes unitários para a lógica principal da aplicação.
    Princípios de Design (SOLID): Aplicação dos princípios SOLID.
    Arquitetura Limpa (Clean Architecture): Separação clara e organizada das responsabilidades do sistema.
    Gerenciamento de Dependências: Ferramenta como Maven ou Gradle.

## Imagens Telas
    Consulta de Clientes:

    Cadastro de Cliente:

    Consulta de Produtos:

    Cadastro de Produto:

    Consulta de Vendas:

    Cadastro de Venda:

## Modelo Entidade-Relacionamento


## Instruções de Execução


## Tecnologias Utilizadas
Backend: Java
Frontend: Java SWING
Banco de dados: Postgresql
conexão com banco de dados: JDBC

## Autor
Luiz Fernando Neto

## Licença
Licença: MIT License
Para mais informações, consulte o arquivo LICENSE
