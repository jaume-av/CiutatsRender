Para configurar tu **Projecte Ciutats** en Render y desplegarlo como un sitio web estático, sigue estos pasos:

---

### **1. Requisitos previos**
Antes de empezar, asegúrate de que:
- El proyecto está subido a un repositorio en GitHub.
- El directorio que contiene tu sitio web estático (con `index.html`, otros HTML, CSS, JS, etc.) está correctamente organizado. En tu caso, parece que los archivos generados se encuentran en `fitxersWeb`.

---

### **2. Configuración en Render**
1. **Accede a Render**:
    - Ve a [Render](https://render.com) e inicia sesión con tu cuenta.

2. **Conecta tu cuenta de GitHub**:
    - Cuando Render te pida permisos, selecciona la opción **"Only select repositories"**.
    - Elige el repositorio que contiene tu `Projecte Ciutats`.

3. **Crea un nuevo Static Site**:
    - En tu panel de Render, haz clic en **"New +"** y selecciona **"Static Site"**.
    - Configura los siguientes parámetros:
        - **Name**: Dale un nombre a tu sitio, por ejemplo, `Projecte-Ciutats`.
        - **Repository**: Escoge el repositorio donde está tu proyecto.
        - **Branch**: Selecciona la rama principal (generalmente `main` o `master`).
        - **Root Directory**: Si tus archivos HTML, CSS y JS están en `fitxersWeb`, especifica esa carpeta.
        - **Build Command**: Para un sitio estático, no necesitas un comando de compilación. Puedes dejarlo vacío.
        - **Publish Directory**: Indica dónde se encuentran los archivos generados. En tu caso, es `fitxersWeb`.

4. **Opciones avanzadas (opcional)**:
    - Si quieres que Render regenere el sitio automáticamente cada vez que actualices tu repositorio, activa el **Auto Deploy**.

5. **Despliega el sitio**:
    - Haz clic en **"Create Static Site"**.
    - Render empezará a construir tu sitio y lo publicará en una URL del tipo `https://nombre-proyecto.onrender.com`.

---

### **3. Prueba y verifica**
1. Una vez desplegado, Render te dará una URL para acceder a tu sitio.
2. Verifica que todas las páginas HTML (`index.html`, páginas individuales de ciudades, etc.) y los estilos CSS se cargan correctamente.
3. Si algo no funciona:
    - Revisa que los archivos necesarios están en `fitxersWeb`.
    - Comprueba las rutas relativas en los enlaces y archivos CSS/JS.

---

### **4. Mantén el sitio actualizado**
- Si haces cambios en tu proyecto local y los subes a GitHub, Render reconstruirá el sitio automáticamente si activaste **Auto Deploy**.
- Si no activaste esta opción, puedes reconstruir manualmente desde el panel de Render.

---

### **5. (Opcional) Personaliza la URL**
Si quieres que tu sitio tenga un dominio propio (por ejemplo, `projecteciutats.com`):
1. Compra un dominio en un proveedor como Namecheap o Google Domains.
2. Configura los registros DNS para apuntar a los servidores de Render.
3. Añade el dominio personalizado en la configuración del sitio en Render.

---


La opción **Environment Variables** (Variables de Entorno) en Render te permite configurar valores que tu aplicación puede usar mientras está en ejecución. Estas variables son útiles para manejar configuraciones específicas del entorno, como claves secretas, rutas específicas o cualquier dato sensible que no quieras incluir directamente en tu código fuente.

---

### **¿Qué son las variables de entorno?**
Son pares clave-valor que se pueden definir fuera de tu código y luego ser accesibles por la aplicación durante su ejecución. Por ejemplo:
- **Claves secretas**: API keys, tokens de autenticación, claves de bases de datos.
- **Configuraciones del entorno**: URL de servicios externos, configuraciones de modo (producción/desarrollo), etc.
- **Datos específicos**: Valores que pueden cambiar según el entorno, como nombres de directorios, configuraciones de tiempo, etc.

---

### **¿Por qué son importantes?**
1. **Seguridad**:
    - Evitas incluir información sensible (como claves API o contraseñas) directamente en tu código fuente, lo que protege tus secretos si alguien accede a tu repositorio.

2. **Flexibilidad**:
    - Puedes usar diferentes configuraciones para entornos de desarrollo, prueba y producción sin cambiar el código fuente.

3. **Facilidad de mantenimiento**:
    - Centralizas la configuración de tu aplicación, lo que facilita cambios futuros.

---

### **¿Cómo funciona en Render?**
1. **Definición**:
    - En el panel de Render, puedes definir las variables de entorno que tu aplicación necesita.
    - Por ejemplo:
      ```plaintext
      KEY=value
      DATABASE_URL=https://mi-database.com
      ```

2. **Acceso desde tu código**:
    - Tu aplicación puede leer estas variables utilizando la API específica del lenguaje que uses. Por ejemplo:
        - En **Java**, se usan las funciones de `System.getenv()`:
      
          ```java
          String databaseUrl = System.getenv("DATABASE_URL");
          System.out.println("Base de datos: " + databaseUrl);
          ```
          
        - En otros lenguajes como Python, se usa `os.environ`.

3. **Valores únicos para cada entorno**:
    - Puedes definir diferentes valores para las mismas variables dependiendo de si estás trabajando en desarrollo, pruebas o producción.

---

### **Ejemplo práctico para Projecte Ciutats**
Supongamos que en tu proyecto tienes una clave API para un servicio de mapas. Puedes definirla como una variable de entorno:

1. **En Render**:
    - Ve a la configuración del Static Site o Service en Render.
    - Busca la sección **Environment Variables**.
    - Añade algo como:
      ```plaintext
      MAP_API_KEY=abcd1234secretkey
      ```

2. **En tu código Java**:
    - Usa `System.getenv()` para obtener el valor:
      ```java
      String apiKey = System.getenv("MAP_API_KEY");
      System.out.println("Clave de API: " + apiKey);
      ```

3. **Ventaja**:
    - Si necesitas cambiar la clave API en producción, puedes hacerlo directamente en Render sin modificar el código.

---

### **Consideraciones importantes**
- **Nunca expongas estas variables directamente en el frontend** (HTML o JavaScript accesibles públicamente).
- Asegúrate de no subir información sensible al repositorio.


### Explicación de las opciones avanzadas de Render

Estas opciones ofrecen más control sobre cómo Render gestiona los despliegues, la seguridad y la configuración de tu aplicación. Aquí tienes una explicación detallada de cada una:

---

### **1. Secret Files**
Esta funcionalidad te permite almacenar archivos de texto que contienen datos sensibles, como claves privadas o configuraciones que no quieres incluir en tu código fuente. Por ejemplo:
- Un archivo `.env` con variables de entorno.
- Una clave privada SSH.
- Certificados.

#### **Cómo funciona**:
1. **Subes el archivo secreto**:
    - En Render, puedes añadir un archivo secreto desde la opción **Add Secret File**.
    - Este archivo estará disponible en tu aplicación durante la construcción o ejecución.

2. **Ubicación en el sistema**:
    - Los archivos secretos se almacenan en `/etc/secrets/<filename>` o en la raíz de tu aplicación, dependiendo de cómo lo configures.

#### **Ejemplo práctico**:
Si tienes un archivo `.env` con claves API y configuraciones:
```plaintext
API_KEY=abcd1234
DB_PASSWORD=supersecurepassword
```
Puedes:
1. Subir este archivo a Render como un Secret File.
2. Configurar tu aplicación para leer las claves desde `/etc/secrets/.env` durante la ejecución.

En **Java**, podrías usar una librería como `dotenv-java` para cargar y leer las variables desde este archivo.

---

### **2. Auto-Deploy**
Render tiene la opción de **Auto-Deploy**, que automáticamente vuelve a desplegar tu aplicación cada vez que actualizas el código en el repositorio conectado.

#### **Ventajas**:
- Ideal para flujo de trabajo continuo: subes un cambio a GitHub, Render lo detecta y actualiza tu aplicación automáticamente.
- Reduce el trabajo manual.

#### **Opciones disponibles**:
- **Enabled (Sí)**: Se habilita el despliegue automático. Es lo recomendado si estás haciendo cambios frecuentes y quieres minimizar el trabajo manual.
- **Disabled (No)**: Deshabilitas el auto-despliegue. Esto es útil si prefieres controlar manualmente cuándo se actualiza la aplicación (por ejemplo, en entornos de producción).

#### **Ejemplo práctico**:
Si estás trabajando activamente en **Projecte Ciutats** y subes cambios a `dades.json` o una plantilla HTML, Render actualizará automáticamente el sitio sin que tengas que intervenir.

---

### **3. Build Filters**
Los filtros de construcción te permiten definir qué rutas del repositorio deben disparar un nuevo despliegue. Esto es útil para:
- Evitar despliegues innecesarios si realizas cambios en archivos que no afectan al sitio (como documentación o pruebas).
- Optimizar tiempos de despliegue.

#### **Cómo configurarlo**:
- **Include paths**: Especificas rutas que deberían **incluirse** para considerar un despliegue.
- **Ignore paths**: Especificas rutas que deberían **ignorarse**.

#### **Ejemplo práctico**:
En **Projecte Ciutats**, si tus archivos críticos están en `fitxersWeb`, puedes configurarlo así:
```plaintext
Include paths:
fitxersWeb/
Ignore paths:
README.md
test/
```
Esto asegura que solo los cambios en `fitxersWeb` disparen un nuevo despliegue.

---

### **Resumen práctico para Projecte Ciutats**
1. Usa **Secret Files** si necesitas gestionar claves API o configuraciones sensibles (como un `.env`).
2. Activa el **Auto-Deploy** para que Render reconstruya tu sitio automáticamente tras cada cambio en el repositorio.
3. Configura **Build Filters** si quieres limitar qué cambios en el repositorio disparan un nuevo despliegue.

