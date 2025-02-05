package es.studium.proyectogestion;

import es.studium.proyectogestion.ServicioDAO;
import es.studium.proyectogestion.Servicio;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ServicioBajaFrame extends JFrame {
    private ServicioDAO servicioDAO;
    private JTable table;
    private DefaultTableModel tableModel;

    public ServicioBajaFrame() {
        setTitle("Baja de Servicios");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        servicioDAO = new ServicioDAO();
        initComponents();
        loadServicios();
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
        btnEliminar.addActionListener(e -> eliminarServicio());

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnEliminar);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(panelButtons, BorderLayout.SOUTH);
    }

    private void loadServicios() {
        List<Servicio> servicios = servicioDAO.getAllServicios();
        tableModel.setRowCount(0);
        for (Servicio s : servicios) {
            tableModel.addRow(new Object[]{
                s.getId(), s.getNombre(), s.getDescripcion(), s.getPrecio(), s.getCreatedAt()
            });
        }
    }

    private void eliminarServicio() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este servicio?", "Confirmar baja", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (servicioDAO.deleteServicio(id)) {
                    JOptionPane.showMessageDialog(this, "Servicio eliminado correctamente.");
                    loadServicios();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el servicio.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio para eliminar.");
        }
    }
}

