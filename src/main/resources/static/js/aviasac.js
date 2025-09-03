document.addEventListener('DOMContentLoaded', function () {
    // 1. Navegación entre secciones
    const navLinks = document.querySelectorAll('.navbar-nav .nav-link');
    navLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();
            const targetId = this.getAttribute('href');
            if (targetId.startsWith('#')) {
                const targetElement = document.querySelector(targetId);
                if (targetElement) {
                    window.scrollTo({
                        top: targetElement.offsetTop - 80,
                        behavior: 'smooth'
                    });
                }
            } else {
                window.location.href = this.href;
            }
        });
    });

    // Función para descargar el folleto PDF
    document.getElementById('descargarFolleto').addEventListener('click', function (e) {
        e.preventDefault();

        const pdfUrl = '/Assets/documentos/Aviasac.pdf';

        const link = document.createElement('a');
        link.href = pdfUrl;
        link.download = 'Aviasac.pdf'; 

        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);

        alert('¡El folleto se está descargando!');
    });

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

    // 7. Formulario de contacto
    document.getElementById('contactForm').addEventListener('submit', function (e) {
        e.preventDefault();

        if (this.checkValidity()) {
            // Simular envío exitoso
            alert('¡Mensaje enviado con éxito! Te responderemos a la brevedad.');
            this.reset();
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

    // 18. Búsqueda interna
    document.querySelector('.navbar form').addEventListener('submit', function (e) {
        e.preventDefault();
        const searchTerm = this.querySelector('input[type="search"]').value.toLowerCase().trim();

        if (searchTerm) {
            // Buscar en todo el contenido de la página
            const sections = document.querySelectorAll('section');
            let found = false;

            sections.forEach(section => {
                const sectionText = section.textContent.toLowerCase();
                if (sectionText.includes(searchTerm)) {
                    // Scroll a la sección que contiene el término
                    window.scrollTo({
                        top: section.offsetTop - 80,
                        behavior: 'smooth'
                    });
                    found = true;

                    // Destacar la sección momentáneamente
                    section.style.transition = 'all 0.5s';
                    section.style.backgroundColor = 'rgba(25, 135, 84, 0.1)';

                    setTimeout(() => {
                        section.style.backgroundColor = '';
                    }, 2000);
                }
            });

            if (!found) {
                alert('No se encontraron resultados para: ' + searchTerm);
            }

            this.reset();
        }
    });

    // Inicializar y Ocultar formularios de reserva al cargar la página
    document.querySelectorAll('.reservation-form').forEach(form => {
        form.style.display = 'none';
    });
});