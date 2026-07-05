package com.hirehub.utility;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.hirehub.entity.Sequence;
import com.hirehub.exception.HireHubException;

@Component
public class Utilities 
{
	private static MongoOperations mongoOperations;
	
	@Autowired
	public void setMongoOperations(MongoOperations mongoOperation) 
	{
		Utilities.mongoOperations = mongoOperation;
	}
	
	public static Long getSequence(String key) throws HireHubException 
	{
		Query query = new Query(Criteria.where("_id").is(key));
		Update update = new Update();
		update.inc("seq",1);
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);
		
		Sequence seq = mongoOperations.findAndModify(query, update, options, Sequence.class);
		
		if(seq == null) 
		{
			throw new HireHubException("Unable Fetch Sequence Id For Key :: " + key); 
		}
		return seq.getSeq();
	}
	
	public static String generateOTP() 
	{
		StringBuilder otp = new StringBuilder();
		SecureRandom random = new SecureRandom();
		for(int i=0 ; i<6 ; i++) 
		{
			otp.append(random.nextInt(10));
		}
		return otp.toString();
	}
}
