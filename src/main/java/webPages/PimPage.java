package webPages;

import java.util.ArrayList;
import org.openqa.selenium.By;

public class PimPage {

	public ArrayList<Object> addEmployeeText() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("Add Employee");
		ele.add("text");
		ele.add(By.xpath("//H6[.='Add Employee']"));
		return ele;
	}

public ArrayList<Object> pIMText() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("PIM");
		ele.add("text");
		ele.add(By.xpath("//H6[.='PIM']"));
		ele.add(By.tagName("H6"));
		return ele;
	}
public ArrayList<Object> addEmployeeLink() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("Add Employee");
		ele.add("link");
		ele.add(By.xpath("//A[normalize-space(.)='Add Employee']"));
		ele.add(By.linkText("Add Employee"));
		return ele;
	}
public ArrayList<Object> firstNameTextfield() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("First Name");
		ele.add("textfield");
		ele.add(By.xpath("//INPUT[normalize-space(@placeholder)='First Name']"));
		ele.add(By.xpath("//*[normalize-space(@placeholder)='First Name']"));
		ele.add(By.name("firstName"));
		//ele.add(By.className("oxd-input oxd-input--active orangehrm-firstname"));
		return ele;
	}
public ArrayList<Object> middleNameTextfield() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("Middle Name");
		ele.add("textfield");
		ele.add(By.xpath("//INPUT[normalize-space(@placeholder)='Middle Name']"));
		ele.add(By.xpath("//*[normalize-space(@placeholder)='Middle Name']"));
		ele.add(By.name("middleName"));
		//ele.add(By.className("oxd-input oxd-input--active orangehrm-middlename"));
		return ele;
	}
public ArrayList<Object> lastNameTextfield() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("Last Name");
		ele.add("textfield");
		ele.add(By.xpath("//INPUT[normalize-space(@placeholder)='Last Name']"));
		ele.add(By.xpath("//*[normalize-space(@placeholder)='Last Name']"));
		ele.add(By.name("lastName"));
		//ele.add(By.className("oxd-input oxd-input--active orangehrm-lastname"));
		return ele;
	}
public ArrayList<Object> employeeIDTextfield() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("EmployeeID");
		ele.add("textfield");
		ele.add(By.xpath("//DIV[contains(@class,'oxd-input-group oxd-input-field-bottom-space')]/descendant-or-self::INPUT[@class='oxd-input oxd-input--active']"));
		return ele;
	}
public ArrayList<Object> saveButton() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("Save");
		ele.add("button");
		ele.add(By.xpath("//BUTTON[normalize-space(.)='Save']"));
		ele.add(By.xpath("//*[normalize-space(.)='Save']"));
		//ele.add(By.className("oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space"));
		return ele;
	}
public ArrayList<Object> successfullySavedText() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("Successfully Saved");
		ele.add("text");
		ele.add(By.xpath("//P[.='Successfully Saved']"));
		ele.add(By.xpath("//*[.='Successfully Saved']"));
		//ele.add(By.className("oxd-text oxd-text--p oxd-text--toast-message oxd-toast-content-text"));
		return ele;
	}
public ArrayList<Object> personalDetailsText() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("Personal Details");
		ele.add("text");
		ele.add(By.xpath("//H6[.='Personal Details']"));
		return ele;
	}
}
