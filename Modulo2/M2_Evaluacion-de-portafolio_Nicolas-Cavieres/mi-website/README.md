# Portfolio Personal - Nicolas Ariel

https://nicolascavieres.github.io/

## Descripción

Este es un sitio web de portafolio personal desarrollado como parte del Bootcamp de JavaScript. El sitio presenta información sobre mis habilidades, proyectos, experiencia y formas de contacto.

## Características

- **Diseño Responsivo**: Adaptable a dispositivos móviles, tablets y escritorio
- **Navegación Suave**: Scroll suave entre secciones
- **Efectos Interactivos**: Animaciones y transiciones CSS/JavaScript
- **Tema Oscuro/Claro**: Funcionalidad para cambiar entre temas
- **Optimizado para SEO**: Estructura HTML semántica

## Tecnologías Utilizadas

### Frontend
- **HTML5**: Estructura del sitio web
- **CSS3**: Estilos y animaciones
- **JavaScript (ES6+)**: Funcionalidad interactiva
- **jQuery 3.7.1**: Manipulación del DOM y efectos
- **Bootstrap 5.3.0**: Framework CSS para diseño responsivo

### Librerías Externas
- **Font Awesome 6.4.0**: Iconos
- **Google Fonts**: Tipografías

## Estructura del Proyecto

```
mi-website/
│
├── index.html              # Página principal
├── css/
│   └── styles.css         # Estilos personalizados
├── js/
│   └── script.js          # JavaScript personalizado
├── assets/
│   └── images/            # Imágenes del sitio
│       └── placeholder.png
└── README.md              # Este archivo
```

## Secciones del Sitio

1. **Inicio**: Presentación personal y llamada a la acción
2. **GitHub**: Información sobre repositorios y proyectos
3. **LinkedIn**: Perfil profesional y conexiones
4. **Tecnologías**: Stack tecnológico y herramientas
5. **Acerca de mí**: Información personal y de contacto

## Instalación y Uso

### Requisitos Previos
- Navegador web moderno
- Conexión a internet (para librerías CDN)

### Instrucciones de Instalación

1. **Clonar o descargar el proyecto**:
   ```bash
   git clone [url-del-repositorio]
   cd mi-website
   ```

2. **Abrir el archivo index.html**:
   - Doble clic en `index.html`, o
   - Usar un servidor local como Live Server en VS Code, o
   - Usar Python: `python -m http.server 8000`

3. **Acceder al sitio**:
   - Abrir en navegador: `http://localhost:8000` (si usas servidor local)
   - O directamente el archivo HTML

## Personalización

### Cambiar Información Personal

1. **Editar `index.html`**:
   - Reemplazar "Nicolas Ariel" con tu nombre
   - Actualizar descripciones y textos
   - Modificar links de GitHub y LinkedIn

2. **Actualizar información de contacto**:
   - Cambiar dirección, teléfono y email
   - Modificar links de redes sociales

### Modificar Estilos

1. **Editar `css/styles.css`**:
   - Cambiar colores en las variables CSS
   - Modificar fuentes y espaciados
   - Ajustar animaciones

2. **Personalizar colores principales**:
   ```css
   :root {
     --primary-color: #007bff;
     --secondary-color: #6c757d;
     --success-color: #28a745;
   }
   ```

### Añadir Funcionalidades

1. **Editar `js/script.js`**:
   - Agregar nuevas animaciones
   - Implementar formulario de contacto
   - Añadir más interactividad

## Funcionalidades JavaScript

### Principales Features
- Scroll suave entre secciones
- Navbar que cambia al hacer scroll
- Animaciones de entrada (fade-in)
- Modo oscuro/claro
- Botón scroll-to-top
- Notificaciones toast
- Modales para proyectos
- Efectos hover en cards
- Validación de formularios

### jQuery Features
- Animaciones suaves
- Manipulación del DOM
- Event handling
- Efectos visuales

## Optimización y Performance

### Técnicas Implementadas
- **Lazy Loading**: Carga diferida de contenido
- **CSS Minificado**: Estilos optimizados
- **Imágenes Optimizadas**: Placeholder para errores
- **Smooth Scrolling**: Navegación fluida
- **Responsive Design**: Adaptable a todos los dispositivos

### CDN Utilizados
- Bootstrap CSS/JS desde jsDelivr
- jQuery desde code.jquery.com
- Font Awesome desde cdnjs

## Responsive Design

### Breakpoints
- **Móvil**: < 576px
- **Tablet**: 576px - 768px
- **Desktop**: > 768px

### Adaptaciones Móviles
- Navegación colapsable
- Textos más pequeños
- Botones de bloque completo
- Espaciado optimizado

## Compatibilidad

### Navegadores Soportados
- Chrome 70+
- Firefox 65+
- Safari 12+
- Edge 79+

### Características Modernas Utilizadas
- CSS Grid y Flexbox
- ES6+ JavaScript
- CSS Custom Properties
- Intersection Observer API

## Despliegue

### Opciones de Hosting
1. **GitHub Pages**: Gratuito, ideal para portfolios
2. **Netlify**: Deploy automático desde Git
3. **Vercel**: Optimizado para frontend
4. **Firebase Hosting**: Google Cloud Platform

### Pasos para GitHub Pages
1. Subir código a repositorio GitHub
2. Ir a Settings > Pages
3. Seleccionar rama main
4. Tu sitio estará en: `https://[usuario].github.io/[repositorio]`

## Mejoras Futuras

### Funcionalidades Planeadas
- [ ] Formulario de contacto funcional
- [ ] Blog integrado
- [ ] Galería de proyectos expandida
- [ ] Certificaciones y cursos
- [ ] Sistema de comentarios
- [ ] Analytics integrado
- [ ] SEO avanzado
- [ ] PWA (Progressive Web App)

### Optimizaciones Técnicas
- [ ] Service Worker para cache
- [ ] Optimización de imágenes WebP
- [ ] Bundle de JavaScript minificado
- [ ] Critical CSS inline
- [ ] Preload de recursos importantes

## Contribución

Si deseas contribuir al proyecto:

1. Fork del repositorio
2. Crear rama feature: `git checkout -b feature/nueva-funcionalidad`
3. Commit cambios: `git commit -m 'Añadir nueva funcionalidad'`
4. Push a la rama: `git push origin feature/nueva-funcionalidad`
5. Crear Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## Contacto

- **Email**: micorreo@midireccion.cl
- **LinkedIn**: [Tu perfil de LinkedIn]
- **GitHub**: [Tu perfil de GitHub]

## Agradecimientos

- Bootcamp JavaScript por la formación
- Bootstrap team por el framework
- Font Awesome por los iconos
- jQuery team por la librería
- Comunidad de desarrolladores por la inspiración

---

**Desarrollado con ❤️ por Nicolas Ariel**
