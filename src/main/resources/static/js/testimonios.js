document.addEventListener('DOMContentLoaded', function () {
     const loginBtn = document.getElementById("nav-login");
    const userMenu = document.getElementById("nav-user");
    const usernameSpan = document.getElementById("username");
    const logoutBtn = document.getElementById("logoutBtn");

    // Verificar si hay un usuario logeado
    const user = localStorage.getItem("usuario");

    if (user) {
        loginBtn.classList.add("d-none");   // ocultar botón login
        userMenu.classList.remove("d-none"); // mostrar usuario
        usernameSpan.textContent = user;
    }

    // Cerrar sesión
    logoutBtn.addEventListener("click", () => {
        localStorage.removeItem("usuario");
        location.reload(); // refresca la página para volver a mostrar login
    });
});