package controlador;

import java.sql.SQLException;
import java.time.LocalDate;

import base.Menu;
import dao.AlumnoDao;
import interfaz.AlumnoDaoImpl;
import modelo.Alumno;

public class AppPrueba {
	public static void main(String[] args) {
		Menu m = new Menu();
		m.init();
	}

	public static void testDao() {
		AlumnoDao dao = AlumnoDaoImpl.getInstance();

		Alumno al = new Alumno("Ismael", "Ben Jaddi", "Bachillerato", "segundo", "A", 5678, "H",
				LocalDate.of(2007, 05, 8));

		try {
			int i = dao.add(al);
			System.out.println("El numero de alumnos insertados has sido:" + i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			if (dao.getAll() == null) {
				System.out.println("No hay alumnos registrados");
			} else {
				for (Alumno alumno : dao.getAll()) {
					System.out.println(alumno);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Alumno a1 = dao.getById(123);
			System.out.println(" " + a1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			dao.delete(5678);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
