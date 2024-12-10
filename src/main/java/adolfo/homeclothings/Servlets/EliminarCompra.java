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

@WebServlet("/EliminarCompra")
public class EliminarCompra extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idProductoStr = request.getParameter("id_producto");
        
        if (idProductoStr == null || idProductoStr.isEmpty()) {
            response.sendRedirect("compras.jsp?mensaje=Error: ID del producto no proporcionado.");
            return;
        }

        int idProducto;
        try {
            idProducto = Integer.parseInt(idProductoStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("compras.jsp?mensaje=Error: ID del producto inválido.");
            return;
        }

        try (Connection conn = BD.getConnection()) {
            String sql = "DELETE FROM pagos WHERE id_producto = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idProducto);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Redirige a la página de historial de compras con un mensaje de éxito
                response.sendRedirect("compras.jsp?mensaje=Compra eliminada exitosamente.");
            } else {
                response.sendRedirect("compras.jsp?mensaje=Error: No se encontró la compra.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("compras.jsp?mensaje=Error en la base de datos: " + e.getMessage());
        }
    }
} 