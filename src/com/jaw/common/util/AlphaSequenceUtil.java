package com.jaw.common.util;

import org.springframework.stereotype.Component;

@Component
public class AlphaSequenceUtil {
	
	
	public static String generateAlphaSequence(String seqString,Integer seq){
		
		String sequence = null;
		
		if((seq>=0)&&(seq<=9)){
			sequence  = seqString.concat("00").concat(seq.toString());
		}else if((seq>=10)&&(seq<=99)){
			sequence  = seqString.concat("0").concat(seq.toString());
		}else{
			sequence  = seqString.concat(seq.toString());
		}
		
		return sequence;
	}

}
