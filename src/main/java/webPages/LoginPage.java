package webPages;

import java.util.ArrayList;
import org.openqa.selenium.By;

public class LoginPage {

	public ArrayList<Object> loginText() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("Login");
		ele.add("text");
		ele.add(By.xpath("//H5[.='Login']"));
		ele.add(By.xpath("//*[.='Login']"));
		ele.add(By.tagName("H5"));
		return ele;
	}

	public ArrayList<Object> usernameTextfield() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("Username");
		ele.add("textfield");
		ele.add(By.xpath("//INPUT[normalize-space(@placeholder)='Username']"));
		ele.add(By.xpath("//*[normalize-space(@placeholder)='Username']"));
		ele.add(By.name("username"));
		//ele.add(By.className("oxd-input oxd-input--focus"));
		return ele;
	}

	public ArrayList<Object> passwordTextfield() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("Password");
		ele.add("textfield");
		ele.add(By.xpath("//INPUT[normalize-space(@placeholder)='Password']"));
		ele.add(By.xpath("//*[normalize-space(@placeholder)='Password']"));
		ele.add(By.name("password"));
		//ele.add(By.className("oxd-input oxd-input--active"));
		return ele;
	}

	public ArrayList<Object> loginButton() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("Login");
		ele.add("button");
		ele.add(By.xpath("//BUTTON[normalize-space(.)='Login']"));
		//ele.add(By.className("oxd-button oxd-button--medium oxd-button--main orangehrm-login-button"));
		ele.add(By.tagName("BUTTON"));
		return ele;
	}

public ArrayList<Object> invalidCredentialsText() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("Invalid credentials");
		ele.add("text");
		ele.add(By.xpath("//P[.='Invalid credentials']"));
		//ele.add(By.className("oxd-text oxd-text--p oxd-alert-content-text"));
		return ele;
	}
public ArrayList<Object> usernameRequiredText() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("Username Required");
		ele.add("text");
		ele.add(By.xpath("//DIV[DIV[LABEL[.='Username']]]/descendant::SPAN[.='Required']"));
		return ele;
	}
public ArrayList<Object> passwordRequiredText() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("Password Required");
		ele.add("text");
		ele.add(By.xpath("//LABEL[.='Password']/following::SPAN[.='Required']"));
		return ele;
	}
}
