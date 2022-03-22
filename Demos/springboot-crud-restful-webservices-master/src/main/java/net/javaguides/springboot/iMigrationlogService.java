package net.javaguides.springboot;

import org.springframework.stereotype.Service;

@Service
public interface iMigrationlogService {

    public void savelog(String type, String desc, String job);
}
