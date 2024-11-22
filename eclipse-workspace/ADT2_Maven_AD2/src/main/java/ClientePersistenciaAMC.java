import java.sql.*;

public class ClientePersistenciaAMC {
    private static final String URL = "jdbc:mysql://localhost:3306/hotelAMC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static int crearCliente(String nombre, String apellidos, String email, String dni, String clave) {
        String query = "INSERT INTO clienteAMC (nombre, apellidos, email, dni, clave) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setString(3, email);
            ps.setString(4, dni);
            ps.setString(5, clave);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String leerCliente(int idCliente, String campo) {
        String query = "SELECT " + campo + " FROM clienteAMC WHERE idCliente = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean actualizarCliente(int idCliente, String campo, String nuevoValor) {
        String query = "UPDATE clienteAMC SET " + campo + " = ? WHERE idCliente = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nuevoValor);
            ps.setInt(2, idCliente);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean eliminarCliente(int idCliente) {
        String query = "DELETE FROM clienteAMC WHERE idCliente = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idCliente);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
