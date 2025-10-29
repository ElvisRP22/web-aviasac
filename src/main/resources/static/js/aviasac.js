document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('descargarFolleto').addEventListener('click', function () {
        window.open('/Assets/documentos/Avance_Proyecto_Final_Marcos.docx', '_blank');

    });

    const loginBtn = document.getElementById("nav-login");
    const userMenu = document.getElementById("nav-user");
    const usernameSpan = document.getElementById("username");
    const logoutBtn = document.getElementById("logoutBtn");

    // Verificar si hay un usuario logeado
    const user = localStorage.getItem("usuario");

    if (user) {
        loginBtn.classList.add("d-none");   // ocultar botón login
        userMenu.classList.remove("d-none"); // mostrar usuario
        usernameSpan.textContent = user;
    }

    // Cerrar sesión
    logoutBtn.addEventListener("click", () => {
        localStorage.removeItem("usuario");
        location.reload(); // refresca la página para volver a mostrar login
    });

    // 13. Suscripción a newsletter
    document.getElementById('newsletterForm').addEventListener('submit', function (e) {
        e.preventDefault();

        const email = document.getElementById('newsletterEmail').value;
        if (email && isValidEmail(email)) {
            alert('¡Gracias por suscribirte a nuestro boletín!');
            this.reset();
        } else {
            alert('Por favor, ingrese un correo electrónico válido.');
        }
    });

    function isValidEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }

});

// Suscribirse a newsletter
document.getElementById('newsletterForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const correo = document.getElementById('newsletterEmail').value;
    const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    const formData = new FormData();
    formData.append('correo', correo);

    fetch('/newsletter/suscribirse', {
        method: 'POST',
        body: formData,
        headers: {
            [header]: token
        }
    })
    .then(res => res.json())
    .then(data => {
        mostrarToast(data.message, data.success ? 'success' : 'warning');
        if (data.success) {
            document.getElementById('newsletterForm').reset();
        }
    })
    .catch(err => {
        console.error(err);
        mostrarToast('Error al suscribirse', 'danger');
    });
});

// Mostrar mensaje toast
function mostrarToast(mensaje, tipo = 'success') {
    const toast = document.getElementById('newsletterToast');
    const mensajeEl = document.getElementById('newsletterToastMessage');

    toast.classList.remove('bg-success', 'bg-danger', 'bg-warning');
    toast.classList.add(`bg-${tipo}`);
    mensajeEl.textContent = mensaje;

    toast.style.display = 'block';

    setTimeout(() => {
        toast.style.display = 'none';
    }, 4000);
}

function ocultarToast() {
    document.getElementById('newsletterToast').style.display = 'none';
}