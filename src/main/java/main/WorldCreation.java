package main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import entities.*;
import settings.Wave;
import settings.Wood;
import settings.WoodUpper;
import userinterface.*;
import utilities.ImageLoader;

public class WorldCreation {
	
	private int duckPopulationL = 0;
	private int duckPopulationR = 0;
	private int ammoStored[][] = {{20,0,0,0},  {30,0,0,0},  {20,20,0,0},  {30,35,0,0}, {25,30,0,0},  {60,25,0,0},  	{25,50,0,0},  {40,70,0,0},  {20,50,20,0},  {20,30,25,0},  {20,40,40,0}, {20,45,55,0}};
	private int duckSpawns[][] = {{10,0,0,0,0},{15,5,0,0,0},{10,25,0,0,0},{5,45,0,0,0},{5,35,5,0,0} ,{0,30,25,0,0} ,{0,15,40,0,0},{0,20,50,0,0},{0,20,30,10,0},{0,10,10,25,0},{0,0,30,40,0},{0,0,44,55,0}};
	private final int SCENE_SPLASH = 1;
	private final int SCENE_LEVELSELECTION = 2;
	private final int SCENE_LEVEL = 3;
	
	private static ImageLoader imgLoader;
	private Background background;
	private SplashScreen splashScreen;
	private MainMenu menu;
	private WoodUpper woodUpper;
	private BullsEye bullsEye;
	private State state;
	private ScoreBoard scoreBoard;
	private PauseBoard pauseBoard;
	private GameProgress gameProgress;
	private Instruction instruction;
	private ArrayList<Wood> woods;
	private ArrayList<Wave> waves;
	private ArrayList<Levels> levels;
	private ArrayList<Gun> gunA;
	private ArrayList<Gun> gunB;
	private ArrayList<Gun> gunC;
	private ArrayList<Duck> duckLeft;
	private ArrayList<Duck> duckRight;
	private ArrayList<Integer> stateIteratorL;
	private ArrayList<Integer> stateIteratorR;
	
	private LevelNumber lvlNo[];
	private LevelNumber bestScore[];
	private BufferedImage[] duckStatesL;
	private BufferedImage[] duckStatesR;
	private BufferedImage[] numbers;
	//for display only
	private ArrayList<Ammo> ammos;
	private ArrayList<Integer> ammoStorage;
	private BufferedImage scoreStick;
	private BufferedImage scoreFive;
	
	private Random rand = new Random();
	
	public WorldCreation(){}

	public void load(int sc, int currentLevel){
		//load materials
		if(sc == SCENE_SPLASH){
			imgLoader = new ImageLoader("splashScreen.txt");
			//create objects for splash screen/first scene
			background = new Background();
			background.create("backgroundintro");
			
			splashScreen = new SplashScreen();
			splashScreen.create("splashScreen");
			splashScreen.setX(100);
			splashScreen.setY(50);
			
			menu = new MainMenu();
			menu.create("play");
			menu.setX(250);
			menu.setY(370);
			
		}else if(sc == SCENE_LEVELSELECTION){
			imgLoader = new ImageLoader("levelSelect.txt");
			//create objects for level selection
			background = new Background();
			background.create("levelselectbg");
			
			instruction = new Instruction();
			instruction.create("inst"+instruction.getCurrentPage());
			instruction.setX(0);
			instruction.setY(0);
			
			levels = new ArrayList<Levels>();
			
			for (int i = 0; i < 12; i++) {
				levels.add(new Levels());
				levels.get(i).create("lvl"+(i+1));
				if(i < 4){
					levels.get(i).setX(35 + (i * 165));
					levels.get(i).setY(40);
				}else if(i < 8 && i >= 4){
					levels.get(i).setX(35 + ((i-4) * 165));
					levels.get(i).setY(200);
				}else if(i < 12 && i >= 8){
					levels.get(i).setX(35 + ((i-8) * 165));
					levels.get(i).setY(365);
				}
			}
			
			
		}else if(sc == SCENE_LEVEL){
			imgLoader = new ImageLoader("level"+currentLevel+".txt");
			state = new State();
			//create objects for level
			background = new Background();
			background.create("background");
			
			scoreStick = imgLoader.getImage("stick");
			scoreFive  = imgLoader.getImage("stick5");
			
			scoreBoard = new ScoreBoard();
			scoreBoard.setX(140);
			scoreBoard.setY(150);
			
			pauseBoard = new PauseBoard();
			pauseBoard.create("paused");
			pauseBoard.setX(140);
			pauseBoard.setY(150);
			
			lvlNo = new LevelNumber[2];
			
			lvlNo[0] = new LevelNumber();
			lvlNo[0].create("levelNumberZero");
			lvlNo[0].setX(640);
			lvlNo[0].setY(25);

			lvlNo[1] = new LevelNumber();
			lvlNo[1].create("levelNumberZero");
			lvlNo[1].setX(655);
			lvlNo[1].setY(25);
			
			bestScore = new LevelNumber[2];
			
			bestScore[0] = new LevelNumber();
			bestScore[0].create("levelNumberZero");
			bestScore[0].setX(430);
			bestScore[0].setY(240);

			bestScore[1] = new LevelNumber();
			bestScore[1].create("levelNumberZero");
			bestScore[1].setX(445);
			bestScore[1].setY(240);
			
			gameProgress = new GameProgress();
			gameProgress.create("gameProgress");
			gameProgress.setX(660);
			gameProgress.setY(75);
			
			createStates();
			createUserInterface(currentLevel);
			createDucks(currentLevel);
			createAmmos(currentLevel);			
			createNumbers();
		}
	}//end of load(Scene)w

	private void createNumbers() {
		numbers = new BufferedImage[10];
		
		numbers[0] = imgLoader.getImage("zero");
		numbers[1] = imgLoader.getImage("one");
		numbers[2] = imgLoader.getImage("two");
		numbers[3] = imgLoader.getImage("three");
		numbers[4] = imgLoader.getImage("four");
		numbers[5] = imgLoader.getImage("five");
		numbers[6] = imgLoader.getImage("six");
		numbers[7] = imgLoader.getImage("seven");
		numbers[8] = imgLoader.getImage("eight");
		numbers[9] = imgLoader.getImage("nine");
	}

	private void createAmmos(int currentLevel) {

		ammos = new ArrayList<Ammo>();
		ammoStorage = new ArrayList<Integer>();
		
		int ball = ammoStored[currentLevel-1][0];
		int ap = ammoStored[currentLevel-1][1];
		int ice = ammoStored[currentLevel-1][2];

		
		ammos.add(new Ice(550, 550));
		ammos.get(0).setRectangleHeight(50);
		ammos.get(0).setRectangleWidth(50);
		ammos.get(0).create("displayIce");
		ammoStorage.add(ice);
		
		ammos.add(new AP(600, 550));
		ammos.get(1).setRectangleHeight(50);
		ammos.get(1).setRectangleWidth(50);
		ammos.get(1).create("displayAP");
		ammoStorage.add(ap);
		
		ammos.add(new Ball(650, 550));
		ammos.get(2).setRectangleHeight(35);
		ammos.get(2).setRectangleWidth(35);
		ammos.get(2).create("displayBall");
		ammoStorage.add(ball);
	}

	private void createStates() {

		duckStatesL = new BufferedImage[6];
		duckStatesR = new BufferedImage[6];
		 
		duckStatesL[0] = imgLoader.getImage("easyLeftFallen1");
		duckStatesL[1] = imgLoader.getImage("easyLeftFallen2");
		duckStatesL[2] = imgLoader.getImage("easyLeftFallen3");
		duckStatesL[3] = imgLoader.getImage("easyLeftFallen4");
		duckStatesL[4] = imgLoader.getImage("easyLeftFallen5");
		duckStatesL[5] = imgLoader.getImage("easyLeftFallen6");

		duckStatesR[0] = imgLoader.getImage("easyRightFallen1");
		duckStatesR[1] = imgLoader.getImage("easyRightFallen2");
		duckStatesR[2] = imgLoader.getImage("easyRightFallen3");
		duckStatesR[3] = imgLoader.getImage("easyRightFallen4");
		duckStatesR[4] = imgLoader.getImage("easyRightFallen5");
		duckStatesR[5] = imgLoader.getImage("easyRightFallen6");
	}
	
	private void createUserInterface(int level) {
		//objects excluding the ducks will be created here
		woods = new ArrayList<Wood>();
		 waves = new ArrayList<Wave>();
		int noOfWavesAndWoods = 0;
		
		if(level == 1){
			noOfWavesAndWoods = 1;
		}else if(level == 2 || level == 3 || level == 5 || level == 9){
			noOfWavesAndWoods = 2;
		}else if(level == 4 || level == 6  || level == 7 || level == 8 || level == 10 || level == 11 || level == 12){
			noOfWavesAndWoods = 3;
		}
		for (int i = 0; i < noOfWavesAndWoods; i++) {
			
			waves.add(new Wave(-15,430 - (i*100)));
			waves.get(i).create("wave1");
			
			woods.add(new Wood(450 - (i*100)));
			woods.get(i).create("wood1");
		}
			
		woodUpper = new WoodUpper(0);
		woodUpper.create("woodUpper");
		gunA = new ArrayList<Gun>();
		gunB = new ArrayList<Gun>();
		gunC = new ArrayList<Gun>();
		
		String[] gunsA = {"gunCenter1","gunLeftA1","gunLeftA2","gunLeftA3","gunLeftA4","gunLeftA5"
						 ,"gunRightA1","gunRightA2","gunRightA3","gunRightA4","gunRightA5"};

		String[] gunsB = {"gunCenter2","gunLeftB1","gunLeftB2","gunLeftB3","gunLeftB4","gunLeftB5"
						 ,"gunRightB1","gunRightB2","gunRightB3","gunRightB4","gunRightB5"};

		String[] gunsC = {"gunCenter3","gunLeftC1","gunLeftC2","gunLeftC3","gunLeftC4","gunLeftC5"
						 ,"gunRightC1","gunRightC2","gunRightC3","gunRightC4","gunRightC5"};
		
		for (int i = 0; i < 11; i++) {
			
			gunA.add(new Gun());
			gunA.get(i).create(gunsA[i]);

			gunB.add(new Gun());
			gunB.get(i).create(gunsB[i]);

			gunC.add(new Gun());
			gunC.get(i).create(gunsC[i]);
		}
		
		bullsEye = new BullsEye();
		bullsEye.create("bullsEye");
	}//end of createUserInterface

	private void createDucks(int currentLevel) {
		//duck factory
		duckLeft = new ArrayList<Duck>();
		duckRight = new ArrayList<Duck>();
		stateIteratorL = new ArrayList<Integer>();
		stateIteratorR = new ArrayList<Integer>();
		
		for	(int j = 0; j < 5; j++){

		for (int i = 0; i < duckSpawns[currentLevel-1][j];i++) { //(noOfDucks[currentLevel-1]) get the no of ducks that will be assigned to each level
			
			int whichLane = 0;
			int lane = 0;
			int life = 0;
			int speed = 0;
			int distance = 0;
			String imageName = "", number = "";
			
			if(currentLevel == 1){
				whichLane = 0;
			}else if(currentLevel == 2 || currentLevel == 3 || currentLevel == 5 || currentLevel == 9){
				whichLane = rand.nextInt(2);
			}else if(currentLevel == 4 || currentLevel == 6 || currentLevel == 7 || currentLevel == 8 || currentLevel == 10 || currentLevel == 11 || currentLevel == 12){
				whichLane = rand.nextInt(3);
			}
			
			if(whichLane == 0){
				lane = 375;
			}else if(whichLane == 1){
				lane = 275;
			}else if(whichLane == 2){
				lane = 175;
			}

			
			if(j == 0){	
				life = 1;
				imageName = "easy";
				speed = 2;
				number = "";
				distance = 200;
			}else if(j == 1){
				life = 2;
				imageName = "helm";
				speed = 2;
				number = "";
				distance = 300;
			}else if(j == 2){
				life = 3;
				imageName = "knight";
				speed = 2;
				number = "1";
				distance = 400;
			}else if(j == 3){
				life = 1;
				imageName = "clown";
				speed = 3;
				number = "";
				distance = 700;
			}
			
			int rOrL = rand.nextInt(2)+1;//generate a random number( 1 or 2) left or right
			
			if(rOrL == 1){//if 1 is generated, a duck facing left will be created. 

				if(j == 0){
					duckLeft.add(new EasyDuck(1200, lane));//create and add a new Duck Object
				}else if(j == 1){			
					duckLeft.add(new HelmDuck(1200, lane));//create and add a new Duck Objectelse					
				}else if(j == 2){
					duckLeft.add(new KnightDuck(1200, lane));//create and add a new Duck Object
				}else if(j == 3){
					duckLeft.add(new ClownDuck(1200, lane));//create and add a new Duck Object
				}
				
				duckLeft.get(duckPopulationL).setAlive(true);
				duckLeft.get(duckPopulationL).setLife(life);
				duckLeft.get(duckPopulationL).create(imageName+"Left"+number);//get the prevAddedDuck and create its image
				duckLeft.get(duckPopulationL).setSpeed(speed);
				stateIteratorL.add(0);
				
				//this will create the x-distances between ducks
				if(duckPopulationL > 0){
					duckLeft.get(duckPopulationL).setX(duckLeft.get(duckPopulationL-1).getX() + (rand.nextInt(200)+distance));
				}
				
				duckPopulationL++;//add the population of duck facing left
				
			
			}else if(rOrL == 2){//same goes here

				if(j == 0){
					duckRight.add(new EasyDuck(-1200, lane));//create and add a new Duck Object
				}else if(j == 1){			
					duckRight.add(new HelmDuck(-1200, lane));//create and add a new Duck Objectelse					
				}else if(j == 2){
					duckRight.add(new KnightDuck(-1200, lane));//create and add a new Duck Object
				}else if(j == 3){
					duckRight.add(new ClownDuck(-1200, lane));//create and add a new Duck Object
				}
				
				duckRight.get(duckPopulationR).setAlive(true);
				duckRight.get(duckPopulationR).setLife(life);
				duckRight.get(duckPopulationR).create(imageName+"Right"+number);
				duckRight.get(duckPopulationR).setSpeed(speed);
				stateIteratorR.add(0);
				
				if(duckPopulationR > 0){
					duckRight.get(duckPopulationR).setX(duckRight.get(duckPopulationR-1).getX() - (rand.nextInt(200)+distance));
				}
				
				duckPopulationR++;
			}

		}
		}
	}//createDucks
	
	public void unload(int sc){
		//unload materials
		if(sc == SCENE_SPLASH){
			//dispose materials from splash before accessing levelselect
			imgLoader = null;
			background = null;
			splashScreen = null;
			
		}else if(sc == SCENE_LEVELSELECTION){
			//dispose materials from levelselect before accessing a certain level or going back to splash
			imgLoader = null;
			background = null;
			levels = null;
			
		}else if(sc == SCENE_LEVEL){
			//dispose materials from level before going to any scenes\
			imgLoader = null;
			background = null;
			scoreStick = null;
			scoreFive  = null;
			scoreBoard = null;
			pauseBoard = null;
			
			lvlNo[0] = null;
			lvlNo[1] = null;
			
			gameProgress = null;
			numbers = null;
			
			duckStatesL[0].flush();
			duckStatesL[1].flush();
			duckStatesL[2].flush();
			duckStatesL[3].flush();
			duckStatesL[4].flush();
			duckStatesL[5].flush();

			duckStatesR[0].flush();
			duckStatesR[1].flush();
			duckStatesR[2].flush();
			duckStatesR[3].flush();
			duckStatesR[4].flush();
			duckStatesR[5].flush();

			waves = null;
			woods = null;
			woodUpper = null;

			gunA = null;
			gunB = null;			
			gunC = null;
		
			bullsEye = null;
			
			duckLeft = null;
			duckRight = null;
			duckPopulationL = 0;
			duckPopulationR = 0;
			stateIteratorL = null;
			stateIteratorR = null;
			ammos = null;
			ammoStorage = null;
		}
		
		System.gc();
	}//end of unload(Scene)

	public int getDuckPopulationL() {
		return duckPopulationL;
	}

	public int getDuckPopulationR() {
		return duckPopulationR;
	}

	public void setDuckPopulationL(int duckPopulationL) {
		this.duckPopulationL = duckPopulationL;
	}

	public void setDuckPopulationR(int duckPopulationR) {
		this.duckPopulationR = duckPopulationR;
	}

	public Background getBackground() {
		return background;
	}

	public Instruction getInstruction() {
		return instruction;
	}
	
	public SplashScreen getSplashScreen() {
		return splashScreen;
	}

	public MainMenu getMenu() {
		return menu;
	}

	public WoodUpper getWoodUpper() {
		return woodUpper;
	}

	public BullsEye getBullsEye() {
		return bullsEye;
	}

	public ArrayList<Wood> getWoods() {
		return woods;
	}

	public ArrayList<Wave> getWaves() {
		return waves;
	}

	public ArrayList<Levels> getLevels() {
		return levels;
	}

	public ArrayList<Gun> getGunA() {
		return gunA;
	}

	public ArrayList<Gun> getGunB() {
		return gunB;
	}

	public ArrayList<Gun> getGunC() {
		return gunC;
	}

	public ArrayList<Duck> getDuckLeft() {
		return duckLeft;
	}

	public ArrayList<Duck> getDuckRight() {
		return duckRight;
	}
	
	public State getState(){
		return state;
	}
	
	public ArrayList<Integer> getStateIteratorL(){
		return stateIteratorL;
	}
	
	public ArrayList<Integer> getStateIteratorR(){
		return stateIteratorR;
	}
	
	public BufferedImage getDuckStatesL(int state){
		return duckStatesL[state];
	}

	public BufferedImage getDuckStatesR(int state){
		return duckStatesR[state];
	}
	
	public BufferedImage getScoreStick() {
		return scoreStick;
	}

	public BufferedImage getScoreFive() {
		return scoreFive;
	}

	public ArrayList<Ammo> getAmmos() {
		return ammos;
	}

	public ArrayList<Integer> getAmmoStorage() {
		return ammoStorage;
	}

	public BufferedImage[] getNumbers() {
		return numbers;
	}

	public ScoreBoard getScoreBoard() {
		return scoreBoard;
	}
	
	public PauseBoard getPauseBoard() {
		return pauseBoard;
	}

	public GameProgress getGameProgress() {
		return gameProgress;
	}
	
	public LevelNumber[] getLvlNo() {
		return lvlNo;
	}

	public LevelNumber[] getBestScore() {
		return bestScore;
	}

	public int[][] getDuckSpawns() {
		return duckSpawns;
	}
	
	public int getTotalDucks(int currentLevel){
		int duckSpawner = 0;

		for (int i = 0; i < 5; i++) {
			duckSpawner += duckSpawns[currentLevel-1][i];
		}

		return duckSpawner;
	}

	public static ImageLoader getImageLoader(){
		//created objects will fetch the current loaded images
		return imgLoader;
	}//end of getImageLoader()
}
