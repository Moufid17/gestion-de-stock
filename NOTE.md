### DTO
    - toEntity : It can't return Category object create with a builder pattern because :
        - "Category" entity extends  from an abstract class (AbstractEntity) and "CategoryEntity" class is different to "CategoryDto" by the param "Id".
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