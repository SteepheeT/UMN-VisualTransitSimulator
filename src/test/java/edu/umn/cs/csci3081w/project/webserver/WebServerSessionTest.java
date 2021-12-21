package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.LargeBusDecorator;
import edu.umn.cs.csci3081w.project.model.PassengerFactory;
import edu.umn.cs.csci3081w.project.model.RandomPassengerGenerator;
import edu.umn.cs.csci3081w.project.model.SmallBusDecorator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import javax.websocket.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class WebServerSessionTest {
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
   * Test command for initializing the simulation.
   */
  @Test
  public void testSimulationInitialization() {
    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);
    JsonObject commandFromClient = new JsonObject();
    commandFromClient.addProperty("command", "initLines");
    webServerSessionSpy.onMessage(commandFromClient.toString());

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject commandToClient = messageCaptor.getValue();
    assertEquals("2", commandToClient.get("numLines").getAsString());

    JsonObject commandFromClient1 = new JsonObject();
    commandFromClient1.addProperty("command", "");
    webServerSessionSpy.onMessage(commandFromClient1.toString());
    System.out.println(commandFromClient1);
    ArgumentCaptor<JsonObject> messageCaptor1 = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor1.capture());
    JsonObject commandToClient1 = messageCaptor1.getValue();
    assertEquals("2", commandToClient1.get("numLines").getAsString());
    webServerSessionSpy.onError(new NullPointerException());

    webServerSessionSpy.onClose(sessionDummy);

  }

  /**
   * Test command for initializing the simulation.
   */
  @Test
  public void testSimulationEmptyCommand() {
    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);
    JsonObject commandFromClient = new JsonObject();
    commandFromClient.addProperty("command", "");
    webServerSessionSpy.onMessage(commandFromClient.toString());
  }

  /**
   * Test command for creat line issue.
   */
  @Test
  public void testSimulationCreateLineIssue() {
    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);

    JsonObject startCommandFromClient = new JsonObject();
    JsonArray jsonArray = new JsonArray();
    jsonArray.add(2);
    jsonArray.add(2);
    startCommandFromClient.addProperty("command", "start");
    startCommandFromClient.addProperty("numTimeSteps", 50);
    startCommandFromClient.add("timeBetweenVehicles", jsonArray);

    webServerSessionSpy.onMessage(startCommandFromClient.toString());

    JsonObject updateCommandFromClient = new JsonObject();
    updateCommandFromClient.addProperty("command", "update");

    JsonObject busLineIssueCommandFromClient = new JsonObject();
    busLineIssueCommandFromClient.addProperty("command", "lineIssue");
    busLineIssueCommandFromClient.addProperty("id", 10000);

    for (int i = 0; i < 20; i++) {
      webServerSessionSpy.onMessage(updateCommandFromClient.toString());
      if (i == 16) {
        webServerSessionSpy.onMessage(busLineIssueCommandFromClient.toString());
      }
    }

    assertEquals(true,
        webServerSessionSpy.getVisualTransitSimulator().getLines().get(0).isIssueExist());


  }

  /**
   * Test for start command.
   */
  @Test
  public void testStartCommand() {
    try {
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      PrintStream originOut = System.out;

      WebServerSession webServerSessionSpy = spy(WebServerSession.class);
      doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
      Session sessionDummy = mock(Session.class);
      webServerSessionSpy.onOpen(sessionDummy);
      JsonObject commandFromClient = new JsonObject();
      JsonArray jsonArray = new JsonArray();
      jsonArray.add(5);
      jsonArray.add(5);
      commandFromClient.addProperty("command", "start");
      commandFromClient.addProperty("numTimeSteps", 50);
      commandFromClient.add("timeBetweenVehicles", jsonArray);

      System.setOut(testStream);

      webServerSessionSpy.onMessage(commandFromClient.toString());
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();

      System.setOut(originOut);

      String strToCompare = "Time between vehicles for route  0: 5" + System.lineSeparator()
          + "Time between vehicles for route  1: 5" + System.lineSeparator()
          + "Number of time steps for simulation is: 50" + System.lineSeparator()
          + "Starting simulation" + System.lineSeparator();
      assertEquals(strToCompare, data);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Testing the Register vehicle command.
   */
  @Test
  public void testRegisterVehicleCommand() {
    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);

    JsonObject startCommandFromClient = new JsonObject();
    JsonArray jsonArray = new JsonArray();
    jsonArray.add(2);
    jsonArray.add(2);
    startCommandFromClient.addProperty("command", "start");
    startCommandFromClient.addProperty("numTimeSteps", 50);
    startCommandFromClient.add("timeBetweenVehicles", jsonArray);

    webServerSessionSpy.onMessage(startCommandFromClient.toString());

    JsonObject updateCommandFromClient = new JsonObject();
    updateCommandFromClient.addProperty("command", "update");

    JsonObject registerVehicleCommandFromClient = new JsonObject();
    registerVehicleCommandFromClient.addProperty("command", "registerVehicle");
    if (LocalDateTime.now().getHour() >= 8 && LocalDateTime.now().getHour() < 16) {
      registerVehicleCommandFromClient.addProperty("id", 2000);
    } else {
      registerVehicleCommandFromClient.addProperty("id", 1000);
    }

    for (int i = 0; i < 1; i++) {
      webServerSessionSpy.onMessage(updateCommandFromClient.toString());
      webServerSessionSpy.onMessage(registerVehicleCommandFromClient.toString());
    }

    if (LocalDateTime.now().getHour() >= 8 && LocalDateTime.now().getHour() < 16) {
      assertEquals(true, webServerSessionSpy.getVisualTransitSimulator()
          .getVehicleConcreteSubject().getObservers().get(0) instanceof LargeBusDecorator);
    } else {
      assertEquals(true, webServerSessionSpy.getVisualTransitSimulator()
          .getVehicleConcreteSubject().getObservers().get(0) instanceof SmallBusDecorator);
    }
  }

  /**
   * Testing the get vehicle command.
   */
  @Test
  public void testGetVehicleCommand() {
    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);

    JsonObject startCommandFromClient = new JsonObject();
    JsonArray jsonArray = new JsonArray();
    jsonArray.add(5);
    jsonArray.add(5);
    startCommandFromClient.addProperty("command", "start");
    startCommandFromClient.addProperty("numTimeSteps", 50);
    startCommandFromClient.add("timeBetweenVehicles", jsonArray);

    webServerSessionSpy.onMessage(startCommandFromClient.toString());

    JsonObject updateCommandFromClient = new JsonObject();
    updateCommandFromClient.addProperty("command", "update");

    JsonObject registerVehicleCommandFromClient = new JsonObject();
    registerVehicleCommandFromClient.addProperty("command", "getVehicles");

    for (int i = 1; i <= 50; i++) {
      webServerSessionSpy.onMessage(updateCommandFromClient.toString());
      if (i == 33) {
        webServerSessionSpy.onMessage(registerVehicleCommandFromClient.toString());
      }
    }

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject commandToClient = messageCaptor.getValue();

    assertEquals("updateVehicles", commandToClient.get("command").getAsString());

    System.out.println(commandToClient.get("vehicles").getAsJsonArray());

  }

  /**
   * Testing the get route command.
   */
  @Test
  public void testGetRoutesCommand() {
    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);

    JsonObject commandFromClient = new JsonObject();
    commandFromClient.addProperty("command", "getRoutes");
    webServerSessionSpy.onMessage(commandFromClient.toString());

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject commandToClient = messageCaptor.getValue();
    assertEquals("updateRoutes", commandToClient.get("command").getAsString());
    assertEquals(4, commandToClient.get("routes").getAsJsonArray().size());
  }
}
