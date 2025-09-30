// Función para cambiar entre secciones
function showSection(sectionId) {
    // Ocultar todas las secciones
    document.querySelectorAll('.section').forEach(section => {
        section.classList.remove('active');
    });

    // Mostrar la sección seleccionada
    document.getElementById(sectionId).classList.add('active');

    // Actualizar enlaces activos del sidebar
    document.querySelectorAll('.sidebar a').forEach(link => {
        link.classList.remove('active');
    });
    document.querySelector(`[data-section="${sectionId}"]`).classList.add('active');

    // Actualizar título
    const titles = {
        'dashboard': 'Dashboard de Administración',
        'cotizaciones': 'Gestión de Cotizaciones',
        'newsletter': 'Suscriptores Newsletter',
        'contactos': 'Consultas de Contacto',
        'reportes': 'Reportes y Estadísticas'
    };
    document.getElementById('section-title').textContent = titles[sectionId];
}

// Configurar event listeners para los enlaces del sidebar
document.querySelectorAll('.sidebar a[data-section]').forEach(link => {
    link.addEventListener('click', function(e) {
        e.preventDefault();
        const sectionId = this.getAttribute('data-section');
        showSection(sectionId);
    });
});

// Datos de ejemplo para las tablas
const cotizaciones = [
    { id: 1, cliente: "Juan Pérez", servicio: "Fumigación con Drone", fecha: "2023-10-15", estado: "Pendiente" },
    { id: 2, cliente: "María García", servicio: "Fumigación con Avioneta", fecha: "2023-10-14", estado: "Completada" },
    { id: 3, cliente: "Carlos López", servicio: "Asesoría Agrícola", fecha: "2023-10-13", estado: "Pendiente" },
    { id: 4, cliente: "Ana Rodríguez", servicio: "Monitoreo de Cultivos", fecha: "2023-10-12", estado: "Cancelada" },
    { id: 5, cliente: "Pedro Martínez", servicio: "Fumigación con Drone", fecha: "2023-10-11", estado: "Completada" }
];

const suscriptores = [
    { email: "cliente1@example.com", fecha: "2023-09-20" },
    { email: "cliente2@example.com", fecha: "2023-09-18" },
    { email: "cliente3@example.com", fecha: "2023-09-15" },
    { email: "cliente4@example.com", fecha: "2023-09-10" },
    { email: "cliente5@example.com", fecha: "2023-09-05" },
    { email: "cliente6@example.com", fecha: "2023-08-28" }
];

const contactos = [
    { nombre: "Roberto Silva", email: "roberto@example.com", telefono: "987654321", asunto: "Consulta sobre servicios", fecha: "2023-10-16", estado: "Pendiente" },
    { nombre: "Laura Mendoza", email: "laura@example.com", telefono: "987654322", asunto: "Solicitud de información", fecha: "2023-10-15", estado: "Respondida" },
    { nombre: "Javier Ruiz", email: "javier@example.com", telefono: "987654323", asunto: "Cotización urgente", fecha: "2023-10-14", estado: "Pendiente" },
    { nombre: "Sofía Castro", email: "sofia@example.com", telefono: "987654324", asunto: "Consulta técnica", fecha: "2023-10-13", estado: "Respondida" }
];

// Función para cargar datos en las tablas
function cargarDatos() {
    // Cargar cotizaciones
    const cotizacionesBody = document.querySelector('#cotizaciones tbody');
    if (cotizacionesBody) {
        cotizacionesBody.innerHTML = '';
        cotizaciones.forEach(cotizacion => {
            const badgeClass = cotizacion.estado === 'Pendiente' ? 'badge-pending' :
                             cotizacion.estado === 'Completada' ? 'badge-completed' : 'badge-cancelled';

            const row = `
                <tr>
                    <td>${cotizacion.id}</td>
                    <td>${cotizacion.cliente}</td>
                    <td>${cotizacion.servicio}</td>
                    <td>${cotizacion.fecha}</td>
                    <td><span class="badge ${badgeClass}">${cotizacion.estado}</span></td>
                    <td>
                        <button class="btn btn-sm btn-primary btn-action" onclick="verDetalleCotizacion(${cotizacion.id})">
                            <i class="fas fa-eye"></i>
                        </button>
                        <button class="btn btn-sm btn-success btn-action" onclick="marcarCompletada(${cotizacion.id})">
                            <i class="fas fa-check"></i>
                        </button>
                    </td>
                </tr>
            `;
            cotizacionesBody.innerHTML += row;
        });
    }

    // Cargar suscriptores
    const newsletterBody = document.querySelector('#newsletter tbody');
    if (newsletterBody) {
        newsletterBody.innerHTML = '';
        suscriptores.forEach(suscriptor => {
            const row = `
                <tr>
                    <td>${suscriptor.email}</td>
                    <td>${suscriptor.fecha}</td>
                    <td>
                        <button class="btn btn-sm btn-danger btn-action" onclick="eliminarSuscriptor('${suscriptor.email}')">
                            <i class="fas fa-trash"></i>
                        </button>
                    </td>
                </tr>
            `;
            newsletterBody.innerHTML += row;
        });
    }

    // Cargar contactos
    const contactosBody = document.querySelector('#contactos tbody');
    if (contactosBody) {
        contactosBody.innerHTML = '';
        contactos.forEach(contacto => {
            const badgeClass = contacto.estado === 'Pendiente' ? 'badge-pending' : 'badge-completed';

            const row = `
                <tr>
                    <td>${contacto.nombre}</td>
                    <td>${contacto.email}</td>
                    <td>${contacto.telefono}</td>
                    <td>${contacto.asunto}</td>
                    <td>${contacto.fecha}</td>
                    <td><span class="badge ${badgeClass}">${contacto.estado}</span></td>
                    <td>
                        <button class="btn btn-sm btn-primary btn-action" onclick="verDetalleContacto('${contacto.email}')">
                            <i class="fas fa-eye"></i>
                        </button>
                        <button class="btn btn-sm btn-success btn-action" onclick="marcarRespondida('${contacto.email}')">
                            <i class="fas fa-reply"></i>
                        </button>
                    </td>
                </tr>
            `;
            contactosBody.innerHTML += row;
        });
    }

    // Actualizar estadísticas del dashboard
    actualizarEstadisticasDashboard();
}

// Función para actualizar las estadísticas del dashboard
function actualizarEstadisticasDashboard() {
    const totalCotizaciones = cotizaciones.length;
    const totalSuscriptores = suscriptores.length;
    const totalContactos = contactos.length;

    const pendientesCotizaciones = cotizaciones.filter(c => c.estado === 'Pendiente').length;
    const pendientesContactos = contactos.filter(c => c.estado === 'Pendiente').length;
    const totalPendientes = pendientesCotizaciones + pendientesContactos;

    // Actualizar los números en el dashboard
    const cotizacionesElement = document.querySelector('.stats-card:nth-child(1) .stats-number');
    const suscriptoresElement = document.querySelector('.stats-card:nth-child(2) .stats-number');
    const contactosElement = document.querySelector('.stats-card:nth-child(3) .stats-number');
    const pendientesElement = document.querySelector('.stats-card:nth-child(4) .stats-number');

    if (cotizacionesElement) cotizacionesElement.textContent = totalCotizaciones;
    if (suscriptoresElement) suscriptoresElement.textContent = totalSuscriptores;
    if (contactosElement) contactosElement.textContent = totalContactos;
    if (pendientesElement) pendientesElement.textContent = totalPendientes;

    // Actualizar acciones requeridas
    actualizarAccionesRequeridas(pendientesCotizaciones, pendientesContactos);
}

// Función para actualizar las acciones requeridas
function actualizarAccionesRequeridas(pendientesCotizaciones, pendientesContactos) {
    const accionesList = document.querySelector('#dashboard .list-group');
    if (accionesList) {
        accionesList.innerHTML = `
            <li class="list-group-item">
                <i class="fas fa-clock text-warning me-2"></i>
                ${pendientesCotizaciones} Cotizaciones pendientes de respuesta
            </li>
            <li class="list-group-item">
                <i class="fas fa-envelope text-primary me-2"></i>
                ${pendientesContactos} Consultas sin responder
            </li>
            <li class="list-group-item">
                <i class="fas fa-chart-line text-success me-2"></i>
                Reporte mensual listo para generar
            </li>
        `;
    }
}

// Funciones de acción
function verDetalleCotizacion(id) {
    const cotizacion = cotizaciones.find(c => c.id === id);
    if (cotizacion) {
        alert(`Detalles de la Cotización:\n\nID: ${cotizacion.id}\nCliente: ${cotizacion.cliente}\nServicio: ${cotizacion.servicio}\nFecha: ${cotizacion.fecha}\nEstado: ${cotizacion.estado}`);
    }
}

function marcarCompletada(id) {
    if (confirm('¿Está seguro de marcar esta cotización como completada?')) {
        const cotizacion = cotizaciones.find(c => c.id === id);
        if (cotizacion) {
            cotizacion.estado = 'Completada';
            alert(`Cotización ${id} marcada como completada`);
            cargarDatos(); // Recargar datos
        }
    }
}

function eliminarSuscriptor(email) {
    if (confirm(`¿Está seguro de eliminar al suscriptor ${email}?`)) {
        const index = suscriptores.findIndex(s => s.email === email);
        if (index !== -1) {
            suscriptores.splice(index, 1);
            alert(`Suscriptor ${email} eliminado`);
            cargarDatos(); // Recargar datos
        }
    }
}

function verDetalleContacto(email) {
    const contacto = contactos.find(c => c.email === email);
    if (contacto) {
        alert(`Detalles del Contacto:\n\nNombre: ${contacto.nombre}\nEmail: ${contacto.email}\nTeléfono: ${contacto.telefono}\nAsunto: ${contacto.asunto}\nFecha: ${contacto.fecha}\nEstado: ${contacto.estado}`);
    }
}

function marcarRespondida(email) {
    if (confirm('¿Está seguro de marcar esta consulta como respondida?')) {
        const contacto = contactos.find(c => c.email === email);
        if (contacto) {
            contacto.estado = 'Respondida';
            alert(`Consulta de ${email} marcada como respondida`);
            cargarDatos(); // Recargar datos
        }
    }
}

// Función para exportar a CSV (simulada)
function exportToCSV(tipo) {
    let datos = [];
    let nombreArchivo = '';

    switch(tipo) {
        case 'cotizaciones':
            datos = cotizaciones;
            nombreArchivo = 'cotizaciones.csv';
            break;
        case 'newsletter':
            datos = suscriptores;
            nombreArchivo = 'suscriptores.csv';
            break;
        case 'contactos':
            datos = contactos;
            nombreArchivo = 'contactos.csv';
            break;
    }

    alert(`Exportando ${datos.length} registros de ${tipo} a ${nombreArchivo}`);
    // Aquí iría la lógica real para exportar a CSV
}

// Cargar datos cuando la página esté lista
document.addEventListener('DOMContentLoaded', function() {
    cargarDatos();

    // Configurar event listeners para las tarjetas del dashboard
    document.querySelectorAll('.stats-card').forEach((card, index) => {
        card.addEventListener('click', function() {
            const sections = ['cotizaciones', 'newsletter', 'contactos', 'dashboard'];
            if (sections[index]) {
                showSection(sections[index]);
            }
        });
    });
});