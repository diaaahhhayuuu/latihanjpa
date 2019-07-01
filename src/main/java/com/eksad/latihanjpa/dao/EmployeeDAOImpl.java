package com.eksad.latihanjpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.eksad.latihanjpa.model.Employee;

@Repository //sama kayak @service berfungsi sbg xxx disimpan di memory
public class EmployeeDAOImpl implements EmployeeDAO {

	@PersistenceContext //
	EntityManager entityManager; //berfungsi sbg menjembatani DB dg objek yg dibuat, srea otomatis akan memaping enitity kita

	@Override
	public List<Employee> getAll() {
		return entityManager.createQuery("select e from Employee e", Employee.class).getResultList(); //JPQL = memanggil e dari nama kelas (Employee), gak harus e, bisa huruf lain (ini tempat penampung)
	}

	@Override
	public Employee getById(int id) {
		return entityManager.find(Employee.class, id);
	}

	@Transactional //fungsinya yaitu ktika aplikasi kita melakukan transaksi kepada DB
	@Override
	public void save(Employee employee) {
		entityManager.persist(employee);
	}

	@Transactional
	@Override
	public void update(Employee employee) {
		entityManager.merge(employee);
		
	}

	@Transactional
	@Override
	public void delete(int id) {
		Employee employee = getById(id);
		entityManager.remove(employee);
		
	}

	@Override
	public List<Employee> getByName(String name) {
		//return entityManager.createNativeQuery("SELECT * FROM employee WHERE name LIKE ?0", Employee.class) //NATIVE QUERY -- LIKE ?0 fungsinya untuk memasukan pramameter kita. 0nya itu, parmeter ke berapa. liat yg di bawah ini. meng-query suatu DB
		return entityManager.createQuery("SELECT e FROM Employee e WHERE e.name LIKE ?0", Employee.class) // meng-query suatu objek.digenerate oleh ORM
		.setParameter(0,"%" +name+ "%")
		.getResultList();
	}

}
