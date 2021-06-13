package model.dao;

import db.DB;
import model.dao.impl.CityDaoJDBC;


public class DaoFactory {

	public static CityDao createCityDao() {
		return new CityDaoJDBC(DB.getConnection());
	}
}
