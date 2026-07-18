# Security Unit Test

## SecurityManager

### 1. shouldAuthenticateUser()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as SecurityManagerTest
    end
    box #e3f2fd Security Components
    participant Security as SecurityManager
    participant User as User
    end

    Test->>Security: authenticate("user", "pass")
    Security->>User: validatePassword("pass")
    User-->>Security: valid
    Security-->>Test: AuthSuccess=true
```

### 2. shouldAuthorizeUser()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as SecurityManagerTest
    end
    box #e3f2fd Security Components
    participant Security as SecurityManager
    participant User as User
    participant Role as Role
    end

    Test->>Security: authorize("user", "read")
    Security->>User: getRoles()
    User-->>Security: roles
    Security->>Role: checkPermission("read")
    Role-->>Security: permitted
    Security-->>Test: AuthzSuccess=true
```

### 3. shouldGrantPermission()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as SecurityManagerTest
    end
    box #e3f2fd Security Components
    participant Security as SecurityManager
    participant User as User
    end

    Test->>Security: grantPermission("user", "read")
    Security->>User: addPermission("read")
    User-->>Security: success
    Security-->>Test: GrantSuccess=true
```

### 4. shouldRevokePermission()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as SecurityManagerTest
    end
    box #e3f2fd Security Components
    participant Security as SecurityManager
    participant User as User
    end

    Test->>Security: revokePermission("user", "read")
    Security->>User: removePermission("read")
    User-->>Security: success
    Security-->>Test: RevokeSuccess=true
```

### 5. shouldRejectInvalidPassword()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as SecurityManagerTest
    end
    box #e3f2fd Security Components
    participant Security as SecurityManager
    participant User as User
    end

    Test->>Security: authenticate("user", "wrongPass")
    Security->>User: validatePassword("wrongPass")
    User-->>Security: invalid
    Security-->>Test: error: InvalidPassword
```

### 6. shouldRejectLockedUser()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as SecurityManagerTest
    end
    box #e3f2fd Security Components
    participant Security as SecurityManager
    participant User as User
    end

    Test->>Security: authenticate("user", "pass")
    Security->>User: isLocked()
    User-->>Security: true
    Security-->>Test: error: UserLocked
```

### 7. shouldRejectDisabledUser()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as SecurityManagerTest
    end
    box #e3f2fd Security Components
    participant Security as SecurityManager
    participant User as User
    end

    Test->>Security: authenticate("user", "pass")
    Security->>User: isEnabled()
    User-->>Security: false
    Security-->>Test: error: UserDisabled
```

### 8. shouldCheckRolePermission()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as SecurityManagerTest
    end
    box #e3f2fd Security Components
    participant Security as SecurityManager
    participant Role as Role
    end

    Test->>Security: checkRole("admin", "read")
    Security->>Role: hasPermission("read")
    Role-->>Security: true
    Security-->>Test: hasPermission=true
```

### 9. shouldGrantRolePermission()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as SecurityManagerTest
    end
    box #e3f2fd Security Components
    participant Security as SecurityManager
    participant Role as Role
    end

    Test->>Security: grantRolePermission("admin", "write")
    Security->>Role: addPermission("write")
    Role-->>Security: success
    Security-->>Test: GrantSuccess=true
```

### 10. shouldVerifyPermissionInheritance()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as SecurityManagerTest
    end
    box #e3f2fd Security Components
    participant Security as SecurityManager
    participant User as User
    participant Role as Role
    end

    Test->>Security: checkPermission("user", "read")
    Security->>User: getRoles()
    User-->>Security: [admin]
    Security->>Role: getInheritedPermissions()
    Role-->>Security: [read]
    Security-->>Test: permitted=true
```

## User

### 1. shouldCreateUser()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as UserTest
    end
    box #e3f2fd Security Components
    participant User as User
    end

    Test->>User: create("name", "hash")
    User-->>Test: UserCreated=true
```

### 2. shouldUpdatePassword()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as UserTest
    end
    box #e3f2fd Security Components
    participant User as User
    end

    Test->>User: updatePassword("newHash")
    User->>User: setPasswordHash()
    User-->>Test: UpdateSuccess=true
```

### 3. shouldLockUser()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as UserTest
    end
    box #e3f2fd Security Components
    participant User as User
    end

    Test->>User: lock()
    User->>User: setLocked(true)
    User-->>Test: LockSuccess=true
```

### 4. shouldUnlockUser()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as UserTest
    end
    box #e3f2fd Security Components
    participant User as User
    end

    Test->>User: unlock()
    User->>User: setLocked(false)
    User-->>Test: UnlockSuccess=true
```

### 5. shouldEnableUser()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as UserTest
    end
    box #e3f2fd Security Components
    participant User as User
    end

    Test->>User: enable()
    User->>User: setEnabled(true)
    User-->>Test: EnableSuccess=true
```

### 6. shouldDisableUser()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as UserTest
    end
    box #e3f2fd Security Components
    participant User as User
    end

    Test->>User: disable()
    User->>User: setEnabled(false)
    User-->>Test: DisableSuccess=true
```

### 7. shouldValidatePasswordHash()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as UserTest
    end
    box #e3f2fd Security Components
    participant User as User
    end

    Test->>User: validatePassword("pass")
    User->>User: compareHash("pass")
    User-->>Test: match=true
```

## Role

### 1. shouldCreateRole()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as RoleTest
    end
    box #e3f2fd Security Components
    participant Role as Role
    end

    Test->>Role: create("admin")
    Role-->>Test: RoleCreated=true
```

### 2. shouldAssignPermission()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as RoleTest
    end
    box #e3f2fd Security Components
    participant Role as Role
    end

    Test->>Role: assignPermission("read")
    Role->>Role: addPermission("read")
    Role-->>Test: AssignSuccess=true
```

### 3. shouldRemovePermission()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as RoleTest
    end
    box #e3f2fd Security Components
    participant Role as Role
    end

    Test->>Role: removePermission("read")
    Role->>Role: deletePermission("read")
    Role-->>Test: RemoveSuccess=true
```

### 4. shouldDeleteRole()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as RoleTest
    end
    box #e3f2fd Security Components
    participant Role as Role
    end

    Test->>Role: delete()
    Role-->>Test: DeleteSuccess=true
```

### 5. shouldRenameRole()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as RoleTest
    end
    box #e3f2fd Security Components
    participant Role as Role
    end

    Test->>Role: rename("newAdmin")
    Role->>Role: setName()
    Role-->>Test: RenameSuccess=true
```

### 6. shouldListPermissions()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as RoleTest
    end
    box #e3f2fd Security Components
    participant Role as Role
    end

    Test->>Role: getPermissions()
    Role-->>Test: list
```

## Permission

### 1. shouldCreatePermission()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as PermissionTest
    end
    box #e3f2fd Security Components
    participant Permission as Permission
    end

    Test->>Permission: create("read", "table1")
    Permission-->>Test: PermissionCreated=true
```

### 2. shouldComparePermissions()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as PermissionTest
    end
    box #e3f2fd Security Components
    participant Permission as Permission
    end

    Test->>Permission: compare(perm1, perm2)
    Permission-->>Test: equals=true
```

### 3. shouldValidatePermission()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as PermissionTest
    end
    box #e3f2fd Security Components
    participant Permission as Permission
    end

    Test->>Permission: validate()
    Permission-->>Test: valid=true
```

### 4. shouldStoreAction()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as PermissionTest
    end
    box #e3f2fd Security Components
    participant Permission as Permission
    end

    Test->>Permission: setAction("read")
    Permission-->>Test: success
```

### 5. shouldStoreResource()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as PermissionTest
    end
    box #e3f2fd Security Components
    participant Permission as Permission
    end

    Test->>Permission: setResource("table1")
    Permission-->>Test: success
```

# Security Integration Test

### 1. shouldAuthenticateAndAuthorizeUser()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as SecurityIntegrationTest
    end
    box #e3f2fd Security Components
    participant Security as SecurityManager
    end

    Test->>Security: login("u", "p")
    Security-->>Test: token
    Test->>Security: access("u", "resource")
    Security-->>Test: allowed=true
```

### 2. shouldAssignRoleAndGrantPermission()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as SecurityIntegrationTest
    end
    box #e3f2fd Security Components
    participant Security as SecurityManager
    end

    Test->>Security: assignRole("u", "admin")
    Security->>Security: grantPermission("admin", "read")
    Security-->>Test: success
```

### 3. shouldRevokePermissionSuccessfully()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as SecurityIntegrationTest
    end
    box #e3f2fd Security Components
    participant Security as SecurityManager
    end

    Test->>Security: revokePermission("admin", "read")
    Security-->>Test: success
```

### 4. shouldRejectUnauthorizedAccess()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as SecurityIntegrationTest
    end
    box #e3f2fd Security Components
    participant Security as SecurityManager
    end

    Test->>Security: access("u", "secret")
    Security-->>Test: error: Unauthorized
```

### 5. shouldAuthenticateGrantAndAccess()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as SecurityIntegrationTest
    end
    box #e3f2fd Security Components
    participant Security as SecurityManager
    end

    Test->>Security: login("u", "p")
    Security->>Security: grantPermission("u", "read")
    Security->>Security: access("u", "read")
    Security-->>Test: allowed=true
```

### 6. shouldGrantRoleThenAuthorize()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as SecurityIntegrationTest
    end
    box #e3f2fd Security Components
    participant Security as SecurityManager
    end

    Test->>Security: assignRole("u", "manager")
    Security->>Security: authorize("u", "write")
    Security-->>Test: authorized=true
```

### 7. shouldRevokePermissionAndRejectAccess()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
    participant Test as SecurityIntegrationTest
    end
    box #e3f2fd Security Components
    participant Security as SecurityManager
    end

    Test->>Security: revoke("u", "read")
    Security->>Security: access("u", "read")
    Security-->>Test: denied=true
```

