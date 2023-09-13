package page.locators;

import winapp.automation.core.Locator;

public class PageLocators {
	
	// This class stores all the locators in a key value pair in the class Locator
    public Locator BTN_NUM_1 = new Locator("BTN_NUM_1", "name=One");
    public Locator BTN_NUM_2 = new Locator("BTN_NUM_2", "name=Two");
    public Locator BTN_PLUS = new Locator("BTN_PLUS", "name=Plus");
    public Locator BTN_EQUALS = new Locator("BTN_EQUALS", "name=Equals");
    public Locator CALC_RESULTS = new Locator("CALC_RESULTS", "automationId=CalculatorResults");

}
