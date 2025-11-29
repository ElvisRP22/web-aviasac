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

function abrirModalCotizacion(boton) {
    const idServicio = boton.getAttribute('data-id');
    const nombreServicio = boton.getAttribute('data-nombre');
    document.getElementById('modal-servicio-id').value = idServicio;
    document.getElementById('modal-servicio-nombre-display').textContent = nombreServicio;

    const divPista = document.getElementById('campo-pista-aterrizaje');
    const selectPista = document.getElementById('input-pista');

    if (nombreServicio.toLowerCase().includes('avioneta')) {
        divPista.style.display = 'block';
        selectPista.setAttribute('required', 'required');
    } else {
        divPista.style.display = 'none';
        selectPista.removeAttribute('required');
        selectPista.value = "";
    }

    const modal = new bootstrap.Modal(document.getElementById('cotizacionModal'));
    modal.show();
}

document.getElementById('cotizacionModal').addEventListener('submit', function(e) {
    e.preventDefault(); 

    const form = this;
    const formData = new FormData(form);

    form.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
    form.querySelectorAll('.invalid-feedback').forEach(el => el.remove());

    fetch('/solicitudes/guardar', { 
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (response.status === 401) {
            window.location.href = '/auth/login';
            return null;
        }
        return response.json();
    })
    .then(data => {
        if (!data) return;

        if (data.status === 'success') {
            const modalEl = document.getElementById('cotizacionModal');
            const modal = bootstrap.Modal.getInstance(modalEl);
            modal.hide();
            form.reset();
            
            alert(data.message); 

        } else if (data.status === 'error' && data.errors) {
            for (const [campo, mensaje] of Object.entries(data.errors)) {
                const input = form.querySelector(`[name="${campo}"]`);
                if (input) {
                    input.classList.add('is-invalid');
                    
                    const errorDiv = document.createElement('div');
                    errorDiv.className = 'invalid-feedback';
                    errorDiv.innerText = mensaje;
                    input.parentNode.appendChild(errorDiv);
                }
            }
        } else {
            alert(data.message || 'Ocurrió un error inesperado');
        }
    })
    .catch(error => console.error('Error de red:', error));
});