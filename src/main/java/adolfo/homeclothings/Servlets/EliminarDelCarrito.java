package adolfo.homeclothings.Servlets;

import adolfo.homeclothings.Conexion.BD;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/EliminarDelCarrito")
public class EliminarDelCarrito extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener los parámetros enviados por el formulario
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));

        // Obtener el idUsuario desde la sesión
        HttpSession session = request.getSession(false); // Obtener la sesión sin crear una nueva si no existe
        Integer idUsuario = (session != null) ? (Integer) session.getAttribute("id_usuario") : null;

        if (idUsuario == null) {
            // Si no hay usuario en la sesión, redirigir a iniciar sesión
            response.sendRedirect("IniciarUsuario");
            return;
        }

        // Realizar la eliminación del producto del carrito en la base de datos
        try (Connection conn = BD.getConnection()) {
            // Sentencia SQL para eliminar el producto del carrito del usuario
            String sql = "DELETE FROM carrito WHERE id_producto = ? AND id_usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idProducto);
            stmt.setInt(2, idUsuario);

            // Ejecutar la sentencia
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Si el producto fue eliminado con éxito, redirigir al carrito
                response.sendRedirect("MostrarCarrito");
            } else {
                // Si no se encontró el producto, redirigir con mensaje de error
                response.sendRedirect("MostrarCarrito?mensaje=Producto no encontrado en el carrito");
            }

        } catch (SQLException e) {
            // En caso de error en la base de datos, se maneja la excepción
            throw new ServletException("Error al eliminar producto del carrito", e);
        }
    }
}