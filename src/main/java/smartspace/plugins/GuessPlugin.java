package Plugin;

import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import smartspace.data.ElementEntity;

@Component
public class GuessPlugin implements Plugin{
	private int numberToGuess;
	private Random random;
	private ObjectMapper jackson;
	
	@PostConstruct
	public void init() {
		this.jackson = new ObjectMapper();
		this.random = new Random(System.currentTimeMillis());
		reset();
	}
	
	@Override
	public ElementEntity process(ElementEntity element) {
		try {
			TotalCostInput guessInput = this.jackson.readValue(
					this.jackson.writeValueAsString(element.getMoreAttributes()),
					TotalCostInput.class);
			int number = guessInput.getNumber();
			
			String result;
			if (number == this.numberToGuess) {
				result = "SUCCESS!";
			}else if (number > this.numberToGuess){
				result = "Your guess is too high";
			}else {
				result = "Your guess is too low";
			}
			element.getMoreAttributes().put("result", result);
			
			return element;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void reset() {
		this.numberToGuess = this.random.nextInt(100);
	}

}

