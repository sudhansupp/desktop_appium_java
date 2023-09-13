package winapp.automation.core;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

public class DriverActions {
    
	/**
     * Brief description about the method FindElement()
     * @param: Locator
     * @description: This method find the match as per the required locator and send the respective element
     * If match not found then it returns null
     * @return : the web element of the respective locator
     * @author: Rakesh 
     */
    public static WebElement FindElement(Locator locator) {
        WebElement element = null;
        
        if (locator.getValue().contains("automationId=")) {
            element = Common.AppSession
                .findElementByAccessibilityId(StringUtils.substringAfter(locator.getValue(), "automationId="));
        }
        else if (locator.getValue().contains("className=")) {
            element = Common.AppSession
            .findElementByClassName(StringUtils.substringAfter(locator.getValue(), "className="));
        }
        else if (locator.getValue().contains("name=")) {            
            element = Common.AppSession            
                .findElementByName(StringUtils.substringAfter(locator.getValue(), "name="));
        }
        else if (locator.getValue().contains("xpath=")) {
            element = Common.AppSession
                .findElementByName(StringUtils.substringAfter(locator.getValue(), "xpath="));
        }
        else if (locator.getValue().contains("id=")) {            
            element = Common.AppSession            
                .findElementById(StringUtils.substringAfter(locator.getValue(), "id="));
            
        }
        else if (locator.getValue().contains("linktext=")) {            
            element = Common.AppSession            
                .findElementByLinkText(StringUtils.substringAfter(locator.getValue(), "linktext="));
            
        }
        else if (locator.getValue().contains("partiallinktext=")) {            
            element = Common.AppSession            
                .findElementByPartialLinkText(StringUtils.substringAfter(locator.getValue(), "partiallinktext="));
            
        }
        else if (locator.getValue().contains("tagname=")) {            
            element = Common.AppSession            
                .findElementByTagName(StringUtils.substringAfter(locator.getValue(), "tagname="));
            
        }
        else if (locator.getValue().contains("css=")) {            
            element = Common.AppSession            
                .findElementByCssSelector(StringUtils.substringAfter(locator.getValue(), "css="));
            
        }
        
        else{
        	Log.info("no matching element found hence returning the value as null");
        	return null;
        }
        
        return element;    
    }

    /**
     * Brief description about the method Click()
     * @param: Locator
     * @description: It tries to click on the element once it loaded in the page
     * @return : null
     * @author: Rakesh 
     */
    public static void Click(Locator locator) {
        Log.info("Click on " + locator.getName());       
        FindElement(locator).click();
    }

    /**
     * Brief description about the method GetText()
     * @param: Locator
     * @description: It tries to find the text of the element and returns as string
     * @return : returns the inner text of the element
     * @author: Rakesh 
     */
    public static String GetText(Locator locator) {        
        return FindElement(locator).getText();
    }

    /**
     * Brief description about the method GetAttribute()
     * @param: Locator, attribute name
     * @description: It tries to find the attribute value based upon the locator and attribute name
     * @return : returns the attribute value of the element
     * @author: Rakesh 
     */
    public static String GetAttribute(Locator locator, String attr) {        
        return FindElement(locator).getAttribute(attr);
    }

}