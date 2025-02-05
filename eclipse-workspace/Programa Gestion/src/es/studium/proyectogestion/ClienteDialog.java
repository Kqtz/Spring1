package es.studium.proyectogestion;

import es.studium.proyectogestion.ClienteDAO;
import es.studium.proyectogestion.Cliente;
import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class ClienteDialog extends JDialog {
    private JTextField txtNombre, txtApellido, txtEmail, txtTelefono;
    private JButton btnGuardar, btnCancelar;
    private ClienteDAO clienteDAO;
    private Cliente cliente; // null = Alta; no null = Modificación

    public ClienteDialog(Frame parent, String title, Cliente cliente) {
        super(parent, title, true);
        this.cliente = cliente;
        clienteDAO = new ClienteDAO();
        initComponents();
        if (cliente != null) {
            loadClienteData();
        }
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(250, 250, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblNombre, gbc);

        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(txtNombre, gbc);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblApellido, gbc);

        txtApellido = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtApellido, gbc);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblEmail, gbc);

        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(txtEmail, gbc);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblTelefono, gbc);

        txtTelefono = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(txtTelefono, gbc);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(70, 130, 180));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnGuardar.setFocusPainted(false);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(220, 20, 60));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnCancelar.setFocusPainted(false);

        JPanel panelButtons = new JPanel();
        panelButtons.setBackground(new Color(250, 250, 250));
        panelButtons.add(btnGuardar);
        panelButtons.add(btnCancelar);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(panelButtons, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarCliente());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void loadClienteData() {
        txtNombre.setText(cliente.getNombre());
        txtApellido.setText(cliente.getApellido());
        txtEmail.setText(cliente.getEmail());
        txtTelefono.setText(cliente.getTelefono());
    }

    private void guardarCliente() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y Apellido son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Correo electrónico inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidPhone(telefono)) {
            JOptionPane.showMessageDialog(this, "Número de teléfono inválido (solo dígitos, mínimo 9 caracteres)", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cliente == null) { // Alta
            Cliente c = new Cliente();
            c.setNombre(nombre);
            c.setApellido(apellido);
            c.setEmail(email);
            c.setTelefono(telefono);
            if (clienteDAO.insertCliente(c)) {
                JOptionPane.showMessageDialog(this, "Cliente guardado correctamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else { // Modificación
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setEmail(email);
            cliente.setTelefono(telefono);
            if (clienteDAO.updateCliente(cliente)) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para validar correo
    private boolean isValidEmail(String email) {
        return Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email);
    }

    // Método para validar número de teléfono
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{9,15}"); // Solo permite números con entre 9 y 15 caracteres
    }
}
