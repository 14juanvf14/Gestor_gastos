$(document).ready(function (){
    getUsuarios();
    span = document.getElementById("nombreGestor")
    span.textContent = localStorage.getItem("nombre")
    setInterval(getUsuarios,10000)

    let nombre = localStorage.getItem("nombre");
});


function openPopUpGasto() {
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

function openFormAddUser(){
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

function closeFormAddUser(){
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

function openFormUpdateUser(id){
    var table = document.getElementById("miTable");
    table.classList.add("d-none")
    var formAgrega = document.getElementById("form-update-user")
    formAgrega.classList.remove("d-none")
    var parent = document.getElementById("content-wrapper");
    parent.style.backgroundColor = "rgba(0,0,0,0.1)"
    var btnAgregar = document.getElementById("btn-agregar");
    btnAgregar.classList.add("d-none")
    var btnCancelar = document.getElementById("btn-cancelar");
    btnCancelar.classList.remove("d-none")
    getUserID(id)
}

function closeFormUpdateUser(){
    var table = document.getElementById("miTable");
    table.classList.remove("d-none")
    var formAgrega = document.getElementById("form-update-user")
    formAgrega.classList.add("d-none")
    var parent = document.getElementById("content-wrapper");
    parent.style.backgroundColor = ""
    var btnAgregar = document.getElementById("btn-agregar");
    btnAgregar.classList.remove("d-none")
    var btnCancelar = document.getElementById("btn-cancelar");
    btnCancelar.classList.add("d-none")
}


async function addUsuarios() {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var nombre = document.getElementById("nombreUser").value;
    var valorEstado = document.querySelector('input[name="estado"]:checked').value;
    let estado = 0;
    valorEstado==="Activo"? estado = 1: estado = 0;

    var email = document.getElementById("emailUser").value;
    var fecha = document.getElementById("dateUser").value;
    var identificador = document.getElementById("identificadorUser").value;
    var usuario = {
        "id": identificador,
        "estado": estado,
        "nombre": nombre,
        "email": email,
        "fecha_ingreso": fecha
    };

    var raw = JSON.stringify(usuario);

    if (localStorage.getItem("token") === null) {
        var requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };
    } else {
        myHeaders.append("Authorization", localStorage.getItem("token"));
        var requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };
    }

    try {
        const currentURL = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port;
        const response = await fetch(currentURL + "/usuario", requestOptions);
        if (response.ok) {
            alert("El usuario ha sido guardado con éxito");
            closeFormAddUser();
        } else if (response.status === 403) {
            window.location.href = 'index.html';
            alert("No tiene acceso a esta sección");
        } else {
            throw await response.json();
        }
    } catch (error) {
        alert("Error en la solicitud: " + error.error_mensaje + "\nError Code: " + error.code);
    }
}

async function getUsuarios() {
    var myHeaders = new Headers();
    if (localStorage.getItem("token") === null) {
        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };
    } else {
        myHeaders.append("Authorization", localStorage.getItem("token"));
        var requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };
    }
    var table = document.getElementById("tusuarios");
    var tbody = table.getElementsByTagName("tbody")[0];
    try {
        const currentURL = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port;
        const response = await fetch(currentURL + "/usuario", requestOptions);
        if (response.ok) {
            const result = await response.json();

            // Agregar una fila por cada objeto en la respuesta
            if (result.every(usuario => usuario.hasOwnProperty('id'))) {
                tbody.innerHTML = "";// Limpiar el cuerpo de la tabla antes de agregar los nuevos datos
                result.forEach(function (usuario) {
                    var row = tbody.insertRow();
                    row.insertCell().innerHTML = usuario.id;
                    row.insertCell().innerHTML = usuario.nombre;
                    row.insertCell().innerHTML = usuario.estado === 1 ? "Activo" : "Inactivo";
                    row.insertCell().innerHTML = usuario.email;
                    row.insertCell().innerHTML = usuario.fecha_ingreso;
                    var acciones = row.insertCell();
                    acciones.innerHTML = '<div class="dropdown mb-4 show"><button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="false" aria-expanded="true">Acciones</button>' +
                        '<div class="dropdown-menu animated--fade-in" aria-labelledby="dropdownMenuButton" style="position: absolute; transform: translate3d(0px, 38px, 0px); top: 0px; left: 0px; will-change: transform;" x-placement="bottom-start">' +
                        '<a class="dropdown-item" href="#" onclick="openFormUpdateUser('+usuario.id+')">Modificar usuario</a><a class="dropdown-item" href="#" onclick="deleteUserID(' + usuario.id + ')">Eliminar usuario</a><a class="dropdown-item" href="#" onclick="openPopUpGasto()">Ver gastos</a></div></div>';
                });
            }

        } else if (response.status === 403) {
            window.location.href = 'index.html'
            alert("No tiene acceso a esta sección")
        } else {
            throw await response.json();
        };
        $("#tusuarios").DataTable();
    } catch (error) {
        $("#tusuarios").DataTable();
        console.log("Error en la solicitud: " + error.error_mensaje + "\nError Code: " + error.code);
        tbody.innerHTML = '<tr class="odd"><td valign="top" colspan="6" class="dataTables_empty">No hay datos disponibles para esta tabla</td></tr>'

    }

}

async function deleteUserID(id) {
    var myHeaders = new Headers();
    if (localStorage.getItem("token") === null) {
        var requestOptions = {
            method: 'DELETE',
            redirect: 'follow'
        };
    } else {
        myHeaders.append("Authorization", localStorage.getItem("token"));
        var requestOptions = {
            method: 'DELETE',
            headers: myHeaders,
            redirect: 'follow'
        };
    }
    try {
        const currentURL = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port;
        const response = await fetch(currentURL + "/usuario/" + id + "", requestOptions);
        if (response.ok) {
            alert("El gestor ha sido eliminado correctamente");
        } else if (response.status === 403) {
            window.location.href = 'index.html';
            alert("No tiene acceso a esta sección");
        } else {
            throw await response.json();
        }
    } catch (error) {
        alert("Error en la solicitud: " + error.error_mensaje + "\nError Code: " + error.code);
    }
}

async function getUserID(id) {

    var myHeaders = new Headers();
    if(localStorage.getItem("token")===null){
        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };
    }
    else {
        myHeaders.append("Authorization", localStorage.getItem("token"));
        var requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };
    }
    try {
        const currentURL = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port;
        const response = await fetch(currentURL + "/usuario/"+id+"", requestOptions);
        if (response.ok) {
            const result = await response.json();
            var nombre = document.getElementById("nombreUserUpdate");
            var email = document.getElementById("emailUserUpdate");
            var radioActivo = document.querySelector('input[name="estadoUpdate"][value="Activo"]');
            var radioInactivo = document.querySelector('input[name="estadoUpdate"][value="Inactivo"]');
            var identificador = document.getElementById("identificadorUserUpdate");
            var date = document.getElementById("dateUserUpdate");



            nombre.value = result.nombre
            email.value = result.email
            date.value = result.fecha_ingreso
            result.estado === 1 ? radioActivo.checked = true : radioInactivo.checked = true;
            identificador.value = result.id;
            identificador.disabled = true;

        } else if (response.status === 403){
            window.location.href = 'index.html'
            alert("No tiene acceso a esta sección")
        }
        else {
            throw await response.json();
        }
    } catch (error) {
        alert("Error en la solicitud: " + error.error_mensaje + "\nError Code: " + error.code);
    }
}

async function updateUser(){
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var nombre = document.getElementById("nombreUserUpdate").value;
    var valorEstado = document.querySelector('input[name="estadoUpdate"]:checked').value;
    let estado = 0;
    valorEstado==="Activo"? estado = 1: estado = 0;

    var email = document.getElementById("emailUserUpdate").value;
    var fecha = document.getElementById("dateUserUpdate").value;
    var identificador = document.getElementById("identificadorUserUpdate").value;
    var usuario = {
        "id": identificador,
        "estado": estado,
        "nombre": nombre,
        "email": email,
        "fecha_ingreso": fecha
    };

    var raw = JSON.stringify(usuario);

    if (localStorage.getItem("token") === null) {
        var requestOptions = {
            method: 'PUT',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };
    } else {
        myHeaders.append("Authorization", localStorage.getItem("token"));
        var requestOptions = {
            method: 'PUT',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };
    }

    try {
        const currentURL = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port;
        const response = await fetch(currentURL + "/usuario/"+identificador+"", requestOptions);
        if (response.ok) {
            alert("El gestor ha sido guardado con éxito");
            closeFormUpdateUser();
        } else if (response.status === 403) {
            window.location.href = 'index.html';
            alert("No tiene acceso a esta sección");
        } else {
            throw await response.json();
        }
    } catch (error) {
        alert("Error en la solicitud: " + error.error_mensaje + "\nError Code: " + error.code);
    }
}