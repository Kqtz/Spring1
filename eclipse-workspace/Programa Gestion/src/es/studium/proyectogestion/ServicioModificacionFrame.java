package es.studium.proyectogestion;

import es.studium.proyectogestion.ServicioDAO;
import es.studium.proyectogestion.Servicio;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ServicioModificacionFrame extends JFrame {
    private ServicioDAO servicioDAO;
    private JTable table;
    private DefaultTableModel tableModel;

    public ServicioModificacionFrame() {
        setTitle("Modificación de Servicios");
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

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBackground(new Color(70, 130, 180));
        btnModificar.setForeground(Color.WHITE);
        btnModificar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnModificar.setFocusPainted(false);
        btnModificar.addActionListener(e -> modificarServicio());

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnModificar);

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

    private void modificarServicio() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            Servicio servicio = servicioDAO.getServicioById(id);
            if (servicio != null) {
                new ServicioDialog(this, "Modificar Servicio", servicio).setVisible(true);
                loadServicios();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio para modificar.");
        }
    }
}
