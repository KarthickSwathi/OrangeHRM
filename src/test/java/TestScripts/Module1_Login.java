package TestScripts;

import org.testng.annotations.Test;

import genericLib.BaseClass;

public class Module1_Login extends BaseClass {

	@Test(priority = 1)
	public void verify_login_with_valid_data() throws InterruptedException
	{
		setDelayBwtSteps(1);
		
		navigateToLoginPage();
		web.EnterInto(lp.usernameTextfield(), "Admin");
		web.EnterInto(lp.passwordTextfield(), "admin123");
		web.clickOn(lp.loginButton());
		web.verifyDisplayOf(dbp.dashboardText());
		web.quitBrowser();
	}
	
	@Test(priority = 2)
	public void verify_login_with_invalid_data() throws InterruptedException
	{
		setDelayBwtSteps(1);
		
		navigateToLoginPage();
		web.EnterInto(lp.usernameTextfield(), "Admin");
		web.EnterInto(lp.passwordTextfield(), "admin");
		web.clickOn(lp.loginButton());
		web.verifyDisplayOf(lp.invalidCredentialsText());
		web.quitBrowser();
	}
	
	@Test(priority = 3)
	public void verify_login_without_data() throws InterruptedException
	{
		setDelayBwtSteps(1);
		
		navigateToLoginPage();
		web.clickOn(lp.loginButton());
		web.verifyDisplayOf(lp.usernameRequiredText());
		web.verifyDisplayOf(lp.passwordRequiredText());
		web.quitBrowser();
	}
	
}
