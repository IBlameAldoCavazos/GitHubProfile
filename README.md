# 🤓 GitHubProfile

Aplicación Android nativa desarrollada en **Kotlin** con **Jetpack Compose** que permite buscar perfiles de GitHub y visualizar información del usuario, sus seguidores y repositorios en tiempo real consumiendo la [API pública de GitHub](https://docs.github.com/en/rest).

---

## Características

- **Búsqueda de usuarios** — Ingresa cualquier nombre de usuario de GitHub para consultar su perfil.
- **Perfil detallado** — Muestra avatar, nombre, bio, cantidad de seguidores, seguidos y repositorios públicos.
- **Lista de seguidores** — Visualiza los seguidores del usuario en un Bottom Sheet con avatar y nombre.
- **Repositorios** — Vista previa de los 2 repos más recientes en el perfil, con opción de ver la lista completa en una pantalla dedicada.
- **Búsqueda rápida** — Diálogo para cambiar de usuario sin volver a la pantalla inicial.
- **Manejo de errores** — Mensajes claros para usuario no encontrado, campo vacío o problemas de conexión.

---

## Arquitectura

El proyecto sigue el patrón **MVVM (Model-View-ViewModel)** con una separación clara de responsabilidades:

```
com.example.githubprofile
├── data
│   ├── model          # Data classes: GitHubUser, GitHubRepo, GitHubFollower
│   ├── remote         # Interfaz Retrofit (GitHubApi) y proveedor (ApiProvider)
│   └── repository     # GitHubRepository — capa de abstracción sobre la API
├── navigation         # Rutas de navegación (Routes)
├── ui
│   ├── screens        # ProfileScreen, ReposScreen
│   ├── state          # ProfileUiState (sealed class)
│   ├── theme          # Colores, tipografía y tema Material 3
│   └── viewmodel      # ProfileViewModel
└── MainActivity.kt    # Entry point con NavHost
```

---

## ⌨️ Tech Stack

| Categoría | Tecnología |
|---|---|
| Lenguaje | Kotlin 2.0 |
| UI | Jetpack Compose + Material 3 |
| Networking | Retrofit 2.11 |
| Serialización | kotlinx.serialization 1.7 |
| Imágenes | Coil Compose 2.7 |
| Navegación | Navigation Compose 2.8 |
| Asincronía | Kotlin Coroutines 1.9 |
| Ciclo de vida | Lifecycle ViewModel Compose 2.8 |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 36 |

---

## Cómo ejecutar

1. **Clona el repositorio**

   ```bash
   git clone https://github.com/IBlameAldoCavazos/GitHubProfile.git
   ```

2. **Abre el proyecto** en Android Studio (Ladybug o superior recomendado).

3. **Sincroniza Gradle** — Android Studio descargará las dependencias automáticamente.

4. **Ejecuta la app** en un emulador o dispositivo físico con Android 7.0+.

> **Nota:** La app consume la API pública de GitHub sin autenticación, por lo que está sujeta a un límite de 60 peticiones por hora por IP.

---

## Flujo de la app

1. **Pantalla inicial** — Campo de texto para ingresar un nombre de usuario y botón de búsqueda.
2. **Perfil** — Se muestra el avatar (tap para ampliarlo), nombre, bio, chips con estadísticas y una vista previa de repositorios.
3. **Seguidores** — Al tocar el chip de seguidores se despliega un Bottom Sheet con la lista.
4. **Todos los repos** — Botón "Ver más repositorios" navega a una pantalla con la lista completa.

---

## Endpoints consumidos

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/users/{username}` | Información del perfil |
| `GET` | `/users/{username}/followers` | Lista de seguidores (hasta 100) |
| `GET` | `/users/{username}/repos` | Repositorios ordenados por última actualización (hasta 100) |

---

## Contribuciones

¡Las contribuciones son bienvenidas! Abre un **issue** o envía un **pull request** si tienes ideas para mejorar la app. (Pero mejor no mandes nada)

---

## Autor

**Aldo Alberto Cavazos Martinez** — [@IBlameAldoCavazos](https://github.com/IBlameAldoCavazos)

---

## Licencia

Este proyecto es de uso libre con fines educativos.
