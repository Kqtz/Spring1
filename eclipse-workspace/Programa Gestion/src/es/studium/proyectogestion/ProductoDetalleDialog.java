package es.studium.proyectogestion;

import es.studium.proyectogestion.Producto;
import javax.swing.*;
import java.awt.*;

public class ProductoDetalleDialog extends JDialog {
    public ProductoDetalleDialog(Frame parent, String title, Producto producto) {
        super(parent, title, true);
        initComponents(producto);
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents(Producto producto) {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("ID:"));
        JTextField txtId = new JTextField(String.valueOf(producto.getId()));
        txtId.setEditable(false);
        panel.add(txtId);

        panel.add(new JLabel("Nombre:"));
        JTextField txtNombre = new JTextField(producto.getNombre());
        txtNombre.setEditable(false);
        panel.add(txtNombre);

        panel.add(new JLabel("Descripción:"));
        JTextField txtDescripcion = new JTextField(producto.getDescripcion());
        txtDescripcion.setEditable(false);
        panel.add(txtDescripcion);

        panel.add(new JLabel("Precio:"));
        JTextField txtPrecio = new JTextField(String.valueOf(producto.getPrecio()));
        txtPrecio.setEditable(false);
        panel.add(txtPrecio);

        panel.add(new JLabel("Fecha:"));
        JTextField txtFecha = new JTextField(producto.getCreatedAt().toString());
        txtFecha.setEditable(false);
        panel.add(txtFecha);

        getContentPane().add(panel, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel panelButton = new JPanel();
        panelButton.add(btnCerrar);
        getContentPane().add(panelButton, BorderLayout.SOUTH);
    }
}
