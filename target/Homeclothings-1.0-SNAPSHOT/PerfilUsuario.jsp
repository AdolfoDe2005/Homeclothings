<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="PerfilUsuario.css">
    <title>Perfil de Usuario</title>
</head>
<body>
    <div class="banner">
        Homeclothings
    </div>
    <div class="main-container">
        <div class="profile-card">
            <div class="profile-image">
                <img src="${imagen}" alt="${nombre}" onerror="this.onerror=null; this.src='img/images.png';">
            </div>
            <div class="profile-details">
                <h2 class="profile-title">${nombre}</h2>
                <p class="profile-description">${descripcion}</p>
                <p class="profile-age">Edad: ${edad}</p>
                <p class="profile-email">Email: ${email}</p>
                <p class="profile-password">Clave: ${clave}</p>
            </div>
        </div>

        <div class="edit-profile">
            <h2>Editar Perfil</h2>
            <form action="PerfilUsuario" method="post">
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" value="${nombre}" required>

                <label for="descripcion">Descripción:</label>
                <textarea id="descripcion" name="descripcion" required>${descripcion}</textarea>

                <label for="edad">Edad:</label>
                <input type="number" id="edad" name="edad" value="${edad}" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${email}" required>
                
                <label for="clave">Clave:</label>
                <input type="password" id="clave" name="clave" value="${clave}" required>

                <button type="submit" class="save-button">Guardar Cambios</button>
            </form>
        </div>
    </div>

    <footer>
        <p>© 2024 Homeclothings. Todos los derechos reservados.</p>
    </footer>
</body>
</html>
