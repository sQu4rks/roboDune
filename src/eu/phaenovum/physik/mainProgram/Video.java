package eu.phaenovum.physik.mainProgram;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import static com.googlecode.javacv.cpp.opencv_core.*;

import eu.phaenovum.physik.MainController;

/**
 * This abstract Class supplies easy-to-use Photosave Functions
 * @author marcel
 *
 */
public class Video {
	//OpenCV's internal Image Class
	static IplImage image; 
	//Date to get our Name for the Images
	static Date date;
	public SimpleDateFormat df;
	//Properties File
	Properties fileProperties;
	//Path to Save Images in
	String savePath;
	//File Extension
	String fileExtension;
	
	public Video(){
		//Datum
		date = new Date();
		df = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
		df.setTimeZone(TimeZone.getDefault());
		
		fileProperties = MainController.ioController.loadProperties();
		//Set the savePath
		savePath = fileProperties.getProperty("save_path");
		fileExtension = fileProperties.getProperty("file_extension");
	}
	
	/**
	 * Function which captures and saves the Image
	 */
	public void capture(){
		//Frame Grabber to grab the Image from the Video Device
		OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(fileProperties.getProperty("video_device"));
		try{
			grabber.start();
			//Save image
			image = grabber.grab();
			cvSaveImage(savePath+df.format(date)+fileExtension,image);
			grabber.stop();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
