package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {
	
	private SellerDao dao = DaoFactory.createSellerDao();

	// M�TODO QUE RETORNA UMA LISTA DE TODOS OS VENDEDORS
	public List<Seller> findAll() {
		// RETORNANDO A LISTA DE VENDEDORS DO BANCO DE DADOS
		return dao.findAll();
	}
	
	// M�TODO QUE SALVA OU ATUALIZA UM VENDEDOR
	public void saveOrUpdate(Seller obj) {
		if(obj.getId() == null) {	// SE O ID DO VENDEDOR FOR NULO, ENT�O ELE N�O EXISTE NO BANCO E DEVE SER SALVO
			dao.insert(obj);
		} else {					// CASO CONTR�RIO, SIGNIFICA QUE O VENDEDOR EXISTE E ENT�O DEVE SER ATUALIZADO
			dao.update(obj);
		}
	}
	
	// M�TODO QUE EXCLUI UM VENDEDOR
	public void remove(Seller obj) {
		dao.deleteById(obj.getId());
	}
}
