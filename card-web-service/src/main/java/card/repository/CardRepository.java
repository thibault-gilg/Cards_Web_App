package card.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import card.model.CardEntity;

public interface CardRepository extends CrudRepository<CardEntity, Integer>{
	
	public CardEntity findByName(String Name);

	public CardEntity findById(int id);
	
	public List<CardEntity> findAll();
	
	public long count();

	public List<CardEntity> findByUserId(String userid);

	public List<CardEntity> findByUserIdNull();

}