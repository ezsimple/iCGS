package net.ion.service;

import org.springframework.stereotype.Service;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import net.ion.system.UUIDGenerator;

@Service
public class UUIDService {
	private static final TimeBasedGenerator UUID_GENERATOR 
		= Generators.timeBasedGenerator(EthernetAddress.fromInterface());

    private static UUIDGenerator uuidGenerator() {
        return () -> UUID_GENERATOR.generate();
    }
    
    public static String getUUID () {
    	return uuidGenerator().generate().toString();
    }
}