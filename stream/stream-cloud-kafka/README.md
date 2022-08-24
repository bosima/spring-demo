ZooKeeper + Kafka

    docker pull wurstmeister/kafka
    docker pull zookeeper

    docker run --name zoo1 -p 2181:2181 -d zookeeper
    
    docker run --name kafka1 -p 9092:9092 \
    -e KAFKA_BROKER_ID=0 \
    -e KAFKA_ZOOKEEPER_CONNECT=172.17.0.2:2181 \
    -e KAFKA_ADVERTISED_HOST_NAME=192.168.1.4 \
    -e KAFKA_ADVERTISED_PORT=9092 \
    -d wurstmeister/kafka

KAFKA_ADVERTISED_HOST_NAME is the ip of host machine.