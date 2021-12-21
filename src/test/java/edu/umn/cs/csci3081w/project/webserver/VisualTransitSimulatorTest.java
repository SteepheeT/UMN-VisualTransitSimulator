package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.PassengerFactory;
import edu.umn.cs.csci3081w.project.model.RandomPassengerGenerator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.websocket.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class VisualTransitSimulatorTest {

  /**
   * Setup deterministic operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;
  }

  /**
   * Testing update function without initialize the VTS.
   */
  @Test
  public void testUpdateWithoutInitialization() {
    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);

    JsonObject updateCommandFromClient = new JsonObject();
    updateCommandFromClient.addProperty("command", "update");

    webServerSessionSpy.onMessage(updateCommandFromClient.toString());

  }

  /**
   * Testing update function with initialize the VTS.
   */
  @Test
  public void testUpdateWithInitialization() {
    try {
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      PrintStream originOut = System.out;

      WebServerSession webServerSessionSpy = spy(WebServerSession.class);
      doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
      Session sessionDummy = mock(Session.class);
      webServerSessionSpy.onOpen(sessionDummy);

      JsonObject startCommandFromClient = new JsonObject();
      JsonArray jsonArray = new JsonArray();
      jsonArray.add(2);
      jsonArray.add(2);
      startCommandFromClient.addProperty("command", "start");
      startCommandFromClient.addProperty("numTimeSteps", 80);
      startCommandFromClient.add("timeBetweenVehicles", jsonArray);
      System.setOut(testStream);
      webServerSessionSpy.onMessage(startCommandFromClient.toString());

      JsonObject updateCommandFromClient = new JsonObject();
      updateCommandFromClient.addProperty("command", "update");
      JsonObject busLineIssueCommandFromClient = new JsonObject();
      busLineIssueCommandFromClient.addProperty("command", "lineIssue");
      busLineIssueCommandFromClient.addProperty("id", "10000");
      JsonObject trainLineIssueCommandFromClient = new JsonObject();
      trainLineIssueCommandFromClient.addProperty("command", "lineIssue");
      trainLineIssueCommandFromClient.addProperty("id", "10001");
      for (int i = 0; i < 70; i++) {
        webServerSessionSpy.onMessage(updateCommandFromClient.toString());
        if (i == 1) {
          webServerSessionSpy.onMessage(busLineIssueCommandFromClient.toString());
          webServerSessionSpy.onMessage(trainLineIssueCommandFromClient.toString());
        }
        if (i == 4) {
          webServerSessionSpy.onMessage(busLineIssueCommandFromClient.toString());
        }
        if (i == 10) {
          webServerSessionSpy.onMessage(busLineIssueCommandFromClient.toString());
        }
      }
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();

      System.setOut(originOut);

      String strToCompare = "Time between vehicles for route  0: 2" + System.lineSeparator()
          + "Time between vehicles for route  1: 2" + System.lineSeparator()
          + "Number of time steps for simulation is: 80" + System.lineSeparator()
          + "Starting simulation" + System.lineSeparator();
      for (int i = 1; i <= 70; i++) {
        strToCompare += "~~~~The simulation time is now at time step " + i + "~~~~";
        strToCompare += System.lineSeparator();
      }
      assertEquals(strToCompare, data);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Test update function with initialize the logging.
   */
  @Test
  public void testUpdateWithInitializedLogging() {
    try {
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      PrintStream originOut = System.out;

      WebServerSession webServerSessionSpy = spy(WebServerSession.class);
      doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
      Session sessionDummy = mock(Session.class);
      webServerSessionSpy.onOpen(sessionDummy);

      webServerSessionSpy.getVisualTransitSimulator().setLogging(true);

      JsonObject startCommandFromClient = new JsonObject();
      JsonArray jsonArray = new JsonArray();
      jsonArray.add(2);
      jsonArray.add(2);
      startCommandFromClient.addProperty("command", "start");
      startCommandFromClient.addProperty("numTimeSteps", 20);
      startCommandFromClient.add("timeBetweenVehicles", jsonArray);
      System.setOut(testStream);
      webServerSessionSpy.onMessage(startCommandFromClient.toString());
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      System.setOut(originOut);

      String strToCompare = "Time between vehicles for route  0: 2" + System.lineSeparator()
          + "Time between vehicles for route  1: 2" + System.lineSeparator()
          + "Number of time steps for simulation is: 20" + System.lineSeparator()
          + "Starting simulation" + System.lineSeparator();

      assertEquals(strToCompare, data);

      JsonObject updateCommandFromClient = new JsonObject();
      updateCommandFromClient.addProperty("command", "update");
      JsonObject busLineIssueCommandFromClient = new JsonObject();
      busLineIssueCommandFromClient.addProperty("command", "lineIssue");
      busLineIssueCommandFromClient.addProperty("id", "10000");
      JsonObject trainLineIssueCommandFromClient = new JsonObject();
      trainLineIssueCommandFromClient.addProperty("command", "lineIssue");
      trainLineIssueCommandFromClient.addProperty("id", "10001");
      for (int i = 0; i < 1; i++) {
        webServerSessionSpy.onMessage(updateCommandFromClient.toString());
      }

      assertEquals(true, webServerSessionSpy.getVisualTransitSimulator().getLogging());

    } catch (IOException ioe) {
      fail();
    }
  }
}
