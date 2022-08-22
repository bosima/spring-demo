    docker run -d -p 5672:5672 -p 15672:15672 --rm --name myrabbitmq rabbitmq:3.7-management

    curl http://localhost:8080/demo/publish