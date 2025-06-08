
# API de Avaliação de Risco - Java 

Este projeto é uma API REST desenvolvida em Java, usando Quarkus (Maven), que realiza o cadastro de usuários, login, registro de avaliações de risco habitacional e simulações para estimar o nível de risco com base nos dados fornecidos. O objetivo é auxiliar pessoas em situação de vulnerabilidade, como moradores de encostas e regiões de alagamento.

---

## IMPORTANTE

O projeto está no ar pelo Render: https://gs-java-k07h.onrender.com/
Quando não utilizado por mais de 15 minutos, ele desliga e, quando requisitado novamente, demora uns minutos para voltar ao ar
por isso, é normal uma demora na resposta da API acontecer.

---

## 📋 Funcionalidades

- Cadastro de novos usuários  
- Autenticação de usuários  
- Registro de avaliações de risco habitacional  
- Simulação de avaliação sem necessidade de cadastro  
- Cálculo de risco com base em fatores como tipo de construção, alagamento, encostas, etc.

---

## 🚀 Tecnologias Utilizadas

- Java 17  
- Spring Boot 3  
- Spring Data JPA  
- MySQL  
- Lombok  
- MapStruct

---

## 📦 Endpoints

### 🔐 Autenticação

#### POST `/usuarios/cadastro`  
Cadastra um novo usuário.

**Request:**  
```json
{
  "nome": "João da Silva",
  "email": "joao@email.com",
  "senha": "123456"
}
```

**Response:**  
```json
{
  "id": 1,
  "nome": "João da Silva",
  "email": "joao@email.com"
}
```

---

#### POST `/usuarios/login`  
Realiza o login e retorna os dados do usuário.

**Request:**  
```json
{
  "email": "joao@email.com",
  "senha": "123456"
}
```

**Response:**  
```json
{
  "id": 1,
  "nome": "João da Silva",
  "email": "joao@email.com"
}
```

---

### 📊 Avaliações

#### POST `/avaliacoes`  
Cria uma nova avaliação associada a um usuário.

**Request:**  
```json
{
  "usuarioId": 1,
  "cidade": "São Paulo",
  "estado": "SP",
  "moraEmEncosta": true,
  "ruaAlaga": true,
  "tipoConstrucao": "MADEIRA",
  "numeroPessoas": 5
}
```

**Response:**  
```json
{
  "id": 10,
  "usuarioId": 1,
  "cidade": "São Paulo",
  "estado": "SP",
  "moraEmEncosta": true,
  "ruaAlaga": true,
  "tipoConstrucao": "MADEIRA",
  "numeroPessoas": 5,
  "nivelRisco": "ALTO"
}
```

---

#### POST `/avaliacoes/simular`  
Permite simular uma avaliação de risco (tem que usar um id de usuario existente)

**Request:**  
```json
{
  "usuario_id": 33,
  "cidade": "Rio de Janeiro",
  "estado": "RJ",
  "moraEmEncosta": true,
  "ruaAlaga": false,
  "tipoConstrucao": "ALVENARIA",
  "numeroPessoas": 4,
}
```

**Response:**  
```json
{
  "nivelRisco": "MEDIO"
}
```

---

## 📌 Observações

- Os níveis de risco são classificados como: `BAIXO`, `MEDIO`, `ALTO`, com base em uma lógica de regras no back-end.

---


## 👨‍💻 Autor

Desenvolvido por joaoGFG.
