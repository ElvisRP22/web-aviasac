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

        if (this.checkValidity()) {
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

        const hectareas = document.getElementById('hectareas-avioneta').value;
        if (hectareas < 10) {
            alert('El servicio con avioneta requiere un mínimo de 10 hectáreas.');
            return;
        }

        if (this.checkValidity()) {
            alert('¡Solicitud enviada con éxito! Nos pondremos en contacto contigo pronto.');
            this.reset();
            hideReservationForm('avioneta');
        } else {
            alert('Por favor, complete todos los campos obligatorios.');
        }
    });

    // Galería de imágenes
    window.openGalleryModal = function (src, alt) {
        const modalImage = document.getElementById('galleryModalImage');
        const modalDescription = document.getElementById('galleryModalDescription');

        modalImage.src = src;
        modalDescription.textContent = alt;

        const galleryModal = new bootstrap.Modal(document.getElementById('galleryModal'));
        galleryModal.show();
    };

    // Inicializar y ocultar formularios de reserva al cargar la página
    document.querySelectorAll('.reservation-form').forEach(form => {
        form.style.display = 'none';
    });
});