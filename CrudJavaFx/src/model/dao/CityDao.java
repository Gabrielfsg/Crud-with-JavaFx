package model.dao;

import java.util.List;

import model.entities.City;

public interface CityDao {

	void insert(City obj);
	void update(City obj);
	void deleteById(Integer id);
	City findById(Integer id);
	List<City> findAll();
}
