package adolfo.homeclothings.Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/PerfilUsuario")
public class PerfilUsuarioServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:5151/login";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Batcountry2005";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getSession().getAttribute("id_usuario").toString());

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM usuario WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                request.setAttribute("nombre", resultSet.getString("Nombre"));
                request.setAttribute("descripcion", resultSet.getString("Descripcion"));
                request.setAttribute("edad", resultSet.getInt("Edad"));
                request.setAttribute("email", resultSet.getString("email"));
                request.setAttribute("clave", resultSet.getInt("Clave"));
                request.setAttribute("imagen", resultSet.getString("Imagen"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("PerfilUsuario.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getSession().getAttribute("id_usuario").toString());
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        int edad = Integer.parseInt(request.getParameter("edad"));
        String email = request.getParameter("email");
        int clave = Integer.parseInt(request.getParameter("clave"));

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "UPDATE usuario SET Nombre = ?, Descripcion = ?, Edad = ?, email = ?, Clave = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setInt(3, edad);
            statement.setString(4, email);
            statement.setInt(5, clave);
            statement.setInt(6, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("PerfilUsuario");
    }
}
