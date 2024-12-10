package adolfo.homeclothings.Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.http.HttpSession;

@WebServlet("/PagarProducto")
public class PagarProducto extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recoge datos de la compra y los guarda en la sesión
        HttpSession session = request.getSession();
        session.setAttribute("idProducto", request.getParameter("idProducto"));
        session.setAttribute("cantidad", request.getParameter("cantidad"));
        session.setAttribute("precio", request.getParameter("precio"));

        // Redirige al formulario de pago JSP
        request.getRequestDispatcher("pagar.jsp").forward(request, response);
    }
}
