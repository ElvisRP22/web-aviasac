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

document.getElementById('filtroEstado')?.addEventListener('change', function() {
    const estado = this.value;
    const filas = document.querySelectorAll('#tablaSolicitudes tbody tr');
    
    filas.forEach(fila => {
        const badge = fila.querySelector('td:nth-child(9) .badge');
        if (badge) {
            const estadoActual = badge.textContent.trim().toUpperCase().replace(' ', '_');
            
            if (estado === '' || estadoActual === estado) {
                fila.style.display = '';
            } else {
                fila.style.display = 'none';
            }
        }
    });
});

async function verDetalle(btn) {
    const id = btn.getAttribute('data-id');
    const token = getJwtToken();

    if (!token) {
        Swal.fire('Error', 'Debe iniciar sesión para ver el detalle.', 'warning')
            .then(() => window.location.href = '/auth/login');
        return;
    }
    
    try {
        const response = await fetch(`/solicitudes/detalle/${id}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        
        if (handleAuthError(response.status)) return;
        if (!response.ok) throw new Error(`Error HTTP: ${response.status}`);
        
        const result = await response.json();
        
        if (result.success) {
            const sol = result.solicitud;
            
            document.getElementById('detId').textContent = sol.id;
            document.getElementById('detCliente').textContent = sol.cliente;
            document.getElementById('detEmail').textContent = sol.email;
            document.getElementById('detTelefono').textContent = sol.telefono;
            document.getElementById('detServicio').textContent = sol.servicio;
            document.getElementById('detCultivo').textContent = sol.cultivo;
            document.getElementById('detHectareas').textContent = sol.numHectareas + ' hectáreas';
            document.getElementById('detUbicacion').textContent = sol.ubicacion;
            document.getElementById('detArea').textContent = sol.areaParaAterrizar || 'No especificado';
            document.getElementById('detMensaje').textContent = sol.mensaje || 'Sin mensaje adicional';
            document.getElementById('detFechaSolicitud').textContent = 
                new Date(sol.fechaSolicitud).toLocaleString('es-PE');
            document.getElementById('detFechaPreferida').textContent = 
                sol.fechaPreferida ? new Date(sol.fechaPreferida).toLocaleDateString('es-PE') : 'Sin fecha preferida';
            
            const badgeEstado = document.getElementById('detEstado');
            badgeEstado.textContent = sol.estado.replace('_', ' ');
            badgeEstado.className = 'badge ';
            
            switch(sol.estado) {
                case 'PENDIENTE': badgeEstado.classList.add('bg-warning', 'text-dark'); break;
                case 'EN_PROCESO': badgeEstado.classList.add('bg-info'); break;
                case 'COMPLETADA': badgeEstado.classList.add('bg-success'); break;
                case 'CANCELADA': badgeEstado.classList.add('bg-danger'); break;
            }
            
            new bootstrap.Modal(document.getElementById('detalleModal')).show();
        } else {
            Swal.fire('Error', result.message, 'error');
        }
    } catch (error) {
        if (!error.message.includes("UNAUTHORIZED")) {
            console.error('Error:', error);
            Swal.fire('Error', 'No se pudo cargar el detalle', 'error');
        }
    }
}

async function cambiarEstado(id, nuevoEstado) {
    const token = getJwtToken();

    if (!token) {
        Swal.fire('Error', 'Debe iniciar sesión para actualizar el estado.', 'warning')
            .then(() => window.location.href = '/auth/login');
        return;
    }

    try {
        const response = await fetch(`/solicitudes/actualizar-estado/${id}?estado=${nuevoEstado}`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        
        if (handleAuthError(response.status)) return; // Manejar 401/403
        if (!response.ok) throw new Error(`Error HTTP: ${response.status}`);
        
        const result = await response.json();
        
        if (result.success) {
            Swal.fire('Éxito', result.message, 'success')
                .then(() => location.reload());
        } else {
            Swal.fire('Error', result.message, 'error');
        }
    } catch (error) {
        if (!error.message.includes("UNAUTHORIZED")) {
            console.error('Error:', error);
            Swal.fire('Error', 'No se pudo actualizar el estado', 'error');
        }
    }
}

function confirmarEliminacion(id) {
    Swal.fire({
        title: '¿Eliminar solicitud?',
        text: 'Esta acción no se puede deshacer',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            eliminarSolicitud(id);
        }
    });
}

async function eliminarSolicitud(id) {
    const token = getJwtToken();

    if (!token) {
        Swal.fire('Error', 'Debe iniciar sesión para eliminar.', 'warning')
            .then(() => window.location.href = '/auth/login');
        return;
    }

    try {
        const response = await fetch(`/solicitudes/eliminar/${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        
        if (handleAuthError(response.status)) return;
        if (!response.ok) throw new Error(`Error HTTP: ${response.status}`);
        
        const result = await response.json();
        
        if (result.success) {
            Swal.fire('Eliminado', result.message, 'success')
                .then(() => location.reload());
        } else {
            Swal.fire('Error', result.message, 'error');
        }
    } catch (error) {
        if (!error.message.includes("UNAUTHORIZED")) {
            console.error('Error:', error);
            Swal.fire('Error', 'No se pudo eliminar la solicitud', 'error');
        }
    }
}