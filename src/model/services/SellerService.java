package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {
	
	private SellerDao dao = DaoFactory.createSellerDao();

	// MÉTODO QUE RETORNA UMA LISTA DE TODOS OS VENDEDORS
	public List<Seller> findAll() {
		// RETORNANDO A LISTA DE VENDEDORS DO BANCO DE DADOS
		return dao.findAll();
	}
	
	// MÉTODO QUE SALVA OU ATUALIZA UM VENDEDOR
	public void saveOrUpdate(Seller obj) {
		if(obj.getId() == null) {	// SE O ID DO VENDEDOR FOR NULO, ENTÃO ELE NÃO EXISTE NO BANCO E DEVE SER SALVO
			dao.insert(obj);
		} else {					// CASO CONTRÁRIO, SIGNIFICA QUE O VENDEDOR EXISTE E ENTÃO DEVE SER ATUALIZADO
			dao.update(obj);
		}
	}
	
	// MÉTODO QUE EXCLUI UM VENDEDOR
	public void remove(Seller obj) {
		dao.deleteById(obj.getId());
	}
}
