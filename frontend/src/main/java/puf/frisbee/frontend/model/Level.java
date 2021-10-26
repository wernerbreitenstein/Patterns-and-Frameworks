package puf.frisbee.frontend.model;

import puf.frisbee.frontend.model.Environment;
import puf.frisbee.frontend.model.Character;
import puf.frisbee.frontend.model.Frisbee;

public class Level {
	
	private String name;
	private Environment environment;
	private Character[] characters;
	private Frisbee frisbee;
//	private Interruption[] interruptions;
	
	public Level() {
		Environment environment = new Environment();
		Character character1 = new Character("Bonnie");
		Character character2 = new Character("Clyde");
		Frisbee frisbee = new Frisbee();
		
	}

}
