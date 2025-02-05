package es.studium.proyectogestion;

import es.studium.proyectogestion.DatabaseConnection;
import es.studium.proyectogestion.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public boolean insertProducto(Producto producto) {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, created_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setDate(4, new java.sql.Date(producto.getCreatedAt().getTime()));
            return ps.executeUpdate() > 0;
        } catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean updateProducto(Producto producto) {
        String sql = "UPDATE productos SET nombre=?, descripcion=?, precio=?, created_at=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setDate(4, new java.sql.Date(producto.getCreatedAt().getTime()));
            ps.setInt(5, producto.getId());
            return ps.executeUpdate() > 0;
        } catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteProducto(int id) {
        String sql = "DELETE FROM productos WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public Producto getProductoById(int id) {
        Producto producto = null;
        String sql = "SELECT * FROM productos WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setCreatedAt(rs.getDate("created_at"));
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return producto;
    }
    
    public List<Producto> getAllProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()){
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setCreatedAt(rs.getDate("created_at"));
                lista.add(producto);
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return lista;
    }
}
