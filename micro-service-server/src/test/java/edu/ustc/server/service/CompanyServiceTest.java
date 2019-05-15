package edu.ustc.server.service;

import edu.ustc.server.MicroServiceServer;
import edu.ustc.server.mongo.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@SpringBootTest(classes = {MicroServiceServer.class})
public class CompanyServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CompanyService companyService;

    @Test(enabled = false)
    public void testAdd() {
        Company companyDto = new Company();
        companyDto.setName("plantlink");
        companyDto.setAddress("westlake");
        companyDto.setHeadcount(2);
        String id = companyService.add(companyDto);
        Assert.assertNotNull(id);
    }

    @Test
    public void testMock() {
        String mock = companyService.mock();
        Assert.assertEquals(mock, "mock");
    }
}
