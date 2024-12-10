<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="Producto.css">
    <title>Producto</title>
</head>
<body>
    <div class="banner">
        Homeclothings
    </div>
    <div class="main-container">
        <div class="product-card">
            <div class="product-image">
                <img src="${imagen}" alt="${nombre}" onerror="this.onerror=null; this.src='img/default-image.png';">
            </div>
            <div class="product-details">
                <h2 class="product-title">${nombre}</h2>
                
                <div class="puntuación">
                    <c:forEach var="i" begin="1" end="${puntuación}">
                        <span>★</span> 
                    </c:forEach>
                    <c:forEach var="i" begin="${puntuación + 1}" end="5">
                        <span>☆</span> 
                    </c:forEach>
                </div>
                
                <p class="product-description">${descripcion}</p>
                <p class="product-price">$${precio}</p>
                
              
                <form action="AgregarAlCarrito" method="get">
                    <input type="hidden" name="id" value="${id}">
                    <input type="hidden" name="idUsuario" value="${sessionScope.id_usuario}">
                    <label for="quantity">Cantidad:</label>
                    <input type="number" id="quantity" name="cantidad" value="1" min="1" max="${stock}">
                    <button type="submit" class="add-to-cart">Agregar al carrito</button>
                </form>
            </div>
        </div>

        <div class="technical-specs-container">
            <h2>Ficha Técnica</h2>
            <ul class="technical-specs">
                <li><strong>Material:</strong> ${material}</li>
                <li><strong>Color:</strong> ${color}</li>
                <li><strong>Tallas Disponibles:</strong> ${talla}</li>
            </ul>
            
            <h2>Características</h2>
            <ul class="features">
                <li>${caracteristicas}</li>
            </ul>
        </div>
    </div>

    <footer>
        <p>© 2024 Homeclothings. Todos los derechos reservados.</p>
    </footer>
</body>
</html>
