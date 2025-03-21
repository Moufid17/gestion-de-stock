### Features
- [x] Define the entities
- [x] Define the Dtos (Data Transfer Objects)
- [x] Define dao
- [x] Define validators
- [x] exceptions
- [x] Picture management
- [x] Swagger

- Gobal errors
    - [ ] GET {articleId} : (/api/v1/articles/2) "Required path variable 'articleId' is not present."

- [ ] TI : 
    - [x] Company to article

- [1] Client Order 
    - [x] Edit qte : 
        - [x] Error raised while find order id (OrderClientDto calls OrderLineClientDto.fromEntity while OrderLineClientDto calls OrderClientDto..fromEntity) : dependency loop error
        - [x] dependency loop error
    - [x] Edit client : java.lang.NullPointerException: Cannot invoke "java.util.List.size()" because the return value of "com.tmdigital.gestiondestock.dto.OrderClientDto.getOrderLineClients()" is null
    - [x] Add order state updating (canceled, In progress, shipped)
    - [x] Edit an article : Error raised while find order id (OrderClientDto calls OrderLineClientDto.fromEntity while OrderLineClientDto calls OrderClientDto..fromEntity).
    - [x] Delete an article
    - [ ] Add an article
    - [x] Retreive client orders lines
- [2] Supplier order
    - [ ] Edit qte
    - [ ] Edit client
    - [ ] Add order state updating (canceled, In progress, shipped)
    - [ ] Edit an article
    - [ ] Delete an article
    - [ ] Retreive client orders lines
- [3] Article
    - [ ] Retreive history
        - [ ] Sales history
        - [ ] Client Order history
        - [ ] Supplier Order history
- [4] Mvt stock Implementation

- [ ] TU:
    - [x] Merge Company and category
    - [5] Update Company and category (if any changement have been made.)
- [6] Security :
    - [x] Jwt
    - [ ] Session
    - [ ] Action privileges
- [7] Company Implementation:
    - [x] create one with an admin user
    - [ ] Send email with email and password
- [x] Category Implementation