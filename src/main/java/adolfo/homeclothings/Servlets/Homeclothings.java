package adolfo.homeclothings.Servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Homeclothings")
public class Homeclothings extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Obtener la sesión actual, si existe
        HttpSession session = request.getSession(false);

        // Verificar si el usuario ha iniciado sesión
        if (session == null || session.getAttribute("id_usuario") == null) {
            // Si no hay sesión o el usuario no ha iniciado sesión, redirigir al registro
            response.sendRedirect("RegistroUsuarios");
            return; // Terminar el método después de redirigir
        }

       RequestDispatcher dispatcher = request.getRequestDispatcher("Homeclothings.html");
       dispatcher.forward(request, response);
    }
}
