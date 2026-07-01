
### Template

**Title:**

**Description:**

- **Fixes #xxx:** Which issue does this resolve?
    
- **Behaviours Completed:** What to-do items or actions have you completed?
    
- **Files Changed:** Which files did you modify?
    
- **Brief Explanation:** Briefly explain how your code works.
    
- **Testing:** How did you test your changes?
    
- **Unsure About:** Is there anything you are unsure about?
    
### Example:

#### Title:  
`Implement current user tracking `

#### Description:
Fixes \#xxxx

**Behaviours Completed**
- Implemented current-user tracking functionality during the login process.

**Files Changed**
- `src/main/java/use_case/login/LoginUserDataAccessInterface.java`
- `src/main/java/data_access/InMemoryUserDataAccessObject.java`
- `src/main/java/use_case/login/LoginInteractor.java`
- `src/test/java/use_case/login/LoginInteractorTest.java`

**Brief Explanation**
- Expanded the DAO interface to include `setCurrentUsername` and `getCurrentUsername`.
- Implemented these methods in `InMemoryUserDataAccessObject` using a private instance variable to store the state.
- Updated `LoginInteractor` so that upon a successful password match, the interactor saves the authenticated user's name into the DAO before preparing the success view.

**Testing**
- Added and passed `successUserLoggedInTest` in `LoginInteractorTest` to verify that the DAO correctly records the user's name after a successful login and returns `null` beforehand.
- Manually ran `app.Main` to ensure the program compiles and the login GUI still functions smoothly.

**Unsure About**
- N/A (Everything works as expected).- Added `successUserLoggedInTest` to verify behaviour.
