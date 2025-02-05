package es.studium.proyectogestion;

import es.studium.proyectogestion.ProductoDAO;
import es.studium.proyectogestion.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoConsultaFrame extends JFrame {
    private ProductoDAO productoDAO;
    private JTable table;
    private DefaultTableModel tableModel;

    public ProductoConsultaFrame() {
        setTitle("Consulta de Productos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        productoDAO = new ProductoDAO();
        initComponents();
        loadProductos();
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Descripción", "Precio", "Fecha"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnVerDetalle = new JButton("Ver Detalle");
        btnVerDetalle.addActionListener(e -> verDetalle());

        JButton btnExportar = new JButton("Exportar a PDF");
        btnExportar.addActionListener(e -> exportarPDF());

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnVerDetalle);
        btnVerDetalle.setBackground(new Color(70, 130, 180));
        btnVerDetalle.setForeground(Color.WHITE);
        btnVerDetalle.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnVerDetalle.setFocusPainted(false);
        panelButtons.add(btnExportar);
        btnExportar.setBackground(new Color(70, 130, 180));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnExportar.setFocusPainted(false);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(panelButtons, BorderLayout.SOUTH);
    }

    private void loadProductos() {
        List<Producto> productos = productoDAO.getAllProductos();
        tableModel.setRowCount(0);
        for (Producto p : productos) {
            tableModel.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getCreatedAt()
            });
        }
    }

    private void verDetalle() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            Producto producto = productoDAO.getProductoById(id);
            if (producto != null) {
                new ProductoDetalleDialog(this, "Detalle de Producto", producto).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para ver el detalle.");
        }
    }

    private void exportarPDF() {
        List<Producto> productos = productoDAO.getAllProductos();
        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos para exportar.");
            return;
        }

        String[] headers = {"ID", "Nombre", "Descripción", "Precio", "Fecha"};
        List<String[]> data = new ArrayList<>();

        for (Producto p : productos) {
            data.add(new String[]{String.valueOf(p.getId()), p.getNombre(), p.getDescripcion(), String.valueOf(p.getPrecio()), p.getCreatedAt().toString()});
        }

        PDFExporter.exportToPDF("Listado de Productos", data, headers);
    }
}