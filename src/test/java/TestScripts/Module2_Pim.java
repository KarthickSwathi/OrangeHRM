package TestScripts;

import org.testng.annotations.Test;

import genericLib.BaseClass;

public class Module2_Pim extends BaseClass {

	@Test(priority = 1)
	public void verify_add_employee_without_credentials() throws InterruptedException
	{
		loginAsUser();
		web.clickOn(dbp.pIMLink());
		web.verifyDisplayOf(pp.pIMText());
		web.clickOn(pp.addEmployeeLink());
		web.verifyDisplayOf(pp.addEmployeeText());
		web.EnterInto(pp.firstNameTextfield(), "Demo");
		web.EnterInto(pp.middleNameTextfield(), "Test");
		web.EnterInto(pp.lastNameTextfield(), "User");
		web.EnterInto(pp.employeeIDTextfield(), "1234");
		web.clickOn(pp.saveButton());
		web.verifyDisplayOf(pp.successfullySavedText());
		web.verifyDisplayOf(pp.personalDetailsText());
		web.quitBrowser();
		
	}
		
}
