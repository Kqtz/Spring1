package es.studium.proyectogestion;

import es.studium.proyectogestion.ProductoDAO;
import es.studium.proyectogestion.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductoModificacionFrame extends JFrame {
    private ProductoDAO productoDAO;
    private JTable table;
    private DefaultTableModel tableModel;

    public ProductoModificacionFrame() {
        setTitle("Modificación de Productos");
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

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBackground(new Color(70, 130, 180));
        btnModificar.setForeground(Color.WHITE);
        btnModificar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnModificar.setFocusPainted(false);
        btnModificar.addActionListener(e -> modificarProducto());

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnModificar);

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

    private void modificarProducto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            Producto producto = productoDAO.getProductoById(id);
            if (producto != null) {
                new ProductoDialog(this, "Modificar Producto", producto).setVisible(true);
                loadProductos();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para modificar.");
        }
    }
}
