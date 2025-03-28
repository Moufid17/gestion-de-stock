### Ressources :
    [App UX workflow](https://excalidraw.com/#json=VcjqkwtH5qYGLkMrxzYJF,ZdNhzSL050cMiSZhZTl52g)

### Logique métier :
    - Save Order Client :
        
        
### DTO
    - toEntity : It can't return Category object create with a builder pattern because :
        - "Category" entity extends  from an abstract class (AbstractEntity) and "Category" entity class is different to "CategoryDto" by the param "Id" (define in abstract class).
        NB: So, it make sense to remove `@Builder` annotation from all model entities (abstract class can't be a builder)
    - Il est recommandé d'éviter de mentionner les champs List d'une classe car cela peut les problèmes de référence dans de dépendance circulaire.
        Cependant cela, simplifie l'affiche de l'objet et consomme moins en ressource. Ils seront demandés juste en cas de besoin.

### VALIDATOR
    - User --- fetch(CategoryDto) data ---> Input Checking : Validator(CategoryValidator) --> DB(retreive or persist).

### DAO :
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

### SERVICE
    - COmmandeCLient Il faut vérifier l'existance effective du client avant la création de la commande client. (Pas dans le validateur : c'est une vérification métier)

    - Article service : Pour l'entreprise, on prendra l'entreprise connecté pour un utilisateur standard mais il faudra le notifier pour un utilsateur admin.
### TOOLS
- Flickr : [Get access token](https://www.youtube.com/watch?v=VHeUDOY_Z6A)
    https://www.flickr.com/services/api/auth.oauth.html