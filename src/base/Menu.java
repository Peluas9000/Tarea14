package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.StringTokenizer;

import dao.AlumnoDao;
import interfaz.AlumnoDaoImpl;
import modelo.Alumno;

public class Menu {
	private AlumnoDao alumnoDao;

	public void init( ) {
		Scanner entrada=new Scanner(System.in);
		int opcion;
		do {
			menu();
			opcion = entrada.nextInt();
			switch (opcion) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				insertar();

				break;
			case 4:
				break;
			case 5:
				break;
			case 0:
				System.out.println("Saliendo del programa");

				break;
			default:
				System.err.print("Has escogido un numero  no valido entre 0 -5");
			}

		} while (opcion != 0);

	}

	public Menu() {
		super();
		alumnoDao = AlumnoDaoImpl.getInstance();
	}

	public void menu() {

		System.out.println("SISTEMA DE GESTIÓN DE EMPLEADOS");
		System.out.println("____----------------------------------____");
		System.out.println("-> Introduzca una opción de entre las siguientes: \n");
		System.out.println("0: Salir");
		System.out.println("1: Listar todos los empleados");
		System.out.println("2: Listar un empleado por su ID");
		System.out.println("3: Insertar un nuevo empleado");
		System.out.println("4: Actualizar un empleado");
		System.out.println("5: Eliminar un empleado");
		System.out.print("\nOpción: ");
	}

	public void insertar() {
		Scanner entrada = new Scanner(System.in);
		System.out.println("Empecemos coknk la inserccion de un alumno");

		System.out.println("Dime el nia del alumno");
		int nia = entrada.nextInt();

		System.out.println("Dime el nombre del alumno sin los apellidos");
		String nombre = entrada.nextLine();

		System.out.println("Dime el apellido del alumnos");
		String apellido = entrada.next();

		System.out.println("Dime el año de  fecha de nacimiento del alumno");
		int anio = entrada.nextInt();

		System.out.println("Dime el mes de  fecha de nacimiento del alumno");
		int mes = entrada.nextInt();

		System.out.println("Dime el dia de  fecha de nacimiento del alumno");
		int dia = entrada.nextInt();

		System.out.println("Dime el ciclo que esta cursando el alumno");
		String ciclo = entrada.next();

		System.out.println("Dime el curso del alumno");
		String curso = entrada.next();

		System.out.println("Dime en que grupo del curso esta el alumno");
		String grupo = entrada.next();

		System.out.println("Dime el genero del alumno");
		String genero = entrada.next();

		Alumno al = new Alumno(nombre, apellido, ciclo, curso, grupo, nia, genero, LocalDate.of(anio, mes, dia));
		try {
			alumnoDao.add(al);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
