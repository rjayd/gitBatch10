package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class addEmployeePage extends CommonMethods {

    @FindBy (id = "firstName")
    public WebElement firstName;

    @FindBy (id = "middleName")
    public WebElement middleName;

    @FindBy (id = "lastName")
    public WebElement lastName;

    @FindBy (id = "photofile")
    public WebElement photograph;

    @FindBy (id = "chkLogin")
    public WebElement createLoginCheckbox;

    @FindBy (id = "user_name")
    public WebElement createUsername;

    @FindBy (id = "user_password")
    public WebElement createPassword;

    @FindBy (id = "re_password")
    public WebElement rePassword;

    @FindBy (id = "btnSave")
    public WebElement saveBtn;

    @FindBy (id = "employeeId")
    public WebElement employeeId;


    public addEmployeePage(){
        PageFactory.initElements(driver,this);
    }
}
