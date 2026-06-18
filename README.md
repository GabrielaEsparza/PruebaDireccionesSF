# nsmail — Formulario de captura de dirección (DE-TT04)

Aplicación web (Spring Boot + PostgreSQL) que implementa un formulario de
captura de dirección para México, usando el catálogo de estados, municipios,
localidades, colonias y códigos postales proporcionado en `catalogos_mx.sql`.

## Funcionalidad

- Al cargar la pantalla se listan los estados disponibles.
- Al elegir un estado se cargan sus municipios y localidades.
- Al capturar un código postal válido, se autoselecciona estado/municipio/
  localidad y se listan las colonias correspondientes.
- Si el código postal no existe en el catálogo, se muestra un error.
- Al hacer clic en "Continuar" se valida que los datos capturados sean
  consistentes con el catálogo.

## Stack

- Java 17, Spring Boot 4.1.0 (Gradle)
- PostgreSQL
- Frontend: HTML/CSS/JS servido como recurso estático del mismo proyecto
  (sin servidor separado)

## Requisitos previos

- JDK 17
- PostgreSQL 13+ instalado y corriendo localmente
- No se necesita instalar Gradle: el proyecto incluye el wrapper (`gradlew`)

## Configuración de la base de datos

1. Crea la base de datos vacía:

   ```bash
   createdb -U postgres catalogos_mx
   ```

2. Carga el catálogo incluido en este repo (`database/catalogos_mx.sql`):

   ```bash
   psql -U postgres -d catalogos_mx -f database/catalogos_mx.sql
   ```

3. La conexión se configura por **variables de entorno**, no hay credenciales
   escritas en el código. Variables usadas (ver
   `src/main/resources/application.properties`):

   | Variable      | Default si no se define              | Obligatoria |
   |---------------|----------------------------------------|:-----------:|
   | `DB_URL`      | `jdbc:postgresql://localhost:5432/catalogos_mx` | No |
   | `DB_USER`     | `postgres`                              | No |
   | `DB_PASSWORD` | *(sin default)*                         | **Sí** |

   Antes de levantar la app, define al menos la contraseña:

   ```bash
   export DB_PASSWORD=tu_password
   # opcional si tu usuario/URL son distintos al default:
   export DB_USER=tu_usuario
   export DB_URL=jdbc:postgresql://localhost:5432/catalogos_mx
   ```

   En Windows (PowerShell):

   ```powershell
   $env:DB_PASSWORD="tu_password"
   ```

## Despliegue / cómo correrlo

Con las variables de entorno ya exportadas en la misma terminal:

```bash
./gradlew bootRun
```

La aplicación queda disponible en `http://localhost:8080`.

Alternativamente, generar el `.jar` y ejecutarlo:

```bash
./gradlew build
java -jar build/libs/nsmail-0.0.1-SNAPSHOT.jar
```

## Endpoints expuestos

| Método | Ruta                                | Descripción                          |
|--------|--------------------------------------|---------------------------------------|
| GET    | `/api/estados`                       | Lista de estados                     |
| GET    | `/api/estado/{estado}/municipios`    | Municipios de un estado              |
| GET    | `/api/estado/{estado}/localidades`   | Localidades de un estado             |
| GET    | `/api/cp/{cp}`                       | Datos del código postal              |
| GET    | `/api/cp/{cp}/colonias`              | Colonias de un código postal         |

## Estructura relevante

```
nsmail/
├── database/
│   └── catalogos_mx.sql        # catálogo proporcionado para la prueba
├── src/main/java/com/prueba/nsmail/
│   ├── controller/              # endpoints REST
│   ├── model/                   # entidades JPA
│   ├── repository/              # repositorios Spring Data
│   └── service/                 # lógica de negocio
├── src/main/resources/
│   ├── application.properties
│   └── static/                  # frontend (HTML/CSS/JS)
└── build.gradle
```
