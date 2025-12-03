function logout(e) {
    e.preventDefault(); // Evita que el enlace recargue la página

    Swal.fire({
        title: '¿Cerrar sesión?',
        text: "¿Estás seguro de que deseas salir?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, salir',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            // 1. Borrar el Token de LocalStorage (Fundamental para tus fetch)
            localStorage.removeItem('jwtToken');

            // 2. Borrar la Cookie (Fundamental para la navegación Thymeleaf)
            // Seteamos la fecha de expiración en el pasado para que el navegador la borre
            document.cookie = "jwtToken=; path=/; max-age=0; expires=Thu, 01 Jan 1970 00:00:00 GMT";

            // 3. Feedback visual y redirección
            Swal.fire({
                title: '¡Hasta pronto!',
                text: 'Cerrando sesión...',
                icon: 'success',
                timer: 1000,
                showConfirmButton: false
            }).then(() => {
                // Redirigir al login
                window.location.href = '/auth/login?logout=true';
            });
        }
    });
}