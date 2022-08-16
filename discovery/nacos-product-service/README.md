    docker pull nacos/nacos-server
    docker run -d --rm --name nacos1 -p 8848:8848 -p 9848:9848 -e MODE=standalone nacos/nacos-server