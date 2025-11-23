document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('contactForm').addEventListener('submit', async function (e) {
        e.preventDefault();

        if (!this.checkValidity()) {
            mostrarNotificacion("Por favor, complete todos los campos obligatorios.", "danger");
            return;
        }

        const formData = new FormData(this);

        try {
            const response = await fetch("/contacto/enviar", {
                method: "POST",
                body: formData
            });

            const data = await response.json();

            if (data.success) {
                mostrarNotificacion(data.message, "success");
                this.reset();
            } else {
                mostrarNotificacion(data.message, "danger");
            }

        } catch (error) {
            mostrarNotificacion("Ocurrió un error al enviar el mensaje.", "danger");
        }
    });

});

function mostrarNotificacion(mensaje, tipo) {
    const contenedor = document.getElementById("alertContainer");

    contenedor.innerHTML = `
        <div class="alert alert-${tipo} alert-dismissible fade show mt-3" role="alert">
            ${mensaje}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    `;
}
