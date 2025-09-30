document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById('loginForm');
    const errorMessage = document.getElementById('error-message');

    // Verificar que los elementos existan
    if (!loginForm || !errorMessage) {
        console.error('No se encontraron los elementos del formulario');
        return;
    }

    // Credenciales de ejemplo
    const usuarios = [
        {
            email: "usuario@aviasac.com",
            password: "password123",
            nombre: "Stephano Benites",
            rol: "usuario"
        },
        {
            email: "admin@aviasac.com",
            password: "admin123",
            nombre: "Administrador",
            rol: "administrador"
        }
    ];

    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        console.log('Intentando login con:', email, password); // Para debug

        // Validar campos vacíos
        if (!email || !password) {
            mostrarError("Por favor, complete todos los campos.");
            return;
        }

        // Buscar usuario en el array de credenciales
        const usuario = usuarios.find(user =>
            user.email === email && user.password === password
        );

        if (usuario) {
            console.log('Usuario encontrado:', usuario); // Para debug
            // Guardar usuario en localStorage
            localStorage.setItem("usuario", usuario.nombre);
            localStorage.setItem("rol", usuario.rol);
            localStorage.setItem("email", usuario.email);

            // Mostrar mensaje de éxito
            mostrarExito(`¡Bienvenido ${usuario.nombre}! Redirigiendo...`);

            // Redirigir según el rol después de un breve delay
            setTimeout(() => {
                if (usuario.rol === "administrador") {
                    window.location.href = "/admin";
                } else {
                    window.location.href = "/";
                }
            }, 1500);

        } else {
            console.log('Credenciales incorrectas'); // Para debug
            mostrarError("Email o contraseña incorrectos. Por favor, inténtalo de nuevo.");
        }
    });

    // Función para mostrar mensajes de error
    function mostrarError(mensaje) {
        errorMessage.textContent = mensaje;
        errorMessage.style.display = "block";
        errorMessage.className = "alert alert-danger";
    }

    // Función para mostrar mensajes de éxito
    function mostrarExito(mensaje) {
        errorMessage.textContent = mensaje;
        errorMessage.style.display = "block";
        errorMessage.className = "alert alert-success";

        setTimeout(function () {
            errorMessage.style.display = "none";
        }, 3000);
    }

    // Verificar si hay credenciales guardadas
    const rememberedEmail = localStorage.getItem('rememberedEmail');
    if (rememberedEmail) {
        document.getElementById('email').value = rememberedEmail;
        document.getElementById('rememberMe').checked = true;
    }

    // Guardar email si la casilla "Recordarme" está marcada
    const rememberMe = document.getElementById('rememberMe');
    if (rememberMe) {
        rememberMe.addEventListener('change', function () {
            const email = document.getElementById('email').value;
            if (this.checked && email) {
                localStorage.setItem('rememberedEmail', email);
            } else {
                localStorage.removeItem('rememberedEmail');
            }
        });
    }
});