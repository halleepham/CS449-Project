package sprint0.testing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sprint0.product.TempConverter;

public class TempConverterTest {
	
	@Test
	public void tempConversion_given32F_returns0C() {
		var tempConverter = new TempConverter();
		
		double result = tempConverter.tempConversion(32.0, "F");
		
		assertEquals(0.0, result, 0.0001);
	}
	
	@Test
	public void tempConversion_given212F_returns100C() {
		var tempConverter = new TempConverter();
		
		double result = tempConverter.tempConversion(212.0, "F");
		
		assertEquals(100.0, result, 0.0001);
	}
	
	@Test
	public void tempConversion_given0C_returns32F() {
		var tempConverter = new TempConverter();
		
		double result = tempConverter.tempConversion(0.0, "");
		
		assertEquals(32.0, result, 0.0001);
	}
	
}
