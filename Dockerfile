# Etapa 1: Construcción (Build)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Empaquetar saltando los tests para agilizar el deploy en la nube
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Runtime) -> Imagen ligera
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
# Copiamos el JAR generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Puerto que expone la app
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]