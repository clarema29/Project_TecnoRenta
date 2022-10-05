var username = new URL(location.href).searchParams.get("username");
var user;

$(document).ready(function () {


    fillUsuario().then(function () {

        $("#user-saldo").html("$" + user.saldo.toFixed());

        getAlquiladas(user.username);
    });

    $("#reservar-btn").attr("href", `home.html?username=${username}`);

    $("#form-modificar").on("submit", function (event) {

        event.preventDefault();
        modificarUsuario();
    });

    $("#aceptar-eliminar-cuenta-btn").click(function () {

        eliminarCuenta().then(function () {
            location.href = "index.html";
        })
    })

});

async function fillUsuario() {
    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletUsuarioPedir",
        data: $.param({
            username: username,
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                user = parsedResult;

                $("#input-username").val(parsedResult.username);
                $("#input-clave").val(parsedResult.clave);
                $("#input-nombre").val(parsedResult.nombre);
                $("#input-apellido").val(parsedResult.apellido);
                $("#input-email").val(parsedResult.email);
                $("#input-saldo").val(parsedResult.saldo.toFixed(2));
                $("#input-premium").prop("checked", parsedResult.premium);

            } else {
                console.log("Error recuperando los datos del usuario");
            }
        }
    });
}

function getAlquiladas(username) {


    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletAlquilerListar",
        data: $.param({
            username: username,
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {

                mostrarHistorial(parsedResult)

            } else {
                console.log("Error recuperando los datos de la reserva");
            }
        }
    });
}

function mostrarHistorial(productos) {
    let contenido = "";
    if (productos.length >= 1) {
        $.each(productos, function (index, producto) {
            producto = JSON.parse(producto);

            contenido += '<tr><th scope="row">' + producto.id + '</th>' +
                    '<td>' + producto.tipo + '</td>' +
                    '<td>' + producto.referencia + '</td>' +
                    '<td><input type="checkbox" name="novedad" id="novedad' + producto.id 
                    + '" disabled ';
            if (producto.novedad) {
                contenido += 'checked'
            }
            contenido += '></td><td>' + producto.fechaAlquiler + '</td>' +
                    '<td><button id="devolver-btn" onclick= "devolverproducto(' + producto.id 
                    + ');" class="btn btn-danger">Devolver producto</button></td></tr>';

        });
        $("#historial-tbody").html(contenido);
        $("#historial-table").removeClass("d-none");
        $("#historial-vacio").addClass("d-none");

    } else {
        $("#historial-vacio").removeClass("d-none");
        $("#historial-table").addClass("d-none");
    }
}


function devolverProducto(id) {

    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletProductoDevolver",
        data: $.param({
            username: username,
            id: id,
        }),
        success: function (result) {

            if (result != false) {

                location.reload();

            } else {
                console.log("Error devolviendo el Producto");
            }
        }
    });

}

function modificarUsuario() {

    let username = $("#input-username").val();
    let clave = $("#input-clave").val();
    let nombre = $("#input-nombre").val();
    let apellido = $("#input-apellido").val();
    let email = $("#input-email").val();
    let saldo = $("#input-saldo").val();
    let premium = $("#input-premium").prop('checked');
    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletUsuarioModificar",
        data: $.param({
            username: username,
            clave: clave,
            nombre: nombre,
            apellido: apellido,
            email: email,
            saldo: saldo,
            premium: premium,
        }),
        success: function (result) {

            if (result != false) {
                $("#modificar-error").addClass("d-none");
                $("#modificar-exito").removeClass("d-none");
            } else {
                $("#modificar-error").removeClass("d-none");
                $("#modificar-exito").addClass("d-none");
            }

            setTimeout(function () {
                location.reload();
            }, 3000);

        }
    });

}

async function eliminarCuenta() {

    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletUsuarioEliminar",
        data: $.param({
            username: username
        }),
        success: function (result) {

            if (result != false) {

                console.log("Usuario eliminado")

            } else {
                console.log("Error eliminando el usuario");
            }
        }
    });
}


