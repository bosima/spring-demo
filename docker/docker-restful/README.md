    mvn dockerfile:build

    docker run --rm -p 8089:8080 docker-restful:0.0.1-SNAPSHOT


http://localhost:8089/product/1