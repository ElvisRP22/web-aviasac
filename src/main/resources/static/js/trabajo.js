document.getElementById('trabajoModal').addEventListener('submit', function(e) {
    e.preventDefault(); 

    const form = this;
    const formData = new FormData(form);

    form.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
    form.querySelectorAll('.invalid-feedback').forEach(el => el.remove());

    fetch('/trabajos/guardar', { 
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
            const modalEl = document.getElementById('trabajoModal');
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