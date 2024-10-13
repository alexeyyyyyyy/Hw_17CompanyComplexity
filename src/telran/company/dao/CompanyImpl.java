package telran.company.dao;

import java.util.function.Predicate;

import telran.company.model.Employee;
import telran.company.model.SalesManager;

public class CompanyImpl implements Company {
	String name;
	Employee[] employees;
	int size;

	public CompanyImpl(int capacity) {
		employees = new Employee[capacity];
	}

	@Override // O(n) - linear 
	public boolean addEmployee(Employee employee) {  
		if (employee == null || size == employees.length || findEmployee(employee.getId()) != null) {
			return false;
		}
		employees[size] = employee;
		size++;
		return true;
	}

	@Override // O(n) - linear 
	public Employee removeEmployee(int id) {  
		for (int i = 0; i < size; i++) {
			if (employees[i].getId() == id) {
				Employee victim = employees[i];
				employees[i] = employees[size - 1];
				employees[size - 1] = null;
				size--;
				return victim;
			}
		}
		return null;
	}

	@Override  // O(n) - linear 
	public Employee findEmployee(int id) {
		for (int i = 0; i < size; i++) {
			if (employees[i].getId() == id) {
				return employees[i];
			}
		}
		return null;
	}

	@Override // O(n) - linear 
	public double totalSalary() { 
		double sum = 0;
		for (int i = 0; i < size; i++) {
			sum += employees[i].calcSalary();
		}
		return sum;
	}

	@Override // O(1) - const 
	public double averageSalary() { 
		return totalSalary() / size;
	}

	@Override // O(n) - linear 
	public double totalSales() {
		double sum = 0;

		for (int i = 0; i < size; i++) { 
			if (employees[i] instanceof SalesManager) {
//				SalesManager sm = (SalesManager) employees[i];
//				sum+= sm.getSalesValue();
				sum += ((SalesManager) employees[i]).getSalesValue();
			}
		}

		return sum;
	}

	@Override //O(1) - const
	public int size() {
		return size;
	}

	@Override  //O(n) - linear
	public void printEmployees() {
		for (int i = 0; i < size; i++) {
			System.out.println(employees[i]);
		}

	}

	@Override // O(n) - linear 
	public Employee[] findEmployeesHoursGreateThan(int hours) {
		return findEmployeesByPredicate(e -> e.getHours() >= hours);
	}

	@Override // O(n) - linear 
	public Employee[] findEmployeesSalaryBetween(double min, double max) {
//		Predicate<Employee> predicate = new Predicate<Employee>() {
//
//			@Override
//			public boolean test(Employee t) {
//				return t.calcSalary() >= min && t.calcSalary() < max;
//			}
//		};
		return findEmployeesByPredicate(t -> t.calcSalary() >= min && t.calcSalary() < max);
	}

	private Employee[] findEmployeesByPredicate(Predicate<Employee> predicate) { // O(n) - linear 
		int count = 0;
		for (int i = 0; i < size; i++) {
			if (predicate.test(employees[i])) {
				count++;
			}
		}
		Employee[] res = new Employee[count];
		for (int i = 0, j = 0; j < res.length; i++) {
			if (predicate.test(employees[i])) {
				res[j++] = employees[i];
			}
		}
		return res;
	}

}