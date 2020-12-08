package favorite;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FavoriteDAO implements FavoriteService  {
	  @Autowired private SqlSession sql;
	  
	  @Override 
	  public List<FavoriteVO> favorite_list(String id) { 
		   return sql.selectList("favorite.mapper.list", id); 
		 }
	 
}
