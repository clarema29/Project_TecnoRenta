
var username = new URL(location.href).searchParams.get("username");
var user;

$(document).ready(function () {

    $(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });

    getUsuario().then(function () {
        
        $("#mi-perfil-btn").attr("href","profile.html?username=" + username);
        
        $("#user-saldo").html(user.saldo.toFixed(2) + "$");

        getProductos(false, "ASC");

        $("#ordenar-referencia").click(ordenarProductos);
    });
});


async function getUsuario() {

    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletUsuarioPedir",
        data: $.param({
            username: username
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                user = parsedResult;
            } else {
                console.log("Error recuperando los datos del usuario");
            }
        }
    });

}
function getProductos(ordenar, orden) {

    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletProductoListar",
        data: $.param({
            ordenar: ordenar,
            orden: orden
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                mostrarProductos(parsedResult);
            } else {
                console.log("Error recuperando los datos del producto");
            }
        }
    });
}
function mostrarProductos(productos) {

    let contenido = "";

    $.each(productos, function (index, producto) {

        producto = JSON.parse(producto);
        let precio;

        if (producto.cantidad > 0) {

            if (user.premium) {

                if (producto.novedad) {
                    precio = (2 - (2 * 0.1));
                } else {
                    precio = (1 - (1 * 0.1));
                }
            } else {
                if (producto.novedad) {
                    precio = 2;
                } else {
                    precio = 1;
                }
            }

            contenido += '<tr><th scope="row">' + producto.id + '</th>' +
                    '<td>' + producto.tipo + '</td>' +
                    '<td>' + producto.referencia + '</td>' +
                    '<td>' + producto.marca + '</td>' +
                    '<td>' + producto.cantidad + '</td>' +
                    '<td><input type="checkbox" name="novedad" id="novedad' + producto.id + '" disabled ';
            if (producto.novedad) {
                contenido += 'checked';
            }
            contenido += '></td>' +
                    '<td>' + precio + '</td>' +
                    '<td><button onclick="alquilarProducto(' + producto.id + ',' + precio + ');" class="btn btn-success" ';
            if (user.saldo < precio) {
                contenido += ' disabled ';
            }

            contenido += '>Reservar</button></td></tr>'

        }
    });
    $("#productos-tbody").html(contenido);
}

function ordenarProductos() {

    if ($("#icono-ordenar").hasClass("fa-sort")) {
        getProductos(true, "ASC");
        $("#icono-ordenar").removeClass("fa-sort");
        $("#icono-ordenar").addClass("fa-sort-down");
    } else if ($("#icono-ordenar").hasClass("fa-sort-down")) {
        getProductos(true, "DESC");
        $("#icono-ordenar").removeClass("fa-sort-down");
        $("#icono-ordenar").addClass("fa-sort-up");
    } else if ($("#icono-ordenar").hasClass("fa-sort-up")) {
        getProductos(false, "ASC");
        $("#icono-ordenar").removeClass("fa-sort-up");
        $("#icono-ordenar").addClass("fa-sort");
    }
}
function alquilarProducto(id, precio) {

    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletProductoAlquilar",
        data: $.param({
            id: id,
            username: username

        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                restarDinero(precio).then(function () {
                    location.reload();
                })
            } else {
                console.log("Error en la reserva del producto");
            }
        }
    });
}


async function restarDinero(precio) {

    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletUsuarioRestarDinero",
        data: $.param({
            username: username,
            saldo: parseFloat(user.saldo - precio)

        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                console.log("Saldo actualizado");
            } else {
                console.log("Error en el proceso de pago");
            }
        }
    });
}