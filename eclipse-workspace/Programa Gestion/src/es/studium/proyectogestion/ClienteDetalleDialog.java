package es.studium.proyectogestion;

import es.studium.proyectogestion.Cliente;

import javax.swing.*;
import java.awt.*;

public class ClienteDetalleDialog extends JDialog {
    public ClienteDetalleDialog(Frame parent, String title, Cliente cliente) {
        super(parent, title, true);
        initComponents(cliente);
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents(Cliente cliente) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblId = new JLabel("ID:");
        lblId.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblId, gbc);

        JTextField txtId = new JTextField(String.valueOf(cliente.getId()), 15);
        txtId.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(txtId, gbc);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblNombre, gbc);

        JTextField txtNombre = new JTextField(cliente.getNombre(), 15);
        txtNombre.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtNombre, gbc);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblApellido, gbc);

        JTextField txtApellido = new JTextField(cliente.getApellido(), 15);
        txtApellido.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(txtApellido, gbc);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblEmail, gbc);

        JTextField txtEmail = new JTextField(cliente.getEmail(), 15);
        txtEmail.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(txtEmail, gbc);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(lblTelefono, gbc);

        JTextField txtTelefono = new JTextField(cliente.getTelefono(), 15);
        txtTelefono.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(txtTelefono, gbc);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(220, 20, 60)); // Rojo
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnCerrar.setFocusPainted(false);
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelButton = new JPanel();
        panelButton.setBackground(new Color(245, 245, 245));
        panelButton.add(btnCerrar);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(panelButton, BorderLayout.SOUTH);
    }
}
