# <img src="https://raw.githubusercontent.com/bobocode-projects/resources/master/image/logo_transparent_background.png" height=50/>Hibernate "Dirty checking" tutorial
This is the tutorial well-known Hibernate feature called "Dirty checking"

### Pre-conditions :heavy_exclamation_mark:
You're supposed to have basic knowledge of ORM, JPA and Hibernate.  
##
**Dirty checking mechanism** is a *Hibernate* feature that **transforms entity field changes into SQL `UPDATE`** statements in the 
scope of *Persistence Context (PC)*.

**By default, dirty checking is enabled.** For each managed(persistent) entity it Hibernate creates a snapshot of persisted 
data (entity copy). That data is stored in the scope of *PC* as an array of `Object` elements. That array basically holds
the same data as corresponding database row. At flush time, every entity is compared with its snapshot copy, if some changes detected, Hibernate generates appropriate 
`UPDATE` statement.

```java
        performWithinPersistenceContext(entityManager -> { // Persistence Context begins
            Account account = entityManager.find(Account.class, accountId); // account is managed by Hibernate
            account.setLastName(newLastName); // this changes will be detected by Dirty Checking mechanism
            // on flush dirty checking will generate UPDATE statement and will send it to the database
        });// Persistence Context ends
``` 

Please note, that this mechanism has **huge performance impact**, because it **doubles the size of the *PC***, requires 
**additional CPU resources** to check each attribute of each manges entity, and causes **additional Garbage Collection.**
In order to **turn of dirty checking**, you should set default read-only for the `Session`, or specify a hint when creating
new select query.

```java
        performWithinPersistenceContext(entityManager -> {
            Session session = entityManager.unwrap(Session.class);
            session.setDefaultReadOnly(true); // turns off dirty checking for this session (for this entityManager)
            Account managedAccount = entityManager.find(Account.class, accountId);
            managedAccount.setFirstName("XXX"); // won't cause SQL UPDATE statement since dirty checking is disabled
        });
```

In order to disable dirty checking for a particular entity on select use query hints:

```java
        performWithinPersistenceContext(entityManager -> {
            Account managedAccount = entityManager.createQuery("select a from Account a where a.email = :email", Account.class)
                    .setParameter("email", email)
                    .setHint(QueryHints.HINT_READONLY, true)// turns off dirty checking for this particular entity
                    .getSingleResult();
            managedAccount.setFirstName("XXX"); // won't cause SQL UPDATE statement since dirty checking is disabled for this entity
        });
``` 
 
### Best practices
* always use **read-only** queries for all `SELECT` statements
* keep *Persistence Context* size as small as possible

