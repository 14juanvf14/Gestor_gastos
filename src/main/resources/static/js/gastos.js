function openPopUpGasto(id) {
    var table = document.getElementById("miTable");
    table.classList.add("d-none")
    var popup = document.getElementById("miPopup");
    popup.classList.remove("d-none")
    var parent = document.getElementById("content-wrapper");
    parent.style.backgroundColor = "rgba(0,0,0,0.1)"
    var btnAgregar = document.getElementById("btn-agregar");
    btnAgregar.classList.add("d-none")
    localStorage.idUsuario = id
    getGastosByUser();
    setInterval(getGastosByUser, 10000)
}

function cerrarPopup() {
    var popup = document.getElementById("miPopup");
    popup.classList.add("d-none")
    var table = document.getElementById("miTable");
    table.classList.remove("d-none")
    var parent = document.getElementById("content-wrapper")
    parent.style.backgroundColor = ""
    var btnAgregar = document.getElementById("btn-agregar");
    btnAgregar.classList.remove("d-none");
    localStorage.removeItem("idUsuario");
}

function openAddGasto() {
    var popup = document.getElementById("miPopup");
    popup.classList.add("d-none")
    var table = document.getElementById("form-add-gasto");
    table.classList.remove("d-none")
    var parent = document.getElementById("content-wrapper")
    parent.style.backgroundColor = "rgba(0,0,0,0.1)"
    var btnCancelar = document.getElementById("btn-cancelarGasto");
    btnCancelar.classList.remove("d-none")
}

function closeAddGasto(){
    var popup = document.getElementById("miPopup");
    popup.classList.remove("d-none")
    var table = document.getElementById("form-add-gasto");
    table.classList.add("d-none")
    var parent = document.getElementById("content-wrapper")
    parent.style.backgroundColor = ""
    var btnCancelar = document.getElementById("btn-cancelarGasto");
    btnCancelar.classList.add("d-none")
}
function openUpdateGasto(id) {
    var popup = document.getElementById("miPopup");
    popup.classList.add("d-none")
    var table = document.getElementById("form-update-gasto");
    table.classList.remove("d-none")
    var parent = document.getElementById("content-wrapper")
    parent.style.backgroundColor = "rgba(0,0,0,0.1)"
    var btnCancelar = document.getElementById("btn-cancelarGastoUpdate");
    btnCancelar.classList.remove("d-none")
    localStorage.idGasto = id
    getGastoId(id)
}

function closeUpdateGasto(){
    var popup = document.getElementById("miPopup");
    popup.classList.remove("d-none")
    var table = document.getElementById("form-update-gasto");
    table.classList.add("d-none")
    var parent = document.getElementById("content-wrapper")
    parent.style.backgroundColor = ""
    var btnCancelar = document.getElementById("btn-cancelarGastoUpdate");
    btnCancelar.classList.add("d-none")
    localStorage.removeItem("idGasto")
}

async function getGastosByUser() {
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
    var table = document.getElementById("tgastos");
    var tbody = table.getElementsByTagName("tbody")[0]; // Obtener el primer elemento del arreglo
    var id = localStorage.getItem("idUsuario")
    try {
        const currentURL = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port;
        const response = await fetch(currentURL + "/gastos/usuario/" + id + "", requestOptions);
        if (response.ok) {
            const result = await response.json();
            // Limpiar el cuerpo de la tabla antes de agregar los nuevos datos
            tbody.innerHTML = "";

            // Agregar una fila por cada objeto en la respuesta
            result.forEach(function (gasto) {
                var row = tbody.insertRow();
                row.insertCell().innerHTML = gasto.id;
                row.insertCell().innerHTML = gasto.fecha;
                row.insertCell().innerHTML = gasto.monto;
                row.insertCell().innerHTML = gasto.descripcion;
                var acciones = row.insertCell();
                acciones.innerHTML = '<div class="dropdown mb-4 show"><button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="false" aria-expanded="true">Acciones</button>' +
                    '<div class="dropdown-menu animated--fade-in" aria-labelledby="dropdownMenuButton" style="position: absolute; transform: translate3d(0px, 38px, 0px); top: 0px; left: 0px; will-change: transform;" x-placement="bottom-start">' +
                    '<a class="dropdown-item" href="#" onclick=openUpdateGasto('+gasto.id+')>Modificar gasto</a><a class="dropdown-item" href="#" onclick=deleteGastoId('+gasto.id+')>Eliminar gasto</a>';
            });
        } else if (response.status === 403) {
            window.location.href = 'index.html'
            localStorage.removeItem("token")
            localStorage.removeItem("nombre")
            alert("No tiene acceso a esta sección")
        } else {
            throw await response.json();
        }
        $("#tgastos").DataTable();
    } catch (error) {
        $("#tgastos").DataTable();
        console.log("Error en la solicitud: " + error.error_mensaje + "\nError Code: " + error.code);
        tbody.innerHTML = '<tr class="odd"><td valign="top" colspan="6" class="dataTables_empty">No hay datos disponibles para esta tabla</td></tr>'
    }
}

async function addGasto() {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var descripcion = document.getElementById("descripcion").value;
    var fecha = document.getElementById("dateGasto").value;
    var monto = document.getElementById("monto").value;

    var gasto = {
        "fecha": fecha,
        "monto": monto,
        "descripcion": descripcion,
        "usuario": {
            "id": Number(localStorage.getItem("idUsuario"))
        }
    };
    var raw = JSON.stringify(gasto);
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
        const response = await fetch(currentURL + "/gastos", requestOptions);
        if (response.ok) {
            alert("El gasto se agrego correctamente");
            closeAddGasto();
        } else if (response.status === 403) {
            window.location.href = 'index.html';
            localStorage.removeItem("token")
            localStorage.removeItem("nombre")
            alert("No tiene acceso a esta sección");
        } else {
            throw await response.json();
        }
    } catch (error) {
        alert("Error en la solicitud: " + error.error_mensaje + "\nError Code: " + error.code);
    }
}

async function deleteGastoId(id){
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
        const response = await fetch(currentURL + "/gastos/" + id + "", requestOptions);
        if (response.ok) {
            alert("El gasto ha sido eliminado correctamente");
        } else if (response.status === 403) {
            window.location.href = 'index.html';
            localStorage.removeItem("token")
            localStorage.removeItem("nombre")
            alert("No tiene acceso a esta sección");
        } else {
            throw await response.json();
        }
    } catch (error) {
        alert("Error en la solicitud: " + error.error_mensaje + "\nError Code: " + error.code);
    }
}

async function getGastoId(id){
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
        const response = await fetch(currentURL + "/gastos/"+id+"", requestOptions);
        if (response.ok) {
            const result = await response.json();

            var descripcion = document.getElementById("descripcionUpdate");
            var fecha = document.getElementById("dateGastoUpdate");
            var monto = document.getElementById("montoUpdate");

            descripcion.value = result.descripcion
            fecha.value = result.fecha
            monto.value = result.monto
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

async function updateGasto(){
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var descripcion = document.getElementById("descripcionUpdate").value;
    var fecha = document.getElementById("dateGastoUpdate").value;
    var monto = document.getElementById("montoUpdate").value;

    var gasto = {
        "id": Number(localStorage.getItem("idGasto")),
        "fecha": fecha,
        "monto": monto,
        "descripcion": descripcion,
        "usuario": {
            "id": Number(localStorage.getItem("idUsuario"))
        }
    };

    var raw = JSON.stringify(gasto);

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
        const response = await fetch(currentURL + "/gastos/"+Number(localStorage.getItem("idGasto"))+"", requestOptions);
        if (response.ok) {
            alert("El gasto ha sido actualizado con éxito");
            closeUpdateGasto();
        } else if (response.status === 403) {
            window.location.href = 'index.html';
            localStorage.removeItem("token")
            localStorage.removeItem("nombre")
            alert("No tiene acceso a esta sección");
        } else {
            throw await response.json();
        }
    } catch (error) {
        alert("Error en la solicitud: " + error.error_mensaje + "\nError Code: " + error.code);
    }
}