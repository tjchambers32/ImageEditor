import java.io.*;

public class ImageEditor {

	/*test arguments:
	audio.ppm audio-inverted.ppm invert
	slctemple.ppm slcEmboss.ppm emboss
	slctemple.ppm slcMotion2.ppm motionblur 10
	*/
	
	private static PPMParser image;
	
	public static void main(String[] args)
	{
		String input;
		String output;
		String transform;
		int blur = 0;
		
		input = args[0];
		output = args[1];
		transform = args[2];
		if (transform.equals("motionblur"))
			blur = Integer.parseInt(args[3]);
		
		
		//Read in PPM File and create image
		try 
		{
			image = new PPMParser(input);
		} 
		catch (FileNotFoundException e) {
			System.out.println("You must input a valid file");
			e.printStackTrace();
		}
		
		//Perform the specified transform on the image
		switch(transform){

			case "grayscale":
				image.grayscale();
				break;
			case "invert":
				image.invert();
				break;
			case "emboss":
				image.emboss();
				break;
			case "motionblur":
				image.motionBlur(blur);
				break;
			default:
				break;
		}
		
		//Write the image to an output file
		
		try {
			//File destination = new File(output);
			PrintWriter PW = new PrintWriter(output);
			PW.print(image.toString());
			PW.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}
	
}
