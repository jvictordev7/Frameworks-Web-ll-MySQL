# 🚀 Frameworks Web II – Aula 03 (MySQL + Spring Boot)

Aplicação desenvolvida para a disciplina **Frameworks Web 2** do curso de **Análise e Desenvolvimento de Sistemas**. O objetivo é demonstrar o fluxo completo de uma API REST em Spring Boot conectada a um banco MySQL, manipulando duas entidades relacionadas: **Category** e **Product**.

---

## 🧱 Stack utilizada
- Java 17
- Spring Boot 3.5.7 (Web + Data JPA)
- MySQL 8+ (ou XAMPP/MySQL compatível)
- Maven Wrapper

---

## ✅ Pré-requisitos
1. **Java 17** configurado (`JAVA_HOME` apontando para o JDK).
2. **MySQL** em execução (pode ser via XAMPP).  
3. Banco criado com o nome presente em `application.properties` (por padrão `frameworks2_aula03`).

```sql
CREATE DATABASE frameworks2_aula03;

CREATE TABLE categories (
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE products (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(150) NOT NULL,
    price       DECIMAL(10,2) NOT NULL,
    category_id BIGINT NOT NULL,
    CONSTRAINT fk_products_category
        FOREIGN KEY (category_id) REFERENCES categories(id)
);
```

---

## ⚙️ Configuração
1. Ajuste `src/main/resources/application.properties` com seu usuário e senha MySQL:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/frameworks2_aula03?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=SUASENHA
   ```
2. (Opcional) Popule dados de teste diretamente no banco ou usando os endpoints.

---

## ▶️ Como executar
```bash
# Dentro da pasta do projeto
./mvnw spring-boot:run
# ou
./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```
A API ficará disponível em `http://localhost:8080`.

---

## 📡 Endpoints principais

### Categorias (`/categories`)
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/categories` | Lista todas as categorias |
| GET | `/categories/{id}` | Busca por ID |
| GET | `/categories/byName/{name}` | Busca usando JPQL |
| GET | `/categories/byNameSQL/{name}` | Busca usando SQL nativo |
| POST | `/categories` | Cria uma categoria |
| PUT | `/categories/{id}` | Atualiza nome da categoria |
| DELETE | `/categories/{id}` | Exclui uma categoria (somente se não houver produtos associados) |

### Produtos (`/products`)
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/products` | Lista todos os produtos |
| GET | `/products/{id}` | Busca por ID |
| GET | `/products/below-price/{maxPrice}` | Produtos abaixo de um preço |
| GET | `/products/category/{categoryId}` | Produtos de uma categoria |
| POST | `/products` | Cria um produto (informar `category.id`) |
| PUT | `/products/{id}` | Atualiza dados do produto |
| DELETE | `/products/{id}` | Remove produto |

---

## 🧪 Exemplos de requisição

### Criar categoria
```http
POST /categories
Content-Type: application/json

{
  "name": "Alimentos"
}
```

### Criar produto
```http
POST /products
Content-Type: application/json

{
  "name": "Arroz 5kg",
  "price": 28.90,
  "category": { "id": 1 }
}
```

---

## 📎 Referências úteis
- [Documentação Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [MySQL Reference](https://dev.mysql.com/doc/)

