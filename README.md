# 🏦 Projeto Banco PDI - Microsserviços com Kafka e Elasticsearch

Este repositório contém o código-fonte de um sistema bancário simplificado, desenvolvido como parte de um Plano de Desenvolvimento Individual (PDI) focado no estudo e aplicação de tecnologias de sistemas distribuídos, com ênfase no ecossistema do Elasticsearch.

O projeto simula operações de criação de contas e transferências monetárias, utilizando uma arquitetura de microsserviços robusta, resiliente e orientada a eventos.

---
### 🎯 Alinhamento com os Objetivos do PDI

Este projeto foi desenhado para cumprir os seguintes objetivos de aprendizado:

* **1. Fundamentos do Elasticsearch:**
    * ✅ **Concluído:** Através da criação do `notificacao-service`, que consome eventos de transações do Kafka e os indexa em tempo real no índice `transacoes`, estabelecendo o pipeline de dados completo.

* **2. Aprofundamento em Consultas e Análise:**
    * ✅ **Concluído:** Com a implementação da API de busca no `notificacao-service`, que expõe endpoints para buscas filtradas (filtros dinâmicos por conta e data), busca full-text e, principalmente, a API de agregações para analytics em tempo real (ex: total de gastos por conta).

* **3. Visualização com Kibana:**
    * ✅ **Concluído:** Com a criação de um dashboard interativo no Kibana, com gráficos de pizza e séries temporais que consomem os dados do Elasticsearch para fornecer insights visuais sobre as transações.

---
### 🏛️ Arquitetura

O sistema é composto por 4 microsserviços principais e uma infraestrutura containerizada com Docker. A comunicação entre os serviços é feita de forma síncrona (REST com Feign Client) e assíncrona (via eventos com Kafka).

**Fluxo Simplificado:**
`Cliente` -> `API Gateway` -> `Serviços de Negócio` -> `Kafka` -> `Serviço de Notificação` -> `Elasticsearch` -> `Kibana`

---
### 🚀 Tecnologias Utilizadas

* **Backend:**
    * Java 21 (com Virtual Threads habilitadas)
    * Spring Boot 3
    * Spring Cloud Gateway (API Gateway)
    * Spring Data JPA (Persistência Relacional)
    * Spring Data Elasticsearch (Busca e Análise)
    * OpenFeign (Comunicação Síncrona)
    * Resilience4j (Circuit Breaker)
* **Mensageria:**
    * Apache Kafka
* **Banco de Dados & Busca:**
    * PostgreSQL
    * Elasticsearch
* **Ferramentas e Infraestrutura:**
    * Docker & Docker Compose
    * Gradle (Build Tool)
    * Kibana (Visualização)
    * Kafka UI (Monitoramento do Kafka)
    * Lombok

---
### ⚙️ Como Executar o Projeto

**Pré-requisitos:**
* Java 21
* Docker e Docker Compose

**Passos:**
1.  **Clone o Repositório:**
    ```sh
    git clone <URL_DO_SEU_REPOSITORIO>
    cd <PASTA_RAIZ_DO_PROJETO>
    ```

2.  **Inicie a Infraestrutura:**
    Este comando subirá os containers do Postgres, Kafka, Elasticsearch e Kibana.
    ```sh
    docker-compose up -d
    ```

3.  **Construa o Projeto:**
    Este comando compilará todos os módulos.
    ```sh
    ./gradlew.bat clean build
    ```

4.  **Execute os Serviços:**
    Abra um terminal para cada serviço e execute o comando correspondente.

    * **Terminal 1 (Conta Service):**
        ```sh
        ./gradlew.bat :conta:bootRun
        ```
    * **Terminal 2 (Transação Service):**
        ```sh
        ./gradlew.bat :transacao:bootRun
        ```
    * **Terminal 3 (Notificação Service):**
        ```sh
        ./gradlew.bat :notificacao:bootRun
        ```
    * **Terminal 4 (API Gateway):**
        ```sh
        ./gradlew.bat :api-gateway:bootRun
        ```

A aplicação estará pronta para ser usada!

---
### 📖 Documentação da API (Endpoints)

Todas as requisições devem ser feitas para o **API Gateway**: `http://localhost:8080`.

#### Serviço de Contas (`/contas`)
| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/contas` | Cria uma nova conta. |
| `GET` | `/contas/{id}` | Busca os detalhes de uma conta por ID. |

#### Serviço de Transações (`/transacoes`)
| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/transacoes` | Inicia uma nova transação. Requer o header `Idempotency-Key`. |

#### Serviço de Busca (`/transacoes-search`)
| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `GET` | `/transacoes-search` | Busca filtrada e paginada de transações. |
| `GET` | `/transacoes-search/busca` | Busca textual livre na descrição das transações. |
| `GET` | `/transacoes-search/stats/gastos-por-conta-destino` | Agrega e retorna o total gasto por conta de destino. |

---
### 🧪 Ambiente e Collection Postman

Para facilitar os testes, você pode baixar a collection completa da API e o arquivo de ambiente do Postman no link abaixo.

* **[🔗 Baixar Arquivos do Postman (Collection + Environment)](https://drive.google.com/drive/folders/10QGD9_cyRip6ufS0HmKyoueP61tx6Vlk?usp=sharing)**

---
### 🔎 Como o Elasticsearch foi Utilizado

O Elasticsearch é o coração da capacidade de análise e consulta deste projeto, cumprindo os objetivos do PDI.

1.  **Pipeline de Dados em Tempo Real:** O `notificacao-service` atua como um consumidor do tópico Kafka `transacoes.concluidas`. Assim que uma transação é bem-sucedida, o evento é capturado e o dado é indexado quase instantaneamente no Elasticsearch.

2.  **Busca e Análise:** O Elasticsearch é usado para duas finalidades principais:
    * **API de Busca:** Fornece uma API de busca poderosa e desacoplada do banco de dados relacional. Isso permite consultas complexas (filtros, paginação, busca textual) sem sobrecarregar o banco transacional.
    * **Analytics em Tempo Real:** A API de Agregações do Elasticsearch foi usada para criar o endpoint de estatísticas (`/stats/gastos-por-conta-destino`), que realiza cálculos complexos (agrupamentos e somas) diretamente no motor de busca, de forma performática.

3.  **Visualização:** Os dados indexados no Elasticsearch servem como fonte para o **Kibana**, onde foi construído um dashboard para monitorar e visualizar os indicadores de negócio (volume de transações, totais, etc.) de forma interativa.