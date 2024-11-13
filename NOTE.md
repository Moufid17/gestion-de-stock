### DTO
    - toEntity cann't return Category object create with a builder pattern because :
        - The param "Id" is define in Abstract Class which couldn't be an entity so the Abstract Class can't get `@Builder` annotation.
        NB: In this case, it make sense to remove `@Builder` annotation from all model entities.
    - Il est recommandé d'éviter de mentionner les champs List d'une classe car cela peut les problèmes de référence dans de dépendance circulaire.
        Cependant cela, simplifie l'affiche de l'aobjet et consomme moins en ressource. Ils seront demandés juste en cas de besoin.