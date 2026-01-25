package dao;

import java.sql.SQLException;
import java.util.List;

import modelo.Alumno;

public interface AlumnoDao {
	int add(Alumno alumno) throws SQLException;

	Alumno getById(int id) throws SQLException;

	List<Alumno> getAll() throws SQLException;
	
	int update (Alumno al) throws SQLException;
	
	void delete(int id) throws SQLException;
}
