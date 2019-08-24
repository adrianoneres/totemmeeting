# Totem Meeting

**Totem Meeting** é uma aplicação com finalidade didática para controle da sala de reuniões. Foi desenvolvida durante os *workshops* de **Java** na **Totem TI**.

Este é o guia de desenvolvimento utilizado durante o *workshop* para que os participantes pudessem ter um roteiro para seguir.



## 1. Criar o projeto

O *backend* da aplicação será desenvolvido utilizando **Java** com **Spring Boot**. 

1. Acesse o [Spring Initializr](https://start.spring.io/).
2. Crie uma aplicação com **Gradle**, **Java**, na última versão estável do **Spring Boot**, com o grupo **br.com.totemti** e o nome do artefato **totemmeeting**. Em dependêncies adicione **Spring Web Starter**, **Spring Data JPA** e **PostgreSQL Driver**.
3. Clique no botão **"Generate the project"**, faça o download para um diretório de sua preferência.
4. Instale o [Lombok](https://projectlombok.org/) na sua IDE.
5. Importe o projeto na sua IDE.
6. adicione o `id 'eclipse'` na sessão de plugins do seu arquivo `build.gradle`.



## 2. Configurações iniciais

1. Crie o banco de dados `totemmeeting` e as tabelas:

   ```sql
   CREATE TABLE public.users (
       id INTEGER PRIMARY KEY NOT NULL,
       name CHARACTER VARYING(255) NOT NULL,
       email CHARACTER VARYING(100) NOT NULL UNIQUE,
       password CHARACTER VARYING(255) NOT NULL
   );
   
   CREATE SEQUENCE public.users_seq
       START WITH 1
       INCREMENT BY 1
       NO MINVALUE
       NO MAXVALUE
       CACHE 1;
   
   CREATE TABLE public.meetings (
       id INTEGER PRIMARY KEY NOT NULL,
       date TIMESTAMP WITH TIME ZONE NOT NULL,
       user_id INTEGER NOT NULL
   );
   
   CREATE SEQUENCE public.meetings_seq
       START WITH 1
       INCREMENT BY 1
       NO MINVALUE
       NO MAXVALUE
       CACHE 1;
   
   ALTER TABLE ONLY public.meetings
       ADD CONSTRAINT fk_meetings_users FOREIGN KEY (user_id) REFERENCES public.users(id);
   ```

2. No arquivo `application.properties`, adicione as configurações do banco de dados:


```
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost/totemmeeting
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults = false
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL9Dialect
```



## 3. Gerenciamento de usuários

### 3.1 Criar estrutura para usuário

1. Crie o *model* `User.java` e mapeie conforme sua tabela do banco de dados.
2. Crie o *repository* `UserRepository.java` e faça a configuração necessária. Lembre-se que os *repositories* devem ser ***interfaces***.
3. Crie o *service* `UserService.java`, injete o *repository* correspondente.
4. Crie o *controller* `UserController.java`, injete o *service* correspondente .



### 3.2 Criar usuário

1. Crie o método `create` em `UserService.java`.
2. Crie o método `create` em `UserController.java` e mapeie a rota adequadamente.
3. Altere o `setPassword` na classe `User.java` para criptografar a senha utilizando o **Apache Commons Codec**.



### 3.3 Validar dados de entrada

1. Faça a validação dos dados de entrada utilizando **Bean Validation**.
2. Garanta que o `UserController.java` valide a entrada de dados utilizando a anotação `@Valid`.



### 3.4 Editar usuário

1. Crie um atributo *transient* no *model* de usuário para efetuar validações de senha. Este atributo deverá se chamar `oldPassword`.

2. Crie o método `edit` no *service* correspondente. Lembre-se de fazer as seguintes validações:

   1. Se o usuário enviar o atributo `oldPassword`, significa que ele deseja alterar a senha. Verifique se ele corresponde ao persisitido no banco de dados.
   2. Se o usuário não enviar `oldPassword` significa que ele não deseja alterar a senha. Apenas verifique se o `password` enviado corresponde ao persisitido no banco de dados.

   

## 4 Gerenciamento de autenticação

1. Adicione a biblioteca **JSON Web Token Support For The JVM** no projeto.
2. Crie um `DTO` para armazenar o *token JWT* que será gerado pela aplicação.
3. Crie uma classe utilitária que contenha dois métodos estáticos: um para gerar o *token JWT*  e outro para recuperar o *payload* de um *token* já gerado.
4. Crie o `SessionController.java` com a rota `/sessions`, utilizando o método `POST` para criar um novo *token JWT* . 
5. Crie o `SessionService.java` contendo a lógica de autenticação do usuário.
6. Crie o `SessionFilter.java` para tratar as requisições para as rotas protegidas da API.

