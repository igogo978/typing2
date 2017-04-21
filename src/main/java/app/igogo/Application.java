package app.igogo;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("psvm running");
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Override
    public void run(String... strings) throws Exception {
        log.info("cmd 執行");

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        log.info("passwd encode: " + encoder.encode("123456"));

        jdbcTemplate.execute("DROP TABLE IF EXISTS user_roles");
        jdbcTemplate.execute("CREATE TABLE user_roles(sn int AUTO_INCREMENT PRIMARY KEY, userid varchar(255) not null, role varchar(20))");

        jdbcTemplate.execute("INSERT INTO user_roles(userid, role) VALUES('admin','ROLE_USER')");
        jdbcTemplate.execute("INSERT INTO user_roles(userid, role) VALUES('admin','ROLE_ADMIN')");
        jdbcTemplate.execute("INSERT INTO user_roles(userid, role) VALUES('mary','ROLE_USER')");

        log.info("create table and insert  users");
        jdbcTemplate.execute("DROP TABLE IF EXISTS users");
        jdbcTemplate.execute("CREATE TABLE users("
                + "userid VARCHAR(255) not null primary key, passwd VARCHAR(255) not null, name VARCHAR(255), enabled BOOLEAN  DEFAULT TRUE)");

        jdbcTemplate.execute("INSERT INTO users VALUES('admin','$2a$10$UO5EcGOJY5S6kmfVS0imzuq2BRLToGPl6cFI4nbs7MLdJnwdZcSk6','亞當',true)");
        jdbcTemplate.execute("INSERT INTO users VALUES('Bob','$2a$10$UO5EcGOJY5S6kmfVS0imzuq2BRLToGPl6cFI4nbs7MLdJnwdZcSk6','巴伯',true)");
        jdbcTemplate.execute("INSERT INTO users VALUES('Mary','','馬力',true)");

    }

}
