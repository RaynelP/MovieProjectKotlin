# MovieProjectKotlin

App que consume una API de peliculas con el Lenguaje Kotlin utilizando el patron MVVM, dataBinding y Navigation.

Basicamente es una app en la que puedes iniciar sesion o crearte una cuenta con email y password(usando Firestore), en la que puedes mirar peliculas y filtrarlas(proximamente implementare un filtro),
agregar las peliculas a favoritos y ademas hay una pestaña(fragmento) en la app donde puedes ver la informacion vinculada a tu cuenta y ademas podras subir una foto de perfil(proximamente)

Mas tecnicamente... Hasta ahora hay 3 Actividades, una que contiene el splash, otra es la principal y otra que contiene el Login y SignUP.

¿Que componentes pongo en practica?
    *Navigation
    *ButtonNavigation
    *DataBinding
    *ViewPager
    *RecyclerView
    *Room (para guardar peliculas en favoritos a nivel local)
    *Biclioteca de Pagging
    *Uso Retrofit para consumir los datos
 
    
Ademas uso Firebase:
    *Autenticacion con email y password
    *Base de datos (Cloud Firestore)
    *Storage (Proximamente)
    
En el splash uso la libreria de Lottie para animacion.
