package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import java.util.List;

public class RegisterVehicleCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  public RegisterVehicleCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Registers vehicle in observer.
   *
   * @param session current simulation session
   * @param command the get vehicles command content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    List<Vehicle> activeVehicles = simulator.getActiveVehicles();
    int vehicleId = command.get("id").getAsInt();
//    System.out.println("got lst: " + activeVehicles);
//    System.out.println(vehicleId);
    Vehicle vehicle = null;
    for (Vehicle activeVehicle : activeVehicles) {
      if (activeVehicle.getId() == vehicleId) {
        vehicle = activeVehicle;
      }
    }
//    System.out.println("adding Vehicle: " + vehicle.getId());
    simulator.addObserver(vehicle);
  }
}
