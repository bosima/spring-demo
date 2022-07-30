package cn.bossma.springdemo.web.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@SpringBootApplication
@RestController
// redis key ttl is 60+300
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60)
public class WebSessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSessionApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(HttpSession httpSession, String name) {
        String sessionName = (String) httpSession.getAttribute("name");
        if (sessionName == null) {
            httpSession.setAttribute("name", name);
            sessionName = name;
        }
        return "hello " + sessionName;
    }
}
