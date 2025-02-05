package es.studium.proyectogestion;

import es.studium.proyectogestion.Usuario;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private Usuario usuario;

    public MainFrame(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Proyecto Recuperacion - Menú Principal (" + usuario.getRole() + ")");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(240, 248, 255)); 
        menuBar.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Menú Clientes
        JMenu menuClientes = new JMenu("Clientes");
        customizeMenu(menuClientes);
        JMenuItem miAltaCliente = new JMenuItem("Alta Cliente");
        customizeMenuItem(miAltaCliente);
        miAltaCliente.addActionListener(e -> new ClienteDialog(this, "Alta Cliente", null).setVisible(true));

        JMenuItem miBajaCliente = new JMenuItem("Baja Cliente");
        customizeMenuItem(miBajaCliente);
        miBajaCliente.addActionListener(e -> new ClienteBajaFrame().setVisible(true));

        JMenuItem miModificacionCliente = new JMenuItem("Modificación Cliente");
        customizeMenuItem(miModificacionCliente);
        miModificacionCliente.addActionListener(e -> new ClienteModificacionFrame().setVisible(true));

        JMenuItem miConsultaCliente = new JMenuItem("Consulta Cliente");
        customizeMenuItem(miConsultaCliente);
        miConsultaCliente.addActionListener(e -> new ClienteConsultaFrame().setVisible(true));

        menuClientes.add(miAltaCliente);
        if (usuario.getRole().equals("admin")) {
            menuClientes.add(miBajaCliente);
            menuClientes.add(miModificacionCliente);
            menuClientes.add(miConsultaCliente);
        }
        menuBar.add(menuClientes);


        JMenu menuProductos = new JMenu("Productos");
        customizeMenu(menuProductos);
        JMenuItem miAltaProducto = new JMenuItem("Alta Producto");
        customizeMenuItem(miAltaProducto);
        miAltaProducto.addActionListener(e -> new ProductoDialog(this, "Alta Producto", null).setVisible(true));

        JMenuItem miBajaProducto = new JMenuItem("Baja Producto");
        customizeMenuItem(miBajaProducto);
        miBajaProducto.addActionListener(e -> new ProductoBajaFrame().setVisible(true));

        JMenuItem miModificacionProducto = new JMenuItem("Modificación Producto");
        customizeMenuItem(miModificacionProducto);
        miModificacionProducto.addActionListener(e -> new ProductoModificacionFrame().setVisible(true));

        JMenuItem miConsultaProducto = new JMenuItem("Consulta Producto");
        customizeMenuItem(miConsultaProducto);
        miConsultaProducto.addActionListener(e -> new ProductoConsultaFrame().setVisible(true));

        menuProductos.add(miAltaProducto);
        if (usuario.getRole().equals("admin")) {
            menuProductos.add(miBajaProducto);
            menuProductos.add(miModificacionProducto);
            menuProductos.add(miConsultaProducto);
        }
        menuBar.add(menuProductos);

        // Menú Servicios
        JMenu menuServicios = new JMenu("Servicios");
        customizeMenu(menuServicios);
        JMenuItem miAltaServicio = new JMenuItem("Alta Servicio");
        customizeMenuItem(miAltaServicio);
        miAltaServicio.addActionListener(e -> new ServicioDialog(this, "Alta Servicio", null).setVisible(true));

        JMenuItem miBajaServicio = new JMenuItem("Baja Servicio");
        customizeMenuItem(miBajaServicio);
        miBajaServicio.addActionListener(e -> new ServicioBajaFrame().setVisible(true));

        JMenuItem miModificacionServicio = new JMenuItem("Modificación Servicio");
        customizeMenuItem(miModificacionServicio);
        miModificacionServicio.addActionListener(e -> new ServicioModificacionFrame().setVisible(true));

        JMenuItem miConsultaServicio = new JMenuItem("Consulta Servicio");
        customizeMenuItem(miConsultaServicio);
        miConsultaServicio.addActionListener(e -> new ServicioConsultaFrame().setVisible(true));

        menuServicios.add(miAltaServicio);
        if (usuario.getRole().equals("admin")) {
            menuServicios.add(miBajaServicio);
            menuServicios.add(miModificacionServicio);
            menuServicios.add(miConsultaServicio);
        }
        menuBar.add(menuServicios);

        setJMenuBar(menuBar);
    }


    private void customizeMenu(JMenu menu) {
        menu.setForeground(new Color(70, 130, 180)); 
        menu.setFont(new Font("SansSerif", Font.BOLD, 14));
    }

    private void customizeMenuItem(JMenuItem item) {
        item.setBackground(new Color(240, 248, 255)); 
        item.setForeground(new Color(70, 130, 180));
        item.setFont(new Font("SansSerif", Font.PLAIN, 14));
    }
}