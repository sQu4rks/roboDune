package eu.phaenovum.physik.mainProgram;
/**
 * Interface that defines the voids to be included from a selectable classfile
 * @author marcel
 *
 */
public interface Script {

	/**
	 * This function will be called on execution. Please return true or false on failure or success
	 */
	public boolean execute();
	
	/**
	 * This function will be called on emergency stop. Do a nice shut down. Ah just kidding, do whatever you want
	 */
	public boolean stopTheEngine();
}
