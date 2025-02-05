package es.studium.proyectogestion;

import es.studium.proyectogestion.UsuarioDAO;
import es.studium.proyectogestion.Usuario;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private UsuarioDAO usuarioDAO;

    public LoginFrame() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setTitle("Login - Gestión de Empresa");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        usuarioDAO = new UsuarioDAO();

        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245)); 
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblUsername = new JLabel("Usuario:");
        lblUsername.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblUsername, gbc);
        
        txtUsername = new JTextField();
        txtUsername.setPreferredSize(new Dimension(200, 35)); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(txtUsername, gbc);
        
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblPassword, gbc);
        
        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(txtPassword, gbc);
        
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(50, 40)); 
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnLogin, gbc);
        
        getContentPane().add(panel, BorderLayout.CENTER);
        
        btnLogin.addActionListener(e -> login());
    }

    private void login() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        Usuario usuario = usuarioDAO.login(username, password);
        if (usuario != null) {
            new MainFrame(usuario).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrecta", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}