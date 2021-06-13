package model.services;

import java.util.ArrayList;
import java.util.List;

import model.dao.CityDao;
import model.dao.DaoFactory;
import model.entities.City;

public class CityService {

	private CityDao dao = DaoFactory.createCityDao();
	
	public List<City> findAll(){
		return dao.findAll();
	}
	
	public int saveOrUpdate(City city) {
		City c = dao.findById(city.getId());
		if(c != null) {
			dao.update(city);
			return 1;
		}else {
			dao.insert(city);
			return 2;
		}
	}
	
	public int deleteCity(int cod) {
		City c = dao.findById(cod);
		if(c!= null) {
			dao.deleteById(cod);
			return 1;
		}else {
			return 2;
		}
		
	}
}
