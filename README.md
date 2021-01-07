# RickAndMortyApp

La app consiste en que se consultas personajes de una serie llamada Rick & Morty, personales los cuales si presionas sobre uno en especifico te manda a una nueva pantalla con el detalle del personaje y la lista capitulos en los cuales aparece; en la pantalla del detalle puede señalarlo como personaje favorito, para posteriormente poder visualizar en una pantalla sus personajes favoritos.

### Se emplea:

  - **Kotlin** con el patrón **MVVM** + patrón **repository**
  - **Retrofit** para las peticiones HTTP
  - **Room database** para el almacenamiento local de los personajes favoritos
  - **View pager2** para las dos vistas principales (Home - Favorite)
  - **Rx Java** para las peticiones asincronas y manejo de datos
  - **Koin** para la inyección de dependencias
  - **Material design** para el diseño de los cardviews y de más
  - **Pruebas unitarias**
  - **Pruebas de integración**
  - **https://rickandmortyapi.com/** la api REST para la obtención de la información
