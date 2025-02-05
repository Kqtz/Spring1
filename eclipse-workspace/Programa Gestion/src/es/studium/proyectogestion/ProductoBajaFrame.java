package es.studium.proyectogestion;

import es.studium.proyectogestion.ProductoDAO;
import es.studium.proyectogestion.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductoBajaFrame extends JFrame {
    private ProductoDAO productoDAO;
    private JTable table;
    private DefaultTableModel tableModel;

    public ProductoBajaFrame() {
        setTitle("Baja de Productos");
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

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(220, 20, 60)); // Rojo
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(e -> eliminarProducto());

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnEliminar);

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

    private void eliminarProducto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este producto?", "Confirmar baja", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (productoDAO.deleteProducto(id)) {
                    JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
                    loadProductos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar.");
        }
    }
}
