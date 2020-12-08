package favorite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl implements FavoriteService {
	@Autowired private FavoriteDAO dao; 
	
	 @Override 
	 public List<FavoriteVO> favorite_list(String id) {
		return dao.favorite_list(id); 
	}
	 
}
