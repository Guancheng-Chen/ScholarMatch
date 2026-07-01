### Template

**Add a title:**

A brief description of the task.

**Add a description:**

- **Goal:** The to-do item or action you plan to complete.
    
- **Files to edit:** The files you need to modify.
    
- **Expected behaviour:** The expected behavior after the issue is resolved.
    
- **Testing:** Any relevant tests or manual checks.
    

### Example:

**Goal:** Add current-user tracking to the login use case.

**Files to edit:**

- `LoginUserDataAccessInterface.java`
    
- `InMemoryUserDataAccessObject.java`
    
- `LoginInteractor.java`
    
- `LoginInteractorTest.java`
    

**Expected behaviour:**

After a successful login, the DAO should store the logged-in username. Before login, it should be null.

**Testing:**

Write and pass `successUserLoggedInTest` in `LoginInteractorTest`.