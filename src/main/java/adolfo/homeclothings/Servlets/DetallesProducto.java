package adolfo.homeclothings.Servlets;

import adolfo.homeclothings.Conexion.BD;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DetallesProducto")
public class DetallesProducto extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idProducto = request.getParameter("id");
        
        if (idProducto != null) {
            try (Connection conn = BD.getConnection()) {
                String sql = "SELECT * FROM productos WHERE id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, idProducto);
                
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    // Extraer datos del producto
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String descripcion = resultSet.getString("descripcion");
                    double precio = resultSet.getDouble("precio");
                    int stock = resultSet.getInt("stock");
                    String material = resultSet.getString("material");
                    String color = resultSet.getString("color");
                    String talla = resultSet.getString("talla");
                    String caracteristicas = resultSet.getString("caracteristicas");
                    int puntuación = resultSet.getInt("puntuación");
                    String imagen = resultSet.getString("imagen");
                    
                    
                   request.setAttribute("id", id);
                    request.setAttribute("nombre", nombre);
                    request.setAttribute("descripcion", descripcion);
                    request.setAttribute("precio", precio);
                    request.setAttribute("stock", stock);
                    request.setAttribute("material", material);
                    request.setAttribute("color", color);
                    request.setAttribute("talla", talla);
                    request.setAttribute("caracteristicas", caracteristicas);
                    request.setAttribute("puntuación", puntuación);
                    request.setAttribute("imagen", imagen);
                    
                    
                    
                    // Redirigir al modulo producto
                    request.getRequestDispatcher("Producto").forward(request, response);
                } else {
                    // si el Producto no es encontrado
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado");
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al recuperar el producto");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de producto no proporcionado");
        }
    }
}