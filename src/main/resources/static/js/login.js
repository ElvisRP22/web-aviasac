document.getElementById('loginForm').addEventListener('submit', function (e) {
    e.preventDefault(); // Evitar recarga

    const userField = document.getElementById('username');
    const passField = document.getElementById('password');
    const btnLogin = document.getElementById('btnLogin');
    const btnText = document.getElementById('btnText');
    const btnSpinner = document.getElementById('btnSpinner');

    // UI Loading
    userField.disabled = true;
    passField.disabled = true;
    btnLogin.disabled = true;
    btnText.textContent = "Verificando...";
    btnSpinner.classList.remove('d-none');

    // Datos a enviar
    const data = {
        username: userField.value,
        password: passField.value
    };

    fetch('/auth/token', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Credenciales incorrectas');
            }
            return response.json();
        })
        .then(authResponse => {
            // 1. Guardar Token
            localStorage.setItem('jwtToken', authResponse.token);

            // 2. (Opcional) Guardar en Cookie para compatibilidad básica
            document.cookie = `jwtToken=${authResponse.token}; path=/; max-age=86400`;

            // 3. Éxito
            Swal.fire({
                icon: 'success',
                title: '¡Bienvenido!',
                text: 'Redirigiendo al sistema...',
                timer: 1500,
                showConfirmButton: false
            }).then(() => {
                // Redirigir al home o dashboard
                window.location.href = '/';
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Error de Acceso',
                text: 'Usuario o contraseña incorrectos',
                confirmButtonColor: '#d33'
            });

            // Reset UI
            userField.disabled = false;
            passField.disabled = false;
            btnLogin.disabled = false;
            btnText.textContent = "Iniciar Sesión";
            btnSpinner.classList.add('d-none');
        });
});