    docker run -it -p 8500:8500 --name myconsul --rm consul
    docker run -d -p 9411:9411 --name myconsul --rm openzipkin/zipkin

View Zipkin: http://127.0.0.1:9411/zipkin