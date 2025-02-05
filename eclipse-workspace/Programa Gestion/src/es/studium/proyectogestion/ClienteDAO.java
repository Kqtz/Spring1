package es.studium.proyectogestion;

import es.studium.proyectogestion.DatabaseConnection;
import es.studium.proyectogestion.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public boolean insertCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, apellido, email, telefono) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getTelefono());
            return ps.executeUpdate() > 0;
        } catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean updateCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre=?, apellido=?, email=?, telefono=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getTelefono());
            ps.setInt(5, cliente.getId());
            return ps.executeUpdate() > 0;
        } catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    
    public Cliente getClienteById(int id) {
        Cliente cliente = null;
        String sql = "SELECT * FROM clientes WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefono(rs.getString("telefono"));
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return cliente;
    }
    
    public List<Cliente> getAllClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()){
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefono(rs.getString("telefono"));
                lista.add(cliente);
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return lista;
    }
}
