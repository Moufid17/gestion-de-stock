
### Features
- [ ] Migration to postgresql
    - [x] setting up docker compose, env file, dependencies and ressources.
    - [ ] Error :
        - [ ] Model name "user" can be used for postgres database 
            - 'org.postgresql.util.PSQLException: ERROR: syntax error at or near "user"'
            - 'org.postgresql.util.PSQLException: ERROR: relation "users_roles" does not exist'
- [x] Define the entities
- [x] Define the Dtos (Data Transfer Objects)
- [x] Define dao
- [x] Define validators
- [x] exceptions
- [x] Picture management
- [x] Swagger

- Gobal errors
    - [x] GET {articleId} : (/api/v1/articles/2) "Required path variable 'articleId' is not present."
    - [x] GET {articleId} : NoSuchElementException when you try to retreive unauthorised article. (for exemple from another company)
    - [x] GET supplier orders : return "null" for existing order lines
    - [x] GET supplier orders/{id} : return "null" for existing order lines
    - [x] GET suppliers : "GET" method not allowed

- [x] Client Order 
    - [x] Edit qte : 
        - [x] Error raised while find order id (OrderClientDto calls OrderLineClientDto.fromEntity while OrderLineClientDto calls OrderClientDto..fromEntity) : dependency loop error
        - [x] dependency loop error
    - [x] Edit client : java.lang.NullPointerException: Cannot invoke "java.util.List.size()" because the return value of "com.tmdigital.gestiondestock.dto.OrderClientDto.getOrderLineClients()" is null
    - [x] Add order state updating (canceled, In progress, shipped)
    - [x] Edit an article : Error raised while find order id (OrderClientDto calls OrderLineClientDto.fromEntity while OrderLineClientDto calls OrderClientDto..fromEntity).
    - [x] Delete an article
    - [x] Add an article
    - [x] Retreive client orders lines
    - [x] Prohibit deletion of a cmd line if it is the last one.
- [x] Supplier order
    - [x] Edit qte
    - [x] Edit Supplier
    - [x] Update order status (canceled, In progress, shipped)
    - [x] Edit an article
    - [x] Delete an article
    - [x] Add an article
    - [x] Retreive client orders lines
    - [x] Prohibit deletion of a cmd line if it is the last one.
- [x] Article
    - [x] Retreive history
        - [x] Sales history
        - [x] Client Order history
        - [x] Supplier Order history

- [*] stock Mvt  Implementation
    - [x] Stock réel de chaque article
    - [x] Consulter mvt de stock de chaque article
    - [x] Entree de stock
    - [x] Sortie de stock
    - [x] Order supplier : update orderline id after any changement.
        - [x] update stock : +
        - [x] set stock mvt according to order source.
        - [x] add order line
        - [x] update order (quantity , article )
        - [x] delete order line
        - [x] delete order
    - [ ] Order client : 
        - [ ] update stock : -
        - [ ] set stock mvt according to order source.
        - [ ] add order line
        - [ ] update order (quantity , article )
        - [ ] delete order line
        - [ ] delete order

- [ ] User : Reset password
- [ ] Validator : Finsih with the rest

- [ ] Stats :
    - [ ] Most sales articles
    - [ ] Most loyal customers
    - [ ] Sales stats per period (start and end date)

- [6] Security :
    - [x] Jwt
    - [ ] Session
    - [ ] Action privileges
    
- [7] Company Implementation:
    - [x] create one with an admin user
    - [ ] Send email with email and password

- [x] Category Implementation

- [ ] TI : 
    - [x] Company to article
    - [x] supplier to supplier order
    - [x] client to client order

- [ ] TU:
    - [x] Merge Company and category
    - [5] Update Company and category (if any changement have been made.)
    - [5bis] Client :
        - [ ] Client
        - [ ] Client Order
    - [5bis+] Supplier :
        - [ ] Supplier
        - [ ] Supplier Order
    - [ ] Article
    - [ ] Sales
    - [ ] Stock mvt