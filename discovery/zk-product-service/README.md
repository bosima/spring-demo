    docker run -it --name zk --rm zookeeper
    ./bin/zkCli.sh
    ls /services/product.service
    get /services/product.service/{serviceId}
