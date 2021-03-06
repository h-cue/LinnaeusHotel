package view;



import javax.naming.OperationNotSupportedException;

import controller.AbstractController;
import controller.CreateReservatinController;
import controller.oldSerachReservationController;
import controller.MainController;
import controller.ManagerAddEditWindowController;
import controller.ManagerWindowController;
import controller.ModelAccess;
import controller.SearchReservationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class ViewFactory {
	
	public static ViewFactory defaultFactory=new ViewFactory();
	
	private static boolean mainViewInitiliazed=false;
	
	private ModelAccess modelAccess=new ModelAccess();
	
	private MainController mainController;
	private oldSerachReservationController oldSearchReservationController;
	private CreateReservatinController createReservationController;
	private ManagerWindowController managerWindowController;
	private ManagerAddEditWindowController addEditRoomWindowController;
	private SearchReservationController searchReservationController;
	
	
	
	private final String DEFAULT_CSS="style.css";
	
	private final String MAIN_SCREEN_FXML="MainLayout.fxml";
	private final String RES_LAYOUT_FXML="reservationLayout.fxml";
	private final String CREATE_RES_FXML="CreateReservationLayout.fxml";
	private final String MAN_WIND_FXML="managerWindowLayout.fxml";
	private final String MAN_ADD_EDIT_FXML="managerEditorAddRoom.fxml";
	private final String SEARCH_RES_FXML="searchReservationLayout.fxml";
	
	
	
	public Scene getMainScene() throws OperationNotSupportedException{
		if (!mainViewInitiliazed) {
			mainViewInitiliazed=true;
			mainController = new MainController(modelAccess);
			return initializeScene(MAIN_SCREEN_FXML, mainController);
		}else{
			throw new OperationNotSupportedException("Main Scene Already Initiliazed");
		}
		
	}
	
	public Scene getReservationLayoutScene(){
		
		oldSearchReservationController=new oldSerachReservationController(modelAccess);
		return initializeScene(RES_LAYOUT_FXML,oldSearchReservationController);
		
	}
	
	public Scene getCreateReservationScene(){
		
		createReservationController=new CreateReservatinController(modelAccess);
		return initializeScene(CREATE_RES_FXML,createReservationController);
		
	}
	
	public Scene getManagerWindowScene(){
		
		managerWindowController=new ManagerWindowController(modelAccess);
		return initializeScene(MAN_WIND_FXML,managerWindowController);
		
	}
	
	public Scene getAddEditRoomWindow(){
		addEditRoomWindowController=new ManagerAddEditWindowController(modelAccess);
		return initializeScene(MAN_ADD_EDIT_FXML, addEditRoomWindowController);
		
	}
	
	public Scene getSearchReservationWindow(){
		searchReservationController=new SearchReservationController(modelAccess);
		return initializeScene(SEARCH_RES_FXML,searchReservationController);
		
	}
	
	
	private Scene initializeScene(String fxmlPath,AbstractController controller){
		FXMLLoader loader;
		Parent parent;
		Scene scene;
		
		try {
			loader=new FXMLLoader();
			loader.setLocation(getClass().getResource(fxmlPath));
			loader.setController(controller);
			parent=loader.load();
			
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
		
		scene=new Scene(parent);
		//scene.getStylesheets().add(getClass().getResource(DEFAULT_CSS).toExternalForm());
		return scene;
	}

}
