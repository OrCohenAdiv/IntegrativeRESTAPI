package Plugin;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import antlr.collections.List;
import smartspace.dao.EnhancedElementDao;
import smartspace.data.ElementEntity;

@Component
public class GetWinnersPlugin implements Plugin {
	private EnhancedElementDao<String> elementDao;
	private ObjectMapper jackson;
	
	@Autowired	
	public GetWinnersPlugin(EnhancedElementDao<String> elementDao) {
		super();
		this.elementDao = elementDao;
		this.jackson = new ObjectMapper();
	}


	@Override
	public ElementEntity process(ElementEntity element) {
		try {
			GetWinnersInput guessInput = this.jackson.readValue(
					this.jackson.writeValueAsString(element.getMoreAttributes()),
					GetWinnersInput.class);
			List<ElementEntity> winnersList = 
					this.elementDao
						.readMessagesByNameAndDetailsResult(
						"guess", 
						"SUCCESS!", 
						guessInput.getSize(), 
						guessInput.getPage());
			
			element.getMoreAttributes().put("allWinners", 
				winnersList
					.stream()
					.map(msg->msg.getAuthor().toString())
					.collect(Collectors.toList())
					.toArray(new String[0]));
			
			return element;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

