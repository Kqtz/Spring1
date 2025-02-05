package es.studium.proyectogestion;

import es.studium.proyectogestion.ServicioDAO;
import es.studium.proyectogestion.Servicio;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioConsultaFrame extends JFrame {
    private ServicioDAO servicioDAO;
    private JTable table;
    private DefaultTableModel tableModel;

    public ServicioConsultaFrame() {
        setTitle("Consulta de Servicios");
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

    private void loadServicios() {
        List<Servicio> servicios = servicioDAO.getAllServicios();
        tableModel.setRowCount(0);
        for (Servicio s : servicios) {
            tableModel.addRow(new Object[]{
                s.getId(), s.getNombre(), s.getDescripcion(), s.getPrecio(), s.getCreatedAt()
            });
        }
    }

    private void verDetalle() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            Servicio servicio = servicioDAO.getServicioById(id);
            if (servicio != null) {
                new ServicioDetalleDialog(this, "Detalle de Servicio", servicio).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio para ver el detalle.");
        }
    }

    private void exportarPDF() {
        List<Servicio> servicios = servicioDAO.getAllServicios();
        if (servicios.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay servicios para exportar.");
            return;
        }

        String[] headers = {"ID", "Nombre", "Descripción", "Precio", "Fecha"};
        List<String[]> data = new ArrayList<>();

        for (Servicio s : servicios) {
            data.add(new String[]{String.valueOf(s.getId()), s.getNombre(), s.getDescripcion(), String.valueOf(s.getPrecio()), s.getCreatedAt().toString()});
        }

        PDFExporter.exportToPDF("Listado de Servicios", data, headers);
    }
}