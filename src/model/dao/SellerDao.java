package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	// CONTRACTS
	void insert(Seller obj); 		// INSERT A NEW SELLER
	void update(Seller obj);		// UPDATE AN SELLER
	void deleteById(Integer id);	// DELETE A SELLER BASED ON ID
	Seller findById(Integer id);	// FIND SOMEONE SELLER BASED ON ID
	List<Seller> findAll();			// SEARCH ALL SELLERS
	List<Seller> findByDepartment(Department department);	// FIND A SELLER BASED ON DEPARTMENT
}
