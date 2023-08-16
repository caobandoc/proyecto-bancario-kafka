# Proyecto de Sistema Bancario

Este proyecto utiliza varias tecnologías para su funcionamiento. A continuación, se proporciona una breve descripción de las herramientas y configuraciones utilizadas.

## Tecnologías Utilizadas

- **Zookeeper:** Se utiliza para gestionar y coordinar los nodos de Kafka, lo que garantiza la escalabilidad y la alta disponibilidad.
- **Kafka:** Se utiliza como plataforma de streaming para el procesamiento de eventos en tiempo real.
- **MySQL:** Se utiliza como base de datos relacional para almacenar datos estructurados relacionados con el sistema bancario.
- **MongoDB (en la nube):** Debido a problemas con Docker, se optó por utilizar MongoDB en la nube para almacenar datos no estructurados y escalables.

## Configuración de Red y Volúmenes

Se ha creado una red llamada `bankingNetwork` para facilitar la comunicación entre los diferentes componentes del sistema. Además, se han configurado volúmenes para las bases de datos, lo que asegura la persistencia de los datos incluso si los contenedores se reinician.

## Instrucciones de Uso

1. **Clonar el repositorio:** `git clone https://github.com/tuusuario/proyecto-bancario.git`
2. **Configuración:** Asegúrate de tener instalados Zookeeper, Kafka, MySQL y el cliente de MongoDB en tu sistema.
3. **Red y Volumenes:** Verifica que la red `bankingNetwork` esté disponible en tu entorno de Docker. Puedes usar el siguiente comando para crearla: `docker network create bankingNetwork`. Los volúmenes para las bases de datos se crearán automáticamente al ejecutar los contenedores.

