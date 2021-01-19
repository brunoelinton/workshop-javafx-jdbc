package model.entities;

import java.io.Serializable;

public class Department implements Serializable {
	// N�MERO DE VERS�O
	private static final long serialVersionUID = 1L;

	// ATRIBUTOS DO DEPARTAMENTO
	private Integer id;
	private String name;
	
	// CONSTRUTOR PADR�O
	public Department() {
		
	}
	
	// SOBRECARGA DO CONSTRUTOR
	public Department(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	// M�TODOS GETTERS E SETTERS
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// SOBREPOSI��O / SOBRESCRITA DO M�TODO 'toString()'
	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]";
	}
	
	// M�TODOS HASHCODE E EQUALS PARA TORNAR O DEPARTAMENTO UM OBJETO COMPAR�VEL
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
