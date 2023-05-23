$(document).ready(function (){
    getGestores();
    span = document.getElementById("nombreGestorAdmin")
    span.textContent = localStorage.getItem("nombre")
    setInterval(getGestores,5000)
});


//Funciones para manejo del DOM
function openAddGestorForm(){
    var table = document.getElementById("miTable");
    table.classList.add("d-none")
    var formAgrega = document.getElementById("form-add-gestor")
    formAgrega.classList.remove("d-none")
    var parent = document.getElementById("content-wrapper");
    parent.style.backgroundColor = "rgba(0,0,0,0.1)"
    var btnAgregar = document.getElementById("btn-agregar");
    btnAgregar.classList.add("d-none")
    var btnCancelar = document.getElementById("btn-cancelarAdd");
    btnCancelar.classList.remove("d-none")

}

function closeAddGestorForm(){
    var table = document.getElementById("miTable");
    table.classList.remove("d-none")
    var formAgrega = document.getElementById("form-add-gestor")
    formAgrega.classList.add("d-none")
    var parent = document.getElementById("content-wrapper");
    parent.style.backgroundColor = ""
    var btnAgregar = document.getElementById("btn-agregar");
    btnAgregar.classList.remove("d-none")
    var btnCancelar = document.getElementById("btn-cancelarAdd");
    btnCancelar.classList.add("d-none")
}

function openUpdateGestorForm(id){
    var table = document.getElementById("miTable");
    table.classList.add("d-none")
    var formAgrega = document.getElementById("form-update-gestor")
    formAgrega.classList.remove("d-none")
    var parent = document.getElementById("content-wrapper");
    parent.style.backgroundColor = "rgba(0,0,0,0.1)"
    var btnAgregar = document.getElementById("btn-agregar");
    btnAgregar.classList.add("d-none")
    var btnCancelar = document.getElementById("btn-cancelarUpdate");
    btnCancelar.classList.remove("d-none")
    getGestorID(id);
}

function closeUpdateGestorForm(){
    var table = document.getElementById("miTable");
    table.classList.remove("d-none")
    var formAgrega = document.getElementById("form-update-gestor")
    formAgrega.classList.add("d-none")
    var parent = document.getElementById("content-wrapper");
    parent.style.backgroundColor = ""
    var btnAgregar = document.getElementById("btn-agregar");
    btnAgregar.classList.remove("d-none")
    var btnCancelar = document.getElementById("btn-cancelarUpdate");
    btnCancelar.classList.add("d-none")
}
function close_sesion() {
    localStorage.removeItem("token")
    window.location.href = 'index.html'

}
/*
Integración a backend
 */
async function getGestores() {

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
        const response = await fetch(currentURL + "/gestor", requestOptions);
        if (response.ok) {
            const result = await response.json();
            var table = document.getElementById("tgestor");
            var tbody = table.getElementsByTagName("tbody")[0]; // Obtener el primer elemento del arreglo

            // Limpiar el cuerpo de la tabla antes de agregar los nuevos datos
            tbody.innerHTML = "";

            // Agregar una fila por cada objeto en la respuesta
            result.forEach(function(gestor) {
                var row = tbody.insertRow();
                row.insertCell().innerHTML = gestor.id;
                row.insertCell().innerHTML = gestor.nombre;
                row.insertCell().innerHTML = gestor.email;
                row.insertCell().innerHTML = gestor.rol;
                var acciones = row.insertCell();
                acciones.innerHTML = '<div class="dropdown mb-4 show"><button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="false" aria-expanded="true">Acciones</button>' +
                    '<div class="dropdown-menu animated--fade-in" aria-labelledby="dropdownMenuButton" style="position: absolute; transform: translate3d(0px, 38px, 0px); top: 0px; left: 0px; will-change: transform;" x-placement="bottom-start">' +
                    '<a class="dropdown-item" href="#" onclick="openUpdateGestorForm('+gestor.id+')">Modificar gestor</a>' +
                    '<a class="dropdown-item" href="#" onclick="deleteGestorID('+gestor.id+')">Eliminar Gestor</a></div></div>';
            });

        } else if (response.status === 403){
            window.location.href = 'index.html'
            alert("No tiene acceso a esta sección")
        }
        else {
            throw await response.json();
        }
        $("#tgestor").DataTable();
    } catch (error) {
        console.log("Error en la solicitud: " + error.error_mensaje + "\nError Code: " + error.code);
    }
}
async function addGestor() {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var nombre = document.getElementById("nombreGestor").value;
    var email = document.getElementById("emailGestor").value;
    var rol = document.querySelector('input[name="rol"]:checked').value;
    var identificador = document.getElementById("identificadorGestor").value;
    var password = document.getElementById("passwordGestor").value;

    var gestor = {
        "id": identificador,
        "nombre": nombre,
        "email": email,
        "password": password,
        "rol": rol
    };

    var raw = JSON.stringify(gestor);

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
        const response = await fetch(currentURL + "/gestor", requestOptions);
        if (response.ok) {
            alert("El gestor ha sido guardado con éxito");
            closeAddGestorForm();
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

async function deleteGestorID(id){
    var myHeaders = new Headers();
    if(localStorage.getItem("token")===null){
        var requestOptions = {
            method: 'DELETE',
            redirect: 'follow'
        };
    }
    else {
        myHeaders.append("Authorization", localStorage.getItem("token"));
        var requestOptions = {
            method: 'DELETE',
            headers: myHeaders,
            redirect: 'follow'
        };
    }
    try {
        const currentURL = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port;
        const response = await fetch(currentURL + "/gestor/"+id+"", requestOptions);
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

async function getGestorID(id) {

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
        const response = await fetch(currentURL + "/gestor/"+id+"", requestOptions);
        if (response.ok) {
            const result = await response.json();
            var nombre = document.getElementById("nombreGestorUpdate");
            var email = document.getElementById("emailGestorUpdate");
            var radioAdministrador = document.querySelector('input[name="rolUpdate"][value="Administrador"]');
            var radioGestor = document.querySelector('input[name="rolUpdate"][value="Gestor"]');
            var identificador = document.getElementById("identificadorGestorUpdate");
            var password = document.getElementById("passwordGestorUpdate");



            nombre.value = result.nombre
            email.value = result.email
            password.value = ''
            if (radioAdministrador && result.rol === "Administrador") {
                radioAdministrador.checked = true;
            }
            else{
                radioGestor.checked = true;
            }
            identificador.value = result.id

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
async function updateGestor(){
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var nombre = document.getElementById("nombreGestorUpdate").value;
    var email = document.getElementById("emailGestorUpdate").value;
    var rol = document.querySelector('input[name="rolUpdate"]:checked').value;
    var identificador = document.getElementById("identificadorGestorUpdate").value;
    var password = document.getElementById("passwordGestorUpdate").value;
    alert(rol)
    var gestor = {
        "id": identificador,
        "nombre": nombre,
        "email": email,
        "password": password,
        "rol": rol
    };

    var raw = JSON.stringify(gestor);

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
        const response = await fetch(currentURL + "/gestor/"+identificador+"", requestOptions);
        if (response.ok) {
            alert("El gestor ha sido guardado con éxito");
            closeUpdateGestorForm();
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