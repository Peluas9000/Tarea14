package interfaz;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.AlumnoDao;
import dao.MyDataSource;
import modelo.Alumno;

public class AlumnoDaoImpl implements AlumnoDao {
	private static AlumnoDaoImpl instance;

	static {
		instance = new AlumnoDaoImpl();
	}

	private AlumnoDaoImpl() {
	}

	public static AlumnoDaoImpl getInstance() {
		return instance;
	}

	@Override
	public int add(Alumno alumno) throws SQLException {
		String slq = "INSERT INTO alumno(nia,nombre,apellidos,genero,fecha_nacimiento,ciclo,curso,grupo) VALUES (?,?,?,?,?,?,?,?)";

		int result;

		try (Connection conn = MyDataSource.getConnection(); PreparedStatement pst = conn.prepareStatement(slq)) {

			pst.setInt(1, alumno.getNia());
			pst.setString(2, alumno.getNombre());
			pst.setString(3, alumno.getApellidos());
			pst.setString(4, alumno.getGenero());
			pst.setDate(5, Date.valueOf(alumno.getFecha_nacimiento()));
			pst.setString(6, alumno.getCiclo());
			pst.setString(7, alumno.getCurso());
			pst.setString(8, alumno.getGrupo());

			result = pst.executeUpdate();
		}

		return result;
	}

	
	@Override
	public List<Alumno> getAll() throws SQLException {

		String sql = "SELECT * FROM alumno";
		List<Alumno> result = new ArrayList();

		try (Connection conn = MyDataSource.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery()) {

			Alumno al;

			while (rs.next()) {
				al = new Alumno();
				al.setNia(rs.getInt("nia"));
				al.setNombre(rs.getString("nombre"));
				al.setApellidos(rs.getString("apellidos"));
				al.setGenero(rs.getString("genero"));
				al.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
				al.setCiclo(rs.getString("ciclo"));
				al.setCurso(rs.getString("curso"));
				al.setGrupo(rs.getString("grupo"));

				result.add(al);
			}
		}

		return result;
	}

	
	@Override
	public Alumno getById(int id) throws SQLException {
		String sql = "SELECT * FROM alumno WHERE nia = ?";
		Alumno al = null;

		try (Connection conn = MyDataSource.getConnection(); 
		     PreparedStatement pst = conn.prepareStatement(sql)) {
			
			pst.setInt(1, id);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					al = new Alumno();
					al.setNia(rs.getInt("nia"));
					al.setNombre(rs.getString("nombre"));
					al.setApellidos(rs.getString("apellidos"));
					al.setGenero(rs.getString("genero"));
					//  convertir sql.Date a LocalDate
					al.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
					al.setCiclo(rs.getString("ciclo"));
					al.setCurso(rs.getString("curso"));
					al.setGrupo(rs.getString("grupo"));
				}
			}
		}
		return al;
	}

	@Override
	public int update(Alumno al) throws SQLException {
		// Actualizamos todos los campos excepto el ID (nia), que usamos en el WHERE
		String sql = "UPDATE alumno SET nombre=?, apellidos=?, genero=?, fecha_nacimiento=?, ciclo=?, curso=?, grupo=? WHERE nia=?";
		int result = 0;

		try (Connection conn = MyDataSource.getConnection(); 
		     PreparedStatement pst = conn.prepareStatement(sql)) {

			pst.setString(1, al.getNombre());
			pst.setString(2, al.getApellidos());
			pst.setString(3, al.getGenero());
			pst.setDate(4, Date.valueOf(al.getFecha_nacimiento()));
			pst.setString(5, al.getCiclo());
			pst.setString(6, al.getCurso());
			pst.setString(7, al.getGrupo());
			
			// El último parámetro es el NIA para la condición WHERE
			pst.setInt(8, al.getNia());

			result = pst.executeUpdate();
		}
		
		return result;
	}

	@Override
	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM alumno WHERE nia = ?";

		try (Connection conn = MyDataSource.getConnection(); 
		     PreparedStatement pst = conn.prepareStatement(sql)) {
			
			pst.setInt(1, id);
			pst.executeUpdate();
		}
	}
	

}
