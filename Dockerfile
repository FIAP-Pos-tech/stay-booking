# Etapa de compilação
FROM openjdk:17-slim as build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw package -DskipTests

# Etapa de execução
FROM openjdk:17-slim

# Copia o artefato compilado da etapa de build para a imagem de execução
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta que a aplicação usa
EXPOSE 8083

# Comando para executar a aplicação
ENTRYPOINT ["java","-jar","/app.jar"]