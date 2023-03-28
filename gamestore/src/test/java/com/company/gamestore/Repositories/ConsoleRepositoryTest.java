package com.company.gamestore.Repositories;

import com.company.gamestore.Controllers.ConsoleController;
import com.company.gamestore.Models.Console;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsoleRepositoryTest {

    @Autowired
    ConsoleRepository repo;

    @Before
    public void setUp() throws Exception {
        repo.deleteAll();
    }


    @Test
    public void shouldAddConsole() {
        //Arrange...
        Console console = new Console();
        console.setModel("Nintendo Switch");
        console.setManufacturer("Nintendo");
        console.setMemory_amount("50GB");
        console.setProcessor("Nvidia Tegra X1");
        console.setPrice(new BigDecimal("299.99"));
        console.setQuantity(2);

        //Act...
        console = repo.save(console);

        //Assert...
        Optional<Console> console1 = repo.findById(console.getConsole_id());

        assertEquals(console1.get(), console);
    }


    @Test
    public void shouldGetConsoleByID() {
        Console console = new Console();
        console.setModel("Nintendo Switch");
        console.setManufacturer("Nintendo");
        console.setMemory_amount("50GB");
        console.setProcessor("Nvidia Tegra X1");
        console.setPrice(new BigDecimal("299.99"));
        console.setQuantity(2);

        repo.save(console);

        Optional<Console> console1 = repo.findById(console.getConsole_id());
        assertEquals(console1.get(), console);
    }

    @Test
    public void shouldgGetAllConsoles() {
        //Arrange...

        //Act...
        Console console = new Console();
        console.setModel("Nintendo Switch");
        console.setManufacturer("Nintendo");
        console.setMemory_amount("50GB");
        console.setProcessor("Nvidia Tegra X1");
        console.setPrice(new BigDecimal("299.99"));
        console.setQuantity(2);

        repo.save(console);

        Console console2 = new Console();
        console2.setModel("Nintendo Switch Lite");
        console2.setManufacturer("Nintendo");
        console2.setMemory_amount("25GB");
        console2.setProcessor("Nvidia Tegra X1");
        console2.setPrice(new BigDecimal("200.00"));
        console2.setQuantity(6);

        repo.save(console2);

        List<Console> consoleList = repo.findAll();

        //Assert...
        assertEquals(2, consoleList.size());
    }

}