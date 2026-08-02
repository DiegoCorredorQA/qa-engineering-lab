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

## 2026-08-02

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