### DTO
    - toEntity cann't return Category object create with a builder pattern because :
        - The param "Id" is define in Abstract Class which couldn't be an entity so the Abstract Class can't get `@Builder` annotation.
        NB: In this case, it make sense to remove `@Builder` annotation from all model entities.