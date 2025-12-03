let modalInstance;

document.addEventListener('DOMContentLoaded', function () {
    const modalElement = document.getElementById('faqModal');
    if (modalElement) {
        modalInstance = new bootstrap.Modal(modalElement);
    }
});

function getJwtToken() {
    let token = localStorage.getItem('jwtToken');

    if (!token) {
        const match = document.cookie.match(new RegExp('(^| )jwtToken=([^;]+)'));
        token = match ? match[2] : null;
    }
    return token;
}


function abrirModalCrear() {
    document.getElementById('faqForm').reset();
    document.getElementById('faqId').value = '';
    document.getElementById('modalTitle').innerText = 'Nueva Pregunta Frecuente';
    modalInstance.show();
}

function abrirModalEditar(btn) {
    document.getElementById('faqForm').reset();
    document.getElementById('faqId').value = btn.getAttribute('data-id');
    document.getElementById('faqPregunta').value = btn.getAttribute('data-pregunta');
    document.getElementById('faqRespuesta').value = btn.getAttribute('data-respuesta');
    document.getElementById('faqEstado').value = btn.getAttribute('data-estado');
    document.getElementById('modalTitle').innerText = 'Editar Pregunta Frecuente';
    modalInstance.show();
}


document.getElementById('faqForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const id = document.getElementById('faqId').value;
    const formData = new FormData(this);
    const url = id ? `/faqs/editar/${id}` : '/faqs/crear';

    const token = getJwtToken();
    if (!token) {
         Swal.fire('Error', 'Debe iniciar sesión para realizar esta acción.', 'warning')
             .then(() => window.location.href = '/auth/login');
         return;
    }

    fetch(url, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}` 
        },
        body: formData
    })
    .then(response => {
        if (response.status === 401 || response.status === 403) {
            Swal.fire('Sesión Expirada', 'Su sesión ha expirado. Redirigiendo al login.', 'warning')
                .then(() => {
                    localStorage.removeItem('jwtToken');
                    window.location.href = '/auth/login';
                });
            throw new Error("UNAUTHORIZED");
        }
        
        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        if (data.success) {
            modalInstance.hide();
            Swal.fire({
                icon: 'success',
                title: '¡Éxito!',
                text: data.message,
                showConfirmButton: false,
                timer: 1500
            }).then(() => location.reload());
        } else {
            Swal.fire('Error', data.message || 'Error inesperado', 'error');
        }
    })
    .catch(error => {
        if (error.message !== "UNAUTHORIZED") {
             console.error('Error:', error);
             Swal.fire('Error', 'No se pudo procesar la solicitud. Intente más tarde.', 'error');
        }
    });
});


function eliminarFaq(id) {
    const token = getJwtToken();

    if (!token) {
         Swal.fire('Error', 'Debe iniciar sesión para realizar esta acción.', 'warning')
             .then(() => window.location.href = '/auth/login');
         return;
    }

    Swal.fire({
        title: '¿Estás seguro?',
        text: "Esta acción no se puede revertir",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`/faqs/eliminar/${id}`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
            .then(response => {
                if (response.status === 401 || response.status === 403) {
                    Swal.fire('Sesión Expirada', 'Su sesión ha expirado. Redirigiendo al login.', 'warning')
                        .then(() => {
                             localStorage.removeItem('jwtToken');
                             window.location.href = '/auth/login';
                        });
                    throw new Error("UNAUTHORIZED");
                }
                if (!response.ok) throw new Error(`Error HTTP: ${response.status}`);
                return response.json();
            })
            .then(data => {
                if (data.success) {
                    Swal.fire('¡Eliminado!', data.message, 'success')
                        .then(() => location.reload());
                } else {
                    Swal.fire('Error', 'No se pudo eliminar', 'error');
                }
            })
            .catch(error => {
                if (error.message !== "UNAUTHORIZED") {
                    Swal.fire('Error', 'Error de conexión o token inválido', 'error');
                }
            });
        }
    });
}