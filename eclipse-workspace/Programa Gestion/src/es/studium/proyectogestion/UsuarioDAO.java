package es.studium.proyectogestion;

import es.studium.proyectogestion.DatabaseConnection;
import es.studium.proyectogestion.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {


    public Usuario login(String username, String password) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setRole(rs.getString("role"));
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return usuario;
    }
    
    public boolean insertUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getRole());
            return ps.executeUpdate() > 0;
        } catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean updateUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET username=?, password=?, role=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getRole());
            ps.setInt(4, usuario.getId());
            return ps.executeUpdate() > 0;
        } catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    
    public Usuario getUsuarioById(int id) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setRole(rs.getString("role"));
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return usuario;
    }
    
    public List<Usuario> getAllUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setRole(rs.getString("role"));
                lista.add(usuario);
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return lista;
    }
}
