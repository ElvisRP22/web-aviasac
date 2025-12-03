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
            text: 'Su sesión ha expirado. Redirigiendo al login.',
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


$(document).ready(function () {
    $('#trabajoDescripcion').summernote({
        placeholder: 'Escribe la descripción del trabajo...',
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
    document.getElementById('trabajoForm').reset();
    document.getElementById('trabajoId').value = '';
    document.getElementById('modalTitle').innerText = 'Nuevo Trabajo';
    $('#trabajoDescripcion').summernote('code', '');
    
    new bootstrap.Modal(document.getElementById('trabajoModal')).show();
}

function confirmarEliminacion(id) {
    Swal.fire({
        title: "¿Eliminar trabajo?",
        text: "Esta acción no se puede deshacer.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar",
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6"
    }).then((result) => {
        if (result.isConfirmed) {
            eliminarTrabajo(id);
        }
    });
}

async function eliminarTrabajo(id) {
    const token = getJwtToken();

    if (!token) {
        Swal.fire('Error', 'Debe iniciar sesión para eliminar.', 'warning')
            .then(() => window.location.href = '/auth/login');
        return;
    }

    try {
        const response = await fetch(`/trabajos/eliminar/${id}`, {
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
        console.error("Error:", err);
        Swal.fire("Error", "No se pudo conectar con el servidor", "error");
    }
}

function editarTrabajo(btn) {
    const id = btn.getAttribute("data-id");
    const titulo = btn.getAttribute("data-titulo");
    const descripcion = btn.getAttribute("data-descripcion");
    document.getElementById("trabajoId").value = id;
    document.getElementById("trabajoTitulo").value = titulo;
    $('#trabajoDescripcion').summernote('code', descripcion);

    document.getElementById("modalTitle").innerText = "Editar Trabajo";

    new bootstrap.Modal(document.getElementById("trabajoModal")).show();
}

document.getElementById("trabajoForm").addEventListener("submit", guardarTrabajo);

async function guardarTrabajo(event) {
    event.preventDefault();

    let id = document.getElementById("trabajoId").value;
    let tituloTrabajo = document.getElementById("trabajoTitulo").value;
    let descripcion = $('#trabajoDescripcion').summernote('code');
    let imagenInput = document.querySelector('input[name="archivoImagen"]');
    let imagen = imagenInput.files[0];
    const token = getJwtToken();

    if (!token) {
        Swal.fire('Error', 'Debe iniciar sesión para guardar.', 'warning')
            .then(() => window.location.href = '/auth/login');
        return;
    }

    if (!tituloTrabajo.trim()) {
        Swal.fire("Error", "El título es obligatorio", "error");
        return;
    }

    if (tituloTrabajo.length > 200) {
        Swal.fire("Error", "El título no debe superar 200 caracteres", "error");
        return;
    }

    if (!descripcion.trim() || descripcion === '<p><br></p>') {
        Swal.fire("Error", "La descripción es obligatoria", "error");
        return;
    }

    if (descripcion.length > 5000) {
        Swal.fire("Error", "La descripción no debe superar 5000 caracteres", "error");
        return;
    }

    let formData = new FormData();
    formData.append("tituloTrabajo", tituloTrabajo);
    formData.append("descripcion", descripcion);
    if (id) {
        formData.append("id", id);
    }

    if (imagen) {
        formData.append("archivoImagen", imagen);
    }

    try {
        const response = await fetch("/trabajos/guardar", {
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