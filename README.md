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
- Al hacer clic en "Continuar" se valida en el servidor que los datos
  capturados sean consistentes entre sí contra el catálogo (municipio
  pertenece al estado, localidad pertenece al estado, el código postal
  existe y coincide con el estado, la colonia pertenece al código postal).

## Stack

- Java 17, Spring Boot 4.1.0 (Gradle)
- PostgreSQL
- Frontend: HTML/CSS/JS (Bootstrap + SweetAlert2) servido como recurso
  estático del mismo proyecto (sin servidor separado)

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

   | Variable      | Default si no se define                          | Obligatoria |
      |---------------|---------------------------------------------------|:-----------:|
   | `DB_URL`      | `jdbc:postgresql://localhost:5432/catalogos_mx`    | No |
   | `DB_USER`     | `postgres`                                         | No |
   | `DB_PASSWORD` | *(sin default)*                                    | **Sí** |

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

   En IntelliJ (Run/Debug Configurations → Modify options → Environment
   variables): agregar `DB_PASSWORD=tu_password`.

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

Todos bajo el prefijo `/api/direccion`.

| Método | Ruta                                          | Descripción                                  |
|--------|-----------------------------------------------|-----------------------------------------------|
| GET    | `/api/direccion/estados`                      | Lista de estados                              |
| GET    | `/api/direccion/estado/{estado}/municipios`   | Municipios de un estado                       |
| GET    | `/api/direccion/estado/{estado}/localidades`  | Localidades de un estado                      |
| GET    | `/api/direccion/cp/{cp}`                      | Datos del código postal (estado, municipio, localidad, colonias). 404 si no existe |
| GET    | `/api/direccion/cp/{cp}/colonias`             | Colonias de un código postal                  |
| POST   | `/api/direccion/validar`                      | Valida consistencia de una dirección completa |

## Estructura relevante

```
nsmail/
├── database/
│   └── catalogos_mx.sql        # catálogo proporcionado para la prueba
├── src/main/java/com/prueba/nsmail/
│   ├── controller/              # endpoints REST
│   ├── model/                   # entidades JPA (incluye llaves compuestas
│   │                             #   para Municipio, Localidad y Colonia)
│   ├── repository/               # repositorios Spring Data JPA
│   ├── service/                  # lógica de negocio y validación
│   └── dto/                      # objetos de transferencia de datos
├── src/main/resources/
│   ├── application.properties
│   └── static/                   # frontend (HTML/CSS/JS)
│       ├── index.html
│       └── JS/
│           └── script.js
└── build.gradle.kts
```

## Notas de diseño

- Las tablas `municipio`, `localidad` y `colonia` usan llave primaria
  compuesta (`clave` + `estado`, o `clave` + `cp`), reflejando que sus
  claves no son únicas a nivel nacional en el catálogo oficial mexicano.
  Se modelaron con `@EmbeddedId` y clases `@Embeddable` dedicadas.
- La validación de consistencia de la dirección se realiza en el backend,
  no solo en el frontend, ya que la interfaz (dropdowns) no es una garantía
  de seguridad ante peticiones directas a la API.