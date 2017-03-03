package net.ion.service;

import org.springframework.stereotype.Service;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

@Service
public class UUIDService {

	private static final TimeBasedGenerator timeBasedGenerator;
	
	static {
		timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
	}

	public static String getNextId() {
		return timeBasedGenerator.generate().toString();
	}

}