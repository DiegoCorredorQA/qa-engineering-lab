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

Pude reutilizar `RequestSpecs` en más de una clase y eliminar la configuración duplicada sin cambiar el comportamiento de las pruebas.