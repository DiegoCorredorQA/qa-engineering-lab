# API Testing Notes

Notas personales del programa de formación en API Automation con Java, Rest Assured, TestNG y Maven.

---

## Formato de cada sesión

### Fecha

### Tema

### Concepto en mis palabras

### Ejemplo mínimo

### Error o aprendizaje

### Decisión de QA

### Duda pendiente

---

## 2026-08-01

### Tema

Extracción de respuestas y encadenamiento de peticiones.

### Concepto en mis palabras

Un objeto `Response` permite guardar la respuesta completa de una petición para luego consultar campos, headers o reutilizar información en otra solicitud.

### Ejemplo mínimo

```java
Response response =
        given()
        .when()
                .get("/comments/1")
        .then()
                .statusCode(200)
                .extract()
                .response();

int postId = response.jsonPath().getInt("postId");
```

## 2026-08-02

### Tema

Centralización de la configuración común de las peticiones.

### Concepto en mis palabras

Se crea una clase `RequestSpecs` que construye y devuelve una
`RequestSpecification`.

Esta especificación contiene configuraciones compartidas por varias
peticiones, como la URI base, el `Content-Type`, el header `Accept`
y el logging.

Esto evita duplicar configuración en cada test y facilita el mantenimiento.
Los datos específicos de cada escenario, como path parameters o query
parameters, deben permanecer en el test.

### Ejemplo mínimo

#### Crear la especificación

```java
public static RequestSpecification jsonPlaceholderRequestSpec() {
    return new RequestSpecBuilder()
            .setBaseUri("https://jsonplaceholder.typicode.com")
            .setContentType(JSON)
            .setAccept(JSON)
            .log(LogDetail.ALL)
            .build();
}
```
#### Inicializarla en el test
```java
given()
.spec(requestSpec)
.when()
.get("/posts/{postId}", postId)
.then()
.statusCode(200);
```

#### Aplicarla a una petición
```java
given()
.spec(requestSpec)
.when()
.get("/posts/{postId}", postId)
.then()
.statusCode(200);
```

### Cierre del tema

Pude reutilizar `RequestSpecs` en más de una clase y eliminar la configuración duplicada sin cambiar el comportamiento 
de las pruebas.

## 2026-08-05

### Tema

Centralización de la configuración común de las respuestas.

### Concepto en mis palabras

Se crea una clase `ResponseSpecs` que construye y devuelve una
`RequestSpecBuilder`. No se genera una para todas las respuestas, sino por ejemplo unas para las exitosas (200) 

Esta especificación contiene los specs comunes por ejemplo a los positive test, donde esperariamos un Content Type JSON,
Status Code 200 y un nivel de login especifico.

Evita duplicidad de código y facilita la mantenibilidad.

### Ejemplo mínimo

#### Crear la especificación

```java
public static ResponseSpecification successfulJsonResponseSpec() {
    return new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(200)
            .build();
}
```
#### Inicializarla en el test
```java
private ResponseSpecification successfulResponseSpec;

@BeforeClass
public void setUp() {
    successfulResponseSpec = ResponseSpecs.successfulJsonResponseSpec();
}
```

#### Aplicarla a una petición
```java
.then()
.spec(successfulResponseSpec)
```
### Diferencia entre validaciones comunes y particulares

Las validaciones comunes son expectativas compartidas por varios escenarios
del mismo tipo, como status code, content type o headers.

No todas las respuestas exitosas usan status 200. El código esperado depende
del método y del contrato de la API, por ejemplo 200, 201 o 204.

Las assertions particulares expresan el comportamiento específico del test,
como validar que el `id` de un post coincide con el `postId` extraído de un
comentario.

Solo conviene reutilizar una validación cuando representa exactamente la misma
regla en varios escenarios. No se debe abstraer únicamente para reducir líneas
de código.

### Cierre del tema

Creé especificaciones de respuesta separadas para escenarios exitosos y negativos.

Comprendí que una respuesta positiva no siempre usa status `200` y que cada especificación debe representar un contrato
claro, sin aceptar varios resultados incompatibles.

## 2026-08-10

### Tema

Serialización de objetos Java a JSON.

### Concepto en mis palabras

En lugar de construir el request body como un String JSON, puedo representar
los datos mediante una clase Java.

Rest Assured usa Jackson para serializar el objeto Java a JSON antes de
enviarlo a la API.

### Flujo

PostRequest → Jackson → JSON → Rest Assured → API

### Error o aprendizaje

Rest Assured no incluye por sí solo un serializador JSON. Fue necesario
agregar Jackson Databind al proyecto.

Además, Jackson necesita poder descubrir las propiedades del objeto; en este
caso lo hace mediante getters públicos.

### Decisión de QA

Los modelos de request deben representar solo los datos que enviamos.
Un campo generado por el servidor, como `id`, no debe agregarse al
`PostRequest`.