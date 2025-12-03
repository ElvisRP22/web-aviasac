document.addEventListener('DOMContentLoaded', function () {
    window.showReservationForm = function (serviceType) {
        document.querySelectorAll('.reservation-form').forEach(form => {
            form.style.display = 'none';
        });

        document.getElementById(`${serviceType}-form`).style.display = 'block';
        document.getElementById(`${serviceType}-form`).scrollIntoView({ behavior: 'smooth' });
    };

    window.hideReservationForm = function (serviceType) {
        document.getElementById(`${serviceType}-form`).style.display = 'none';
    };

    window.openGalleryModal = function (src, alt) {
        const modalImage = document.getElementById('galleryModalImage');
        const modalDescription = document.getElementById('galleryModalDescription');

        modalImage.src = src;
        modalDescription.textContent = alt;

        const galleryModal = new bootstrap.Modal(document.getElementById('galleryModal'));
        galleryModal.show();
    };

    document.querySelectorAll('.reservation-form').forEach(form => {
        form.style.display = 'none';
    });

    const formCotizacion = document.getElementById('formCotizacion');
    if (formCotizacion) {
        formCotizacion.addEventListener('submit', enviarSolicitud);
    }
});

function abrirModalCotizacion(boton) {
    const idServicio = boton.getAttribute('data-id');
    const nombreServicio = boton.getAttribute('data-nombre');
    
    document.getElementById('modal-servicio-id').value = idServicio;
    document.getElementById('modal-servicio-nombre-display').textContent = nombreServicio;

    const divPista = document.getElementById('campo-pista-aterrizaje');
    const selectPista = document.getElementById('input-pista');

    if (nombreServicio && nombreServicio.toLowerCase().includes('avioneta')) {
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

async function enviarSolicitud(e) {
    e.preventDefault();

    const form = e.target;
    const formData = new FormData(form);
    let token = localStorage.getItem('jwtToken');
    
    
    if (!token) {
        const match = document.cookie.match(new RegExp('(^| )jwtToken=([^;]+)'));
        token = match ? match[2] : null;
    }

    const headers = {};
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    } else {
        Swal.fire({
             icon: 'warning',
             title: 'Sesión Requerida',
             text: 'Debe iniciar sesión para enviar una solicitud.',
             confirmButtonColor: '#28a745',
             confirmButtonText: 'Ir al Login'
         }).then(() => {
             window.location.href = '/auth/login';
         });
         return;
    }

    form.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
    form.querySelectorAll('.invalid-feedback').forEach(el => el.remove());

    Swal.fire({
        title: 'Enviando solicitud...',
        text: 'Por favor espere',
        allowOutsideClick: false,
        allowEscapeKey: false,
        showConfirmButton: false,
        didOpen: () => {
            Swal.showLoading();
        }
    });

    try {
        const response = await fetch('/solicitudes/guardar', {
            method: 'POST',
            body: formData,
            headers: headers 
        });

        if (response.status === 401 || response.status === 403) {
            Swal.fire({
                icon: 'warning',
                title: 'Sesión Expirada',
                text: 'Por favor inicie sesión nuevamente para continuar.',
                confirmButtonColor: '#28a745',
                confirmButtonText: 'Ir al Login'
            }).then(() => {
                localStorage.removeItem('jwtToken');
                document.cookie = "jwtToken=; path=/; max-age=0"; 
                window.location.href = '/auth/login';
            });
            return;
        }

        const data = await response.json();

        if (data.status === 'success') {
            const modalEl = document.getElementById('cotizacionModal');
            const modal = bootstrap.Modal.getInstance(modalEl);
            modal.hide();
            
            form.reset();

            Swal.fire({
                icon: 'success',
                title: '¡Solicitud Enviada!',
                html: data.message,
                confirmButtonColor: '#28a745',
                confirmButtonText: 'Entendido'
            });

        } else if (data.status === 'error' && data.errors) {
            Swal.close();

            let primeraVez = true;
            for (const [campo, mensaje] of Object.entries(data.errors)) {
                const input = form.querySelector(`[name="${campo}"]`);
                if (input) {
                    input.classList.add('is-invalid');

                    const errorDiv = document.createElement('div');
                    errorDiv.className = 'invalid-feedback';
                    errorDiv.style.display = 'block';
                    errorDiv.innerText = mensaje;
                    input.parentNode.appendChild(errorDiv);

                    if (primeraVez) {
                        input.scrollIntoView({ behavior: 'smooth', block: 'center' });
                        input.focus();
                        primeraVez = false;
                    }
                }
            }

            Swal.fire({
                icon: 'error',
                title: 'Errores de Validación',
                text: 'Por favor corrija los campos marcados en rojo',
                confirmButtonColor: '#dc3545'
            });

        } else {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: data.message || 'Ocurrió un error inesperado',
                confirmButtonColor: '#dc3545'
            });
        }
    } catch (error) {
        console.error('Error de red:', error);
        Swal.fire({
            icon: 'error',
            title: 'Error de Conexión',
            text: 'No se pudo conectar con el servidor. Por favor, verifique su conexión a internet.',
            confirmButtonColor: '#dc3545'
        });
    }
}

function verDetalleTrabajo(btn) {
    const titulo = btn.getAttribute('data-titulo');
    const descripcion = btn.getAttribute('data-descripcion');
    const imagen = btn.getAttribute('data-imagen');

    document.getElementById('modalTrabajoTitulo').textContent = titulo;
    document.getElementById('modalTrabajoDescripcion').innerHTML = descripcion;
    const imgElement = document.getElementById('modalTrabajoImagen');
    const imgContainer = document.getElementById('modalImagenContainer');

    if (imagen && imagen !== 'null' && imagen !== '') {
        imgElement.src = '/uploads/trabajos/' + imagen;
        imgElement.alt = titulo;
        imgContainer.style.display = 'block';
    } else {
        imgContainer.style.display = 'none';
    }
}

document.addEventListener('input', function(e) {
    if (e.target.classList.contains('is-invalid')) {
        e.target.classList.remove('is-invalid');
        const errorDiv = e.target.parentNode.querySelector('.invalid-feedback');
        if (errorDiv) {
            errorDiv.remove();
        }
    }
});

document.getElementById('cotizacionModal')?.addEventListener('hide.bs.modal', function (e) {
    const form = document.getElementById('formCotizacion');
    const hasErrors = form.querySelectorAll('.is-invalid').length > 0;
    
    if (hasErrors && e.target === this) {
        e.preventDefault();
        Swal.fire({
            title: '¿Desea cerrar?',
            text: 'Hay errores en el formulario sin corregir',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#dc3545',
            cancelButtonColor: '#6c757d',
            confirmButtonText: 'Sí, cerrar',
            cancelButtonText: 'Continuar editando'
        }).then((result) => {
            if (result.isConfirmed) {
                const modal = bootstrap.Modal.getInstance(this);
                form.reset();
                form.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
                form.querySelectorAll('.invalid-feedback').forEach(el => el.remove());
                modal.hide();
            }
        });
    }
});