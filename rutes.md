# **Rutes en local i producció**

Quan desenvolupem un projecte en local, les rutes sovint funcionen sense problemes perquè tot està al nostre ordinador i sabem exactament on es troben els fitxers. Però quan passem a desplegar el projecte en producció, les coses es compliquen. Per què? Doncs perquè les configuracions del servidor, l'estructura de carpetes i les configuracions d'entorn poden ser diferents de les que tenim en local.

A continuació vorem les problemàtiques més comunes i com abordar-les en diversos escenaris: **NGINX**, **Render**, **GitHub Pages**, etc.

---

## **1. Problemàtiques habituals**

### **1.1 Rutes absolutes que no funcionen**
En local, una ruta absoluta com aquesta pot funcionar sense problemes:
```html
<link rel="stylesheet" href="/css/estil.css">
```
Però quan despleguem en un servidor, pot passar que:
1. **El servidor no serveix des de l’arrel**: Si el projecte es desplega en un subdirectori com `/projecte`, la ruta absoluta `/css/estil.css` buscarà a `/css/estil.css`, i no a `/projecte/css/estil.css`.
2. **El context path varia segons l’entorn**: Això passa sobretot en frameworks o servidors que afegeixen un prefix dinàmic al camí.

### **1.2 Rutes relatives trencades**
Les rutes relatives són sensibles a l'estructura del projecte:
```html
<link rel="stylesheet" href="css/estil.css">
```
En local, funcionen si el navegador obri directament `index.html` i troba `css/estil.css` en el mateix nivell. Però en producció:
1. **El servidor reorganitza els fitxers**.
2. **Les rutes relatives depenen del fitxer on es troben**, i poden trencar-se si es mouen fitxers o s’accedeix a subdirectoris.

---

## **2. Problemàtiques específiques per entorns**

### **2.1 En NGINX**
**NGINX** és un servidor web molt popular que pot servir fitxers des d’una carpeta configurada com a arrel (`root`). Les rutes poden fallar si:
1. El `root` no apunta correctament al directori del projecte.
2. No s’usa un directori de fitxers estàtics coherent.

#### **Exemple**
Configura NGINX per servir fitxers des de `/var/www/html/projecte`:
```nginx
server {
    listen 80;
    server_name exemple.com;

    root /var/www/html/projecte;
    index index.html;

    location / {
        try_files $uri $uri/ =404;
    }
}
```
Si les rutes absolutes apunten a `/css/estil.css`, fallaran perquè no reconeixeran `/projecte`.

#### **Solució**
Usa rutes relatives o dinàmiques que no depenguen de l’arrel.

---

### **2.2 En Render**
**Render** espera que especifiques un directori de publicació (Publish Directory) com a font per servir fitxers estàtics. Si tens fitxers fora d’aquest directori, Render no els podrà servir.

#### **Exemple**
Si el teu projecte té l’estructura:
```
src/
├── main/
│   ├── resources/
│   │   ├── css/
│   │   ├── index.html
```
I configures el **Publish Directory** com `src/main/resources`, Render servirà `/css/estil.css` correctament. Però si `index.html` apunta a una ruta com:
```html
```
Fallaria perquè Render no reconeix aquesta estructura.

#### **Solució**
1. Copia tots els fitxers al directori de publicació amb un **Build Command**:
   ```bash
   cp -r src/main/resources fitxersWeb
   ```
2. Assegura’t que les rutes són relatives:
   ```html
   <link rel="stylesheet" href="css/estil.css">
   ```

---

### **2.3 En GitHub Pages**
En **GitHub Pages**, els fitxers es serveixen des d’un subdirectori com `/repositori/`. Això significa que les rutes absolutes `/css/estil.css` no funcionaran.

#### **Exemple**
Si el teu projecte està publicat en:
```
https://usuari.github.io/repositori/
```
Una ruta com:
```html
<link rel="stylesheet" href="/css/estil.css">
```
Buscarà `https://usuari.github.io/css/estil.css`, que no existeix.

#### **Solució**
Usa rutes relatives o configura un prefix per ajustar les rutes:
```html
<link rel="stylesheet" href="css/estil.css">
```

---

## **3. Solucions generals**

### **3.1 Usa rutes dinàmiques sempre que siga possible**
Els frameworks com **Thymeleaf**, **React**, **Vue** o **Angular** poden generar rutes que s'adapten automàticament a l'entorn:
- **Thymeleaf (Java)**:
  ```html
  <link rel="stylesheet" th:href="@{/css/estil.css}">
  ```
- **React**:
  Configura `homepage` en `package.json`:
  ```json
  {
    "homepage": "/repositori"
  }
  ```

### **3.2 Prova sempre en un servidor local**
- Usa un servidor com **Python**:
  ```bash
  python3 -m http.server
  ```
- O un servidor local com **http-server** en Node.js:
  ```bash
  npm install -g http-server
  http-server .
  ```

### **3.3 Adapta les rutes al context**
Quan treballes en entorns de producció, assegura’t de configurar correctament:
- **NGINX**: ajusta el `root` i usa rutes relatives.
- **Render**: mantingues tots els fitxers dins del Publish Directory.
- **GitHub Pages**: usa rutes relatives o configura el prefix.

---

Amb aquestes solucions, podràs fer que les rutes del teu projecte funcionen tant en local com en producció, independentment de l’entorn! 😊