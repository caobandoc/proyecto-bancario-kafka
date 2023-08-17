# Proyecto de Sistema Bancario con CQRS utilizando Gradle

Este proyecto implementa un Sistema Bancario que aprovecha la arquitectura CQRS (Command Query Responsibility Segregation) para mejorar la escalabilidad y la eficiencia en el procesamiento de datos. A continuación, se presenta una descripción detallada de las tecnologías y pasos necesarios para poner en funcionamiento el proyecto.

## Tecnologías Utilizadas

El sistema se construye utilizando varias tecnologías clave:

- **Zookeeper:** Gestiona y coordina los nodos de Kafka, asegurando una alta disponibilidad y escalabilidad en la plataforma de streaming.
- **Kafka:** Proporciona una plataforma de streaming para el procesamiento de eventos en tiempo real, crucial para la arquitectura CQRS.
- **MySQL:** Sirve como base de datos relacional para almacenar datos estructurados relacionados con el sistema bancario.
- **MongoDB (en la nube):** Para el almacenamiento escalable de datos no estructurados, debido a problemas con Docker se optó por la nube.

## Configuración de Red y Volúmenes

Para facilitar la comunicación y el funcionamiento sin interrupciones entre los componentes del sistema, se ha creado una red denominada `bankingNetwork`. Además, para garantizar la persistencia de los datos incluso en reinicios de contenedores, se han configurado volúmenes para las bases de datos.

## Instrucciones de Uso

Sigue estos pasos para poner en marcha el proyecto:

1. **Clonar el Repositorio:** Ejecuta `git clone https://github.com/caobandoc/proyecto-bancario-kafka.git` en tu terminal.
2. **Configuración Requerida:** Asegúrate de tener instalados en tu sistema Zookeeper, Kafka, MySQL y el cliente de MongoDB.
3. **Red y Volúmenes:** Verifica si la red `bankingNetwork` ya está presente en tu entorno de Docker. Si no, créala mediante el comando `docker network create bankingNetwork`.
4. **Ejecutar Docker-Compose:** Navega al directorio del repositorio clonado y ejecuta `docker-compose up`. Esto iniciará y orquestará los contenedores necesarios para el sistema.
5. **Gradle y CQRS:** Antes de interactuar con el sistema, asegúrate de que la aplicación CQRS esté construida correctamente utilizando Gradle.

¡Tu Sistema Bancario con CQRS está listo para ser explorado y utilizado!

No dudes en consultar la documentación adicional proporcionada en el repositorio para obtener detalles sobre cómo interactuar con los diferentes componentes y realizar operaciones bancarias a través de la arquitectura CQRS.

_*Nota:*_ Si experimentas problemas con el proceso de Docker o encuentras dificultades, verifica la documentación y busca posibles soluciones en el repositorio.
