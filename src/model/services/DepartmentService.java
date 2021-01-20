package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao();

	public List<Department> findAll() {
		// RETORNANDO A LISTA DE DEPARTAMENTOS DO BANCO DE DADOS
		return dao.findAll();
	}
}
