package model.services;

import java.util.List;
import java.util.ArrayList;

import model.entities.Department;

public class DepartmentService {

	public List<Department> findAll() {
		// LISTA DE DEPARTAMENTOS
		List<Department> list = new ArrayList<>();
			
		// ADICIONANDO DEPARTAMENTOS À LISTA
		list.add(new Department(1, "Books"));
		list.add(new Department(2, "Computers"));
		list.add(new Department(3, "Electronics"));
			
		return list;
	}
}
