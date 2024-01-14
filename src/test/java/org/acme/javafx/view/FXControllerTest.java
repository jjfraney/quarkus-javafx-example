package org.acme.javafx.view;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 * @author John J. Franey
 */


/**
 * Edit out one or none of the annotations.
 *
 * Results:  Looks like the testFx does not work with Quarkus CDI.
 *
 * <ul>
 * <li>
 * QuarkusTest only:  Scene not loaded.  @Start does not run.
 * </li>
 * <li>
 * TestFx only: Scene not loaded.  FXMLloader is not injected - no CDI container.
 * </li>
 * <li>
 * Both enabled: Scene not loaded.  @Start runs but FXMLloader field is null;
 * Start runs before the container or Start and Test are on different proxies/instances (?).
 * </li>
 * </ul>
 */
@QuarkusTest
@ExtendWith(ApplicationExtension.class)
@Slf4j
public class FXControllerTest {

  @Inject
  FXMLLoader fxmlLoader;

  @Start
  public void start(Stage stage) {

    if(fxmlLoader == null) {
      log.error("fxmlLoader was not injected.");
    } else {
      log.info("creating a scene from the fxmlLoader.");
      Scene scene = fxmlLoader.getController();
      stage.setScene(scene);
      stage.show();
    }
  }

  @Test
  public void test() {
    Assertions.assertNotNull(fxmlLoader);
  }
}
