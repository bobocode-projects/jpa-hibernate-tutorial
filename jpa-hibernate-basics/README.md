# Basic JPA examples
**EntityManagerSimpleExample**  provides a simple examples of `EntityManager` usage. It represents a _unit of work_. 
Please note, that all operations should be performed in the scope on `EntityManager` (session). In that case all operations
 are performed within _PersistenceContext_.

**EntityManagerCrudOperationsExample** shows what operations are already implemented in the `EntityManger`. It's basic 
CRUD(create, read, update, delete) operations.

**OneToOneRelationExample** provides an example of one to one relation between `User` and `Address`. Please note, 
that **relation is managed on the child side** (table `address` contains a _foreign key_ `user_id`). In this example we save 
new entities `User`, `Address` and link them together.   

**OneToManyRelationExample** provides an example of one to many relation between `User` and it's `Role` list. The relation
is managed on the client side (Role side). Table `roles` contains a _foreign key_ `user_id`