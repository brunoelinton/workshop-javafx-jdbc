package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	// DEPENDENCY
	private Connection conn;
	
	// CONSTRUCTOR TO FORCE THE INJECTION DEPENDENCY
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {		
		// QUERY
		String query = 
				"INSERT INTO seller " +
				"(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
				"VALUES " +
				"(?, ?, ?, ?, ?)";
		// VARIABLE TO PREPARE A QUERY
		PreparedStatement st = null;
		// VARIABLE TO SAVE RESULT QUERY
		ResultSet rs = null;
		
		try {
			// MAKING QUERY
			st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			// INSERT PARÂMETERS OF QUERY
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			// EXECUTING INSERTION
			int rowsAffected = st.executeUpdate();
			
			// VARIFYING IF INSERTION WAS SUCCESSFULL
			if(rowsAffected > 0) {
				// OBTEINING KEYS OF INSERTIONS REGISTER
				rs = st.getGeneratedKeys();
				// CHEKING RESULT
				if(rs.next()) {
					// SAVING ID OF REGISTER
					int id = rs.getInt(1);
					// POPULATE FILD ID OF OBJECT
					obj.setId(id);
					// SHOWS RESULTS OF OPERATION AND ID OF REGISTER
					System.out.println("Done! Id: " + id);
				}
				// SHOW NUMBER OF ROWS AFFECTED
				System.out.println("Rows affected: " + rowsAffected);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());			
		} finally { // CLOSING RESOURCES
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Seller obj) {
		// QUERY
		String query =
				"UPDATE seller " +
				"SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " + 
				"WHERE Id = ?";
		// VARIABLE TO PREPARE A QUERY
		PreparedStatement st = null;


		try {
			// MAKING QUERY
			st = conn.prepareStatement(query);
			// INSERT PARÂMETERS OF QUERY
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());
			// EXECUTING INSERTION
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally { // CLOSING RESOURCES
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		String query =
				"DELETE FROM seller " +
				"WHERE Id = ?";
		// VARIABLE TO PREPARE A QUERY
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(query);
			st.setInt(1, id);
			st.executeUpdate();
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally { // CLOSING RESOURCES
			DB.closeStatement(st);
		}
	}
	
	/*METHOD:		findById(Integer id)	
	 *OBJECTIVE:	find a seller based on id */
	@Override
	public Seller findById(Integer id) {
		// QUERY
		String query = "SELECT seller.*, department.Name as DepName " +
						"FROM seller INNER JOIN department " +
						"ON seller.DepartmentId = department.Id " +
						"WHERE seller.Id = ?";
		// VARIABLE TO PREPARE A QUERY
		PreparedStatement st = null;
		// VARIABLE TO SAVE RESULT QUERY
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(query);	// MAKE QUERY
			st.setInt(1, id);					// INSERT PARAMETERS OF QUERY
			rs = st.executeQuery();				// EXECUTING QUERY
			
			// VERIFYING THE RESULT OF QUERY
			if(rs.next()) {
				// INITIALIZING INSTANCES OF OBJECTS IN MEMORY
				// DEPARTMENT
				Department dep = instantiateDepartment(rs);
				// SELLER
				Seller obj = instantiateSeller(rs, dep);
				return obj;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {	// CLOSING RESOURCES
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		return null;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		 return new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		return new Seller(rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"), rs.getDate("BirthDate"), rs.getDouble("BaseSalary"), dep);

	}
	
	@Override
	public List<Seller> findAll() {
		// LIST OF SELLERS
		List<Seller> sellers = new ArrayList<>();
		// COLLECTION TO SAVE UNIQUES REGISTERS OF DEPARTMENT
		Map<Integer, Department> map = new HashMap<>();
		// QUERY
		String query =
				"SELECT seller.*,department.Name as DepName " +
				"FROM seller INNER JOIN department " +
				"ON seller.DepartmentId = department.Id " +
				"ORDER BY Name";
		// VARIABLE TO PREPARE QUERY
		PreparedStatement st = null;
		// VARIABLE TO SAVE RESULT QUERY
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(query);	// MAKE A QUERY
			rs = st.executeQuery();				// EXECUTING QUERY AND SAVE RESULTS

			// VERIFYING RESULTS OF QUERY
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				// CHECKING IF THE DEPARTMENT ALREADY EXISTS OR NOT 
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				// CREATE A SELLER
				Seller obj = instantiateSeller(rs, dep);
				
				// ADD SELLERS INTO SELLER'S COLECTION
				sellers.add(obj);
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {	// CLOSING RESOURCES
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		
		return sellers;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		// LIST OF SELLERS
		List<Seller> sellers = new ArrayList<>();
		// COLLECTION TO SAVE REGISTERS OF DEPARTMENT
		Map<Integer, Department> map = new HashMap<>();
		// QUERY
		String query =
				"SELECT seller.*,department.Name as DepName " +
				"FROM seller INNER JOIN department " +
				"ON seller.DepartmentId = department.Id " +
				"WHERE DepartmentId = ? " +
				"ORDER BY Name";
		// VARIABLE TO PREPARE QUERY
		PreparedStatement st = null;
		// VARIABLE TO SAVE RESULT QUERY
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(query);	// MAKE A QUERY
			st.setInt(1, department.getId());	// INSERTING PARAMETERS OF QUERY
			rs = st.executeQuery();				// EXECUTING QUERY AND SAVE RESULTS

			// VERYFING RESULTS OF QUERY
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				// CREATE A SELLER
				Seller obj = instantiateSeller(rs, dep);
				
				// ADD SELLERS INTO SELLER'S COLECTION
				sellers.add(obj);
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {	// CLOSING RESOURCES
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		
		return sellers;
	}

}
