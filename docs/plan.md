# 1. Database Objects (10 Core Features)

> **Mục tiêu:** Quản lý toàn bộ đối tượng logic trong Database.

| # | Core Feature | Tại sao là Core? | Pattern phù hợp |
|---|---|---|---|
| 1 | Table Management | Trung tâm của Database Objects | Builder ⭐ |
| 2 | Column Management | Định nghĩa cấu trúc bảng | Builder |
| 3 | Schema Management | Quản lý namespace | Composite |
| 4 | Constraint Management | Đảm bảo toàn vẹn dữ liệu | Strategy ⭐ |
| 5 | Index Management | Tăng hiệu năng truy vấn | Factory Method ⭐ |
| 6 | Object Relationship Management | Quan hệ Database → Schema → Table → Column | Composite ⭐ |
| 7 | Dependency Management | FK, View, Index phụ thuộc Table | Observer |
| 8 | Catalog / Metadata Management | Lưu thông tin object | Repository |
| 9 | DDL Command Processing | CREATE / ALTER / DROP | Command |
| 10 | Database Lifecycle Management | Tạo/Xóa database | Factory Method |

---

# 2. Storage Engine (10 Core Features)

> **Mục tiêu:** Quản lý lưu trữ vật lý và bộ nhớ.

| # | Core Feature | Tại sao là Core? | Pattern phù hợp |
|---|---|---|---|
| 1 | Buffer Pool Management | Cache toàn bộ Page | Singleton ⭐ |
| 2 | Buffer Replacement | Thay Page khi đầy RAM | Strategy ⭐ |
| 3 | Record Management | Đọc/Ghi Record trong Page | Builder |
| 4 | Page Management | Đơn vị lưu trữ cơ bản | Factory Method |
| 5 | Page Allocation | Cấp phát Page mới | Factory Method ⭐ |
| 6 | Disk I/O Management | Đọc/Ghi Page | Adapter |
| 7 | Space Management | Quản lý vùng trống | Strategy |
| 8 | File Management | Quản lý file vật lý | Adapter ⭐ |
| 9 | Storage Optimization | Compression, Fragmentation | Strategy |
| 10 | Cache Management | Quản lý cache | Singleton |

---

# 3. Query Processing (10 Core Features)

> **Mục tiêu:** Chuyển SQL thành kết quả.

| # | Core Feature | Tại sao là Core? | Pattern phù hợp |
|---|---|---|---|
| 1 | Query Execution | Thực thi Plan | Chain of Responsibility ⭐ |
| 2 | Query Optimization | Chọn kế hoạch tối ưu | Strategy ⭐ |
| 3 | Physical Plan Generation | Chuyển sang Execution Plan | Factory Method |
| 4 | Logical Plan Generation | Sinh Logical Plan | Builder |
| 5 | Operator Management | Scan, Join, Sort... | Command |
| 6 | Semantic Analysis | Kiểm tra Table, Column | Visitor |
| 7 | AST Management | Quản lý cây cú pháp | Visitor ⭐ |
| 8 | SQL Parsing | Sinh AST | Interpreter ⭐ |
| 9 | SQL Lexical Analysis | Tokenize SQL | Interpreter |
| 10 | Result Processing | Trả kết quả cho Client | Iterator |

-