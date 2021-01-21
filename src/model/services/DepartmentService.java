package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao();

	// MÉTODO QUE RETORNA UMA LISTA DE TODOS OS DEPARTAMENTOS
	public List<Department> findAll() {
		// RETORNANDO A LISTA DE DEPARTAMENTOS DO BANCO DE DADOS
		return dao.findAll();
	}
	
	// MÉTODO QUE SALVA OU ATUALIZA UM DEPARTAMENTO
	public void saveOrUpdate(Department obj) {
		if(obj.getId() == null) {	// SE O ID DO DEPARTAMENTO FOR NULO, ENTÃO ELE NÃO EXISTE NO BANCO E DEVE SER SALVO
			dao.insert(obj);
		} else {					// CASO CONTRÁRIO, SIGNIFICA QUE O DEPARTAMENTO EXISTE E ENTÃO DEVE SER ATUALIZADO
			dao.update(obj);
		}
	}
	
	// MÉTODO QUE EXCLUI UM DEPARTAMENTO
	public void remove(Department obj) {
		dao.deleteById(obj.getId());
	}
}
