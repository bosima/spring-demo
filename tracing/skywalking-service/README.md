Run Skywalking Server:

    docker run --name oap -p 12800:12800 -p 11800:11800 -p 1234:1234 --restart always -d apache/skywalking-oap-server:8.9.1

Run Skywalking UI:

    docker run --name oap-ui --restart always -d -e SW_OAP_ADDRESS=http://localhost:12800 apache/skywalking-ui

Run Skywalking Agent:

Download: https://dlcdn.apache.org/skywalking/java-agent/8.11.0/apache-skywalking-java-agent-8.11.0.tgz

Unzip and copy to your favorite path.

Run App：

    java -javaagent:{your favorite path}/skywalking-agent/skywalking-agent.jar -Dskywalking.collector.backend_service=localhost:11800 -jar skywalking-service-0.0.1-SNAPSHOT.jar
