document.addEventListener('DOMContentLoaded', function () {
    
   const btnFolleto = document.getElementById('descargarFolleto');
    if (btnFolleto) {
        btnFolleto.addEventListener('click', function () {
            window.open('/Assets/documentos/Avance_Proyecto_Final_Marcos.docx', '_blank');
        });
    }

    const newsletterForm = document.getElementById('newsletterForm');
    
    if (newsletterForm) {
        newsletterForm.addEventListener('submit', function (e) {
            e.preventDefault();

            const emailInput = document.getElementById('newsletterEmail');
            const correo = emailInput.value;

            if (!correo || !isValidEmail(correo)) {
                mostrarToast('Por favor, ingrese un correo electrónico válido.', 'warning');
                return;
            }

            const formData = new FormData();
            formData.append('correo', correo);

            const token = localStorage.getItem('jwtToken');
            
            const headers = {};
            if (token) {
                headers['Authorization'] = `Bearer ${token}`;
            }

            fetch('/newsletter/suscribirse', {
                method: 'POST',
                headers: headers,
                body: formData
            })
            .then(res => {
                if (!res.ok) throw new Error('Error en la solicitud');
                return res.json();
            })
            .then(data => {
                mostrarToast(data.message, data.success ? 'success' : 'warning');
                if (data.success) {
                    newsletterForm.reset();
                }
            })
            .catch(err => {
                console.error(err);
                mostrarToast('Ocurrió un error al intentar suscribirse.', 'danger');
            });
        });
    }

    function isValidEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }
});

function mostrarToast(mensaje, tipo = 'success') {
    const toast = document.getElementById('newsletterToast');
    const mensajeEl = document.getElementById('newsletterToastMessage');

    if (toast && mensajeEl) {
        toast.classList.remove('bg-success', 'bg-danger', 'bg-warning');
        toast.classList.add(`bg-${tipo}`);
        mensajeEl.textContent = mensaje;

        toast.style.display = 'block';

        setTimeout(() => {
            toast.style.display = 'none';
        }, 4000);
    }
}

function ocultarToast() {
    const toast = document.getElementById('newsletterToast');
    if (toast) {
        toast.style.display = 'none';
    }
}