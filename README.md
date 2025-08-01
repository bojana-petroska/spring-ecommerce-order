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
   - [x] `CartItemRepository` using `JpaRepository`
   - [x] `ProductRepository` using `JpaRepository`
   - [x] `MemberRepository` using `JpaRepository`
   - [x] `CartRepository` using `JpaRepository`
   - [x] refactor tests, using `@DataJpaTest` 
3. Refactor Service
   - ex. validation wit `require()`

4. Add `options` to product information.
   - Property
     - [x] `id`
     - [x] `name` - up to 50 characters
     - [x] `quantity` - 1 to 100_000_000
   - Method
     - [x] decrease the quantity of the option by a specific amount
   - [x] A `product` must always have at least one `option`

### Entity

#### Cart

- [x] has `id`
- [x] has `cart_item_id` [ref: < cart_item.id]

## Functional Requirements

### Step 1.3

#### Overview
- Add **options** to product information.
- Design and implement the feature considering the relationship between the **Product** and **Option** models.

#### Constraints
1. A product **must always have at least one option**. 
2. Option names can include **up to 50 characters**, including spaces. 
3. **Allowed special characters** in option names:
    - `(`, `)`, `[`, `]`, `+`, `-`, `&`, `/`, `_`
    - **All other special characters are not allowed.**
4. Option quantity must be **at least 1 and less than 100,000,000**. 
5. **Duplicate option names are not allowed** within the same product to prevent confusion during purchase. 
6. Implement a method to **decrease the quantity of a product option by a specified amount**:
    - No need to create a separate HTTP API.
   - This logic should be implemented in the **Service** class or **Entity** class for future reuse.

#### Optional
 - Provide a way to **add options through an admin interface**.

### Step 1.2

Implement **pagination** for both the product list and the wishlist view.

- Most web applications do not display all data at once. Instead, content is split into multiple pages.
- Pagination allows users to define how data should be **sorted**, how many items are shown per **page**, and which **page number** to retrieve.
- Sorting can also be used to prioritize which data appears first.


### Step 1.1

Refactor your existing codebase from `JdbcTemplate` to use **Spring Data JPA**.
Learn how to model real domain objects and map them to database tables using JPA annotations.

- Read the DDL (Data Definition Language) statements below and infer how the entity and repository classes should be structured.
- Write learning tests using `@DataJpaTest`.
