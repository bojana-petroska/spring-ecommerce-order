# spring-ecommerce-order

## Features List

1. Refactor model into entity
   - [x] `Product` into entity
   - [x] `Member` into entity
   - [x] `CartItem` into entity
   - [x] introduce new entity `Cart`
   - [x] find way to deal with cascade
   - [x] find way to deal with one-to-one &one-to-many & many-to-one & many-to-many

2. Refactor Repository
   - refactor test, using `@DataJpaTest` 
3. Refactor Service
   - ex. validation wit `require()`

### Entity

#### Cart

- [ ] has `id`
- [ ] has `member_id` [ref: - member.id]
- [ ] has `cart_item_id` [ref: < cart_item.id]

## Functional Requirements

### Step 1.1

Refactor your existing codebase from `JdbcTemplate` to use **Spring Data JPA**.
Learn how to model real domain objects and map them to database tables using JPA annotations.

- Read the DDL (Data Definition Language) statements below and infer how the entity and repository classes should be structured.
- Write learning tests using `@DataJpaTest`.
