# Security Unit Test

## Security Manager

### 1. shouldAuthenticateUser()
```mermaid
sequenceDiagram
    autonumber
    participant Test as SecurityManagerTest
    participant Security as SecurityManager
    participant User as User

    Test->>Security: authenticate(username,password)

    Security->>User: findUser(username)

    User-->>Security: user found

    Security->>User: verifyPassword(password)

    User-->>Security: password valid

    Security-->>Test: AuthenticationSuccess=true
```

### 2. shouldAuthorizeUser()
```mermaid
sequenceDiagram
    autonumber
    participant Test as SecurityManagerTest
    participant Security as SecurityManager
    participant User as User
    participant Role as Role

    Test->>Security: authorize(user,resource)

    Security->>User: getAssignedRoles()

    User-->>Security: roles

    Security->>Role: containsPermission(resource)

    Role-->>Security: permission granted

    Security-->>Test: AuthorizationSuccess=true
```

### 3. shouldGrantPermission()
```mermaid
sequenceDiagram
    autonumber
    participant Test as SecurityManagerTest
    participant Security as SecurityManager
    participant Role as Role
    participant Permission

    Test->>Security: grantPermission(role,permission)

    Security->>Role: addPermission(permission)

    Role->>Permission: validate()

    Permission-->>Role: valid

    Role-->>Security: permission added

    Security-->>Test: GrantSuccess=true
```
### 4. shouldRevokePermission()
```mermaid
sequenceDiagram
    autonumber
    participant Test as SecurityManagerTest
    participant Security as SecurityManager
    participant Role as Role
    participant Permission

    Test->>Security: revokePermission(role,permission)

    Security->>Role: removePermission(permission)

    Role-->>Security: permission removed

    Security-->>Test: RevokeSuccess=true
```

## User 

### 5. shouldCreateUser()
```mermaid
sequenceDiagram
    autonumber
    participant Test as UserTest
    participant User

    Test->>User: create(username,password)

    User->>User: hashPassword()

    User->>User: initializeStatus()

    User-->>Test: UserCreated
```

### 6. shouldUpdatePassword()
```mermaid 
sequenceDiagram
    autonumber
    participant Test as UserTest
    participant User

    Test->>User: updatePassword(newPassword)

    User->>User: hashPassword()

    User->>User: replacePasswordHash()

    User-->>Test: PasswordUpdated
```

### 7. shouldLockUser()
```mermaid
sequenceDiagram
    autonumber
    participant Test as UserTest
    participant User

    Test->>User: lock()

    User->>User: status=LOCKED

    User-->>Test: Locked
```

### 8. shouldUnlockUser()
```mermaid 
sequenceDiagram
    autonumber
    participant Test as UserTest
    participant User

    Test->>User: unlock()

    User->>User: status=ACTIVE

    User-->>Test: Unlocked
```

## Role

### 9. shouldCreateRole()
```mermaid
sequenceDiagram
    autonumber
    participant Test as RoleTest
    participant Role

    Test->>Role: create(roleName)

    Role->>Role: initializePermissionSet()

    Role-->>Test: RoleCreated
```

### 10. shouldAssignPermission()
```mermaid
sequenceDiagram
    autonumber
    participant Test as RoleTest
    participant Role
    participant Permission

    Test->>Role: assignPermission(permission)

    Role->>Permission: validate()

    Permission-->>Role: valid

    Role->>Role: add(permission)

    Role-->>Test: PermissionAssigned
```

### 11. shouldRemovePermission()
```mermaid 
sequenceDiagram
    autonumber
    participant Test as RoleTest
    participant Role
    participant Permission

    Test->>Role: removePermission(permission)

    Role->>Role: delete(permission)

    Role-->>Test: PermissionRemoved
```

## Permission 

### 12. shouldCreatePermission()
```mermaid 
sequenceDiagram
    autonumber
    participant Test as PermissionTest
    participant Permission

    Test->>Permission: create(resource,action)

    Permission->>Permission: initialize()

    Permission-->>Test: PermissionCreated
```

### 13. shouldComparePermissions()
```mermaid
sequenceDiagram
    autonumber
    participant Test as PermissionTest
    participant Permission

    Test->>Permission: equals(otherPermission)

    Permission->>Permission: compareResource()

    Permission->>Permission: compareAction()

    Permission-->>Test: CompareResult
```

### 14. shouldValidatePermission()
```mermaid 
sequenceDiagram
    autonumber
    participant Test as PermissionTest
    participant Permission

    Test->>Permission: validate()

    Permission->>Permission: checkResource()

    Permission->>Permission: checkAction()

    Permission-->>Test: ValidationResult
```
# Security Integration Test

### 15. shouldAuthenticateAndAuthorizeUser()
```mermaid
sequenceDiagram
    autonumber
    participant Test as SecurityIntegrationTest
    participant Security as SecurityManager
    participant User
    participant Role

    Test->>Security: authenticate(username,password)

    Security->>User: verifyPassword()

    User-->>Security: authenticated

    Security->>Role: checkPermission()

    Role-->>Security: permission granted

    Security-->>Test: AccessGranted
```

### 16. shouldAssignRoleAndGrantPermission()
```mermaid
sequenceDiagram
    autonumber
    participant Test as SecurityIntegrationTest
    participant Security as SecurityManager
    participant User
    participant Role
    participant Permission

    Test->>Security: assignRole(user,role)

    Security->>User: attachRole(role)

    User-->>Security: role assigned

    Security->>Role: addPermission(permission)

    Role->>Permission: validate()

    Permission-->>Role: valid

    Role-->>Security: permission added

    Security-->>Test: AssignmentCompleted
```

### 17. shouldRevokePermissionSuccessfully()
```mermaid
sequenceDiagram
    autonumber
    participant Test as SecurityIntegrationTest
    participant Security as SecurityManager
    participant Role
    participant Permission

    Test->>Security: revokePermission(role,permission)

    Security->>Role: removePermission(permission)

    Role-->>Security: permission removed

    Security-->>Test: RevokeCompleted
```

### 18. shouldRejectUnauthorizedAccess()
```mermaid
sequenceDiagram
    autonumber
    participant Test as SecurityIntegrationTest
    participant Security as SecurityManager
    participant User
    participant Role

    Test->>Security: authorize(user,resource)

    Security->>User: getAssignedRoles()

    User-->>Security: roles

    Security->>Role: checkPermission(resource)

    Role-->>Security: permission denied

    Security-->>Test: AccessDenied
```