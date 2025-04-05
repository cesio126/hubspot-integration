# HubSpot Integration API

Este projeto é uma aplicação backend desenvolvida com Spring Boot para integração com a API do HubSpot utilizando OAuth 2.0. Ele fornece endpoints para autenticação, criação de contatos no CRM e escuta de eventos via Webhooks.

## 🚀 Como Executar o Projeto

### Pré-requisitos
- Java 17 ou superior
- Maven 3.8 ou superior
- Conta de desenvolvedor no HubSpot
- IDE como VS Code ou IntelliJ (opcional)

### Passos Detalhados para Execução Local

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/hubspot-integration.git
   cd hubspot-integration
   ```

2. **Configure o arquivo de propriedades:**
   Abra o arquivo `src/main/resources/application.yml` e adicione suas credenciais:

   ```yaml
   hubspot:
     client:
       id: YOUR_CLIENT_ID
       secret: YOUR_CLIENT_SECRET
       redirect:
         uri: http://localhost:8080/oauth/callback
   ```

   > 🔒 Substitua `YOUR_CLIENT_ID` e `YOUR_CLIENT_SECRET` pelas credenciais da sua conta de desenvolvedor no HubSpot.

3. **Execute o projeto usando Maven:**
   ```bash
   ./mvnw spring-boot:run
   ```
   Ou, caso esteja usando VS Code ou IntelliJ, localize a classe `HubspotIntegrationApplication.java` e clique em "Run".

4. **Abra o navegador e acesse os endpoints:**
   - Gere a URL de autorização OAuth:
     ```
     GET http://localhost:8080/oauth/url
     ```
   - Após o redirecionamento do HubSpot, o código será enviado para o endpoint `/oauth/callback`.

5. **Criar um contato (exemplo usando curl):**
   ```bash
   curl -X POST http://localhost:8080/contacts \
     -H "Content-Type: application/json" \
     -d '{
       "email": "joao@exemplo.com",
       "firstName": "João",
       "lastName": "Silva"
     }'
   ```

6. **Webhook:**
   Configure no HubSpot a URL `http://localhost:8080/contacts/webhook` para eventos do tipo `contact.creation`.

### Endpoints Disponíveis

| Método | Rota                   | Descrição                            |
|--------|------------------------|----------------------------------------|
| GET    | `/oauth/url`          | Gera a URL para autenticação OAuth     |
| GET    | `/oauth/callback`     | Troca o código pelo token de acesso    |
| POST   | `/contacts`           | Cria um novo contato no CRM HubSpot    |
| POST   | `/contacts/webhook`   | Escuta eventos de criação de contato   |

---

## 📚 Documentação Técnica

### Estrutura do Projeto
O projeto está dividido em pacotes organizados por responsabilidade:
- `controller`: Camada de exposição da API.
- `service`: Lógica de negócio e comunicação com a API do HubSpot.
- `dto`: Objetos de transferência de dados.
- `resources`: Configurações como `application.yml`.

### Decisões Técnicas e Bibliotecas Utilizadas

- **Spring Boot:** Framework principal utilizado pela sua produtividade, configuração simplificada e robustez para APIs REST.
- **WebClient (Spring WebFlux):** Biblioteca moderna e reativa para chamadas HTTP. Utilizada por oferecer maior flexibilidade e suporte a `Retry` para tratamento de rate limiting.
- **Lombok:** Incluído para reduzir código boilerplate como getters, setters, construtores e logs. Melhora a legibilidade e reduz a verbosidade do Java.
- **Reactor Retry:** Implementado implicitamente via `WebClient.retryWhen()` para tratar de forma elegante limites de requisição da API HubSpot.

### Tratamento de Rate Limiting
Usamos o `Retry.backoff` com WebClient para reexecutar automaticamente requisições em caso de erro ou limite de taxa excedido (rate limit), respeitando as boas práticas da HubSpot.

### Segurança
- As credenciais e tokens não são armazenados em disco — apenas em memória. Idealmente, o token seria armazenado com segurança (ex: Redis, Vault).
- Seguindo práticas do OAuth 2.0 padrão e recomendações oficiais da HubSpot.

---

## 🔮 Possíveis Melhorias Futuras

- Armazenar e renovar o `access_token` com `refresh_token` em um repositório seguro.
- Criar testes automatizados com `SpringBootTest` e mocks para APIs externas.
- Adicionar documentação Swagger para a API.
- Criar UI frontend para facilitar a autorização OAuth.
- Substituir o armazenamento em memória por cache distribuído.
- Permitir múltiplas integrações para diferentes contas HubSpot.

---

Desenvolvido por Ricardo Baptista em Java e Spring Boot.