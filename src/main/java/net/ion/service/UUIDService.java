package net.ion.service;

import org.springframework.stereotype.Service;
import net.ion.utils.ObjectId;

@Service
public class UUIDService {

    public static String seqId() {
		return new ObjectId().toString();
    }

}