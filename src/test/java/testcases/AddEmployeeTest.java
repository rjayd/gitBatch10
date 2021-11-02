package testcases;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.DashboardPage;
import pages.EmployeeListPage;
import pages.LoginPage;
import pages.addEmployeePage;
import utils.CommonMethods;
import utils.ConfigReader;
import utils.Constants;
import utils.ExcelReading;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddEmployeeTest extends CommonMethods {
    @Test()
    public void addEmployee(){
        LoginPage login = new LoginPage();
        login.login(ConfigReader.getPropertyValue("username"),ConfigReader.getPropertyValue("password"));

        DashboardPage dashboardPage = new DashboardPage();
        click(dashboardPage.pimOption);
        click(dashboardPage.addEmployeeBtn);

        addEmployeePage addEmployeePage = new addEmployeePage();
        sendText(addEmployeePage.firstName, "test94845");
        sendText(addEmployeePage.middleName, "451547test");
        sendText(addEmployeePage.lastName, "test45154");
        click(addEmployeePage.saveBtn);
    }
    @Test
    public void addMultipleEmployees(){
        //login
        LoginPage loginPage = new LoginPage();
        loginPage.login(ConfigReader.getPropertyValue("username"),ConfigReader.getPropertyValue("password"));
        //navigate to employee page
        DashboardPage dashboardPage = new DashboardPage();
        addEmployeePage addEmployeePage = new addEmployeePage();
        EmployeeListPage empList = new EmployeeListPage();
        SoftAssert softAssert = new SoftAssert();

        List<Map<String,String>> newEmployees = ExcelReading.excelIntoListMap(Constants.TESTDATA_FILEPATH,"EmployeeSheet");

        Iterator<Map<String,String>> it = newEmployees.iterator();

        while(it.hasNext()){
            click(dashboardPage.pimOption);
            click(dashboardPage.addEmployeeBtn);
            Map<String,String> mapNewEmployee = it.next();
            sendText(addEmployeePage.firstName, mapNewEmployee.get("FirstName"));
            sendText(addEmployeePage.middleName, mapNewEmployee.get("MiddleName"));
            sendText(addEmployeePage.lastName, mapNewEmployee.get("LastName"));

            //capturing employee ID from system
            String employeeIdValue = addEmployeePage.employeeId.getAttribute("value");
            sendText(addEmployeePage.photograph, mapNewEmployee.get("Photograph"));

            if(!addEmployeePage.createLoginCheckbox.isSelected()){
                click(addEmployeePage.createLoginCheckbox);
            }
            //provide credentials for user
            sendText(addEmployeePage.createUsername, mapNewEmployee.get("Username"));
            sendText(addEmployeePage.createPassword, mapNewEmployee.get("Password"));
            sendText(addEmployeePage.rePassword, mapNewEmployee.get("Password"));
            click(addEmployeePage.saveBtn);

            //navigate to employee page
            click(dashboardPage.pimOption);
            click(dashboardPage.employeeListOption);

            sendText(empList.idEmployee, employeeIdValue);
            click(empList.searchButton);

            List<WebElement> rowData = driver.findElements(By.xpath("//table[@id=\"resultTable\"]/tbody/tr"));
            for (int i=0; i<rowData.size(); i++){
                System.out.println("I am inside the loop to get values for the newly generated employee");
                String rowText = rowData.get(i).getText();
                System.out.println(rowText);

                String expectedData = employeeIdValue + " " + mapNewEmployee.get("FirstName") + " "+ mapNewEmployee.get("MiddleName")+ "" +mapNewEmployee.get("LastName");

                softAssert.assertEquals(rowText,expectedData);
            }
        }
        softAssert.assertAll();
    }
}
