function getJwtToken() {
    let token = localStorage.getItem('jwtToken');
    if (!token) {
        const match = document.cookie.match(new RegExp('(^| )jwtToken=([^;]+)'));
        token = match ? match[2] : null;
    }
    return token;
}

function handleAuthError(responseStatus) {
    if (responseStatus === 401 || responseStatus === 403) {
        Swal.fire({
            icon: 'warning',
            title: 'Sesión Expirada',
            text: 'Su sesión ha expirado. Por favor inicie sesión nuevamente.',
            confirmButtonColor: '#28a745',
            confirmButtonText: 'Ir al Login'
        }).then(() => {
            localStorage.removeItem('jwtToken');
            window.location.href = '/auth/login';
        });
        return true;
    }
    return false;
}


document.getElementById("servicioForm").addEventListener("submit", guardarServicio);
$(document).ready(function () {
    $('#servicioDesc').summernote({
        placeholder: 'Escribe la descripción detallada del servicio...',
        tabsize: 2,
        height: 150,
        toolbar: [
            ['style', ['style']],
            ['font', ['bold', 'underline', 'clear']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['insert', ['link']],
            ['view', ['fullscreen', 'codeview', 'help']]
        ]
    });
});

function abrirModalNuevo() {
    document.getElementById('servicioForm').reset();
    document.getElementById('servicioId').value = '';
    document.getElementById('modalTitle').innerText = 'Nuevo Servicio';

    $('#servicioDesc').summernote('code', '');

    new bootstrap.Modal(document.getElementById('servicioModal')).show();
}

function confirmarEliminacion(id) {
    Swal.fire({
        title: "¿Eliminar servicio?",
        text: "Esta acción no se puede deshacer.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar"
    }).then((result) => {
        if (result.isConfirmed) {
            eliminarServicio(id);
        }
    });
}

async function eliminarServicio(id) {
    const token = getJwtToken();

    if (!token) {
         Swal.fire('Error', 'Debe iniciar sesión para eliminar.', 'warning')
             .then(() => window.location.href = '/auth/login');
         return;
    }

    try {
        const response = await fetch(`/servicios/eliminar/${id}`, {
            method: "DELETE",
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (handleAuthError(response.status)) return;

        const result = await response.json();

        if (result.success) {
            Swal.fire("Eliminado", result.message, "success")
                .then(() => { location.reload(); });
        } else {
            Swal.fire("Error", result.message, "error");
        }
    } catch (err) {
        Swal.fire("Error", "No se pudo conectar con el servidor.", "error");
    }
}

function editarServicio(btn) {
    const id = btn.getAttribute("data-id");
    const nombre = btn.getAttribute("data-nombre");
    const descripcion = btn.getAttribute("data-descripcion");

    document.getElementById("servicioId").value = id;
    document.getElementById("servicioNombre").value = nombre;

    $('#servicioDesc').summernote('code', descripcion);

    document.getElementById("modalTitle").innerText = "Editar Servicio";

    new bootstrap.Modal(document.getElementById("servicioModal")).show();
}


async function guardarServicio(event) {
    event.preventDefault();

    let id = document.getElementById("servicioId").value;
    let nombre = document.getElementById("servicioNombre").value;
    let descripcion = $('#servicioDesc').summernote('code');
    let imagenInput = document.querySelector('input[name="archivoImagen"]');
    let imagen = imagenInput.files[0];
    const token = getJwtToken();

    if (!token) {
         Swal.fire('Error', 'Debe iniciar sesión para guardar.', 'warning')
             .then(() => window.location.href = '/auth/login');
         return;
    }

    let formData = new FormData();
    formData.append("nombre", nombre);
    formData.append("descripcion", descripcion);
    
    if (id) {
        formData.append("id", id);
    }

    if (imagen) {
        formData.append("archivoImagen", imagen);
    }

    try {
        const response = await fetch("/servicios/guardar", {
            method: "POST",
            body: formData,
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (handleAuthError(response.status)) return;

        const result = await response.json();

        if (response.ok) {
            Swal.fire("Éxito", result.message, "success")
                .then(() => location.reload());
        } else {
            Swal.fire("Error", result.message || "No se pudo guardar", "error");
        }
    } catch (error) {
        console.error("Error:", error);
        Swal.fire("Error", "No se pudo conectar con el servidor", "error");
    }
}