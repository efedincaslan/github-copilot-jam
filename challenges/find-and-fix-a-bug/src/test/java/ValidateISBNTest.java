import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValidateISBNTest {

	@Test
	public void checkAValid10DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("0140449116");
		assertTrue(result,"first value");
		result = validator.checkISBN("0140177396");
		assertTrue(result, "second value");
	}
	
	@Test
	public void checkAValid13DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("9781853260087");
		assertTrue(result,"first value");
		result = validator.checkISBN("9781853267338");
		assertTrue(result, "second value");
	}
	
	@Test
	public void TenDigitISBNNumbersEndingInAnXAreValid() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("012000030X");
		assertTrue(result);
	}

	@Test
	public void checkAnInvalid10DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("0140449117");
		assertFalse(result);
	}

	@Test
	public void checkInvalid10DigitISBNWithAnX() {
		ValidateISBN validator = new ValidateISBN();
		assertThrows(NumberFormatException.class, 
				() -> {
					validator.checkISBN("0140449X17");
				});
	}
	
	@Test
	public void checkAnInvalid13DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("9781853267336");
		assertFalse(result);
	}

	@Test
	public void checkAnInvalid13DigitISBNWithCharacter() {
		ValidateISBN validator = new ValidateISBN();
		assertThrows(NumberFormatException.class, 
				() -> {
					validator.checkISBN("9786853267X36");
				});
	}
	
	@Test
	public void nineDigitISBNsAreNotAllowed() {
		ValidateISBN validator = new ValidateISBN();
		assertThrows(NumberFormatException.class, 
				() -> {
					validator.checkISBN("123456789");
				});
	}

	@Test
	public void elevenDigitISBNsAreNotAllowed() {
		ValidateISBN validator = new ValidateISBN();
		assertThrows(NumberFormatException.class, 
				() -> {
					validator.checkISBN("12345678901");
				});
	}

	@Test
	public void twelveDigitISBNsAreNotAllowed() {
		ValidateISBN validator = new ValidateISBN();
		assertThrows(NumberFormatException.class, 
				() -> {
					validator.checkISBN("123456789012");
				});
	}

	@Test
	public void fourteenDigitISBNsAreNotAllowed() {
		ValidateISBN validator = new ValidateISBN();
		assertThrows(NumberFormatException.class, 
				() -> {
					validator.checkISBN("12345678901234");
				});
	}
	
	@Test
	public void nonNumericISBNsAreNotAllowed() {
		ValidateISBN validator = new ValidateISBN();
		assertThrows(NumberFormatException.class, 
				() -> {
					validator.checkISBN("helloworld");
				});
	}
	
	
}

