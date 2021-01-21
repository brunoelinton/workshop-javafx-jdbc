package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao();

	// M�TODO QUE RETORNA UMA LISTA DE TODOS OS DEPARTAMENTOS
	public List<Department> findAll() {
		// RETORNANDO A LISTA DE DEPARTAMENTOS DO BANCO DE DADOS
		return dao.findAll();
	}
	
	// M�TODO QUE SALVA OU ATUALIZA UM DEPARTAMENTO
	public void saveOrUpdate(Department obj) {
		if(obj.getId() == null) {	// SE O ID DO DEPARTAMENTO FOR NULO, ENT�O ELE N�O EXISTE NO BANCO E DEVE SER SALVO
			dao.insert(obj);
		} else {					// CASO CONTR�RIO, SIGNIFICA QUE O DEPARTAMENTO EXISTE E ENT�O DEVE SER ATUALIZADO
			dao.update(obj);
		}
	}
	
	// M�TODO QUE EXCLUI UM DEPARTAMENTO
	public void remove(Department obj) {
		dao.deleteById(obj.getId());
	}
}
