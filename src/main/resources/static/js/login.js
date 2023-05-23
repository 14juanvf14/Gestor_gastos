$(document).ready(function() {
});

async function iniciarSesion() {
    let email = document.getElementById('txtEmail').value;
    let password = document.getElementById('txtPassword').value;

    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var raw = JSON.stringify({
        "email": email,
        "password": password
    });

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    try {
        const currentURL =  window.location.protocol + "//" + window.location.hostname +":"+ window.location.port;
        const response = await fetch(currentURL+"/login", requestOptions);
        if (response.ok) {
            const result = await response.json();
            localStorage.token = result.token;
            localStorage.nombre =  result.nombre;
            var rol = result.rol;
            if (rol === "Gestor") {
                window.location.href = 'usuarios.html';
            } else if (rol === "Administrador") {
                window.location.href = 'administrador.html';
            }
        } else {
            throw await response.json();
        }
    } catch (error) {
        alert("Error en la solicitud: " + error.error_mensaje + "\nError Code: " + error.code);
    }
}