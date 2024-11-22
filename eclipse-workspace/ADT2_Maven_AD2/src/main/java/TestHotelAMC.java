
public class TestHotelAMC {
    public static void main(String[] args) {
        // Crear tres clientes
        int id1 = ClientePersistenciaAMC.crearCliente("Juan", "Pérez", "juan@gmail.com", "12345678A", "clave123");
        int id2 = ClientePersistenciaAMC.crearCliente("María", "López", "maria@gmail.com", "23456789B", "clave456");
        int id3 = ClientePersistenciaAMC.crearCliente("Álvaro", "Moreno Caballero", "alvaro@gmail.com", "34567890C", "clave789");

        // Leer un cliente
        System.out.println("Email del cliente 1: " + ClientePersistenciaAMC.leerCliente(id1, "email"));

        // Actualizar un cliente
        boolean actualizado = ClientePersistenciaAMC.actualizarCliente(id2, "nombre", "María Actualizada");
        System.out.println("Cliente actualizado: " + actualizado);

        // Eliminar un cliente
        boolean eliminado = ClientePersistenciaAMC.eliminarCliente(id3);
        System.out.println("Cliente eliminado: " + eliminado);
    }
}
