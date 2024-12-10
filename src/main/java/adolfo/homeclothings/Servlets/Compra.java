package adolfo.homeclothings.Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet("/Compra")
public class Compra extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:5151/Login";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Batcountry2005";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mostrarHistorial(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Verificar que el usuario esté autenticado
        Integer idUsuario = (Integer) session.getAttribute("id_usuario");
        if (idUsuario == null) {
            // Redirigir a la página de inicio de sesión si idUsuario no está en la sesión
            session.setAttribute("mensaje", "Por favor, inicie sesión para continuar.");
            response.sendRedirect("InicioSesion.jsp");
            return;
        }

        // Obtener detalles de compra desde la sesión y la solicitud
        int idProducto = Integer.parseInt((String) session.getAttribute("idProducto"));
        int cantidad = Integer.parseInt((String) session.getAttribute("cantidad"));
        double precio = Double.parseDouble((String) session.getAttribute("precio"));
        String direccionEnvio = request.getParameter("direccion_envio");
        String ciudad = request.getParameter("ciudad");
        String codigoPostal = request.getParameter("codigo_postal");
        String metodoPago = request.getParameter("metodo_pago");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Insertar la compra en la base de datos
            String insertSQL = "INSERT INTO pagos (id_usuario, id_producto, cantidad, precio, direccion_envio, ciudad, codigo_postal, metodo_pago, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'pendiente')";
            stmt = conn.prepareStatement(insertSQL);
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idProducto);
            stmt.setInt(3, cantidad);
            stmt.setDouble(4, precio);
            stmt.setString(5, direccionEnvio);
            stmt.setString(6, ciudad);
            stmt.setString(7, codigoPostal);
            stmt.setString(8, metodoPago);
            stmt.executeUpdate();

            // Redirigir a compras.jsp con un mensaje de éxito
            session.setAttribute("mensaje", "Compra procesada exitosamente.");
            request.getRequestDispatcher("compras.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("mensaje", "Error al procesar la compra: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response); // Redirige a la página de error en caso de fallo
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void mostrarHistorial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id_usuario") == null) {
            response.sendRedirect("InicioSesion.jsp");
            return;
        }

        int idUsuario = (int) session.getAttribute("id_usuario");
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT p.nombre AS productoNombre, c.cantidad, c.precio, c.direccion_envio, c.ciudad, c.codigo_postal, c.metodo_pago, c.fecha, c.estado, c.id_producto " +
                         "FROM pagos c JOIN productos p ON c.id_producto = p.id WHERE c.id_usuario = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();

            request.setAttribute("resultadoHistorial", rs);
            request.getRequestDispatcher("compras.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar el historial de compras: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
