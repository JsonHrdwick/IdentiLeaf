package org.identileaf.identileafcore;


import org.identileaf.identileafcore.service.QueryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
public class IdentiLeafCoreApplication {


	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
		SpringApplication.run(org.identileaf.identileafcore.IdentiLeafCoreApplication.class, args);

		String initQuery = """
				select scientificName, commonName, BarkType.type, LeafType.type, PlantType.type from mydb.Tree
				inner join mydb.BarkType
				on Tree.BarkType_ID = BarkType.ID
				inner join mydb.LeafType
				on Tree.LeafType_ID = LeafType.ID
				inner join mydb.PlantType
				on Tree.PlantType_ID = PlantType.ID""";
	}
}


