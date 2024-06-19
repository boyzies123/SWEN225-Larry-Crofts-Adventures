/**
 * This module defines the structure and dependencies for the
 * LarryCroftsAdventures game. It specifies the required JavaFX modules and the
 * packages that need to be opened for JavaFX's reflection capabilities. This
 * ensures smooth graphics rendering and user interface functionality.
 *
 * @author Dillon Sykes 300196292
 */
module LarryCroftsAdventures {
    requires javafx.controls;
    requires javafx.graphics;
    requires org.json;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires org.junit.jupiter.api;
    requires javafx.fxml;
    requires java.desktop;
    
    opens nz.ac.wgtn.swen225.lc.app to javafx.graphics;
    opens nz.ac.wgtn.swen225.lc.renderer to javafx.graphics;
    opens test.nz.ac.wgtn.swen225.lc.renderer to javafx.graphics;
    opens nz.ac.wgtn.swen225.lc.recorder to com.fasterxml.jackson.databind;
}