document.addEventListener('DOMContentLoaded', function () {
    // Funciones para mostrar/ocultar formularios
    window.showReservationForm = function (serviceType) {
        // Ocultar todos los formularios primero
        document.querySelectorAll('.reservation-form').forEach(form => {
            form.style.display = 'none';
        });

        // Mostrar el formulario solicitado
        document.getElementById(`${serviceType}-form`).style.display = 'block';

        // Scroll al formulario
        document.getElementById(`${serviceType}-form`).scrollIntoView({ behavior: 'smooth' });
    };

    window.hideReservationForm = function (serviceType) {
        document.getElementById(`${serviceType}-form`).style.display = 'none';
    };

    // Validación y envío de formulario para drone
    document.getElementById('droneForm').addEventListener('submit', function (e) {
        e.preventDefault();

        // Validación básica
        if (this.checkValidity()) {
            // Simular envío exitoso
            alert('¡Solicitud enviada con éxito! Nos pondremos en contacto contigo pronto.');
            this.reset();
            hideReservationForm('drone');
        } else {
            alert('Por favor, complete todos los campos obligatorios.');
        }
    });

    // Validación y envío de formulario para avioneta
    document.getElementById('avionetaForm').addEventListener('submit', function (e) {
        e.preventDefault();

        // Validación adicional para hectáreas mínimas
        const hectareas = document.getElementById('hectareas-avioneta').value;
        if (hectareas < 10) {
            alert('El servicio con avioneta requiere un mínimo de 10 hectáreas.');
            return;
        }

        if (this.checkValidity()) {
            // Simular envío exitoso
            alert('¡Solicitud enviada con éxito! Nos pondremos en contacto contigo pronto.');
            this.reset();
            hideReservationForm('avioneta');
        } else {
            alert('Por favor, complete todos los campos obligatorios.');
        }
    });

    // 11. Galería de imágenes
    window.openGalleryModal = function (src, alt) {
        const modalImage = document.getElementById('galleryModalImage');
        const modalDescription = document.getElementById('galleryModalDescription');

        modalImage.src = src;
        modalDescription.textContent = alt;

        const galleryModal = new bootstrap.Modal(document.getElementById('galleryModal'));
        galleryModal.show();
    };


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



    // Inicializar y Ocultar formularios de reserva al cargar la página
    document.querySelectorAll('.reservation-form').forEach(form => {
        form.style.display = 'none';
    });

});