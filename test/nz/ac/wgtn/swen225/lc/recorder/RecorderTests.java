package test.nz.ac.wgtn.swen225.lc.recorder;


import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.AppConfigurationEntry;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;


//import org.junit.Test;
import nz.ac.wgtn.swen225.lc.app.Main;
import nz.ac.wgtn.swen225.lc.recorder.Move;
import nz.ac.wgtn.swen225.lc.recorder.Recorder;
/**
 * These are the tests for testing functionality of recorder module.
 * These include starting recording, loading, step by step replay, 
 * and auto replay.
 */
class RecorderTests {
  private Main app;
  private Recorder recorder;
  

  
  
  @Test
  void recordingTest_1() throws InterruptedException, JsonProcessingException, IOException {
    Main app = new Main();
    app.intializeBoard();
    Recorder r = app.getRecorder();
    app.setRecording(true);
    
     r.record(4, 5, List.of(), "Down");
     r.record(4, 6, List.of(), "Down");
     r.record(4, 7, List.of(), "Down");
     r.record(4, 8, List.of(), "Down");
    //assuming there are two enemies
    List <Move> gameData = r.getGameData();
    List <Move> realGameData = new ArrayList <Move>(gameData);
    r.stopRecording();
    assertEquals(12, realGameData.size());
  }
  @Test
  void recordingTest_2() {
    Main app = new Main();
    app.intializeBoard();
    Recorder r = app.getRecorder();
    app.setRecording(true);
    try {
      r.record(4, 5, List.of(), "Down");
      r.record(4, 6, List.of(), "Down");
      r.record(3, 6, List.of(), "Left");
      assertEquals(9, r.getGameData().size());
    }catch(IOException e) {
      e.printStackTrace();
      fail(e.getMessage());
    } 
  }
  @Test
  void invalidRecordingTest_1() {
    Main app = new Main();
    Recorder r = new Recorder(app);
    app.setRecording(true);
    try {
      r.record(4, 5, List.of(), "Up");
      r.record(4, 4, List.of(), "Up");
      r.record(4, 3, List.of(), "Up");
      r.record(4, 2, List.of(), "Up");
      r.record(4, 1, List.of(), "Up");
      r.record(4, 0, List.of(), "Up");
      r.record(4, -1, List.of(), "Up");
    }catch(IllegalArgumentException e) {
      assertEquals("Coordinates should not be negative", e.getMessage());
      return;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  @Test
  void invalidRecordingTest_2() {
    Main app = new Main();
    Recorder r = new Recorder(app);
    app.setRecording(true);
    try {
      r.record(4, 5, List.of(1, 2, 3, -4), "Up");
      
    }catch(IllegalArgumentException e) {
      assertEquals("Coordinates should not be negative", e.getMessage());
      return;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  @Test 
  void loadingTest_1() {
    Main app = new Main();
    Recorder r = new Recorder(app);
    try {
      app.setRecording(true);
      r.record(4, 5, List.of(), "Down");
      r.record(4, 6, List.of(), "Down");
      r.record(4, 7, List.of(), "Down");
      r.record(4, 8, List.of(), "Down");
      r.stopRecording();
      
    }catch(IOException e) {
      e.printStackTrace();
      fail(e.getMessage());
    } 
    
    List <Move> move = r.load(new File("Recorded data.json"));
    assertTrue(!move.isEmpty());
    //assuming 2 enemies
    assertEquals(12, move.size());
    
  }
  @Test
  void setSpeedTest_1() {
    Main app = new Main();
    Recorder r = new Recorder(app);
    try {
      r.record(4, 5, List.of(), "Down");
      r.record(4, 6, List.of(), "Down");
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    r.setSpeed(3);
    assertEquals(1000/3, r.getSpeed());
  }
  @Test
  void setInvalidSpeedTest_1() {
    Main app = new Main();
    Recorder r = new Recorder(app);
    try {
      r.record(4, 5, List.of(), "Down");
      r.record(4, 6, List.of(), "Down");
      r.setSpeed(-3);
      fail("Invalid speed");
    }catch(IllegalArgumentException e) {
      return; 
    }
      catch (IOException e) {
      e.printStackTrace();
    }
    
    
  
  }
  @Test
  void stepByStepTest_1() {
    Main app = new Main();
    app.intializeBoard();
    Recorder r = app.getRecorder();
    app.setRecording(true);
    try {
      r.startRecording(app.getPlayerX(), app.getPlayerY(), List.of());
      r.stopRecording();
    
    } catch (IOException e) {
      e.printStackTrace();
    }
    File f = new File("Recorded data.json");
    if (f.exists()) {
    }
    List <Move> move = r.load(new File("Recorded data.json"));
    
    r.stepByStep(move);
    assertFalse(app.isAutoReplay());
    assertFalse(app.isStepByStepReplay());
  }
  
  
  
}

