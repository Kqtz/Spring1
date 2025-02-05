package es.studium.proyectogestion;

import es.studium.proyectogestion.ClienteDAO;
import es.studium.proyectogestion.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClienteModificacionFrame extends JFrame {
    private ClienteDAO clienteDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public ClienteModificacionFrame() {
        setTitle("Modificación de Clientes");
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
        
        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBackground(new Color(70, 130, 180));
        btnModificar.setForeground(Color.WHITE);
        btnModificar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnModificar.setFocusPainted(false);
        btnModificar.addActionListener(e -> modificarCliente());
        
        JPanel panelButtons = new JPanel();
        panelButtons.add(btnModificar);
        
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
    
    private void modificarCliente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            Cliente cliente = clienteDAO.getClienteById(id);
            if (cliente != null) {
                new ClienteDialog(this, "Modificar Cliente", cliente).setVisible(true);
                loadClientes();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para modificar.");
        }
    }
}
