$(document).ready(function (){
    $("#tgastos").DataTable();
    verUsuarios();
    setInterval(verUsuarios,4000)

    let nombre = localStorage.getItem("nombre");
});


function mostrarTabla() {
    var table = document.getElementById("miTable");
    table.classList.add("d-none")
    var popup = document.getElementById("miPopup");
    popup.classList.remove("d-none")
    var parent = document.getElementById("content-wrapper");
    parent.style.backgroundColor = "rgba(0,0,0,0.1)"
    var btnAgregar = document.getElementById("btn-agregar");
    btnAgregar.classList.add("d-none")

}

function cerrarPopup() {
    var popup = document.getElementById("miPopup");
    popup.classList.add("d-none")
    var table = document.getElementById("miTable");
    table.classList.remove("d-none")
    var parent = document.getElementById("content-wrapper")
    parent.style.backgroundColor = ""
    var btnAgregar = document.getElementById("btn-agregar");
    btnAgregar.classList.remove("d-none")
}

function mostrarAgregar(){
    var table = document.getElementById("miTable");
    table.classList.add("d-none")
    var formAgrega = document.getElementById("form-add-user")
    formAgrega.classList.remove("d-none")
    var parent = document.getElementById("content-wrapper");
    parent.style.backgroundColor = "rgba(0,0,0,0.1)"
    var btnAgregar = document.getElementById("btn-agregar");
    btnAgregar.classList.add("d-none")
    var btnCancelar = document.getElementById("btn-cancelar");
    btnCancelar.classList.remove("d-none")
}

function cerrarAgregar(){
    var table = document.getElementById("miTable");
    table.classList.remove("d-none")
    var formAgrega = document.getElementById("form-add-user")
    formAgrega.classList.add("d-none")
    var parent = document.getElementById("content-wrapper");
    parent.style.backgroundColor = ""
    var btnAgregar = document.getElementById("btn-agregar");
    btnAgregar.classList.remove("d-none")
    var btnCancelar = document.getElementById("btn-cancelar");
    btnCancelar.classList.add("d-none")
}


function addUsuarios(){
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", localStorage.getItem("token"))

    var nombre = document.getElementById("nombreUser").value;
    var estado = document.getElementById("estado").value;
    var email = document.getElementById("emailUser").value;
    var fecha = document.getElementById("dateUser").value;
    var identificador = document.getElementById("identificadorUser").value;

    // Crear un objeto con los valores de los campos del formulario
    var usuario = {
        "id": identificador,
        "estado": estado,
        "nombre": nombre,
        "email": email,
        "fecha_ingreso": fecha
    };

    var raw = JSON.stringify(usuario);

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    fetch("http://localhost:8080/usuario", requestOptions)
        .then(response => {
            if(response.ok){
                response.json()
            }
            else{
                throw response.json()
            }

        })
        .then(result => console.log(result))
        .catch(error => {
            error.then(onmessageerror => alert(onmessageerror))
        });
}

function verUsuarios(){

    var myHeaders = new Headers();
    myHeaders.append("Authorization", "TuTokenDeAutorizacion");

    var requestOptions = {
        method: 'GET',
        headers: myHeaders,
        redirect: 'follow'
    };

    fetch("http://localhost:8080/usuario", requestOptions)
        .then(response => response.json()) // Convertir la respuesta de texto a JSON
        .then(data => {
            console.log(data); // Verificar que los datos se están recibiendo correctamente
            // Obtener la tabla y el cuerpo de la tabla
            var table = document.getElementById("tusuarios");
            var tbody = table.getElementsByTagName("tbody")[0];

            // Agregar una fila por cada objeto en la respuesta
            if (data.every(usuario => usuario.hasOwnProperty('id'))) {
                tbody.innerHTML = "";// Limpiar el cuerpo de la tabla antes de agregar los nuevos datos
                data.forEach(function(usuario) {
                    var row = tbody.insertRow();
                    row.insertCell().innerHTML = usuario.id;
                    row.insertCell().innerHTML = usuario.nombre;
                    row.insertCell().innerHTML = usuario.estado === 1 ? "Activo" : "Inactivo";
                    row.insertCell().innerHTML = usuario.email;
                    row.insertCell().innerHTML = usuario.fecha_ingreso;
                    var acciones = row.insertCell();
                    acciones.innerHTML = '<div class="dropdown mb-4 show"><button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="false" aria-expanded="true">Acciones</button><div class="dropdown-menu animated--fade-in" aria-labelledby="dropdownMenuButton" style="position: absolute; transform: translate3d(0px, 38px, 0px); top: 0px; left: 0px; will-change: transform;" x-placement="bottom-start"><a class="dropdown-item" href="#" onclick="#">Modificar usuario</a><a class="dropdown-item" href="#" onclick="eliminarUsuarioPorID('+usuario.id+')">Eliminar usuario</a><a class="dropdown-item" href="#" onclick="mostrarTabla()">Ver gastos</a></div></div>';
                });
            }
            $("#tusuarios").DataTable();
        })
        .catch(error => console.log('error', error));
}

function eliminarUsuarioPorID(id){
    var requestOptions = {
        method: 'DELETE',
        redirect: 'follow'
    };

    fetch("http://localhost:8080/usuario/"+id+"", requestOptions)
        .then(response => response.text())
        .then(result => console.log(result))
        .catch(error => console.log('error', error));
}