### Global Ressources :
[App UI workflow](https://excalidraw.com/#json=VcjqkwtH5qYGLkMrxzYJF,ZdNhzSL050cMiSZhZTl52g)

### TOOLS
- Flickr : [Get access token](https://www.youtube.com/watch?v=VHeUDOY_Z6A)
    https://www.flickr.com/services/api/auth.oauth.html

### Logique métier :
- DTO
    - toEntity : It can't return Category object create with a builder pattern because :
        - "Category" entity extends  from an abstract class (AbstractEntity) and "Category" entity class is different to "CategoryDto" by the param "Id" (define in abstract class).
        NB: So, it make sense to remove `@Builder` annotation from all model entities (abstract class can't be a builder)
    - Il est recommandé d'éviter de mentionner les champs List d'une classe car cela peut les problèmes de référence dans de dépendance circulaire.
        Cependant cela, simplifie l'affiche de l'objet et consomme moins en ressource. Ils seront demandés juste en cas de besoin.

- VALIDATOR
    - User --- fetch(CategoryDto) data ---> Input Checking : Validator(CategoryValidator) --> DB(retreive or persist).

- DAO :
    - Toutes les relations ManyToOne d'une entité doivent être définie.

    ```
        - /model/Article
            @ManyToOne
            @JoinColumn(name = "id_category")
            private Category category;

        - /repository/ArticleRepository
            List<Article> findAllByCategoryId(Integer idCategory);
    ```
    NB : 
     - Dans votre requête JPQL, vous avez spécifié stock_movement comme nom de l'entité, mais ce n'est pas correct. Dans JPQL, vous devez utiliser le nom de l'entité Java (classe Java), pas celui de la table en base de données. Dans ce cas, le nom correct est StockMovement, comme défini par la classe.

- SERVICE
    - COmmandeCLient Il faut vérifier l'existance effective du client avant la création de la commande client. (Pas dans le validateur : c'est une vérification métier)

    - Article service : Pour l'entreprise, on prendra l'entreprise connecté pour un utilisateur standard mais il faudra le notifier pour un utilsateur admin.



### DEPLOYMENT
- **`MAIN RESSOURCES`** :
    - DB : [Postgres + neon + java](https://neon.tech/docs/guides/java#connect-with-jdbc)
    - [Spring deploy + heroku](https://docs.spring.io/spring-boot/how-to/deployment/cloud.html#howto.deployment.cloud.heroku)
    - [Setting the HTTP Port for Java Applications](https://devcenter.heroku.com/articles/setting-the-http-port-for-java-applications)

- **`NOTE`** :

    0) New or update image:
        > Only use a Dockerfile file
        1) Build image with tag (v1.0) : `docker build -t moufidmtr/gstock-api:v1.1 .`
        2) Push it to docker hub (log in first) : `docker push moufidmtr/gstock-api:v1.1`

        NB : You can pull the image from docker hub (`docker pull moufidmtr/gstock-api:v1.1`)

    1) Se connecter à Heroku & au registre :
        ```
        heroku login
        heroku container:login
        ```
2) Créer une app si elle n'existe pas : 
    - Créer une application classique puis le set en container
        ```
        heroku create gstock-api
        heroku stack:set container (default stack for image)
        OU
        heroku stack:set heroku-24
        ```
    - Ou  directement créer une application container : 
        ```
        heroku create --stack container gstock-api (default stack for image)
        OU
        heroku create --stack heroku-24 gstock-api
        ```
    NB :
    -  Ressource :
        - [Container Registry & Runtime (Docker Deploys)](https://devcenter.heroku.com/articles/container-registry-and-runtime)
        - [Heroku-24 Stack](https://devcenter.heroku.com/articles/heroku-24-stack#heroku-24-docker-images)

3) Create a new tag with with heroku host :
    - Remarks : 
        > - **Renommer** l’image pour qu’elle corresponde au registre de Heroku (_registry.heroku.com_)
        >  - _web_ = le [process type]() attendu par Heroku (web, worker, etc.)
        > - _moufidmtr/gstock-api:v1.1_ : car c'est une image pull du docker hub.
        >    - _moufidmtr_ : your docker hub username
        >    - _gstock-api_ : image name (local or remote)
        >    - _v1:1_ : image tag

    ```
    docker tag moufidmtr/gstock-api:v1.1 registry.heroku.com/gstock-api/web
    ```
4) Push image on Heroku container registry : `docker push registry.heroku.com/gstock-api/web`

5) Deploy image to Heroku : `heroku container:release web --app gstock-api`

6) Display Logs : `heroku logs --tail --app gstock-api`