package io.ghostyjade.opencv;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.WindowConstants;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;
import org.bytedeco.opencv.opencv_core.Mat;

import io.ghostyjade.pinkanell.PinkanellMain;

/**
 * This class manages the {@linkplain CameraFramer camera} acquisition. Also, it
 * creates the {@linkplain BallRecognizer ball recognizer} instance and the
 * {@linkplain RectangleRecognizer rectangle recognizer} instance.
 * 
 * @author GhostyJade
 */
public class CvManager {

	/**
	 * The {@linkplain CameraFramer} instance.
	 */
	private CameraFramer cameraInstance;

	/**
	 * The {@linkplain BallRecognizer} instance.
	 */
	private BallRecognizer ballRecognizer;
	/**
	 * The {@linkplain RectangleRecognizer} instance.
	 */
	//private RectangleRecognizer rectRecognizer;                         frah ho commentato il rect

	/**
	 * Convert {@link Mat mat} to {@link Frame frame}
	 */
	private ToIplImage converter;

	/**
	 * The window frame
	 */
	private CanvasFrame frame;

	/**
	 * Tell to threads if rendering is enabled.
	 */
	private boolean renderingEnabled = false;

	/**
	 * Class constructor.
	 */
	public CvManager() {
		cameraInstance = new CameraFramer();

		ballRecognizer = new BallRecognizer(cameraInstance);
		//rectRecognizer = new RectangleRecognizer(cameraInstance);                         frah ho commentato il rect
	}

	/**
	 * Starts the camera thread.
	 */
	public synchronized void start() {
		PinkanellMain.serviceExecutor.execute(cameraInstance);
	}

	/**
	 * Starts the {@linkplain BallRecognizer ball} and
	 * {@linkplain RectangleRecognizer rectangle} recognizers.
	 */
	public synchronized void postInit() {
		PinkanellMain.serviceExecutor.execute(ballRecognizer);
		//PinkanellMain.serviceExecutor.execute(rectRecognizer);                         frah ho commentato il rect
	}

	/**
	 * If the rendering is enabled, render the camera frame to the preview.
	 */
	public void render() { // FIXME MOVE.
		if (renderingEnabled) {
			frame.showImage(converter.convert(cameraInstance.getCurrentFrame()));
		}
	}

	/**
	 * Initialize the {@link ToIplImage image converter} and the {@link CameraFramer
	 * camera}.
	 */
	public void init() {
		converter = new ToIplImage();
		cameraInstance.init();
	}

	/**
	 * Destroys all the components.
	 */
	public void destroy() {
		//rectRecognizer.destroy();                         frah ho commentato il rect
		ballRecognizer.destroy();
		cameraInstance.close();
	}

	/**
	 * Creates the panel used to rendering.
	 */
	public void createPanel() {
		frame = new CanvasFrame("JavaCV - Camera recognition", 1);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(false);
		ballRecognizer.setRendering(true);
		renderingEnabled = true;
	}

	/**
	 * Destroys the panel used to rendering
	 */
	public void destroyPanel() {
		ballRecognizer.setRendering(false);
		renderingEnabled = false;
		frame = null;
	}

	/**
	 * @return the frame's internal container.
	 */
	public Container getCameraPane() {
		return frame.getContentPane();
	}

	/**
	 * @return the camera's width and height
	 */
	public Dimension getCameraDimension() {
		return new Dimension(cameraInstance.getCameraWidth(), cameraInstance.getCameraHeight());
	}

}
