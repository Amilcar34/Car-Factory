#Esta linea es para usar la imagen base de websphere que alguien en algun momento creó, el :latest es para usar la ultima version que se haya subido.
FROM ibmcom/websphere-liberty:latest

#Aca se copia el JAR que se generó, si se quiere usar en otra API, hay que cambiar el nombre obviamente.
#Y ese JAR que se copia, hay que meterle en la ruta /config/dropins/spring/ como se ve en a linea, para que websphere lo tome y lo corra.
COPY --chown=1001:0 target/websphere-demo-*.jar /config/dropins/spring/websphere.jar

#Con esta linea se copia el archivo server.xml dentro de la carpeta config, para que websphere lo use.
COPY --chown=1001:0 server.xml /config/

#Se expone el puerto 8084 que es el que le setie a la API.
EXPOSE 8084

#Con esto se corre el servidor, estilo el catalina.sh de tomcat
RUN configure.sh