package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dao.AlumnoDao;
import interfaz.AlumnoDaoImpl;
import modelo.Alumno;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

// Ordenamos los tests para simular un ciclo de vida completo
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AlumnoDaoTest {

    // Instancia del DAO a probar
    private AlumnoDao dao = AlumnoDaoImpl.getInstance();

    // Datos de prueba (usamos un ID/NIA que sepamos que no conflictúa, ej: 9999)
    private static final int ID_PRUEBA = 9999;
    private static final Alumno ALUMNO_TEST = new Alumno(
            "TestNombre", 
            "TestApellido", 
            "DAM", 
            "Segundo", 
            "C", 
            ID_PRUEBA, // NIA
            "H", 
            LocalDate.of(2000, 1, 1)
    );

    @Test
    @Order(1)
    void testAdd() {
        System.out.println("Probando INSERTAR (add)...");
        try {
            // Aseguramos que no exista antes de insertar (limpieza preventiva)
            dao.delete(ID_PRUEBA);

            int resultado = dao.add(ALUMNO_TEST);
            
            // Verificamos que se haya insertado 1 fila
            assertEquals(1, resultado, "El método add debería devolver 1 (fila afectada)");
            
        } catch (SQLException e) {
            fail("Error SQL en testAdd: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    void testGetById() {
        System.out.println("Probando LEER POR ID (getById)...");
        try {
            Alumno recuperado = dao.getById(ID_PRUEBA);
            
            // Verificamos que no sea nulo y que los datos coincidan
            assertNotNull(recuperado, "El alumno debería existir en la BD");
            assertEquals("TestNombre", recuperado.getNombre(), "El nombre debería coincidir");
            assertEquals(ID_PRUEBA, recuperado.getNia(), "El NIA debería coincidir");
            
        } catch (SQLException e) {
            fail("Error SQL en testGetById: " + e.getMessage());
        }
    }
    
    @Test
    @Order(3)
    void testUpdate() {
        System.out.println("Probando ACTUALIZAR (update)...");
        try {
            // Modificamos el objeto localmente
            ALUMNO_TEST.setNombre("NombreModificado");
            ALUMNO_TEST.setApellidos("ApellidoNuevo");
            
            int resultado = dao.update(ALUMNO_TEST);
            
            // Verificamos que la actualización afectó a 1 fila
            assertEquals(1, resultado, "El update debería afectar a 1 fila");
            
            // Comprobamos en BD que realmente cambió
            Alumno actualizado = dao.getById(ID_PRUEBA);
            assertEquals("NombreModificado", actualizado.getNombre());
            
        } catch (SQLException e) {
            fail("Error SQL en testUpdate: " + e.getMessage());
        }
    }

    @Test
    @Order(4)
    void testGetAll() {
        System.out.println("Probando LISTAR TODOS (getAll)...");
        try {
            List<Alumno> lista = dao.getAll();
            
            assertNotNull(lista, "La lista no debería ser nula");
            assertTrue(lista.size() > 0, "La lista debería contener al menos el alumno insertado");
            
            // Opcional: imprimir para ver visualmente
            // lista.forEach(System.out::println);
            
        } catch (SQLException e) {
            fail("Error SQL en testGetAll: " + e.getMessage());
        }
    }

    @Test
    @Order(5)
    void testDelete() {
        System.out.println("Probando ELIMINAR (delete)...");
        try {
            dao.delete(ID_PRUEBA);
            
            // Intentamos recuperarlo para asegurar que YA NO existe
            Alumno eliminado = dao.getById(ID_PRUEBA);
            assertNull(eliminado, "El alumno debería ser null después de eliminarlo");
            
        } catch (SQLException e) {
            fail("Error SQL en testDelete: " + e.getMessage());
        }
    }
}