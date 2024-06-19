package test.nz.ac.wgtn.swen225.lc.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.wgtn.swen225.lc.domain.Boots;
import nz.ac.wgtn.swen225.lc.domain.Exit;
import nz.ac.wgtn.swen225.lc.domain.ExitLock;
import nz.ac.wgtn.swen225.lc.domain.Fire;
import nz.ac.wgtn.swen225.lc.domain.InfoField;
import nz.ac.wgtn.swen225.lc.domain.Key;
import nz.ac.wgtn.swen225.lc.domain.Map;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Tile;
import nz.ac.wgtn.swen225.lc.domain.Treasure;
import nz.ac.wgtn.swen225.lc.domain.Key.Color;
import nz.ac.wgtn.swen225.lc.domain.LockedDoor;
import nz.ac.wgtn.swen225.lc.domain.Teleporter;
import nz.ac.wgtn.swen225.lc.domain.Wall;

class DomainTest {

  @Test
  void Test() {
    Map map = new Map(1, 1);
    Tile tile1 = new Tile();
    Tile[][] tileMap = new Tile[1][1];
    tileMap[0][0] = tile1;
    Player player1 = new Player(0, 0);
    Treasure treasure1 = new Treasure(0, 0);
    
    //setting and getting tilemap
    Map.setMap(tileMap);
    assert Map.getMap() == tileMap;
    
    // adding to tile, and checking the tiles state
    tile1.addObject(player1);
    assert tile1.hasObject();
    assert tile1.getFirstObject() == player1;
    assert tile1.getObjects().get(0) == player1;
    
    // removing object from tile
    tile1.removeObject(player1);
    assert !tile1.hasObject();
    
    // checking if the map has treasure
    assert !Map.treasuresLeft();
    tile1.addObject(treasure1);
    assert Map.treasuresLeft();
    tile1.removeObject(treasure1);
    
    //checking addObject precondition
    try {
      tile1.addObject(null);
    } catch (IllegalArgumentException e) {}
    
    //checking removeObject precondition
    try {
      tile1.removeObject(null);
    } catch (IllegalArgumentException e) {}
    
    //checking getFirstObject precondition
    try {
      tile1.getFirstObject();
    } catch (IllegalStateException e) {}
    
    //checks getTile in Map
    assert map.getTile(0, 0) == tile1;
    try {
      map.getTile(-1, -1);
    } catch (IllegalArgumentException e) {}   
    try {
      map.getTile(0, -1);
    } catch (IllegalArgumentException e) {}   
    
    //checks removeObject in Map
    tile1.addObject(treasure1);
    Map.removeObject(treasure1);
    assert !tile1.hasObject();
    try {
      Map.removeObject(null);
    } catch (IllegalArgumentException e) {}   
    
    //checks the precondition in map constructor
    try {
      new Map(0, 0);
    } catch (IllegalArgumentException e) {}  
    try {
      new Map(1, 0);
    } catch (IllegalArgumentException e) {}  
    
    //precondition in setMap() in Map
    try {
      Map.setMap(null);
    } catch (IllegalArgumentException e) {}  
    
    //getCols in Map
    assert map.getCols() == 1;
    
    //getRows in Map
    assert map.getRows() == 1;
    
    //constructor for tileobject
    try {
      new Player(0, -1);
    } catch (IllegalArgumentException e) {}  
    try {
      new Player(-1, 0);
    } catch (IllegalArgumentException e) {}  
    
    //boots
    Boots boots = new Boots(0, 0);
    assert boots.movementRequest(player1);
    boots.playerInteraction(player1);
    assert player1.hasBoots();
    try {
      boots.playerInteraction(null);
    } catch (IllegalArgumentException e) {}  
    
    //Exit
    Exit exit = new Exit(0, 0);
    assert exit.movementRequest(player1);
    
    //ExitLock
    ExitLock exitLock = new ExitLock(0, 0);
    assert !exitLock.movementRequest(treasure1);
    assert exitLock.movementRequest(player1);
    tile1.addObject(treasure1);
    assert !exitLock.movementRequest(player1);
    try {
      exitLock.movementRequest(null);
    } catch (IllegalArgumentException e) {}  
    exitLock.playerInteraction(player1);
    
    //Fire
    Fire fire = new Fire(0, 0);
    assert fire.movementRequest(player1);
    fire.playerInteraction(player1);
    fire.playerInteraction(new Player(0, 0));
    try {
      fire.playerInteraction(null);
    } catch (IllegalArgumentException e) {}  
    
    //InfoField
    InfoField infoField = new InfoField(0, 0);
    assert infoField.movementRequest(player1);
    
    //Key
    Key redKey = new Key(0, 0, Color.RED);
    Key blueKey = new Key(0, 0, Color.BLUE);
    redKey.getColor();
    assert redKey.movementRequest(player1);
    redKey.playerInteraction(player1);
    try {
      redKey.playerInteraction(null);
    } catch (IllegalArgumentException e) {}  
    
    //LockedDoor
    LockedDoor redDoor = new LockedDoor(0, 0, Color.RED);
    redDoor.getColor();
    assert !redDoor.movementRequest(treasure1);
    player1.addKey(redKey);
    assert redDoor.movementRequest(player1);
    assert !redDoor.movementRequest(new Player(0, 0));
    redDoor.playerInteraction(player1);
    try {
      redDoor.movementRequest(null);
    } catch (IllegalArgumentException e) {}
    try {
      redDoor.playerInteraction(null);
    } catch (IllegalArgumentException e) {} 
    
    //Teleporter
    Teleporter teleporter = new Teleporter(0, 0);
    Teleporter partner = new Teleporter(0, 0);
    assert teleporter.movementRequest(player1);
    teleporter.setPartner(partner);
    teleporter.playerInteraction(player1);
    try {
      teleporter.setPartner(null);
    } catch (IllegalArgumentException e) {}
    try {
      teleporter.playerInteraction(null);
    } catch (IllegalArgumentException e) {} 
    
    //Treasure
    Treasure treasure = new Treasure(0, 0);
    assert treasure.movementRequest(treasure);
    treasure.playerInteraction(player1);
    try {
      treasure.playerInteraction(null);
    } catch (IllegalArgumentException e) {} 
    
    //Wall
    Wall wall = new Wall(0, 0);
    assert !wall.movementRequest(player1);
    try {
      wall.playerInteraction(null);
    } catch (IllegalArgumentException e) {} 
    
    //Player
    Player player = new Player(0, 0);
    assert !player.movementRequest(player1);
    player.getTreasures();
    player.getDirection();
    try {
      player.playerInteraction(player);
    } catch (IllegalArgumentException e) {} 
  }

}
