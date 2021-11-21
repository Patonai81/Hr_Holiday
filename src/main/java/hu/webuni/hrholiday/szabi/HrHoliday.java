package hu.webuni.hrholiday.szabi;

import hu.webuni.hrholiday.szabi.service.InitDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HrHoliday implements CommandLineRunner {

    @Autowired
    InitDBService initDBService;

    public static void main(String[] args) {
        SpringApplication.run(HrHoliday.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initDBService.initDb();
    }
}

