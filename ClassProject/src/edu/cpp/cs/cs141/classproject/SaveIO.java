///**
// * 
// */
//package edu.cpp.cs.cs141.classproject;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//import com.sun.webkit.ThemeClient;
//
///**
// * The SaveIO class represents an object which will be able to save and load the game to and from a .dat file. The {@link #load(String)}
// * method will assemble the serialized objects into an array and return it. The {@link #save(String, Entity[])} method, similarly, will
// * take an array and serialize all objects within. Both will be in the same order, so the saved and loaded data will be
// * compatible.
// * 
// * @author Aidan Novobilski
// */
//public class SaveIO {
//	
//	/**
//	 * This field represents the binary input from a file. It is initialized and used during the {@link #load(String)} method.
//	 */
//	private FileInputStream fis;
//	
//	/**
//	 * This field represents the interpretation of the binary input. It is initialized and used during the {@link #load(String)} method.
//	 */
//	private DataInputStream dis;
//	
//	/**
//	 * This field represents the binary output to a file. It is initialized and used during the {@link #save(String, Entity[])} method.
//	 */
//	private FileOutputStream fos;
//	
//	/**
//	 * This field represents the interpretation to the binary output. It is initialized and used during the {@link #save(String, Entity[])} method.
//	 */
//	private DataOutputStream dos;
//
//	/**
//	 * The default constructor. 
//	 */
//	public SaveIO(){
//		
//	}
//	
//	/**
//	 * This method will establish and interpret an input stream from a file. It will load serialized objects from the
//	 * name/location as specified by the file name, create them,
//	 * and place them into an array to be returned to the {@link GameEngine}, and then close the stream. 
//	 * 
//	 * @param fileName The name/location of the file to be loaded.
//	 * @return Returns the array of entities stored within the file.
//	 */
//	public Entity[] load(String fileName){
//		return null;
//	}
//	
//	/**
//	 * This method will establish and interpret an output stream to a file. It will serialize objects in the taken array and
//	 * write them to a binary file with the name/location specified by the file name, and then write them to the binary
//	 * file. Afterwards it will close the stream.
//	 * 
//	 * @param fileName The name/location of the file to be saved.
//	 * @param entities The array of {@link Entity} to be saved.
//	 */
//	public void save(String fileName, Entity[] entities){
//		
//	}
//}
