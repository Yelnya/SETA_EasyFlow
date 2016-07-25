package view;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.Bitmap;
import util.LogUtility;
import model.BaseModel;
import model.DataModelSingleton;
import model.TrafficLightModel;
import model.TrafficsignModel;
import model.VehicleModel;

// BaseView is the BaseClass for all View classes and implements framework methods

public abstract class BaseView {
	
	// Constructor for View Framework function e.g. register View in Model, add
	// instance to parent view
	protected BaseView parentView=null;
	protected BaseModel modelInstance=null;
	private Bitmap bitmap = null;

	private static Logger logger = Logger.getLogger(LogUtility
			.getName(BaseView.class.getName()));
	
	
	public BaseModel getModelInstance() {
		return modelInstance;
	}

	public void setModelInstance(BaseModel modelInstance) {
		this.modelInstance = modelInstance;
	}

	protected static DataModelSingleton dataModel = null;
	ArrayList<BaseView> childrenList = null;

	public void setChildrenList(ArrayList<BaseView> childrenList) {
		this.childrenList = childrenList;
	}

	public ArrayList<BaseView> getChildrenList() {
		return childrenList;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	// Framework method: add view to parent view
	protected void addChild(BaseView view) {
		childrenList.add(view);
	}

	// Framework method: remove view from parent view
	protected void removeChild(BaseView view) {
		childrenList.remove(view);
	}

	// CONSTRUCTOR
	public BaseView(BaseView parentView, BaseModel model) {
		this.parentView=parentView;
		logger.setLevel(LogUtility.getLevel(Level.SEVERE));
		logger.setUseParentHandlers(LogUtility.getLogEscalation(true));
		
		if (model instanceof VehicleModel)
		{
			setBitmap(((VehicleModel) model).getBitmap());
		}
		if (model instanceof TrafficsignModel)
		{
			setBitmap(((TrafficsignModel) model).getBitmap());
		}
		if (model instanceof TrafficLightModel)
		{
			setBitmap(((TrafficLightModel) model).getTrafficLightBitmap());
		}
		
		model.register(this); // model instance is related to view instance
								// e.g. Vehicle Model and View
								// for update the register is required!
		modelInstance=model;
		if (parentView != null) {
			// null: parentView does not exist, feature not needed

			if (parentView.getChildrenList() == null) // in case the parent view
														// does not exist, a
														// list is allocated
			{
				// create children list, in not existent
				ArrayList<BaseView> theChildrenList = new ArrayList<BaseView>();
				parentView.setChildrenList(theChildrenList);
			}

			parentView.addChild(this); // include this view to parent view

		}

		dataModel = DataModelSingleton.getInstance(); // is the
														// DataModelSingleton
														// reference

	} // end constructor

	public void delete() {
		// TODO Auto-generated method stub
		logger.info("BaseView Delete call");
		if (parentView != null) {
			// remove the instance from the parent's child list! e.g. vehicle view from grid view
			parentView.removeChild(this);
		}
		if (modelInstance != null) {
			// de register the instance from the model inctance: e.g. vehicle view from vehicle model
			modelInstance.deRegister(this);
		}	
	} // end delete

}

