document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById('login-form');
    const errorMessage = document.getElementById('error-message');

    // Credenciales de ejemplo
    const validEmail = "usuario@aviasac.com";
    const validPassword = "password123";

    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        // Validar las credenciales
        if (email === validEmail && password === validPassword) {
            // Guardar usuario en localStorage
            localStorage.setItem("usuario", "Stephano Benites"); // aquí puedes poner dinámico si lo necesitas

            // Redirigir a la página principal
            window.location.href = "/";
        } else {
            // Credenciales incorrectas - mostrar mensaje de error
            errorMessage.textContent = "Email o contraseña incorrectos. Por favor, inténtalo de nuevo.";
            errorMessage.style.display = "block";

            // Ocultar el mensaje de error después de 5 segundos
            setTimeout(function () {
                errorMessage.style.display = "none";
            }, 5000);
        }

    });

    // Verificar si hay credenciales guardadas
    const rememberedEmail = localStorage.getItem('rememberedEmail');
    if (rememberedEmail) {
        document.getElementById('email').value = rememberedEmail;
        document.getElementById('rememberMe').checked = true;
    }

    // Guardar email si la casilla "Recordarme" está marcada
    document.getElementById('rememberMe').addEventListener('change', function () {
        if (this.checked) {
            localStorage.setItem('rememberedEmail', document.getElementById('email').value);
        } else {
            localStorage.removeItem('rememberedEmail');
        }
    });
});
