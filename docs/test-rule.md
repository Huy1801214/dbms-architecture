# DBMS Unit Test Design Rules

## Purpose

Để toàn bộ Unit Test trong hệ thống DBMS tuân theo **OOP**, **SOLID**, **DDD** và **Clean Architecture**, mỗi test class chỉ được kiểm thử đúng **responsibility** của class đó.

> **Nguyên tắc quan trọng nhất:**  
> **Test đúng trách nhiệm của class, không test hành vi của class khác.**

---

# 1. Entity Test

## Purpose

Entity chỉ đại diện cho **Domain Object**.

Entity **không chịu trách nhiệm** tạo, xóa, lưu xuống ổ cứng hay quản lý vòng đời của chính nó.

Entity chỉ kiểm thử:

- State
- Business Rules
- Invariants
- Domain Behaviors

---

## Entity chỉ nên test

Ví dụ với `Database`

```java
shouldRenameDatabase()

shouldOpenDatabase()

shouldCloseDatabase()

shouldSetDatabaseOwner()

shouldUpdateDatabaseStatus()

shouldAddSchema()

shouldRemoveSchema()

shouldRejectOperationWhenClosed()
```

Ví dụ với `Table`

```java
shouldInsertRow()

shouldUpdateRow()

shouldDeleteRow()

shouldIncreaseRowCount()

shouldDecreaseRowCount()

shouldAnalyzeTable()
```

Ví dụ với `Page`

```java
shouldWritePageData()

shouldReadPageData()

shouldMarkPageDirty()

shouldClearDirtyFlag()
```

---

## Entity KHÔNG nên test

Không nên có các test như

```java
shouldCreateDatabase()

shouldDropDatabase()

shouldLoadDatabase()

shouldPersistDatabase()

shouldRecoverDatabase()
```

vì đây không phải responsibility của Entity.

---

# 2. Manager / Service Test

## Purpose

Manager hoặc Service chịu trách nhiệm điều phối (orchestration) và quản lý vòng đời của Domain Object.

Đây là nơi thực hiện:

- Create
- Delete
- Load
- Save
- Validation
- Workflow
- Coordination giữa nhiều Entity

---

## Manager nên test

Ví dụ `DatabaseManager`

```java
shouldCreateDatabase()

shouldDropDatabase()

shouldRenameDatabase()

shouldOpenDatabase()

shouldCloseDatabase()

shouldGetDatabaseByName()

shouldPersistDatabaseMetadata()

shouldLoadExistingDatabases()
```

Ví dụ `PageManager`

```java
shouldAllocatePage()

shouldDeallocatePage()

shouldReuseFreedPage()

shouldRestoreCheckpoint()
```

Ví dụ `BufferPoolManager`

```java
shouldFetchExistingPage()

shouldAllocateNewPage()

shouldFlushDirtyPage()

shouldEvictPage()
```

---

# 3. Repository Test

## Purpose

Repository chịu trách nhiệm giao tiếp với Storage.

Repository chỉ nên test:

- Read
- Write
- Load
- Save
- Serialization
- Deserialization

Ví dụ

```java
shouldSaveMetadata()

shouldLoadMetadata()

shouldDeleteMetadata()

shouldReadMetadataFile()
```

Repository không nên test business logic.

---

# 4. Factory Test

## Purpose

Factory chỉ chịu trách nhiệm tạo object.

Ví dụ

```java
shouldCreateDatabase()

shouldCreateTable()

shouldCreatePage()

shouldRejectInvalidArguments()
```

Factory không nên test persistence hoặc workflow.

---

# 5. Validator Test

Validator chỉ kiểm thử validation.

Ví dụ

```java
shouldValidateDatabaseName()

shouldRejectDuplicateDatabaseName()

shouldValidatePageSize()

shouldRejectNegativePageId()
```

Validator không nên tạo object.

---

# 6. Integration Test

## Purpose

Integration Test kiểm tra nhiều thành phần phối hợp với nhau.

Không test logic nhỏ.

Mà test toàn bộ workflow.

Ví dụ

```text
DatabaseManager
        ↓
Database
        ↓
Schema
        ↓
Table
```

Một Integration Test có thể là

```java
shouldCreateDatabaseWithSchemaAndTable()
```

Hoặc

```text
BufferPool
        ↓
PageManager
        ↓
FileManager
```

```java
shouldFlushDirtyPageToDisk()
```

---

# 7. Không trùng Responsibility

Không để nhiều class test cùng một hành vi.

Ví dụ sai

DatabaseTest

```java
shouldCreateDatabase()
```

DatabaseManagerTest

```java
shouldCreateDatabase()
```

=> Hai class cùng test Create Database.

Điều này vi phạm nguyên tắc phân chia trách nhiệm.

---

# 8. Mapping Responsibility

| Loại Class | Responsibility | Ví dụ Test |
|------------|---------------|------------|
| Entity | State + Business Rule | `shouldRenameDatabase()` |
| Manager | Workflow + Lifecycle | `shouldCreateDatabase()` |
| Service | Business Process | `shouldCommitTransaction()` |
| Repository | Persistence | `shouldSaveMetadata()` |
| Factory | Object Creation | `shouldCreatePage()` |
| Validator | Validation | `shouldRejectDuplicateName()` |
| Integration Test | End-to-End Workflow | `shouldCreateDatabaseWithSchemaAndTable()` |

---

# 9. Quy tắc đặt tên Test

Luôn theo format

```text
should + ExpectedBehavior
```

Ví dụ

```java
shouldCreateDatabase()

shouldRenameDatabase()

shouldOpenDatabase()

shouldCloseDatabase()

shouldInsertRow()

shouldRejectDuplicatePrimaryKey()

shouldGenerateNextValue()
```

Không đặt tên như

```java
testDatabase()

testInsert()

insertTest()

checkDatabase()
```

---

# 10. Quy trình review Unit Test

Trước khi viết Unit Test cho bất kỳ class nào, luôn tự hỏi:

### Bước 1

**Class này chịu trách nhiệm gì?**

Ví dụ

```
Database
```

→ Quản lý trạng thái của Database.

---

### Bước 2

**Behavior nào thuộc về chính class này?**

Ví dụ

```
rename()

open()

close()
```

---

### Bước 3

**Behavior nào thuộc class khác?**

Ví dụ

```
createDatabase()
```

→ thuộc DatabaseManager.

```
saveDatabase()
```

→ thuộc Repository.

```
validateDatabaseName()
```

→ thuộc Validator.

---

### Bước 4

Chỉ viết Unit Test cho những behavior thuộc responsibility của class.

---

# Golden Rule

> **Một Unit Test chỉ kiểm thử đúng responsibility của một class.**

- Entity không tạo chính nó.
- Entity không lưu chính nó.
- Entity không tải chính nó.
- Entity không điều phối workflow.
- Manager điều phối vòng đời của Entity.
- Repository giao tiếp với Storage.
- Factory tạo Object.
- Validator kiểm tra dữ liệu.
- Integration Test kiểm tra sự phối hợp giữa nhiều thành phần.

Tuân thủ quy tắc này sẽ giúp toàn bộ hệ thống Unit Test phản ánh đúng kiến trúc của các DBMS hiện đại như PostgreSQL, MySQL, SQL Server và BusTub, đồng thời giữ cho mã nguồn tuân thủ OOP, SOLID và Clean Architecture.