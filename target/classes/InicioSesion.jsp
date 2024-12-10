<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar Sesión</title>
    <link rel="stylesheet" type="text/css" href="IniciarSesión.css">
</head>
<body>
    <div class="form-IniciarSesión">
        <h4>Iniciar Sesión</h4>
        <form method="POST" action="IniciarUsuario">
            <input type="number" name="id" class="controls" placeholder="ID" required><br>
            <input type="text" name="Nombre" class="controls" placeholder="Nombre" required><br>
            <input type="password" name="Clave" class="controls" placeholder="Contraseña" required><br>
            <button type="submit" class="botons">Iniciar Sesión</button>
        </form>
        <p>¿No tienes cuenta? <a href="Registrarse.html">Regístrate aquí</a></p>
    </div>
</body>
</html>
