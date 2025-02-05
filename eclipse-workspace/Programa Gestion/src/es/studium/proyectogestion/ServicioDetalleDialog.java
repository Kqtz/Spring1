package es.studium.proyectogestion;

import es.studium.proyectogestion.Servicio;
import javax.swing.*;
import java.awt.*;

public class ServicioDetalleDialog extends JDialog {
    public ServicioDetalleDialog(Frame parent, String title, Servicio servicio) {
        super(parent, title, true);
        initComponents(servicio);
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents(Servicio servicio) {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("ID:"));
        JTextField txtId = new JTextField(String.valueOf(servicio.getId()));
        txtId.setEditable(false);
        panel.add(txtId);

        panel.add(new JLabel("Nombre:"));
        JTextField txtNombre = new JTextField(servicio.getNombre());
        txtNombre.setEditable(false);
        panel.add(txtNombre);

        panel.add(new JLabel("Descripción:"));
        JTextField txtDescripcion = new JTextField(servicio.getDescripcion());
        txtDescripcion.setEditable(false);
        panel.add(txtDescripcion);

        panel.add(new JLabel("Precio:"));
        JTextField txtPrecio = new JTextField(String.valueOf(servicio.getPrecio()));
        txtPrecio.setEditable(false);
        panel.add(txtPrecio);

        panel.add(new JLabel("Fecha:"));
        JTextField txtFecha = new JTextField(servicio.getCreatedAt().toString());
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
