package es.studium.proyectogestion;

import es.studium.proyectogestion.ClienteDAO;
import es.studium.proyectogestion.Cliente;
import es.studium.proyectogestion.PDFExporter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteConsultaFrame extends JFrame {
    private ClienteDAO clienteDAO;
    private JTable table;
    private DefaultTableModel tableModel;

    public ClienteConsultaFrame() {
        setTitle("Consulta de Clientes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clienteDAO = new ClienteDAO();
        initComponents();
        loadClientes();
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido", "Email", "Teléfono"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
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

    private void loadClientes() {
        List<Cliente> clientes = clienteDAO.getAllClientes();
        tableModel.setRowCount(0);
        for (Cliente c : clientes) {
            tableModel.addRow(new Object[]{c.getId(), c.getNombre(), c.getApellido(), c.getEmail(), c.getTelefono()});
        }
    }

    private void verDetalle() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            Cliente cliente = clienteDAO.getClienteById(id);
            if (cliente != null) {
                new ClienteDetalleDialog(this, "Detalle de Cliente", cliente).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para ver el detalle.");
        }
    }

    private void exportarPDF() {
        List<Cliente> clientes = clienteDAO.getAllClientes();
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay clientes para exportar.");
            return;
        }

        String[] headers = {"ID", "Nombre", "Apellido", "Email", "Teléfono"};
        List<String[]> data = new ArrayList<>();

        for (Cliente c : clientes) {
            data.add(new String[]{String.valueOf(c.getId()), c.getNombre(), c.getApellido(), c.getEmail(), c.getTelefono()});
        }

        PDFExporter.exportToPDF("Listado de Clientes", data, headers);
    }
}
