//
202419124
Phan Thi Hai Yen
//
import java.util.ArrayList;
import java.util.List;

class Employee {
    protected String id;
    protected String name;
    protected double baseSalary;

    public Employee() {
        this("UNKNOWN", "Unnamed employee", 0.0); 
    }

    public Employee(String id, String name) {
        this(id, name, 0.0);
    }

    public Employee(String id, String name, double baseSalary) {
        if (id == null || id.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("ID and name cannot be null or empty");
        }
        if (baseSalary < 0) {
            throw new IllegalArgumentException("Base salary cannot be negative");
        }
        this.id = id;
        this.name = name;
        this.baseSalary = baseSalary;
    }

    public void increaseSalary(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Increase amount cannot be negative");
        }
        this.baseSalary += amount;
    }

    public void increaseSalary(double value, boolean byPercentage) {
        if (value <= 0) throw new IllegalArgumentException("Increase value cannot be negative");
        if (byPercentage) {
            this.baseSalary += this.baseSalary * value / 100;
        } else {
            this.baseSalary += value;
        }
    }

    public double calculateMonthlyCost() {
        return this.baseSalary;
    }

    public void displayInfo() {
        System.out.println("ID: " + id + ", Name: " + name + ", Base Salary: " + baseSalary);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class SoftwareEngineer extends Employee {
    private String primaryLanguage;
    private double technicalAllowance;

    public SoftwareEngineer(String id, String name, String primaryLanguage) {
        super(id, name, 0.0);
        if (primaryLanguage == null || primaryLanguage.trim().isEmpty()) {
            throw new IllegalArgumentException("Primary language cannot be null or empty");
        }
        this.primaryLanguage = primaryLanguage;
        this.technicalAllowance = 0.0;
    }

    public SoftwareEngineer(String id, String name, double baseSalary, String primaryLanguage, double technicalAllowance) {
        super(id, name, baseSalary);
        if (primaryLanguage == null || primaryLanguage.trim().isEmpty()) {
            throw new IllegalArgumentException("Primary language cannot be null or empty");
        }
        if (technicalAllowance < 0) {
            throw new IllegalArgumentException("Technical allowance cannot be negative");
        }
        this.primaryLanguage = primaryLanguage;
        this.technicalAllowance = technicalAllowance;
    }

    @Override
    public double calculateMonthlyCost() {
        return super.calculateMonthlyCost() + this.technicalAllowance;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("  -> Primary Language: " + primaryLanguage + ", Technical Allowance: " + technicalAllowance);
    }
}

class ProjectTeam {
    private String projectCode;
    private String projectName;
    private Employee leader;
    private List<Employee> members;

    public ProjectTeam(String projectCode, String projectName) {
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.members = new ArrayList<>();
    }

    public ProjectTeam(String projectCode, String projectName, Employee leader) {
        this(projectCode, projectName);
        addMember(leader, true);
    }

    public boolean addMember(Employee employee) {
        return addMember(employee, false);
    }

    public boolean addMember(Employee employee, boolean makeLeader) {
        if (employee == null) {
            return false;
        }
        
        if (contains(employee.getId())) {
            if (makeLeader) {
                this.leader = employee;
                return true;
            }
            return false;
        }
        members.add(employee);
        if (makeLeader) {
            this.leader = employee;
        }
        return true;
    }

    public boolean contains(String id) {
        if (id == null) return false;
        return members.stream().anyMatch(e -> e.getId().equals(id));
    }

    public boolean removeMember(String id) {
        if (id == null) return false;
        
        if (leader != null && leader.getId().equals(id)) {
            System.out.println("Khong the xoa truong nhom khi chua co nguoi thay the");
            return false;
        }
        return members.removeIf(e -> e.getId().equals(id));
    }

    public void changeLeader(Employee newLeader) {
        if (newLeader != null) {
            addMember(newLeader, true);
        }
    }

    public double calculateTotalMonthlyCost() {
        return members.stream().mapToDouble(Employee::calculateMonthlyCost).sum();
    }

    public void displayTeam() {
        System.out.println("Team: " + projectName + " " + projectCode + " | Leader: " + (leader != null ? leader.getName() : "None"));
        members.forEach(Employee::displayInfo);
    }
}

public class Main {
    public static void main(String[] args) {
        Employee emp1 = new Employee("E01", "Nguyen Van A");
        Employee emp2 = new Employee("E02", "Tran Thi B", 1000);

        SoftwareEngineer se1 = new SoftwareEngineer("S01", "Le Van C", "Java");
        SoftwareEngineer se2 = new SoftwareEngineer("S02", "Pham Thi D", 2000, "C++", 500);

        emp1.increaseSalary(200);
        emp2.increaseSalary(10, true);

        ProjectTeam team1 = new ProjectTeam("P01", "Alpha Project");

        team1.addMember(emp1);
        team1.addMember(se1, true);
        team1.addMember(emp1); 
        team1.displayTeam();
        System.out.println("Total cost: " + team1.calculateTotalMonthlyCost());

        System.out.println("\n--- Thu xoa leader S01 ---");
        team1.removeMember("S01"); 
        
        System.out.println("\n--- Thay doi leader sang S02 va xoa S01 ---");
        team1.changeLeader(se2);
        team1.removeMember("S01"); 

        team1.displayTeam();

        {
            ProjectTeam team2 = new ProjectTeam("P02", "Beta Project", emp1); 
            team2.addMember(emp2);
        } 

        System.out.println("\n--- Thong tin nhan su E01 ---");
        emp1.displayInfo();
    }
}
