package genericLib;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import actionsUtils.WebUtils;
import webPages.DashboardPage;
import webPages.DemoPage;
import webPages.LoginPage;
import webPages.PimPage;

import java.util.logging.Level;
import java.util.logging.Logger;

@Listeners(listeners.LogTestListener.class)
public class BaseClass {

	public WebUtils web =new WebUtils();
	 
	public DashboardPage dbp=new DashboardPage();
	public LoginPage lp=new LoginPage();
	public PimPage pp=new PimPage();
	public DemoPage dp=new DemoPage();
	
	@BeforeSuite
	public void beforeSuite() {
	    ExecutionLogManager.createExecutionLog();
	    Logger.getLogger("org.openqa.selenium.devtools").setLevel(Level.OFF);
	    Logger.getLogger("org.openqa.selenium.chromium").setLevel(Level.OFF);

	}
	
	public void setDelayBwtSteps(int seconds)
	{
		web.setDelay(seconds);
	}
	
	//Reuse Functions
	public void navigateToLoginPage() throws InterruptedException
	{
		web.openBrowser();
		web.maximize();
		web.navigateTo("https://opensource-demo.orangehrmlive.com/");
		web.setImplicitwait(5);
		web.verifyDisplayOf(lp.loginText());
	}
	public void loginAsUser() throws InterruptedException
	{
		navigateToLoginPage();
		web.EnterInto(lp.usernameTextfield(), "Admin");
		web.EnterInto(lp.passwordTextfield(), "admin123");
		web.clickOn(lp.loginButton());
		web.verifyDisplayOf(dbp.dashboardText());
	}
}
