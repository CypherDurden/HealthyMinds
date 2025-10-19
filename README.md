# HealthyMinds
202502 - Turma A, Grupo 2 - Trabalho desenvolvido para a matéria Prat Prof em ADS

HealthyMinds é um aplicativo JavaFX para apoio à saúde mental, permitindo que usuários registrem diários e consultas, com integração simulada ou real a uma IA para gerar respostas empáticas.

Tecnologias utilizadas

Java 21

JavaFX 22

SQLite (banco de dados local)

SLF4J para logs

Maven para gerenciamento de dependências

Como rodar o projeto
1. Pré-requisitos

JDK 21 instalado

Maven instalado

IDE (IntelliJ, Eclipse, etc.) configurada para JavaFX

2. Clonar o repositório
   git clone https://github.com/CypherDurden/HealthyMinds.git
   cd HealthyMinds

3. Configurar dependências

Certifique-se que o Maven está baixando todas as dependências:

mvn clean install

4. Configurar banco de dados

O projeto utiliza SQLite.

O arquivo mindcare.db será criado automaticamente na primeira execução, junto com as tabelas diario e consulta.

5. Rodar a aplicação
   Pelo Maven:
   mvn javafx:run

Pela IDE:

Configure uma run configuration para o MainApp.java

Certifique-se de adicionar a biblioteca JavaFX nas configurações de execução.

6. Funcionalidades

Registrar novos diários com título, conteúdo e data.

Registrar consultas com estado mental, dicas desejadas, relato e tom da consulta.

Visualizar histórico de diários e consultas.

Receber respostas de IA (API FREE do gemini).

7. Observações

Para habilitar integração real com a IA Gemini, configure sua chave de API no serviço GeminiService.

Logs detalhados são exibidos no console via SLF4J.
