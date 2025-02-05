package es.studium.proyectogestion;

import es.studium.proyectogestion.ClienteDAO;
import es.studium.proyectogestion.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClienteBajaFrame extends JFrame {
    private ClienteDAO clienteDAO;
    private JTable table;
    private DefaultTableModel tableModel;

    public ClienteBajaFrame() {
        setTitle("Baja de Clientes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clienteDAO = new ClienteDAO();
        initComponents();
        loadClientes();
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido", "Email", "Teléfono"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(220, 20, 60)); // Rojo
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(e -> eliminarCliente());
        
        JPanel panelButtons = new JPanel();
        panelButtons.add(btnEliminar);
        
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
    
    private void eliminarCliente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este cliente?", "Confirmar baja", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (clienteDAO.deleteCliente(id)) {
                    JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
                    loadClientes();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar.");
        }
    }
}
