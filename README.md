Este es el proyecto realizado para el Bootcamp Backend Java, en el cual desarrollé una aplicación utilizando Java y Spring Boot. 
El objetivo de este proyecto es simular que una empresa de energías renovables (SPRINGNOVABLES_PROJECT) registre una serie de Mediciones (Se gestionarán como objetos MedicionDTO) conformadas por distintos 
datos (ID, Año, Latitud, Longitud, Temperatura, Precipitación y Viento) para poder aconsejar e informar a nuestros clientes.
Para obtener dichos datos, Springnovables se ha suscrito a los serviios de una API Restful (METEOJAVA_PROJECT), que le proporciona dichos datos y gestiona la Base de Datos correspondiente, con sus distintas
operaciones CRUD.
Sin embargo, necesitamos otro dato más (Radiación) que no nos lo puede proporcionar la API y que proviene de un fichero JSON alojado en nuestro Disco Local.
Por tanto, tenemos que crear una aplicación que muestre al cliente de una forma optimizada y sencilla en el navegador. Aquí aparecerá la lista de MedicionesFinales para que pueda realizar distintas operaciones
según el rol del usuario (ADMINISTRADOR, USUARIO). El USUARIO solo podrá acceder a un listado de MedicionesFinales, mientras que el ADMINISTRADOR podrá agregar, eliminar, consulat y modificar ese listado.
Todas estasa operaciones serán internas y el usuario será ajeno a este proceso de Backend en el que internamente se están combinando los datos provenientes de la API y los del JSON para obtener un listado final.

ORGANIZACIÓND DEL PROYECTO:
- API (METEOJAVA_PROJECT):
    - Proyecto con los paquetes domain correspondientes, Controller, Domain, Repository, Service. Usa las anotaciones JPA necesarias para que dichas entidades se creen automáticamente en la base de datos.
    - Incorpora las operaciones (CRUD):Crear [POST], Modificar [PUT],Borrar [DELETE], FiltrarPorAny(Any) [GET], VerTodas() [GET]
    - Incluye operaciones SQL para poder gestionar los datos provenientes de la Base de Datos
      
- APLICACIÓN WEB (SPRINGNOVABLES_PROJECT)
    - Aplicación web con Spring Boot que trabaja desde el navegador y que puede agregar, modificar, eliminar, y consultar datos, accediendo a la API REST.
    - He añadido un mecanismo de seguridad que autentifique y autorice a los usuarios a leer y/o modificar datos. Solamente los administradores podrán actualizar datos. El resto de los usuarios solamente podrán leerlos.
      Esto garantiza el acceso seguro a los datos y restringió ciertas funcionalidades según los permisos de cada perfil.
    - Para la interfaz de usuario, diseñé y desarrollé vistas web dinámicas utilizando el motor de plantillas Thymeleaf, asegurando una navegación intuitiva

ESQUEMA RESUMIDO:

APP FINAL<--->MEDICIONFINAL<--->MEDICIONDTO<--->API<---->BASE DE DATOS
                  ^
                  |
                  |
                   ---------> FICHERO JSON

