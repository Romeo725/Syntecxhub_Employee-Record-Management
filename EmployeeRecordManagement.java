public class EmployeeRecordManagement {
	private final java.util.List<Employee> employees = new java.util.ArrayList<>();
	private final java.util.Set<String> departments = new java.util.HashSet<>();
	private final java.util.Map<Integer, Employee> employeesById = new java.util.HashMap<>();

	public boolean addEmployee(Employee employee) {
		if (employee == null || employeesById.containsKey(employee.getId())) return false;
		employees.add(employee);
		employeesById.put(employee.getId(), employee);
		departments.add(employee.getDepartment());
		return true;
	}

	public boolean updateEmployee(int id, String name, String department, double salary) {
		Employee employee = employeesById.get(id);
		if (employee == null) return false;
		String previousDepartment = employee.getDepartment();
		employee.setName(name);
		employee.setDepartment(department);
		employee.setSalary(salary);
		departments.add(department);
		if (!java.util.Objects.equals(previousDepartment, department)
				&& !hasEmployeeInDepartment(previousDepartment)) {
			departments.remove(previousDepartment);
		}
		return true;
	}

	public boolean deleteEmployee(int id) {
		Employee employee = employeesById.remove(id);
		if (employee == null) return false;
		employees.remove(employee);
		if (!hasEmployeeInDepartment(employee.getDepartment())) {
			departments.remove(employee.getDepartment());
		}
		return true;
	}

	private boolean hasEmployeeInDepartment(String department) {
		for (Employee employee : employees) {
			if (java.util.Objects.equals(employee.getDepartment(), department)) return true;
		}
		return false;
	}

	public Employee findEmployee(int id) {
		return employeesById.get(id);
	}

	public java.util.List<Employee> getEmployees() {
		return java.util.Collections.unmodifiableList(employees);
	}

	public java.util.Set<String> getDepartments() {
		return java.util.Collections.unmodifiableSet(departments);
	}

	public static void main(String[] args) {
		EmployeeRecordManagement manager = new EmployeeRecordManagement();
		manager.addEmployee(new Employee(1, "Alice", "Engineering", 75000));
		manager.addEmployee(new Employee(2, "Bob", "Human Resources", 65000));

		for (Employee employee : manager.getEmployees()) {
			System.out.println(employee);
		}
	}

	public static class Employee {
		private final int id;
		private String name;
		private String department;
		private double salary;

		public Employee(int id, String name, String department, double salary) {
			this.id = id;
			this.name = name;
			this.department = department;
			this.salary = salary;
		}

		public int getId() { return id; }
		public String getName() { return name; }
		public String getDepartment() { return department; }
		public double getSalary() { return salary; }
		public void setName(String name) { this.name = name; }
		public void setDepartment(String department) { this.department = department; }
		public void setSalary(double salary) { this.salary = salary; }

		@Override
		public String toString() {
			return id + ": " + name + " (" + department + ", " + salary + ")";
		}
	}
}
