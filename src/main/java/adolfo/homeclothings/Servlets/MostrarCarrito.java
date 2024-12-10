package adolfo.homeclothings.Servlets;

import adolfo.homeclothings.Conexion.BD;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/MostrarCarrito")
public class MostrarCarrito extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener el ID del usuario desde la sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id_usuario") == null) {
            response.sendRedirect("IniciarUsuario"); // Redirigir si no está logueado
            return;
        }

        int idUsuario = (int) session.getAttribute("id_usuario");
        List<Map<String, Object>> carrito = new ArrayList<>();

        try (Connection conn = BD.getConnection()) {
            // Consulta para obtener los productos del carrito de este usuario
            String sql = "SELECT productos.id, productos.nombre, productos.precio, carrito.cantidad " +
                         "FROM carrito " +
                         "JOIN productos ON carrito.id_producto = productos.id " +
                         "WHERE carrito.id_usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> producto = new HashMap<>();
                producto.put("idProducto", rs.getInt("id"));
                producto.put("nombre", rs.getString("nombre"));
                producto.put("precio", rs.getDouble("precio"));
                producto.put("cantidad", rs.getInt("cantidad"));

                carrito.add(producto);
            }

            // Enviar la lista del carrito a la vista en jsp
            request.setAttribute("carrito", carrito);
            request.getRequestDispatcher("carrito.jsp").forward(request, response);
 
        } catch (SQLException e) {
            throw new ServletException("Error al mostrar el carrito", e);
        }
    }
} 