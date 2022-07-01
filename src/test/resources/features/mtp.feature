# language: es

Característica: Web de MTP
  Validación de la Web de MTP.
  Vamos a usarlo a modo de ejemplo.


  Antecedentes:
    Dado      Se accede a la web de MTP
    Dado      Se aceptan la cookies

  Escenario:  Desktop. Acceso a Aseguramiento de la calidad
    Cuando    Se navega a Servicios -> Aseguramiento de la calidad
    Entonces  Se carga la página Aseguramiento de la calidad

  Escenario:  Desktop. Error forzado
    Entonces  Se carga la página Aseguramiento de la calidad

  Escenario:  Desktop. El mensaje de aviso de las cookies no debe reaparecer
    Cuando    Se navega a Servicios -> Aseguramiento de la calidad
    Entonces  Se carga la página Aseguramiento de la calidad
    Y         El mensaje de aviso de las cookies no se muestra
