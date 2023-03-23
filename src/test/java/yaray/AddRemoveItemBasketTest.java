package yaray;

import java.io.IOException;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.ExtentManager;
import Base.Hooks;
import freemarker.template.Configuration;
import pageObjects.Homepage;
import pageObjects.ShopContentPanel;
import pageObjects.ShopHomepage;
import pageObjects.ShopProductPage;
import pageObjects.ShoppingCart;

@Listeners(resources.Listeners.class)

public class AddRemoveItemBasketTest extends Hooks {

	public AddRemoveItemBasketTest() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Test
	public void addRemoveItem() throws IOException, InterruptedException {
		
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
		cfg.setAutoFlush(true);
		
		ExtentManager.log("Starting AddRemoveItemBasketTest...");
		
		// creating an object of the automationtesting.co.uk webpage
		Homepage home = new Homepage();
		Thread.sleep(1000);

		//handles cookie prompt
		home.getCookie().click();

		home.getTestStoreLink().click();

		// creating an object of the test store homepage
		ShopHomepage shopHome = new ShopHomepage();
		ExtentManager.pass("Reached the shop homepage");
		shopHome.getProdOne().click();

		// creating an object of the shop products page (when a product has been
		// selected)
		ShopProductPage shopProd = new ShopProductPage();
		ExtentManager.pass("Reached the shop product page");
		Select option = new Select(shopProd.getSizeOption());
		Thread.sleep(1000);
		option.selectByVisibleText("M");
		Thread.sleep(1000);
		ExtentManager.pass("Have successfully selected product size");
		shopProd.getQuantIncrease().click();
		Thread.sleep(1000);
		ExtentManager.pass("Have successfully increased quantity");
		Thread.sleep(1000);
		shopProd.getAddToCartBtn().click();
		Thread.sleep(1000);
		ExtentManager.pass("Have successfully added product to basket");

		// creating an object of the cart content panel (once an item was added)
		ShopContentPanel cPanel = new ShopContentPanel();
		Thread.sleep(1000);
		cPanel.getContinueShopBtn().click();
		Thread.sleep(1000);
		shopProd.getHomepageLink().click();
		Thread.sleep(1000);
		shopHome.getProdTwo().click();
		Thread.sleep(1000);
		shopProd.getAddToCartBtn().click();
		Thread.sleep(1000);
		cPanel.getCheckoutBtn().click();

		// creating an object for the shopping cart page and deleting item 2
		ShoppingCart cart = new ShoppingCart();
		cart.getDeleteItemTwo().click();

		// using a wait to ensure the deletion has taken place
		waitForElementInvisible(cart.getDeleteItemTwo(), 10);

		// printing the total amount to console
		System.out.println(cart.getTotalAmount().getText());
		
		try {
			// using an assertion to make sure the total amount is what we are expecting
			Assert.assertEquals(cart.getTotalAmount().getText(), "$45.24");
			ExtentManager.pass("The total amount matches the expected amount.");
		} catch (AssertionError e) {
			Assert.fail("Cart amount did not match the expected amount, it found" + cart.getTotalAmount().getText() +
					" expected $45.23");
			ExtentManager.fail("The total amount did not match the expected amount.");
		}

	}

}