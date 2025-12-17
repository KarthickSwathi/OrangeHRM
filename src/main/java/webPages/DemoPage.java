package webPages;

import java.util.ArrayList;
import org.openqa.selenium.By;

public class DemoPage {

	public ArrayList<Object> gymboreeLogo() {
		ArrayList<Object> ele = new ArrayList<>();
		ele.add("Gymboree");
		ele.add("Logo");
		ele.add(By.xpath("//a[contains(@aria-label,'Click to visit Gymboree Website')]"));
		return ele;
	}
}
