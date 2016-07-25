package controller;

import java.util.ArrayList;

import model.DataModelSingleton;
import model.GridModel;
import model.TrafficLightModel;
import model.TrafficsignModel;
import model.VehicleCoordinates;
import model.VehicleModel;

public class PropagateGridModelUpdate implements Runnable {

	private ArrayList<VehicleModel> movedVehicles;
	private ArrayList<VehicleCoordinates> createdVehicles;
	private ArrayList<VehicleModel> deletedVehicles;
	private ArrayList<TrafficsignModel> createdTrafficSigns;
	private ArrayList<TrafficsignModel> deletedTrafficSigns;
	private ArrayList<TrafficLightModel> modifiedTrafficLights;


	public PropagateGridModelUpdate(
			ArrayList<VehicleModel> movedVehicles,
			ArrayList<VehicleCoordinates> createdVehicles,
			ArrayList<VehicleModel> deletedVehicles,
			ArrayList<TrafficsignModel> createdTrafficSigns,
			ArrayList<TrafficsignModel> deletedTrafficSigns,
			ArrayList<TrafficLightModel> modifiedTrafficLights
			) {
		 this.movedVehicles = movedVehicles;
		 this.createdVehicles = createdVehicles;
		 this.deletedVehicles = deletedVehicles;
		 this.createdTrafficSigns = createdTrafficSigns;
		 this.deletedTrafficSigns = deletedTrafficSigns;
		 this.modifiedTrafficLights = modifiedTrafficLights;
	};
	
	@Override
	public void run() {
		
		DataModelSingleton model = DataModelSingleton.getInstance();
		GridModel gridModel= model.getGridModel();
		gridModel.propagateChanges(
				
				 movedVehicles,
				 createdVehicles,
				 deletedVehicles,
				 createdTrafficSigns,
				 deletedTrafficSigns,
				 modifiedTrafficLights);
	}

}
