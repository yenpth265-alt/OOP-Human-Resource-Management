# Lab 03-04: Hệ thống Quản lý Dự án Nhân sự

## 1. Giới thiệu
Dự án mô phỏng hệ thống quản lý nhân sự và phân công nhóm dự án bằng Java. Hệ thống cho phép khởi tạo nhân viên thường, kỹ sư phần mềm (có phụ cấp chuyên môn), và phân bổ họ vào các nhóm dự án với các vai trò cụ thể (thành viên, trưởng nhóm). 

Dự án áp dụng chặt chẽ các nguyên lý Lập trình hướng đối tượng (OOP):
- **Tính đóng gói (Encapsulation):** Bảo vệ dữ liệu thông qua các Access Modifier và xử lý ngoại lệ khi khởi tạo (chặn lương âm, chặn ID rỗng).
- **Tính kế thừa (Inheritance):** `SoftwareEngineer` kế thừa từ `Employee`.
- **Tính đa hình (Polymorphism):** Ghi đè (Override) phương thức tính lương, hiển thị thông tin và nạp chồng (Overload) các phương thức thêm thành viên, tăng lương.

## 2. Sơ đồ thiết kế (Class Diagram)
Sơ đồ dưới đây thể hiện cấu trúc các lớp và mối quan hệ kết tập (Aggregation) giữa Dự án và Nhân sự.

```mermaid
classDiagram
    class Employee {
        #String id
        #String name
        #double baseSalary
        +Employee()
        +Employee(String id, String name)
        +Employee(String id, String name, double baseSalary)
        +increaseSalary(double amount) void
        +increaseSalary(double value, boolean byPercentage) void
        +calculateMonthlyCost() double
        +displayInfo() void
        +getId() String
    }

    class SoftwareEngineer {
        -String primaryLanguage
        -double technicalAllowance
        +SoftwareEngineer(String id, String name, String primaryLanguage)
        +SoftwareEngineer(String id, String name, double baseSalary, String primaryLanguage, double technicalAllowance)
        +calculateMonthlyCost() double
        +displayInfo() void
    }

    class ProjectTeam {
        -String projectCode
        -String projectName
        -Employee leader
        -List~Employee~ members
        +ProjectTeam(String projectCode, String projectName)
        +ProjectTeam(String projectCode, String projectName, Employee leader)
        +addMember(Employee employee) boolean
        +addMember(Employee employee, boolean makeLeader) boolean
        +removeMember(String id) boolean
        +changeLeader(Employee newLeader) void
        +contains(String id) boolean
        +calculateTotalMonthlyCost() double
        +displayTeam() void
    }

    Employee <|-- SoftwareEngineer : Kế thừa (Inheritance)
    ProjectTeam o-- Employee : Kết tập (Aggregation - Leader)
    ProjectTeam o-- Employee : Kết tập (Aggregation - Members)
