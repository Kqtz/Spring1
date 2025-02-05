package es.studium.proyectogestion;

import es.studium.proyectogestion.DatabaseConnection;
import es.studium.proyectogestion.Servicio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO {

    public boolean insertServicio(Servicio servicio) {
        String sql = "INSERT INTO servicios (nombre, descripcion, precio, created_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, servicio.getNombre());
            ps.setString(2, servicio.getDescripcion());
            ps.setDouble(3, servicio.getPrecio());
            ps.setDate(4, new java.sql.Date(servicio.getCreatedAt().getTime()));
            return ps.executeUpdate() > 0;
        } catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean updateServicio(Servicio servicio) {
        String sql = "UPDATE servicios SET nombre=?, descripcion=?, precio=?, created_at=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, servicio.getNombre());
            ps.setString(2, servicio.getDescripcion());
            ps.setDouble(3, servicio.getPrecio());
            ps.setDate(4, new java.sql.Date(servicio.getCreatedAt().getTime()));
            ps.setInt(5, servicio.getId());
            return ps.executeUpdate() > 0;
        } catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteServicio(int id) {
        String sql = "DELETE FROM servicios WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public Servicio getServicioById(int id) {
        Servicio servicio = null;
        String sql = "SELECT * FROM servicios WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                servicio = new Servicio();
                servicio.setId(rs.getInt("id"));
                servicio.setNombre(rs.getString("nombre"));
                servicio.setDescripcion(rs.getString("descripcion"));
                servicio.setPrecio(rs.getDouble("precio"));
                servicio.setCreatedAt(rs.getDate("created_at"));
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return servicio;
    }
    
    public List<Servicio> getAllServicios() {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicios";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()){
                Servicio servicio = new Servicio();
                servicio.setId(rs.getInt("id"));
                servicio.setNombre(rs.getString("nombre"));
                servicio.setDescripcion(rs.getString("descripcion"));
                servicio.setPrecio(rs.getDouble("precio"));
                servicio.setCreatedAt(rs.getDate("created_at"));
                lista.add(servicio);
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return lista;
    }
}
