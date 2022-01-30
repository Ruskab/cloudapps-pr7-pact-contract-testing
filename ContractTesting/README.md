# cloud apps practica 7

## Caracteristicas

* Consumer : uso de MockServer en el puerto 8080 para generar el archivo Pact
* Consumer : en la clase TopoClient.java se ha cambiado la isntanciación de RestTemplate a Inyección para poder usar MockServer
* Consumer : al ejecutar ``mvn verify`` copia automaticamente el achivo `target/pacts/PlannerConsumer-TopoProvider.json` a la carpeta ``Pacts/``
* Provider : dado que `TopoService` no usa ningun repository, en el test `TopoControllerContractTest` mockeo el service entero para evitar  cargar los datos al
  arrancar la aplicación

## Ejecución 

* Se deberia poder ejecutar de forma comun para proyectos maven, manualmente 
  * 1 Ejecutar contract tests del Consumer ``TopoClientTest``
  * 2 Se habra generado en Pacto en el `target/pacts`
  * 3 Copiar el archivo generado `PlannerConsumer-TopoProvider.json` en la carpeta ``/Pacts`` en la raiz del proyecto
  * 4 Ejecutar los contract tests del Provider que usaran ese Pacto para su ejecucución

* Alternativa 
  * He dejado un script en node llamado `verify_conract_tests.js` que hace todo lo anterior automaticamente
  ````shell
   node .\verify_conract_tests.js
  ````
  * Primero hace `mvn verify` del Consumer y copia el `Pact` en la carpeta y luego hace` mvn verify` del Provider
  
