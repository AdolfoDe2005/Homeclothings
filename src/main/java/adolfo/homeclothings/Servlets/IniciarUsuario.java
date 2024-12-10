package adolfo.homeclothings.Servlets;

import adolfo.homeclothings.Conexion.BD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/IniciarUsuario")
public class IniciarUsuario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirige al archivo jsp externo para el formulario de inicio de sesión
        RequestDispatcher dispatcher = request.getRequestDispatcher("InicioSesion.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener parámetros del formulario
        String idStr = request.getParameter("id");
        String Nombre = request.getParameter("Nombre");
        String Clave = request.getParameter("Clave");

        // Validar que los campos no estén vacíos
        if (idStr == null || Nombre == null || Clave == null || idStr.isEmpty() || Nombre.isEmpty() || Clave.isEmpty()) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head><title>Error</title></head>");
            out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
            out.println("<body>");
            out.println("<h1>Error: Todos los campos son obligatorios.</h1>");
            out.println("<a class='btn' href='IniciarSesion'>Volver al inicio de sesión</a>");
            out.println("</body>");
            out.println("</html>");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head><title>Error</title></head>");
            out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
            out.println("<body>");
            out.println("<h1>Error: El ID debe ser un número válido.</h1>");
            out.println("<a class='btn' href='IniciarUsuario'>Volver al inicio de sesión</a>");
            out.println("</body>");
            out.println("</html>");
            return;
        }

        try (Connection conn = BD.getConnection()) {
            String sql = "SELECT * FROM Usuario WHERE id = ? AND Nombre = ? AND Clave = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, Nombre);
            stmt.setString(3, Clave);
            ResultSet rs = stmt.executeQuery();

            // Verificar si el usuario existe
            if (rs.next()) {
                // Iniciar sesión guardando el ID en la sesión
                HttpSession session = request.getSession();
                session.setAttribute("id_usuario", rs.getInt("id")); // sse guarda el id_usuario en la sesión

                // Redirige a la página principal del sitio
                response.sendRedirect("Homeclothings");
            } else {
                // Si no hay coincidencias, mostrar mensaje de error
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html>");
                out.println("<head><title>Error de Inicio de Sesión</title></head>");
                out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
                out.println("<body>");
                out.println("<h1>Error: Usuario o clave incorrectos.</h1>");
                out.println("<a class='btn' href='IniciarUsuario'>Intentar de nuevo</a>");
                out.println("</body>");
                out.println("</html>");
            }
        } catch (SQLException e) {
            // Si ocurre un error, muestra la página de error generada en el servlet
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head><title>Error en la Base de Datos</title></head>");
            out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
            out.println("<body>");
            out.println("<h1>Error en la base de datos: " + e.getMessage() + "</h1>");
            out.println("<a class='btn' href='IniciarUsuario'>Intentar de nuevo</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}