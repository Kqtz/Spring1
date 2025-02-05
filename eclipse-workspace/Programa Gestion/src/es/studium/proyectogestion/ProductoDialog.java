package es.studium.proyectogestion;

import es.studium.proyectogestion.ProductoDAO;
import es.studium.proyectogestion.Producto;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductoDialog extends JDialog {
    private JTextField txtNombre, txtPrecio, txtFecha;
    private JTextArea txtDescripcion;
    private JButton btnGuardar, btnCancelar;
    private ProductoDAO productoDAO;
    private Producto producto;

    public ProductoDialog(Frame parent, String title, Producto producto) {
        super(parent, title, true);
        this.producto = producto;
        productoDAO = new ProductoDAO();
        initComponents();
        if (producto != null) {
            loadProductoData();
        }
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblNombre, gbc);

        txtNombre = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(txtNombre, gbc);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblDescripcion, gbc);

        txtDescripcion = new JTextArea(3, 15);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(scrollDescripcion, gbc);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblPrecio, gbc);

        txtPrecio = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(txtPrecio, gbc);

        JLabel lblFecha = new JLabel("Fecha (yyyy-MM-dd):");
        lblFecha.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblFecha, gbc);

        txtFecha = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(txtFecha, gbc);

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
        panelButtons.setBackground(new Color(245, 245, 245));
        panelButtons.add(btnGuardar);
        panelButtons.add(btnCancelar);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(panelButtons, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarProducto());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void loadProductoData() {
        txtNombre.setText(producto.getNombre());
        txtDescripcion.setText(producto.getDescripcion());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        txtFecha.setText(sdf.format(producto.getCreatedAt()));
    }

    private void guardarProducto() {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String precioText = txtPrecio.getText().trim();
        String fecha = txtFecha.getText().trim();

        if (nombre.isEmpty() || descripcion.isEmpty() || precioText.isEmpty() || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioText);
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un precio válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido (debe ser yyyy-MM-dd)", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (producto == null) { // Alta
            Producto p = new Producto();
            p.setNombre(nombre);
            p.setDescripcion(descripcion);
            p.setPrecio(precio);
            p.setCreatedAt(java.sql.Date.valueOf(fecha));

            if (productoDAO.insertProducto(p)) {
                JOptionPane.showMessageDialog(this, "Producto guardado correctamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else { // Modificación
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setCreatedAt(java.sql.Date.valueOf(fecha));

            if (productoDAO.updateProducto(producto)) {
                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
