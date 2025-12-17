package webPages;

import java.util.ArrayList;
import org.openqa.selenium.By;

public class DashboardPage {

	public ArrayList<Object> dashboardText() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("Dashboard");
		ele.add("text");
		ele.add(By.xpath("//H6[.='Dashboard']"));
		ele.add(By.tagName("H6"));
		return ele;
	}

	public ArrayList<Object> pIMLink() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("PIM");
		ele.add("link");
		ele.add(By.xpath("//A[@href='/web/index.php/pim/viewPimModule']"));
		ele.add(By.xpath("//A[normalize-space(.)='PIM']"));
		ele.add(By.xpath("//*[@href='/web/index.php/pim/viewPimModule']"));
		ele.add(By.linkText("PIM"));
		return ele;
	}

}
