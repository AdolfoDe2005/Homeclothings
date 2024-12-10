package adolfo.homeclothings.Servlets;

import adolfo.homeclothings.Conexion.BD;
import org.apache.commons.lang3.StringUtils; // Para validación de cadenas
import org.apache.commons.lang3.math.NumberUtils; // Para validación de números
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RegistroUsuarios")
public class RegistroUsuarios extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirige al archivo HTML para el formulario de registro
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Registrarse.html");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Recuperar parámetros de la solicitud
        String idStr = request.getParameter("id");
        String nombre = request.getParameter("Nombre");
        String edadStr = request.getParameter("Edad");
        String email = request.getParameter("email");
        String clave = request.getParameter("Clave");
        
        // Validar los campos utilizando Apache Commons
        if (StringUtils.isBlank(nombre) || StringUtils.isBlank(email) || StringUtils.isBlank(clave) 
            || !NumberUtils.isCreatable(idStr) || !NumberUtils.isCreatable(edadStr)) {
            
            // Si alguna validación falla, redirige a la página de error
            mostrarError(response, "Campos inválidos o incompletos");
            return;
        }
        
        int id = NumberUtils.toInt(idStr);
        int edad = NumberUtils.toInt(edadStr);
        
        try (Connection conn = BD.getConnection()) {
            String sql = "INSERT INTO Usuario (id, Nombre, Edad, email, Clave) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, id);        
            stmt.setString(2, nombre);  
            stmt.setInt(3, edad);       
            stmt.setString(4, email);   
            stmt.setString(5, clave);   
            
            stmt.executeUpdate();
            
            // Iniciar sesión automáticamente después del registro
            HttpSession session = request.getSession();
            session.setAttribute("id_usuario", id);

            // Redirige a la página principal del sitio
            response.sendRedirect("Homeclothings");

        } catch (SQLException e) {
            // Si ocurre un error, muestra un mensaje de error en la misma página
            mostrarError(response, "Error al registrar usuario: " + e.getMessage());
        }
    }
    
    // Método para mostrar mensajes de error
    private void mostrarError(HttpServletResponse response, String mensaje) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Error en el Registro</title></head>");
        out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
        out.println("<body>");
        out.println("<h1>" + mensaje + "</h1>");
        out.println("<a class='btn' href='RegistroUsuarios'>Intentar de nuevo</a>");
        out.println("</body>");
        out.println("</html>");
    }
}
