# 🌱 Sistema Web para Aviación Agrícola S.A.C.

Proyecto académico desarrollado en el curso **Marcos de Desarrollo Web**, cuyo objetivo es implementar un sistema web para la **gestión y difusión de los servicios de fumigación aérea** de la empresa **Aviación Agrícola S.A.C.**, ubicada en Castilla – Piura.

[![Estado](https://img.shields.io/badge/Estado-En%20Desarrollo-green)](https://github.com/ElvisRP22/web-aviasac/projects)
[![Versión](https://img.shields.io/badge/Versión-1.0.0-blue)](https://github.com/ElvisRP22/web-aviasac/releases)
[![Java](https://img.shields.io/badge/Java-24-orange)](https://www.oracle.com/java/technologies/javase/jdk24-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://dev.mysql.com/downloads/mysql/)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

---

## 📋 Descripción del Proyecto

La empresa enfrenta dificultades en la difusión de sus servicios y la gestión de solicitudes de fumigación.  
El sistema busca solucionar estos problemas mediante una plataforma web que permita:
- Mayor **presencia digital**
- **Gestión eficiente** de solicitudes de fumigación
- **Comunicación fluida** entre la empresa y los clientes
- Acceso rápido a **información institucional y de contacto**

---

## 🎯 Objetivos

### Objetivo General
Desarrollar un sistema web que permita mejorar la gestión y difusión de los servicios de fumigación aérea de la empresa Aviación Agrícola S.A.C.

### Objetivos Específicos
- Diseñar una plataforma clara y accesible para mostrar los servicios
- Implementar un módulo de gestión de solicitudes de fumigación aérea
- Desarrollar herramientas de comunicación entre empresa y agricultores
- Incorporar un espacio institucional con misión, visión y datos de contacto

---

## 🚀 Características Principales

- 🌐 **Catálogo de Servicios**: Visualización detallada de servicios de fumigación
- 📝 **Gestión de Solicitudes**: Sistema completo de solicitudes de fumigación
- 👥 **Perfiles de Usuario**: Roles diferenciados (Admin, Cliente)
- 📱 **Diseño Responsive**: Adaptable a diferentes dispositivos
- 🔒 **Seguridad**: Autenticación y autorización robusta
- 📈 **Panel Administrativo**: Gestión completa del sistema

---

## 🛠️ Tecnologías Utilizadas

### Frontend
- **HTML5**, **CSS3**, **JavaScript**
- **Bootstrap 5**, **Font Awesome**
- **Thymeleaf** (para integración dinámica con backend)

### Backend
- **Java 17**
- **Spring Boot 3.5.5** (REST Controllers, configuración)
- **Spring Data JPA / Hibernate** (para persistencia)
- **Spring Security + JWT** (para autenticación)
- **Bean Validation** (validación de datos)
- **Lombok** (reducción de boilerplate)

### Base de Datos
- **MySQL 8.0**

### Herramientas
- **Git/GitHub** (control de versiones)
- **Maven** (gestión de dependencias)
- **Postman** (pruebas de APIs)
- **Docker** (opcional para despliegue)

---

## 📦 Estructura del Proyecto

```
web-aviasac/
├── src/
│   ├── main/
│   │   ├── java/com/aviasac/web_aviasac/
│   │   │   ├── controller/    # Controladores MVC
│   │   │   ├── model/         # Entidades JPA
│   │   │   ├── repository/    # Repositorios JPA
│   │   │   ├── service/       # Lógica de negocio
│   │   │   └── WebAviasacApplication.java
│   │   └── resources/
│   │       ├── static/        # Recursos estáticos
│   │       └── templates/     # Plantillas Thymeleaf
│   └── test/                  # Pruebas unitarias
└── pom.xml                    # Configuración Maven
```

---

## 🚀 Instalación

1. **Prerrequisitos**
   ```bash
   - Java 17
   - Maven 3.8+
   - MySQL 8.0
   ```

2. **Clonar el repositorio**
   ```bash
   git clone https://github.com/ElvisRP22/web-aviasac.git
   cd web-aviasac
   ```

3. **Configurar base de datos**
   - Crear base de datos MySQL
   - Actualizar `application.properties` con las credenciales

4. **Compilar y ejecutar**
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

5. **Acceder a la aplicación**
   ```
   http://localhost:8080
   ```

---