<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pagar Producto</title>
    <link rel="stylesheet" href="pagar.css">
</head>
<body>
    <h1>Pagar Producto</h1>
    <form action="Compra" method="POST">
    
        <input type="hidden" name="idProducto" value="<%= session.getAttribute("idProducto") %>">
        <input type="hidden" name="cantidad" value="<%= session.getAttribute("cantidad") %>">
        <input type="hidden" name="precio" value="<%= session.getAttribute("precio") %>">

       
        <label for="direccion">Direcci�n de Env�o:</label>
        <input type="text" id="direccion" name="direccion_envio" required>

        <label for="ciudad">Ciudad:</label>
        <input type="text" id="ciudad" name="ciudad" required>

        <label for="codigo_postal">C�digo Postal:</label>
        <input type="number" id="codigo_postal" name="codigo_postal" required>

        <label for="metodo_pago">M�todo de Pago:</label>
        <select id="metodo_pago" name="metodo_pago" required>
            <option value="tarjeta_credito">Tarjeta de Cr�dito</option>
            <option value="paypal">PayPal</option>
            <option value="transferencia_bancaria">Transferencia Bancaria</option>
        </select>

        <input type="submit" value="Pagar">
    </form>
</body>
</html>
